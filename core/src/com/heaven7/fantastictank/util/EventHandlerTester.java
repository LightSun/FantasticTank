package com.heaven7.fantastictank.util;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
/**
 * 记住.libgdx 事件以左上角为原点,纹理原点也是左上角。世界坐标是左下角
 * @author Administrator
 */
public class EventHandlerTester extends InputAdapter implements GestureListener{
	
	private int touchDownX,touchDownY;
    /**
     * InputAdapter--touchDown返回true之后(其他false)。
     * GestureListener无法接收touchDown,zoom,pinch,tap(只能接收pan.fling)
     */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.touchDownX = screenX;
		this.touchDownY = screenY;
		//System.err.println("InputAdapter: touchDown");
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//System.err.println("InputAdapter: touchUp");
		return false;
	}
	
	@Override //返回true.pan事件无法接收
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//System.err.println("InputAdapter: touchDragged");
		return false;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		System.err.println("GestureListener: touchDown");
		System.out.println("x = "+x+", y = "+y+", pointer = "+pointer);
		return false;
	}
	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		System.err.println("GestureListener: tap");
		System.out.println("x = "+x+", y = "+y+", count = "+count);
		return false;
	}
	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		System.err.println("GestureListener: longPress");
		System.out.println("x = "+x+", y = "+y);
		return false;
	}
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		System.err.println("GestureListener: fling");
		return false;
	}
	@Override //类似 drag事件
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		System.err.println("GestureListener: pan");
		System.out.println("x = "+x+", y = "+y+", deltaX = "+deltaX+", deltaY = "+deltaY);
		return false;
	}
	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		System.err.println("GestureListener: zoom");
		return false;
	}
	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		System.err.println("GestureListener: pinch");
		System.out.println("initialPointer1 = "+initialPointer1+", initialPointer2 = "+initialPointer2);
		System.out.println("pointer1 = "+pointer1+", pointer2 = "+pointer2);
		return false;
	}
	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
}
