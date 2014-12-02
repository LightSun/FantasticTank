package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.Color;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.action.FlickeringAction;
import com.heaven7.fantastictank.support.FactionType;
import com.heaven7.fantastictank.util.ActionHelper;

public class MyTank extends Tank{
	
	private CollideChecker mChecker;
	private FlickeringAction mAction;

	public MyTank(float x, float y) {
		super(x, y);
		def.beHitCount = 1; 
		def.type = FactionType.East; 
		setReliveCount(Constant.RELIVE_COUNT_COMMON);
	}
	
	/**假的更新(主要是实现闪烁效果)*/
	public void updateFake(float deltaTime){
		if(isPaused()){
			mAction.apply(deltaTime);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		//被定身
		if(isPaused()){
			return;
		}
		super.update(deltaTime);
		if(mChecker!=null) mChecker.handleCollideStaticObject(this);
	}

	@Override
	protected void update0(float deltaTime) {
	}
	
	@Override
	public void setPaused(boolean paused) {
		if(paused)  {
			if(mAction == null)
			mAction = ActionHelper.flickering(this, Buff.DEFAULT_VALID_TIME);
		}else
			mAction.reset(); 
		if(!isWhiteColor())
		    setColor(Color.WHITE);
		super.setPaused(paused);
	}
	
	/** 可能为null.如果当前不允许继续射击 */
	@Override 
	public Bullet shoot() {
		int firedCount = getFiredCount();
		int count = getOpenFireCount();
		//int val = count + firedCount;
		//System.out.println("mytank: openFireCount ="+count+", firedCount = "+firedCount);
		if(count > 0 ){
			decreaseOpenFireCount();
		    return super.shoot();
		}
		return null;
	}

	@Override
	public boolean isManual() {
		return true;
	}
	
	@Override
	public void reset() {
		super.reset();
		def.beHitCount = 1; 
		def.type = FactionType.East; 
		setReliveCount(Constant.RELIVE_COUNT_COMMON);
		if(mAction!=null) mAction.reset();
	}

	//============bean methods =============//
	
	public CollideChecker getCollideChecker() {
		return mChecker;
	}

	public void setCollideChecker(CollideChecker checker) {
		this.mChecker = checker;
	}


	/**主动移动时的 碰撞检查器 */
	public static interface CollideChecker{
		//检查并处理坦克身体碰撞静态对象
		void handleCollideStaticObject(MyTank t);
	}
	
}
