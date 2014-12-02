package com.heaven7.fantastictank.matters.buff;

import java.util.List;

import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Tank;
/**
 * ��ʱ�Ի�����Ч�����ʵ�buff
 * @author Administrator
 */
public abstract class TempBuff extends Buff {
	private boolean applied;
	/** ��Чʱ�� (���)*/
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

	 /**{@inheritDoc} ,buffʱ�䵽����� (�����Ե�buff������ô˷���)--����֮���Զ����target*/
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
