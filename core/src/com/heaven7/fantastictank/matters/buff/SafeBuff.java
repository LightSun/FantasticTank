package com.heaven7.fantastictank.matters.buff;

import com.heaven7.fantastictank.matters.Tank;
/**
 * 安全帽/无敌buff
 * @author Administrator
 */
public class SafeBuff extends TempBuff {

	public SafeBuff(float x, float y) {
		super(x, y);
	}
	
	@Override
	public int getId() {
		return ID_SAFE;
	}

	@Override
	protected void doCancel(Tank t) {
		t.setUnrivaled(false);
	}

	@Override   //TODO 无敌给外面加一层无敌的状态UI。以友好体验
	protected void doApply(Tank t) {
		t.setUnrivaled(true);
	}

}
