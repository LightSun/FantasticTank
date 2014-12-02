package com.heaven7.fantastictank.matters.buff;

import com.heaven7.fantastictank.matters.Tank;
/**
 * 五角星buff:改变子弹类型。因为不同子弹有不同子弹的特点
 * @author Administrator
 */
public class StarBuff extends ForeverBuff {

	public StarBuff(float x, float y) {
		super(x, y);
	}

	@Override
	public void apply(Tank t) {
        t.setBulletType(t.getBulletType().next());
	}

	@Override
	public int getId() {
		return ID_STAR;
	}

}
