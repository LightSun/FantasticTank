package com.heaven7.fantastictank.interfaces;

import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;

public  interface WorldEventListener {

	/* auto 死的是否为AutoTank*/
	void onTankDied(float x, float y,Tank t);

	void onHitTank(float x, float y,Tank t);

	void onHitDirtyWall(float x, float y);
	
	void onHitBrickWall(float x, float y);
	
	//artText unit
	void onHitSmallBrickWall(float x, float y);

	/** 当这个子弹b无法攻击obj,且碰撞时穿越失败时调用 */
	void onAcrossFailed(Bullet b, StaticObject obj);

	/** 捕获buff */
	void onCatchBuff(Tank t, Buff buff);
		 
}