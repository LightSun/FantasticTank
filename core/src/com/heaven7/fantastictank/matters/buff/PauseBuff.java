package com.heaven7.fantastictank.matters.buff;

import com.heaven7.fantastictank.matters.Tank;

/**
 * 定身/暂停敌人移动(临时性的buff)
 * @author Administrator
 */
public class PauseBuff extends TempBuff {

	public PauseBuff(float x, float y) {
		super(x, y);
	}

	@Override
	public int getId() {
		return ID_PAUSE_TIME;
	}

	@Override
	protected void doCancel(Tank t) {
		t.setPaused(false);
	}

	@Override
	protected void doApply(Tank t) {
		t.setPaused(true);
	}
	
	@Override
	public boolean applyToHostile() {
		return true;
	}
    @Override
    public byte scope() {
    	return SCOPE_GROUP;
    }
}
