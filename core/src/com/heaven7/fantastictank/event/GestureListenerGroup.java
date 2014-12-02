package com.heaven7.fantastictank.event;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
/**
 */
public class GestureListenerGroup implements GestureListener {
	
	private final Array<GestureListener> listeners = new Array<GestureListener>(3);
	
	public void add(GestureListener l){
		this.listeners.add(l);
	}
	public void addAll(GestureListener... ls){
		for(GestureListener l :ls){
			this.listeners.add(l);
		}
	}
	
	public void remove(GestureListener l){
		this.listeners.removeValue(l, true);
	}
	
	public void clear(){
		this.listeners.clear();
	}
	
	@Override
	public boolean touchDown(final float x,final float y,final int pointer,final int button) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			if(cpyListeners[i].touchDown(x, y, pointer, button))
				return true;
		}
		return false;
	}

	@Override
	public boolean tap(final float x, final float y, final int count,final int button) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			 if(cpyListeners[i].tap(x, y, count, button)) return true;
		}
		return false;
	}

	@Override
	public boolean longPress(final float x,final float y) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			if(cpyListeners[i].longPress(x, y)) return true;
		}
		return false;
	}

	@Override
	public boolean fling(final float velocityX,final float velocityY,final int button) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			  if(cpyListeners[i].fling(velocityX, velocityY,button)) return true;
		}
		return false;
	}

	@Override
	public boolean pan(final float x,final float y,final float deltaX,final float deltaY) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
				if(cpyListeners[i].pan(x, y,deltaX,deltaY)) return true;
		}
		return false;
	}

	@Override
	public boolean zoom(final float initialDistance,final float distance) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
		     if(cpyListeners[i].zoom(initialDistance,distance)) return true;
		}
		return false;
	}

	@Override
	public boolean pinch(final Vector2 initialPointer1, final Vector2 initialPointer2,
			final Vector2 pointer1,final Vector2 pointer2) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			  if(cpyListeners[i].pinch(initialPointer1, initialPointer2,pointer1,pointer2))
				  return true;
		}
		return false;
	}
	
	private GestureListener[] copyListeners(){
		GestureListener[] array;
		synchronized (listeners) {
			array = listeners.toArray(GestureListener.class);
		}
		return array;
	}
	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
