package com.heaven7.fantastictank.support;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.wisdom.WisdomManager;
/**
 * velocity�ٶ�----���ٶ�accel
 * @author Administrator
 */
public abstract class DynamicGameObject extends GameObject implements Updateable{
	public final Vector2 velocity;
	public final Vector2 accel;
	
	private Direction mDir;
	private float mLastX, mLastY;
	
	private HideTool mHideTool;
	/**�Ƿ���ͣ--��ʧȥ�ж�������(�ӵ���������)*/
	private boolean mPaused;
	
	public DynamicGameObject (float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}
	
	
	//======== like bean methods ===========//
	
	public Direction getDirection() {
		return mDir;
	}
	public void setDirection(Direction dir) {
		this.mDir = dir;
	}

	public void setLastX(float mLastX) {
		this.mLastX = mLastX;
	}
	public void setLastY(float mLastY) {
		this.mLastY = mLastY;
	}
	public float getLastX() {
		return mLastX;
	}
	public float getLastY() {
		return mLastY;
	}
	
	public boolean isPaused() {
		return mPaused;
	}
	public void setPaused(boolean paused) {
		this.mPaused = paused;
	}

	//====== ������� ===========
	/** ����Ĭ��ֻ׼1��(true��ʾ����ɹ�) 
	 * @see HideTool#hide(int, float)*/
	public boolean hide(int flag,float value){
		if(mHideTool==null)
			mHideTool=new HideTool(1); 
		return mHideTool.hide(flag, value);
	}
	
	/** �Ƿ�������״̬������ǣ�����Ⱦ--ͬʱ��������ײ�¼� */
	public boolean isHide(){
		return mHideTool!=null && mHideTool.isHide();
	}
	public HideTool getHideTool(){
		return mHideTool;
	}
	public void setHideTool(HideTool tool) {
		this.mHideTool = null;
	}
	//=====================================
	
	/**  ͣ�� /����ԭ��  */
	public void stay(){
		this.position.set(mLastX, mLastY);
		//position����Ҳ������Գ���bug(�������Boundsʱ��position��Ӧ�ñ�)
		//this.bounds.setPosition(position.sub(bounds.width/2, bounds.height/2)); 
		setBoundByPosition();
	}

	/** ����@position ����@bounds�����½�x,y */
	protected void setBoundByPosition() {
		bounds.setPosition(position.x - bounds.width/2, position.y-bounds.height/2);
	}
	
	/**  ��Ե����  ,called by {@link #update(float)} **/
	protected void processEdge() {
		//����������Ե
		if(position.x < bounds.width / 2){
			position.x = bounds.width/2;
			setBoundByPosition();
			if(!processEdge0())
			    randowDirection(true);
		}
		//���������ұ߽�
		if(position.x > Constant.WORLD_WIDTH - bounds.width/2){
			position.x = Constant.WORLD_WIDTH - bounds.width/2;
			setBoundByPosition();
			if(!processEdge0())
				randowDirection(true);
		}
		//�����ױ�
		if(position.y < bounds.height / 2){
			position.y = bounds.height /2;
			setBoundByPosition();
			if(!processEdge0())
				randowDirection(true);
		}
		//��������
		if(position.y > Constant.WORLD_HEIGHT - bounds.height/2){
			position.y =  Constant.WORLD_HEIGHT - bounds.height/2;
			setBoundByPosition();
			if(!processEdge0())
				randowDirection(true);
		}
	}
	
	/**���ݵ�ǰ�������ó�ָ�����ٶ�*/
	public void setVelocity(float speed) {
		switch (getDirection()) {
		case Up:
			this.velocity.set(0, speed);
			break;
			
		case Down:
			this.velocity.set(0, -speed);
			break;
			
		case Left:
			this.velocity.set(-speed, 0);
			break;
			
		case Right:
			this.velocity.set(speed, 0);
			break;

		default:
			throw new RuntimeException("unsupport direction");
		}
	}
	
	/**�л���ָ���ķ���(�ٶ�ֵ���䣬�ٶȷ�����浱ǰ�ķ���)*/
	public void switchDirection(Direction dir){
		if(getDirection() == dir) return;//�����֮ǰ��ͬ
		setDirection(dir);
		float value = Math.abs(velocity.x== 0 ? velocity.y : velocity.x);
		setVelocity(value);
	}

	/** ����л������ٶ�ֵ���䣬������浱ǰ���� */
	public void randowDirection(boolean mayEqualPre) {
	    Direction dir = WisdomManager.randomDirection(getDirection(), mayEqualPre);
		switchDirection(dir);
	}
	//===== abstract methods ========//
	
	/**�����ȼ���Ƿ񱻶��� {@link #isPaused()}*/
	@Override
	public void update(float deltaTime){
		if(!isPaused()){
			this.mLastX = position.x;
			this.mLastY = position.y;
			if(velocity.x==0 && velocity.y==0)
				System.out.println("[ Warn ] name = "+def.name+"�Ķ���, velocity.x==0 && velocity.y==0");
			
			position.add(velocity.x*deltaTime, velocity.y*deltaTime);
			setBoundByPosition();
			
			doWithHideIfNeed(deltaTime);
			
			update0(deltaTime);
			processEdge();
		}
	}

    /** �������������Ҫ */
	protected void doWithHideIfNeed(float deltaTime) {
		HideTool tool = getHideTool();
		if(tool!=null){
			if(!tool.isHide()) return; //����������.�򲻴�������
			
			if(tool.getFlag() == HideTool.FLAG_TIME)
				tool.reduceValue(deltaTime);
			else
				tool.reduceValue(Math.abs(velocity.x ==0 ? velocity.y*deltaTime : velocity.x*deltaTime));
			//����ʾ��
			if(!tool.isHide()){
				tool.setHide(false);
			}
		}
	}

	protected abstract void update0(float deltaTime);
	
	/** ���������Ҫ���д����Ե����,�븴д�˷���*/
	protected boolean processEdge0() {
		return false;
	}

	@Override
	public void reset() {
		super.reset();
		velocity.set(0, 0);
		accel.set(0, 0);
		mLastX = 0; 
		mLastY = 0;
		if(mHideTool!=null)
	        mHideTool.reset();;
		mPaused = false;
	}
}
