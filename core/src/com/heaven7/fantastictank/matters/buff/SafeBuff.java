package com.heaven7.fantastictank.matters.buff;

import com.heaven7.fantastictank.matters.Tank;
/**
 * ��ȫñ/�޵�buff
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

	@Override   //TODO �޵и������һ���޵е�״̬UI�����Ѻ�����
	protected void doApply(Tank t) {
		t.setUnrivaled(true);
	}

}
