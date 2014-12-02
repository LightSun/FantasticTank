package com.heaven7.fantastictank.matters;

import java.util.ArrayList;

import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.interfaces.CollideHandlePolicy;
import com.heaven7.fantastictank.interfaces.impl.CollideHanldePolicyImpl;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.support.DynamicGameObject;
import com.heaven7.fantastictank.support.GameObject;
import com.heaven7.fantastictank.support.PathRecorder;
import com.heaven7.fantastictank.support.policy.BulletTypePolicy;
import com.heaven7.fantastictank.util.CacheHelper;
/**
 * 必须先设置tank类型,方向,bullet类型,和 自身速度,还有BulletTypePolicy
 * <p>额外的对象</p>
 * <li>碰撞处理策略{@link CollideHandlePolicy}
 * <p>由于tank，staticObject对象的宽高-样，且单位宽高同世界宽高。
 * <br>这样会出现一个坦克穿不过去应该穿过去的通道 .you should be careful!
 </p>
 * @author Administrator
 */
public abstract class Tank extends DynamicGameObject{
	
	//================================================//
	/**坦克类型，正义的，最慢的，快的，坚固的,boss 
	 * <li>  注： Honest 坦克没有情绪，因为是人为控制的 */
	public static enum TankType{
		Honest,Slower,Fast,Solid,King;
	}
	
	//默认碰撞处理策略
	private static final CollideHandlePolicy DEFAULT_COLLIDE_POLICY= new CollideHanldePolicyImpl();
	
	/** 阙值，以保证坦克可以正常穿过该穿的通道 */
	protected static final float THRESHOLD = 0.08f;
	//tank boss可能不一样
	public static final float WIDTH  = Constant.COMMON_WIDTH  - THRESHOLD;
	public static final float HEIGHT = Constant.COMMON_HEIGHT - THRESHOLD;

	/**已经开火的 次数(而且是子弹还没消失)*/
	private  int mfiredCount ; 
	private  int mOpenFireCount; 
	
	private TankType mTankType;
	/** 能发射的子弹类型  */
	private BulletType mBulletType;
	private BulletTypePolicy mPolicy;
	private ArrayList<Bullet> mBullets;
	
	/**  完成复活次数相关操作---可复活次数  */
	private int mReliveCount;
	
	/**是否无敌*/
	private boolean mUnrivaled;
	
	/**默认处理策略*/
	private CollideHandlePolicy mCollidePolicy = DEFAULT_COLLIDE_POLICY;
	
	public Tank(float x, float y,float width,float height) {
		super(x, y, width, height);
		mOpenFireCount = 1;
	}
	
	public Tank(float x, float y) {
		this(x, y, WIDTH, HEIGHT);
	}
	
	public  Bullet shoot(){
		float width  = 0;
		float height = 0;
		boolean needPathRecorder = false;
		
		final BulletType type = mBulletType;
		final BulletTypePolicy policy = this.mPolicy;
		
		switch (type) {
		case Normal:
			width  = Constant.BULLET_WIDTH_NORMAL;
			height = Constant.BULLET_HEIGHT_NORMAL;
			break;
			
		case Penetrable:
			width  = Constant.BULLET_WIDTH_BLUE;
			height = Constant.BULLET_HEIGHT_BLUE;
			break;
			
		case Laddered:
			width  = Constant.BULLET_WIDTH_GREEN;
			height = Constant.BULLET_HEIGHT_GREEN;
			needPathRecorder = true;
			break;
			
		case Rebounding:
			width  = Constant.BULLET_WIDTH_DARK_BLUE;
			height = Constant.BULLET_HEIGHT_DARK_BLUE;
			break;
			
		case DieTogether:
			width  = Constant.BULLET_WIDTH_RED;
			height = Constant.BULLET_HEIGHT_RED;
			break;

		default:
			throw new RuntimeException("unsupport bulletType");
		}
		final Bullet b = CacheHelper.obtain(Bullet.class); /*new Bullet(0, 0) ;*/
		b.setPosition(position);
		b.setBounds(width, height);
		//b.setOwner(this);
		b.setBulletType(type);
		b.setDirection(getDirection());
		b.setVelocity(Constant.BULLET_VELOCITY);
		b.def.type = this.def.type;
		b.def.hitCount = policy.getHitCount();
	   /*
	    * tank and bullet 互相观察死亡事件 
	    */
		this.addPoolEvent(new PoolEvent(b) {
			public void beforePooled(GameObject obj) {
				//Bullet b = (Bullet) getObserver();
			/*	if(b.getOwner()!=null && b.getOwner() == obj){
					b.setOwner(null);
				}*/
				getObserver().removePoolEventsByObserver(obj);
			}
		});
		
		b.addPoolEvent( new PoolEvent(this) {
			public void beforePooled(GameObject obj) {
				Tank t = (Tank) getObserver();
			    t.decreaseFiredCount();
				t.increaseOpenFireCount();
				t.removePoolEventsByObserver(obj);
			}
		});
		
		if(needPathRecorder){
			float x = position.x /*+ bounds.width / 2*/;
			float y = position.y /*+ bounds.height / 2*/;
			
			b.setPathRecorder(new PathRecorder(policy.getDirectionList(getDirection(), 
							Constant.WORLD_HEIGHT - y, y, x, Constant.WORLD_WIDTH-x),
							policy.getFlightLength()));
		}
		increaseFiredCount();
		return b;
	}
	
//============ bean methods ==========//

	public TankType getTankType() {
		return mTankType;
	}
	public void setTankType(TankType tankType) {
		this.mTankType = tankType;
	}
	
	public BulletType getBulletType() {
		return mBulletType;
	}
	public void setBulletType(BulletType bulletType) {
		this.mBulletType = bulletType;
		if(mPolicy!=null)
		   mPolicy.setBulletType(bulletType);
	}
	public ArrayList<Bullet> getBullets() {
		if(mBullets==null)
			mBullets = new ArrayList<Bullet>(2);
		return mBullets;
	}
	public void addBullet(Bullet bullet) {
		if(mBullets==null)
			mBullets = new ArrayList<Bullet>(2);
		this.mBullets.add(bullet);
	}
	
	public BulletTypePolicy getBulletTypePolicy() {
		return mPolicy;
	}
	public void setBulletTypePolicy(BulletTypePolicy policy) {
		this.mPolicy = policy;
	}
	
	public CollideHandlePolicy getCollidePolicy() {
		return mCollidePolicy;
	}
	public void setCollidePolicy(CollideHandlePolicy mCollidePolicy) {
		this.mCollidePolicy = mCollidePolicy;
	}

	//==========================//
	//复活次数相关
	/** 是否可以复活/是否有复活次数*/
	public boolean hasReliveCount() {
		return mReliveCount>0;
	}
	/**复活次数减少1*/
	public void decreaseReliveCount() {
		if(mReliveCount>0)
			mReliveCount -= 1;
	}
	/**复活次数增加1*/
	public void increaseReliveCount() {
		 mReliveCount += 1;
	}
	public int getReliveCount() {
		return mReliveCount;
	}
	public void setReliveCount(int reliveCount) {
		this.mReliveCount = reliveCount;
	}
	
	 /**是否无敌*/
	public boolean isUnrivaled() {
		return mUnrivaled;
	}
	/**设置无敌的状态*/
	public void setUnrivaled(boolean unrivaled) {
		this.mUnrivaled = unrivaled;
	}
	
	//======================================================//
	/** 最大的开火次数 */
	public int maxFireCount(){
		return 2;
	}
	
	public synchronized int getFiredCount() {
		return mfiredCount;
	}
	public synchronized void increaseFiredCount() {
		this.mfiredCount++;
	}
	public synchronized void decreaseFiredCount() {
		this.mfiredCount--;
	}

	public synchronized void increaseOpenFireCount(){
		mOpenFireCount++;
	}
	public synchronized void decreaseOpenFireCount(){
		mOpenFireCount--;
	}
	public synchronized int getOpenFireCount() {
		return mOpenFireCount;
	}
	public synchronized void setOpenFireCount(int mOpenFireCount) {
		this.mOpenFireCount = mOpenFireCount;
	}
	
	@Override
	protected void update0(float deltaTime) {

	}

	@Override
	public void reset() {
		super.reset();
		mTankType = null;
		mBulletType = null;
		mPolicy = null;
		if(mBullets!=null)
			mBullets.clear();
		mCollidePolicy = DEFAULT_COLLIDE_POLICY;
		mReliveCount = 0;
		mUnrivaled = false;
		mfiredCount = 0;
		mOpenFireCount = 1;
		mUnrivaled = false;
	}
	
	//===============================================//
	/** 是否人为控制的 */
	public abstract boolean isManual(); 
	
}
