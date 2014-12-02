package com.heaven7.fantastictank.interfaces;

import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;

public  interface WorldEventListener {

	/* auto �����Ƿ�ΪAutoTank*/
	void onTankDied(float x, float y,Tank t);

	void onHitTank(float x, float y,Tank t);

	void onHitDirtyWall(float x, float y);
	
	void onHitBrickWall(float x, float y);
	
	//artText unit
	void onHitSmallBrickWall(float x, float y);

	/** ������ӵ�b�޷�����obj,����ײʱ��Խʧ��ʱ���� */
	void onAcrossFailed(Bullet b, StaticObject obj);

	/** ����buff */
	void onCatchBuff(Tank t, Buff buff);
		 
}