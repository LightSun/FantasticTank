package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.mood.Mood;
import com.heaven7.fantastictank.mood.Mooder;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.wisdom.WisdomManager;

/**
 * 自动化坦克,自动射击
 * @author Administrator
 */
public class AutoTank extends Tank implements Mooder{
	
	protected int step = 20 + WisdomManager.randomInt(20)
			+ WisdomManager.randomInt(70);
	protected static final int DEFAULT_SHOOT_RATE = 2;
	private static final int MIN_RANGE = 1;
	private int mMaxRange;                 //不包含最大值, 射击的范围以确定是否射击
	
	private Mood mMood;                     /* 情绪*/
	protected boolean mShootAtOnce;         /* 立即射击(前提是同时存在的子弹个数在限定范围内)*/
	protected boolean mTrackForman;         /* 跟踪敌人*/

	private boolean mCollideDieTogether;     /* 碰撞一起死亡 */
	protected float mCareDistance;          /* 关心的距离，逃跑用 */
	protected boolean mEscape;              /* mCareDistance到达时，是否逃跑*/
	
	protected Vector2 mFoemanPositon = new Vector2();                 /* 敌人的位置 */
	protected Direction mFoemanDir;                                   //敌人当前的方向
	protected boolean mKnowFoeman;                                    //知道敌人位置了
	
	public AutoTank(float x, float y,float width,float height) {
		super(x, y, width, height);
	}
	
	public AutoTank(float x, float y) {
		super(x, y);
		mMaxRange = MIN_RANGE + DEFAULT_SHOOT_RATE;
		//TODO just for test
		mMood = Mood.Normal;
	}
	
	/** 恢复生命 */
    public void restoreLife() {
		this.def.beHitCount = 4;
	}
	
	/**报告敌人的坐标和方向(right 是否正确)*/
	public void reportForman(float x,float y, Direction dir,boolean right){
		this.mFoemanPositon.set(x, y);
		this.mFoemanDir = dir;
		mKnowFoeman = right;
	}
	
	@Override
	public void update(float deltaTime) {
		// 检查定身
		if (isPaused()) return;
		if(mMood!=null) mMood.update(this);
		
		setLastX(position.x);
		setLastY(position.y);

		//跟踪敌人
		if(mTrackForman){
			step--;
			if (step == 0){ 
				switchDirection(getRelativeDirection());
				step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
			}
		}		
		//是否逃跑；即远离敌人
		else if(mEscape && mKnowFoeman && this.position.dst(mFoemanPositon) <=mCareDistance ){
			//检查距离是否在逃跑的距离内
		    step--;
		    if (step == 0){ 
		    	switchDirection(getRelativeDirection().reverse());
				step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
		    }
		}else{
			step--;
			if (step == 0) {
				randowDirection(true);
				step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
			}
		}

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		setBoundByPosition();
		
		if(mShootAtOnce){
			if (getOpenFireCount() > 0) {
				decreaseOpenFireCount();
				addBullet(shoot());
			}
		}else if (isInRange(WisdomManager.randomInt(100))) { // 只有 == 概率最小
			// System.out.println("[ ranom100>80 ]"+hashCode()+": openFireCount ="+count+", firedCount = "+firedCount);
			if (getOpenFireCount() > 0) {
				// System.err.println("will shoot: "+hashCode());
				decreaseOpenFireCount();
				addBullet(shoot());
			}
		}

		doWithHideIfNeed(deltaTime);
		processEdge();
	}
	/**  val >=MIN_RANGE && val < mMaxRange */
	protected boolean isInRange(int val){
		return val >=MIN_RANGE && val < mMaxRange;
	}
	
	protected  Direction  getRelativeDirection(){
		float dx = mFoemanPositon.x - position.x;
		float dy = mFoemanPositon.y - position.y;
		Direction relativeDir = null; //相对当前对象的方向
		if(dx < 0){
			if(dy < 0)
			    relativeDir = Math.abs(dx) > Math.abs(dy) ? Direction.Left :Direction.Down;
			else
			    relativeDir = Math.abs(dx) > Math.abs(dy) ? Direction.Left :Direction.Up;
		}else{
			if(dy < 0)
			    relativeDir = Math.abs(dx) > Math.abs(dy) ? Direction.Right :Direction.Down;
			else
			    relativeDir = Math.abs(dx) > Math.abs(dy) ? Direction.Right :Direction.Up;
		}
		return relativeDir;
	}

	@Override
	public void reset() {
		super.reset();
		step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
		mMood = Mood.Normal;
		mMaxRange = MIN_RANGE + DEFAULT_SHOOT_RATE;
		mCollideDieTogether = false;
		mEscape = false;
		mFoemanDir = null;
		mKnowFoeman = false;
		mShootAtOnce = false;
		mTrackForman = false;
	}

	@Override
	public boolean isManual() {
		return false;
	}
	
	//========like bean methods ===========//


	// =========== order used for mood (情绪指令调用)=======//
	@Override
	public void setShootAtOnce(boolean b) {
        mShootAtOnce = b;
	}

	@Override
	public void addShootProbability(int n) {
          mMaxRange +=n;
          if(n>100) n = 100 ;
          else if(n<2) n = 2;
	}

	@Override
	public void setTrackFoeman(boolean track) {
         mTrackForman = track;
	}

	@Override
	public boolean isCollideDieTogether(){
		return mCollideDieTogether;
	}
	@Override
	public void setCollideDieTogether(boolean together) {
         this.mCollideDieTogether = together;
	}

	@Override
	public void setEscapeByDistance(float distance, boolean escape) {
         mCareDistance = distance;
         mEscape = escape;
	}

	@Override
	public void setMood(Mood mood) {
		this.mMood = mood;
	}

	@Override
	public Mood getMood() {
		return mMood;
	}

	@Override
	public byte getType() {
		return Mooder.TYPE_NORMAL;
	}

}
