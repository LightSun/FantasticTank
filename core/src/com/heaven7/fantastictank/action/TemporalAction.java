package com.heaven7.fantastictank.action;

import com.badlogic.gdx.math.Interpolation;

/***
 * 时间的动作(持续的动作)
 * @author Administrator
 */
public abstract class TemporalAction extends Action {

	private float duration;
	private float alreadyApplyTime;
	
	private boolean reverse, complete;
	private Interpolation  interpolator;
	
	public TemporalAction(){}
	public TemporalAction(float duration){
		this.duration = duration;
	}
	
	public TemporalAction(float duration, Interpolation interpolator) {
		super();
		this.duration = duration;
		this.interpolator = interpolator;
	}


	@Override 
	public boolean apply(float deltaTime) {
		if(complete) return true;
		if(alreadyApplyTime==0)
			begin();
		alreadyApplyTime += deltaTime;
		complete = alreadyApplyTime >duration;
		
		float percent;
		if(complete)
			percent = 1;
		else{
			percent = alreadyApplyTime /duration;
			if(interpolator!=null)
				percent = interpolator.apply(percent);
		}
		
		update(reverse?1-percent :percent);
		if(complete) end();
		
		return complete;
	}
	
	@Override
	public void reset() {
		super.reset();
		alreadyApplyTime =0;
		complete = false;
		reverse = false;
	}
	
	/**每一帧都会调用(see {@link #apply(long)}),percent 为0~1(The percentage of completion for this action, growing from 0 to 1 over the duration)*/
	protected abstract void update(float percent);
		
	
	/**动作结束时调用*/
	protected void end() {
	}

	/**动作开始时调用*/
	protected void begin() {
	}

	//========== below like bean method ============== //
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}

	public Interpolation getInterpolator() {
		return interpolator;
	}
	public void setInterpolator(Interpolation interpolator) {
		this.interpolator = interpolator;
	}

	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	
	

}
