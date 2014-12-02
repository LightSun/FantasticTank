/**
 * 
 */
package com.heaven7.fantastictank.interfaces.impl;

import java.util.ArrayList;

import com.heaven7.fantastictank.interfaces.WorldEventListener;
import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;
/**
 * 监听器组(监听世界发生的事件)
 * @author Administrator
 */
public class WorldListenerGroup implements WorldEventListener {
	
	private ArrayList<WorldEventListener> listeners;
	
	public WorldListenerGroup() {
		listeners = new ArrayList<WorldEventListener>(3);
	}
	
	public WorldListenerGroup  add(WorldEventListener listener){
		this.listeners.add(listener);
		return this;
	}
	
	public WorldListenerGroup remove(WorldEventListener listener){
		this.listeners.remove(listener);
		return this;
	}
	@Override
	public void onTankDied(float x, float y,Tank t) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			listeners.get(i).onTankDied(x, y,t);
		}
	}

	@Override
	public void onHitTank(float x, float y,Tank t) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			listeners.get(i).onHitTank(x, y,t);
		}
	}

	@Override
	public void onAcrossFailed(Bullet b, StaticObject obj) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			listeners.get(i).onAcrossFailed(b, obj);
		}
	}

	@Override
	public void onCatchBuff(Tank t, Buff buff) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			listeners.get(i).onCatchBuff(t, buff);
		}
	}

	@Override
	public void onHitDirtyWall(float x, float y) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			listeners.get(i).onHitDirtyWall(x,y);
		}
	}

	@Override
	public void onHitBrickWall(float x, float y) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			listeners.get(i).onHitBrickWall(x,y);
		}
	}

	@Override
	public void onHitSmallBrickWall(float x, float y) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			listeners.get(i).onHitSmallBrickWall(x,y);
		}
	}

}
