package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.support.GameObject;
import com.heaven7.fantastictank.support.Updateable;
/**
 * 有生气的/帧动画扮演者的 基类
 * @author Administrator
 */
public abstract class AnimateGameObject extends GameObject implements Updateable{
	
	private float stateTime;
	private Animation anim;

	public AnimateGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/** 增加帧动画的时间 */
	@Override
	public void update(float deltaTime){
		this.stateTime += deltaTime;
	}
	
	/** 绘制-帧，默认模式 ,设置请调用{@link Animation#setPlayMode(int)}*/
	public void draw(SpriteBatch batcher,Animation anim,float scaleWidth,float scaleHeight){
		if(this.anim==null)
			this.anim = anim;
		TextureRegion frame = anim.getKeyFrame(stateTime);
		float realWidth = bounds.width*scaleWidth;
		float realHeight = bounds.height*scaleHeight;
		batcher.draw(frame, position.x - realWidth, position.y - realHeight, realWidth, realHeight);
	}
	
	/** 动画是非循环方式播放时*/
	public boolean isFinish(){
		if(anim==null) return false;
		return anim.isAnimationFinished(stateTime);
	}
	
	@Override
    public void reset(){
    	this.stateTime = 0;
    	anim = null;
    }

    //======= bean methods ===========//
	public float getStateTime() {
		return stateTime;
	}
}
