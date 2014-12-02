package com.heaven7.fantastictank.matters.buff;

import com.heaven7.fantastictank.matters.Tank;
/**
 * ˫ǹBuff
 * @author Administrator
 */
public class DoubleFireBuff extends ForeverBuff {

	public DoubleFireBuff(float x, float y) {
		super(x, y);
	}

	@Override
	public void apply(Tank t) {
       if( (t.getOpenFireCount() + t.getFiredCount()) < t.maxFireCount()){
    	    t.increaseOpenFireCount();
       }
	}

	@Override
	public int getId() {
		return ID_DOUBLE_FIRE;
	}

}
