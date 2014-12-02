package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.mood.Mood;
import com.heaven7.fantastictank.mood.Mooder;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.wisdom.WisdomManager;

/**
 * �Զ���̹��,�Զ����
 * @author Administrator
 */
public class AutoTank extends Tank implements Mooder{
	
	protected int step = 20 + WisdomManager.randomInt(20)
			+ WisdomManager.randomInt(70);
	protected static final int DEFAULT_SHOOT_RATE = 2;
	private static final int MIN_RANGE = 1;
	private int mMaxRange;                 //���������ֵ, ����ķ�Χ��ȷ���Ƿ����
	
	private Mood mMood;                     /* ����*/
	protected boolean mShootAtOnce;         /* �������(ǰ����ͬʱ���ڵ��ӵ��������޶���Χ��)*/
	protected boolean mTrackForman;         /* ���ٵ���*/

	private boolean mCollideDieTogether;     /* ��ײһ������ */
	protected float mCareDistance;          /* ���ĵľ��룬������ */
	protected boolean mEscape;              /* mCareDistance����ʱ���Ƿ�����*/
	
	protected Vector2 mFoemanPositon = new Vector2();                 /* ���˵�λ�� */
	protected Direction mFoemanDir;                                   //���˵�ǰ�ķ���
	protected boolean mKnowFoeman;                                    //֪������λ����
	
	public AutoTank(float x, float y,float width,float height) {
		super(x, y, width, height);
	}
	
	public AutoTank(float x, float y) {
		super(x, y);
		mMaxRange = MIN_RANGE + DEFAULT_SHOOT_RATE;
		//TODO just for test
		mMood = Mood.Normal;
	}
	
	/** �ָ����� */
    public void restoreLife() {
		this.def.beHitCount = 4;
	}
	
	/**������˵�����ͷ���(right �Ƿ���ȷ)*/
	public void reportForman(float x,float y, Direction dir,boolean right){
		this.mFoemanPositon.set(x, y);
		this.mFoemanDir = dir;
		mKnowFoeman = right;
	}
	
	@Override
	public void update(float deltaTime) {
		// ��鶨��
		if (isPaused()) return;
		if(mMood!=null) mMood.update(this);
		
		setLastX(position.x);
		setLastY(position.y);

		//���ٵ���
		if(mTrackForman){
			step--;
			if (step == 0){ 
				switchDirection(getRelativeDirection());
				step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
			}
		}		
		//�Ƿ����ܣ���Զ�����
		else if(mEscape && mKnowFoeman && this.position.dst(mFoemanPositon) <=mCareDistance ){
			//�������Ƿ������ܵľ�����
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
		}else if (isInRange(WisdomManager.randomInt(100))) { // ֻ�� == ������С
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
		Direction relativeDir = null; //��Ե�ǰ����ķ���
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


	// =========== order used for mood (����ָ�����)=======//
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
