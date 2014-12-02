package com.heaven7.fantastictank.interfaces.impl;

import com.heaven7.fantastictank.interfaces.WorldEventListener;
import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;

/***
 *   ≈‰∆˜
 * @author Administrator
 */
public abstract class WorldEventListenerAdapter implements WorldEventListener {

	@Override
	public void onTankDied(float x, float y, Tank t) {
	}

	@Override
	public void onHitTank(float x, float y, Tank t) {
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

	@Override
	public void onAcrossFailed(Bullet b, StaticObject obj) {
	}

	@Override
	public void onCatchBuff(Tank t, Buff buff) {
	}

}
