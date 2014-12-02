package com.heaven7.fantastictank.action;

import com.badlogic.gdx.graphics.Color;

/**
 * 闪烁的action(原理是利用颜色)
 * <li>可以设置持续时间{@link #setDuration(int)},ms为单位
 * @author Administrator
 */
public class FlickeringAction2 extends Action {
	
	public static final float THRESHOLD_DELTA_COLOR = 0.1f; //颜色的增减量
	public static final long DEFAULT_DURATION = 30000; //默认30秒
	
	private boolean add;  
	private long startTime;
	private long duration = DEFAULT_DURATION;   //持续时间

	@Override
	public boolean apply(float deltaTime) {
		if(startTime == 0 ) startTime = System.currentTimeMillis();
		final IActor actor = getActor();
		if(!add){
			actor.decColor(THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, 0);
			Color c = actor.getColor();
			/*float dr = 0 ;
			float dg = 0 ;
			float db = 0 ;
			if (c.r< 0) dr = THRESHOLD_DELTA_COLOR*2;
			if (c.g< 0) dg = THRESHOLD_DELTA_COLOR*2;
			if (c.b< 0) db = THRESHOLD_DELTA_COLOR*2;
			*/
			if(c.r <0){
				add = true;
				actor.addColor(THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, 0);
			}
			
		}else{
			actor.addColor(THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, 0);
			if(actor.getColor().r > 1){
				add = false;
				actor.decColor(THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, 0);
			}
		}
		return System.currentTimeMillis() - startTime > duration;
	}

	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}

}
