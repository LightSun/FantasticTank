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
 * ����������tank����,����,bullet����,�� �����ٶ�,����BulletTypePolicy
 * <p>����Ķ���</p>
 * <li>��ײ�������{@link CollideHandlePolicy}
 * <p>����tank��staticObject����Ŀ��-�����ҵ�λ���ͬ�����ߡ�
 * <br>���������һ��̹�˴�����ȥӦ�ô���ȥ��ͨ�� .you should be careful!
 </p>
 * @author Administrator
 */
public abstract class Tank extends DynamicGameObject{
	
	//================================================//
	/**̹�����ͣ�����ģ������ģ���ģ���̵�,boss 
	 * <li>  ע�� Honest ̹��û����������Ϊ����Ϊ���Ƶ� */
	public static enum TankType{
		Honest,Slower,Fast,Solid,King;
	}
	
	//Ĭ����ײ�������
	private static final CollideHandlePolicy DEFAULT_COLLIDE_POLICY= new CollideHanldePolicyImpl();
	
	/** ��ֵ���Ա�֤̹�˿������������ô���ͨ�� */
	protected static final float THRESHOLD = 0.08f;
	//tank boss���ܲ�һ��
	public static final float WIDTH  = Constant.COMMON_WIDTH  - THRESHOLD;
	public static final float HEIGHT = Constant.COMMON_HEIGHT - THRESHOLD;

	/**�Ѿ������ ����(�������ӵ���û��ʧ)*/
	private  int mfiredCount ; 
	private  int mOpenFireCount; 
	
	private TankType mTankType;
	/** �ܷ�����ӵ�����  */
	private BulletType mBulletType;
	private BulletTypePolicy mPolicy;
	private ArrayList<Bullet> mBullets;
	
	/**  ��ɸ��������ز���---�ɸ������  */
	private int mReliveCount;
	
	/**�Ƿ��޵�*/
	private boolean mUnrivaled;
	
	/**Ĭ�ϴ������*/
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
	    * tank and bullet ����۲������¼� 
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
	//����������
	/** �Ƿ���Ը���/�Ƿ��и������*/
	public boolean hasReliveCount() {
		return mReliveCount>0;
	}
	/**�����������1*/
	public void decreaseReliveCount() {
		if(mReliveCount>0)
			mReliveCount -= 1;
	}
	/**�����������1*/
	public void increaseReliveCount() {
		 mReliveCount += 1;
	}
	public int getReliveCount() {
		return mReliveCount;
	}
	public void setReliveCount(int reliveCount) {
		this.mReliveCount = reliveCount;
	}
	
	 /**�Ƿ��޵�*/
	public boolean isUnrivaled() {
		return mUnrivaled;
	}
	/**�����޵е�״̬*/
	public void setUnrivaled(boolean unrivaled) {
		this.mUnrivaled = unrivaled;
	}
	
	//======================================================//
	/** ���Ŀ������ */
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
	/** �Ƿ���Ϊ���Ƶ� */
	public abstract boolean isManual(); 
	
}
