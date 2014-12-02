package com.heaven7.fantastictank.level;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.interfaces.ILevelGenerator;
import com.heaven7.fantastictank.interfaces.ITankManagePolicy;
import com.heaven7.fantastictank.interfaces.impl.generate.DefaultTankManagePolicy;
import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Tank.TankType;
import com.heaven7.fantastictank.matters.Wood;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.policy.BulletTypePolicyImpl;
import com.heaven7.fantastictank.support.policy.TankTypePolicyImpl;
import com.heaven7.fantastictank.util.ArrayUtil;
import com.heaven7.fantastictank.util.CacheHelper;
import com.heaven7.fantastictank.wisdom.WisdomManager;

public abstract class AbsLevel implements ILevelGenerator {
	
	protected final List<AutoTank>  mAutoTanks     = new CopyOnWriteArrayList<AutoTank>();
	protected final List<Lake>      mLakes         = new CopyOnWriteArrayList<Lake>();
	protected final List<Wood>      mWoods         = new CopyOnWriteArrayList<Wood>();
	protected final List<DirtWall>  mDirtWalls     = new CopyOnWriteArrayList<DirtWall>();
	protected final List<BrickWall> mBrickWalls    = new CopyOnWriteArrayList<BrickWall>();
	protected final List<SmallBrickWall> mSmallBrickWalls    = new CopyOnWriteArrayList<SmallBrickWall>();
	
	private ITankManagePolicy mProcessor = new DefaultTankManagePolicy();
	
	/** �̶��ģ������޸� */
	public static final int[] Xarr = new int[Constant.WORLD_WIDTH];
	/** �̶��ģ������޸� */
	public static final int[] Yarr = new int[Constant.WORLD_HEIGHT];
	
	/** ��common�Զ���̹��Ԥ����λ�� */
	public static final Vector2[] AUTO_RETAIN_POS ={
		new Vector2(getMinX(), getMaxY()),
		new Vector2(getMinX(), (getMaxY() + getMinY())/2 + getMinY()),
		new Vector2((getMaxX() + getMinX())/2 +getMinX(), getMaxY()),
		//ȡ���м������λ��
		//new Vector2((getMaxX() + getMinX())/2 +getMinX(), (getMaxY() + getMinY())/2 +getMinY()),
		new Vector2(getMaxX(), getMaxY()),
		new Vector2(getMaxX(), (getMaxY() + getMinY())/2 + getMinY()),
	};
	/** ��Ϊ���Ƶ�Ԥ��λ�� */
	public static final Vector2[] MANUAL_RETAIN_POS = {
		new Vector2( (getMinX()+getMaxX())/4 + getMinX(),     getMinY()),
		new Vector2( (getMinX()+getMaxX())/4 + getMinX()+getUnitWidth(),     getMinY()),
		new Vector2( (getMinX()+getMaxX())/4 + getMinX()-getUnitWidth(),     getMinY()),
		
		new Vector2( (getMinX()+getMaxX())/4 * 3 + getMinX()    , getMinY()),
		new Vector2( (getMinX()+getMaxX())/4 * 3 + getMinX()+getUnitWidth() , getMinY()),
		new Vector2( (getMinX()+getMaxX())/4 * 3 + getMinX()-getUnitWidth() , getMinY()),
	};
	
	
	static{
		for(int i=0 ; i<Constant.WORLD_WIDTH  ; i++){
			Xarr[i] = i;
		}
		for(int i=0 ; i<Constant.WORLD_HEIGHT ; i++){
			Yarr[i] = i;
		}
	}

	public static final float getMinX(){
		return Constant.COMMON_WIDTH /2 ;
	}
	public static final float getMinY(){
		return Constant.COMMON_HEIGHT /2 ;
	}
	
	public static final  float getMaxX(){
		return Constant.WORLD_WIDTH - Constant.COMMON_WIDTH /2;
	}
	public static final float getMaxY(){
		return Constant.WORLD_HEIGHT - Constant.COMMON_HEIGHT /2;
	}
	/** ��Ԫ����*/
	public static final float getUnitWidth(){
		return Constant.COMMON_WIDTH ;
	}
	/**��Ԫ��߶�*/
	public static final float getUnitHeight(){
		return Constant.COMMON_HEIGHT ;
	}
	
	protected static final WisdomManager getWisdomManager(){
		return WisdomManager.get();
	}
	
	@Override
	public void clearAll(){
		mAutoTanks.clear();
		mBrickWalls.clear();
		mDirtWalls.clear();
		mLakes.clear();
		mWoods.clear();
		mSmallBrickWalls.clear();
	}
	@Override
	public boolean isRetainPosition(float x, float y){
		//��һ����,�м����У��������---��Ҫ����2��(��һ�����м��)
		for(int len = AUTO_RETAIN_POS.length, i=len-1 ; i>=0 ;i-- ){
			if(AUTO_RETAIN_POS[i].x == x && AUTO_RETAIN_POS[i].y ==y){
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isRetainPositionForManual(float x, float y){
	
		for(int len = MANUAL_RETAIN_POS.length, i=len-1 ; i>=0 ;i-- ){
			if(MANUAL_RETAIN_POS[i].x == x && MANUAL_RETAIN_POS[i].y ==y){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public List<AutoTank> getAutotanks() {
		return this.mAutoTanks;
	}

	@Override
	public List<Lake> getLakes() {
		return this.mLakes;
	}

	@Override
	public List<Wood> getWoods() {
		return this.mWoods;
	}

	@Override
	public List<DirtWall> getDirtWalls() {
		return this.mDirtWalls;
	}

	@Override
	public List<BrickWall> getBrickWalls() {
		return this.mBrickWalls;
	}
	
	@Override
	public List<SmallBrickWall> getSmallBrickWalls() {
		return this.mSmallBrickWalls;
	}
	
	@Override
	public AutoTank createAutoTank(float x, float y) {
		return obtainRandomAutoTank(x, y);
	}
	
	@Override
	public void setTankManagePolicy(ITankManagePolicy processor){
		if(processor==null)  throw new IllegalArgumentException("IdleProcessor cannot be null!");
		this.mProcessor = processor;
	}
	
	@Override
	public ITankManagePolicy getTankManagePolicy(){
		return mProcessor;
	}
	
	/** ���Ƿ������ʱ�ڵ�ǰ�ؿ���create �µ� AutoTank  */
	@Override
	public boolean allowGenerateAutoTank(){
		int size = mAutoTanks.size();
		return getTankManagePolicy().allowGenerateAutoTank(size);
	}
	
	/** ����������Ե�autotank*/
	protected AutoTank obtainRandomAutoTank(float x, float y) {
		TankType tantType = WisdomManager.random(ArrayUtil.asArray(
				TankType.Slower, TankType.Fast, TankType.Solid));
		TankTypePolicyImpl tankTypePolicy= new TankTypePolicyImpl(tantType);
		BulletType bulletType = WisdomManager.random(BulletType.values());
		/* this is a debug .may need change!
		 * TankType tantType = TankType.Fast;
		 *  BulletType bulletType =BulletType.Laddered;
		 */
		AutoTank tank = CacheHelper.obtain(x,y,AutoTank.class);
		tank.setDirection(Direction.Down);
		tank.setTankType(tantType);
		tank.setBulletType(bulletType);
		tank.setBulletTypePolicy(new BulletTypePolicyImpl(bulletType));
		tank.setVelocity(tankTypePolicy.getBaseSpeed());
		
		/*return TankFactory.createAutoTank(x, y, Direction.Down,
				new TankTypePolicyImpl(tantType), new BulletTypePolicyImpl(
						bulletType));*/
		//System.err.println("tank count: " + tank.getOpenFireCount());
		//System.err.println("tank firedcount: " + tank.getFiredCount());
		return tank;
	}

}
