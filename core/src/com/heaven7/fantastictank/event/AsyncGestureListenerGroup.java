package com.heaven7.fantastictank.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
/**
 * 异步手势监听实现(所有事件的返回值将无效。因为异步的)
 *<li>结论： 无法这么处理 _2只手一起触发的情况
 * @author Administrator
 */
@Deprecated
public class AsyncGestureListenerGroup implements GestureListener,Disposable {
	
	private final ExecutorService service = Executors.newCachedThreadPool();
	private final Array<GestureListener> listeners = new Array<GestureListener>(2);
	
	public void add(GestureListener l){
		synchronized (listeners) {
			this.listeners.add(l);
		}
	}
	
	public void remove(GestureListener l){
		synchronized (listeners) {
			this.listeners.removeValue(l, true);
		}
	}
	
	public void clear(){
		synchronized (listeners) {
		    this.listeners.clear();
		}
	}
	
	@Override
	public boolean touchDown(final float x,final float y,final int pointer,final int button) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					cpyListeners[index].touchDown(x, y, pointer, button);
				}
			});
		}
		return false;
	}

	@Override
	public boolean tap(final float x, final float y, final int count,final int button) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					cpyListeners[index].tap(x, y, count, button);
				}
			});
		}
		return true;
	}

	@Override
	public boolean longPress(final float x,final float y) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					cpyListeners[index].longPress(x, y);
				}
			});
		}
		return false;
	}

	@Override
	public boolean fling(final float velocityX,final float velocityY,final int button) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					cpyListeners[index].fling(velocityX, velocityY,button);
				}
			});
		}
		return false;
	}

	@Override
	public boolean pan(final float x,final float y,final float deltaX,final float deltaY) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					cpyListeners[index].pan(x, y,deltaX,deltaY);
				}
			});
		}
		return true;
	}

	@Override
	public boolean zoom(final float initialDistance,final float distance) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					cpyListeners[index].zoom(initialDistance,distance);
				}
			});
		}
		return false;
	}

	@Override
	public boolean pinch(final Vector2 initialPointer1, final Vector2 initialPointer2,
			final Vector2 pointer1,final Vector2 pointer2) {
		final GestureListener[] cpyListeners = copyListeners();
		for (int i = 0, n = cpyListeners.length; i < n; i++){
			final int index = i;
			service.submit(new Runnable() {
				public void run() {
					cpyListeners[index].pinch(initialPointer1, initialPointer2,pointer1,pointer2);
				}
			});
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
	public void dispose() {
		service.shutdownNow();
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
