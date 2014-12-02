package com.heaven7.fantastictank.matters.buff;

import com.heaven7.fantastictank.matters.Tank;
/**
 * º”»Àbuff
 * @author Administrator
 */
public final class AddLifeBuff extends ForeverBuff {

	public AddLifeBuff(float x, float y) {
		super(x, y);
	}

	@Override
	public void apply(Tank t) {
        t.increaseReliveCount();
	}

	@Override
	public int getId() {
		return ID_ADD_LIFE;
	}

}
