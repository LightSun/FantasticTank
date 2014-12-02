package com.heaven7.fantastictank;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.heaven7.fantastictank.bean.SkillCount;
import com.heaven7.fantastictank.interfaces.ILevelGenerator;
import com.heaven7.fantastictank.interfaces.WorldStateListener;
import com.heaven7.fantastictank.interfaces.impl.WorldEventListenerAdapter;
import com.heaven7.fantastictank.interfaces.impl.WorldListenerGroup;
import com.heaven7.fantastictank.level.AbsLevel;
import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Explode;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.MyTank;
import com.heaven7.fantastictank.matters.MyTank.CollideChecker;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.matters.Tank.TankType;
import com.heaven7.fantastictank.matters.TankBoss;
import com.heaven7.fantastictank.matters.TankBoss.CommandReceiver;
import com.heaven7.fantastictank.matters.Wood;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.support.BuffController;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.IdleLocationCensor;
import com.heaven7.fantastictank.support.IdleLocationCensor.Procurator;
import com.heaven7.fantastictank.support.policy.BulletTypePolicy;
import com.heaven7.fantastictank.support.policy.BulletTypePolicyImpl;
import com.heaven7.fantastictank.support.policy.TankTypePolicy;
import com.heaven7.fantastictank.support.policy.TankTypePolicyImpl;
import com.heaven7.fantastictank.util.ArrayUtil;
import com.heaven7.fantastictank.util.CacheHelper;
import com.heaven7.fantastictank.util.ClipFactory;
import com.heaven7.fantastictank.util.Judgement;
import com.heaven7.fantastictank.util.TankFactory;

public class GameWorld implements CollideChecker,Procurator,CommandReceiver{
	
	 private final WorldListenerGroup mListener;
	 private final Judgement mJudgement;     //裁判
	 private final IdleLocationCensor mIdleCensor;
	 private WorldStateListener mStateListener;
	 
	 private ILevelGenerator mLevelGenerator;
	 private BuffController mBuffController;
	 
	 public List<AutoTank>  mAutoTanks;
	 public List<Lake>      mLakes;
	 public List<Wood>      mWoods;
	 public List<DirtWall>  mDirtWalls;
	 public List<BrickWall> mBrickWalls;
	 public List<SmallBrickWall> mSmallBrickWalls;
	 
	 public List<MyTank>    mMytanks;
	 public List<Bullet>    mBullets;
	 public List<Bullet>    mGoodBullets;
	 public List<Explode>   mExplodes;
	 
	 public Buff mBuff;

	 public GameWorld(WorldListenerGroup listener){
		 this(null, listener);
	 }
	 public GameWorld(ILevelGenerator generator,WorldListenerGroup listener) {
		super();
		this.mListener = listener;
		this.mLevelGenerator = generator;
		this.mListener.add(mLastListener);
		mJudgement = new Judgement(mListener);
		
		mBuffController = BuffController.get().begin();
		mIdleCensor = new IdleLocationCensor(this); 
		
		if(mLevelGenerator!=null)
		    createWorld(false);
	}

	private void createWorld(boolean gameover) {
		mLevelGenerator.build();
		mAutoTanks = mLevelGenerator.getAutotanks();
		mLakes = mLevelGenerator.getLakes();
		mWoods = mLevelGenerator.getWoods();
		mDirtWalls = mLevelGenerator.getDirtWalls();
		mBrickWalls = mLevelGenerator.getBrickWalls();
		mSmallBrickWalls = mLevelGenerator.getSmallBrickWalls();
		
		mBullets = new CopyOnWriteArrayList<Bullet>();
		mGoodBullets = new CopyOnWriteArrayList<Bullet>();
		mExplodes = new CopyOnWriteArrayList<Explode>();
		mMytanks = new CopyOnWriteArrayList<MyTank>();
	
		if(!gameover){
		     createMyTank1();
		     mBuffController.increaseLeve();
		}
	}
	
	/**创建新的MyTank,并加入到list中 
	 * @param recoverCount */
	private void createMyTank1() {
		TankTypePolicy tankTypePolicy = new TankTypePolicyImpl(TankType.Honest);
		BulletTypePolicy bulletPolicy = new BulletTypePolicyImpl(BulletType.DieTogether);
		MyTank myTank = TankFactory.createMyTank(AbsLevel.MANUAL_RETAIN_POS[0].x,
				AbsLevel.MANUAL_RETAIN_POS[0].y, Direction.Up, tankTypePolicy, bulletPolicy);
		myTank.setCollideChecker(this);
		myTank.setReliveCount(SkillCount.get1().relive);
		mMytanks.add(myTank);
	}

    public void update(float delta){
    	//checkBuff
    	if(mBuff!=null) mBuff.update();
    	mBuffController.update(delta);
    	if(mBuffController.isReachedInterval()){
    		mBuff = mBuffController.generateRandomBuff();
    		Resource.show_bonus.play(1);
    	}
    	updateMytanks(delta);
    	updateTanks(delta);
    	updateBullets(delta);
    	checkCollisions();
    	clip();
    	updateExplodes(delta);
    	checkHotPositions();
    	checkGameOver();
    }

    private void updateMytanks(float delta) {
    	int size = mMytanks.size();
    	for(int i=0;i<size ;i++){
    		mMytanks.get(i).updateFake(delta);
    	}
	}
	private void updateExplodes(float deltaTime) {
    	Explode explode = null;
		int size = mExplodes.size();
		for(int i=0;i<size ;i++){
			explode = mExplodes.get(i);
			explode.update(deltaTime);
			if(explode.isFinish()){
				this.mExplodes.remove(explode);
				size --;
				i -- ;
				CacheHelper.freeSafe(explode);
			}
		}
	}
	/** 检查热点位置(eg：生产自动化坦克和人工坦克的位置)*/
    private void checkHotPositions() {
    	//TODO 
    	mIdleCensor.checkRetainAutoPosition(ArrayUtil.merge(mAutoTanks, mMytanks));
	}
	//裁剪--静态对象被攻击后，是一点一点变小的(前提是能被该种子弹攻击)
	private void clip() {
		int size = mDirtWalls.size();
		for(int i=0 ; i<size ; i++ ){
			DirtWall dirtWall = mDirtWalls.get(i);
			if(!ClipFactory.clip(dirtWall)){
				if(!dirtWall.def.alive){
					mDirtWalls.remove(dirtWall);
					i--;
					size --;
					CacheHelper.freeSafe(dirtWall);
				}
			}
		}
		
		size = mBrickWalls.size();
		for(int i=0 ; i<size ;i++){
			BrickWall wall = mBrickWalls.get(i);
			if(!ClipFactory.clip(wall)){
				if(!wall.def.alive){
					mBrickWalls.remove(wall);
					size = mBrickWalls.size();
					CacheHelper.freeSafe(wall);
				}
			}
		}
		
		size = mWoods.size();
		for(int i=0 ; i<size ;i++){
			Wood wood = mWoods.get(i);
			if(!ClipFactory.clip(wood)){
				if(!wood.def.alive){
					mWoods.remove(wood);
					size = mWoods.size();
					CacheHelper.freeSafe(wood);
				}
			}
		}
	}

	private void checkGameOver() {
		// TODO Auto-generated method stub
		
	}

	private void checkCollisions() {
		mJudgement.handleBulletCollisions(mBullets, mMytanks);
		mJudgement.handleBulletCollisions(mGoodBullets, mAutoTanks);
		mJudgement.handleBulletsCollisions(mGoodBullets, mBullets);
		
		mJudgement.handleStaticCollisions(mBullets, mDirtWalls);
		mJudgement.handleStaticCollisions(mBullets, mBrickWalls);
		mJudgement.handleStaticCollisions(mBullets, mSmallBrickWalls);
		mJudgement.handleStaticCollisions(mBullets, mWoods);
		
		mJudgement.handleStaticCollisions(mGoodBullets, mDirtWalls);
		mJudgement.handleStaticCollisions(mGoodBullets, mBrickWalls);
		mJudgement.handleStaticCollisions(mGoodBullets, mWoods);
		mJudgement.handleStaticCollisions(mGoodBullets, mSmallBrickWalls);
		//mJudgement.handleStaticCollisions(mBullets, mLakes); //湖不能被攻击
		
		mJudgement.handleTankCollisions(mAutoTanks, mDirtWalls);
		mJudgement.handleTankCollisions(mAutoTanks, mBrickWalls);
		mJudgement.handleTankCollisions(mAutoTanks, mLakes);
		mJudgement.handleTankCollisions(mAutoTanks, mSmallBrickWalls);
		
		//mJudgement.handleTankCollisions(mAutoTanks, mWoods);  //树林允许穿过
		
		mJudgement.checkBuffTankCollision(mBuff,mMytanks,mAutoTanks);
		mJudgement.checkBuffTankCollision(mBuff,mAutoTanks,mMytanks);
		
		//TODO 坦克之间的碰撞
		mJudgement.handleTanksCollide(mAutoTanks, mMytanks);
		mJudgement.handleTanksCollide(mMytanks, mMytanks);
		mJudgement.handleTanksCollide(mAutoTanks, mAutoTanks);
	}

	private void updateBullets(float delta) {
		int size = mBullets.size();
		for(int i=0;i<size ;i++){
			Bullet b = mBullets.get(i);
			b.update(delta);
			if(!b.def.alive){
				mBullets.remove(b);
				size --;
				i-- ;
				Judgement.resotreOpenFireCount(b,"mBullets.update()");
			}
		}
		size = mGoodBullets.size();
		for(int i=0;i<size ;i++){
			Bullet b = mGoodBullets.get(i);
			b.update(delta);
			if(!b.def.alive){
				mGoodBullets.remove(b);
				size --;
				i--;
				Judgement.resotreOpenFireCount(b,"mGoodBullets.update()");
			}
		}
	}

	private void updateTanks(float delta) {
		int size = mAutoTanks.size(); 
		for(int i=0;i<size ;i++){
			AutoTank t = mAutoTanks.get(i);
			if(mMytanks.size()>0){
				MyTank mt = mMytanks.get(0);
				t.reportForman(mt.position.x, mt.position.y,mt.getDirection(),true);
			}else
				t.reportForman(0, 0,null,false);
			
			t.update(delta);
			mBullets.addAll(t.getBullets());
			t.getBullets().clear();
			if(!t.def.alive){
				mAutoTanks.remove(t);
				size --;
				i--;
				CacheHelper.freeSafe(t);
			}
		}
		//正方坦克不用更新(cause基于事件去触发的)
	}
	
    // =============  like bean methods ===========//
	public ILevelGenerator getLevelGenerator() {
		return mLevelGenerator;
	}
	public void setLevelGenerator(ILevelGenerator generator) {
		this.mLevelGenerator = generator;
	}
	public void setStateListener(WorldStateListener stateListener) {
		this.mStateListener = stateListener;
	}
	
	public void reset(boolean gameover){
		if(mLevelGenerator!=null){ 
			if(mAutoTanks != null){
				CacheHelper.freeAll(AutoTank.class, mAutoTanks);
				CacheHelper.freeAll(BrickWall.class, mBrickWalls);
				CacheHelper.freeAll(DirtWall.class, mDirtWalls);
				CacheHelper.freeAll(Wood.class, mWoods);
				CacheHelper.freeAll(Lake.class, mLakes);
				CacheHelper.freeAll(Bullet.class, mBullets);
				CacheHelper.freeAll(Bullet.class, mGoodBullets);
				CacheHelper.freeAll(Explode.class, mExplodes);
				CacheHelper.freeAll(SmallBrickWall.class, mSmallBrickWalls);
				mBuff = null;
				this.mBuffController.begin();
			}
			createWorld(gameover);
		}
	}
	//=====================================================================
	
	//主要监听Buff和爆炸
	private final WorldEventListenerAdapter mLastListener = new WorldEventListenerAdapter() {
		public void onTankDied(float x, float y,Tank t) {
			mExplodes.add(CacheHelper.obtain(x, y, Explode.class));
			if(t instanceof MyTank){
				if(t.hasReliveCount()){
					t.decreaseReliveCount();
					SkillCount.get1().relive --;
					createMyTank1();
				}else
					if(mStateListener!=null)  mStateListener.onGameOver();
			}else if(t instanceof MyTank){
				SkillCount.get1().killedCommon +=1;
				if(SkillCount.get1().killedCommon == Constant.KILLED_LEVEL_END){
					if(mStateListener!=null)  mStateListener.onLevelEnd();
				}
			}
		}
		public void onCatchBuff(Tank t, Buff buff) {
			if(buff.getId()==Buff.ID_ADD_LIFE && mMytanks.size()>0 && mMytanks.get(0).equals(t)){
				SkillCount.get1().relive += 1;
			}
			mBuff = null; //buff消失
		}
		public void onHitDirtyWall(float x, float y) {
			mExplodes.add(CacheHelper.obtain(x, y, Explode.class));
		}
		public void onHitBrickWall(float x, float y) {
			mExplodes.add(CacheHelper.obtain(x, y, Explode.class));
		}
		public void onHitSmallBrickWall(float x, float y) {
			mExplodes.add(CacheHelper.obtain(x, y, Explode.class));
		}
	};

	@Override //事件是在主线程，update是在子线程。所以最好在移动时就处理。免得bug
	public void handleCollideStaticObject(MyTank t) {
		mJudgement.handleCollideStaticObject(t,mDirtWalls);
		mJudgement.handleCollideStaticObject(t,mBrickWalls);
		mJudgement.handleCollideStaticObject(t,mLakes);
		mJudgement.handleCollideStaticObject(t,mSmallBrickWalls);
		mJudgement.handleTanksCollide(mAutoTanks,t);
		mJudgement.handleTanksCollide(mMytanks,t);
	}
	@Override
	public void onIdle(float x, float y) {
		if(mLevelGenerator.allowGenerateAutoTank())
		      mAutoTanks.add(mLevelGenerator.createAutoTank(x, y));
	}
	@Override
	public void restoreChildrenLife(TankBoss boss) {
		int size = mAutoTanks.size(); 
		for(int i=0;i<size ;i++){
			mAutoTanks.get(i).restoreLife();
		}
	}
}
