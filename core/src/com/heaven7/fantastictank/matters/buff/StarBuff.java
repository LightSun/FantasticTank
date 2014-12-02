package com.heaven7.fantastictank.matters.buff;

import com.heaven7.fantastictank.matters.Tank;
/**
 * �����buff:�ı��ӵ����͡���Ϊ��ͬ�ӵ��в�ͬ�ӵ����ص�
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
