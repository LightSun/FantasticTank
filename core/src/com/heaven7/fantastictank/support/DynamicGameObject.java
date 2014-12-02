package com.heaven7.fantastictank.support;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.wisdom.WisdomManager;
/**
 * velocity速度----加速度accel
 * @author Administrator
 */
public abstract class DynamicGameObject extends GameObject implements Updateable{
	public final Vector2 velocity;
	public final Vector2 accel;
	
	private Direction mDir;
	private float mLastX, mLastY;
	
	private HideTool mHideTool;
	/**是否被暂停--即失去行动的能力(子弹不吃这套)*/
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

	//====== 隐身相关 ===========
	/** 隐身，默认只准1次(true表示隐身成功) 
	 * @see HideTool#hide(int, float)*/
	public boolean hide(int flag,float value){
		if(mHideTool==null)
			mHideTool=new HideTool(1); 
		return mHideTool.hide(flag, value);
	}
	
	/** 是否处于隐身状态，如果是，则不渲染--同时不处理碰撞事件 */
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
	
	/**  停留 /呆在原地  */
	public void stay(){
		this.position.set(mLastX, mLastY);
		//position本身也会减所以出了bug(这里计算Bounds时，position不应该变)
		//this.bounds.setPosition(position.sub(bounds.width/2, bounds.height/2)); 
		setBoundByPosition();
	}

	/** 基于@position 设置@bounds的左下角x,y */
	protected void setBoundByPosition() {
		bounds.setPosition(position.x - bounds.width/2, position.y-bounds.height/2);
	}
	
	/**  边缘处理  ,called by {@link #update(float)} **/
	protected void processEdge() {
		//检查碰到左边缘
		if(position.x < bounds.width / 2){
			position.x = bounds.width/2;
			setBoundByPosition();
			if(!processEdge0())
			    randowDirection(true);
		}
		//碰到到了右边界
		if(position.x > Constant.WORLD_WIDTH - bounds.width/2){
			position.x = Constant.WORLD_WIDTH - bounds.width/2;
			setBoundByPosition();
			if(!processEdge0())
				randowDirection(true);
		}
		//碰到底边
		if(position.y < bounds.height / 2){
			position.y = bounds.height /2;
			setBoundByPosition();
			if(!processEdge0())
				randowDirection(true);
		}
		//碰到顶边
		if(position.y > Constant.WORLD_HEIGHT - bounds.height/2){
			position.y =  Constant.WORLD_HEIGHT - bounds.height/2;
			setBoundByPosition();
			if(!processEdge0())
				randowDirection(true);
		}
	}
	
	/**根据当前方向设置成指定的速度*/
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
	
	/**切换到指定的方向(速度值不变，速度方向跟随当前的方向)*/
	public void switchDirection(Direction dir){
		if(getDirection() == dir) return;//方向和之前相同
		setDirection(dir);
		float value = Math.abs(velocity.x== 0 ? velocity.y : velocity.x);
		setVelocity(value);
	}

	/** 随机切换方向（速度值不变，方向跟随当前对象） */
	public void randowDirection(boolean mayEqualPre) {
	    Direction dir = WisdomManager.randomDirection(getDirection(), mayEqualPre);
		switchDirection(dir);
	}
	//===== abstract methods ========//
	
	/**必须先检查是否被定身 {@link #isPaused()}*/
	@Override
	public void update(float deltaTime){
		if(!isPaused()){
			this.mLastX = position.x;
			this.mLastY = position.y;
			if(velocity.x==0 && velocity.y==0)
				System.out.println("[ Warn ] name = "+def.name+"的对象, velocity.x==0 && velocity.y==0");
			
			position.add(velocity.x*deltaTime, velocity.y*deltaTime);
			setBoundByPosition();
			
			doWithHideIfNeed(deltaTime);
			
			update0(deltaTime);
			processEdge();
		}
	}

    /** 处理隐身如果需要 */
	protected void doWithHideIfNeed(float deltaTime) {
		HideTool tool = getHideTool();
		if(tool!=null){
			if(!tool.isHide()) return; //不处于隐身.则不处理隐身
			
			if(tool.getFlag() == HideTool.FLAG_TIME)
				tool.reduceValue(deltaTime);
			else
				tool.reduceValue(Math.abs(velocity.x ==0 ? velocity.y*deltaTime : velocity.x*deltaTime));
			//该显示了
			if(!tool.isHide()){
				tool.setHide(false);
			}
		}
	}

	protected abstract void update0(float deltaTime);
	
	/** 如果子类需要自行处理边缘问题,请复写此方法*/
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
