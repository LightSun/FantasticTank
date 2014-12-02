package com.heaven7.fantastictank.matters.buff;

import java.util.List;

import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Tank;
/**
 * 临时性或者有效期性质的buff
 * @author Administrator
 */
public abstract class TempBuff extends Buff {
	private boolean applied;
	/** 有效时间 (秒计)*/
	private int validTime = DEFAULT_VALID_TIME;  

	public TempBuff(float x, float y) {
		super(x, y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override 
	public void apply(Tank t) {
		addTarget(t);
		doApply(t);
		applied = true;
	}

	 /**{@inheritDoc} ,buff时间到后调用 (永久性的buff不会调用此方法)--调用之后自动清空target*/
	@Override
    public void cancel(){
		if(applied){
			List<Tank> ts = getTargets();
			for(int i=0,size = ts.size(); i<size ;i++){
				doCancel(ts.get(i));
			}
	    	clearTargets();
	    	applied = false;
		}
    }
	
    protected abstract void doApply(Tank t);
    protected abstract void doCancel(Tank t);
    
    
    @Override
    public boolean isExpired() {
    	return (System.currentTimeMillis() - getStartTime())  >= validTime*1000;
    }
    
    @Override
    public void reset() {
    	super.reset();
    	validTime = DEFAULT_VALID_TIME;
    	applied = false;
    }
    
    //================= bean methods =================

	public int getValidTime() {
		return validTime;
	}
	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}
    
}
