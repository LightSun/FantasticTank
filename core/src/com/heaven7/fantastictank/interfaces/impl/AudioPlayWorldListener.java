package com.heaven7.fantastictank.interfaces.impl;

import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.res.Resource;
/**
 * ÉùÒô²¥·Å¼àÌýÆ÷
 * @author Administrator
 */
public class AudioPlayWorldListener extends WorldEventListenerAdapter{

	@Override
	public void onTankDied(float x, float y,Tank t) {
		Resource.playSound(Resource.crash_tank);
	}

	@Override
	public void onHitTank(float x, float y,Tank t) {

	}

	@Override
	public void onAcrossFailed(Bullet b, StaticObject obj) {

	}

	@Override
	public void onCatchBuff(Tank t, Buff buff) {
		Resource.playSound(Resource.catch_bonus);
	}

	@Override
	public void onHitDirtyWall(float x, float y) {
		
	}

	@Override
	public void onHitBrickWall(float x, float y) {
		
	}

	@Override
	public void onHitSmallBrickWall(float x, float y) {
		
	}

}
