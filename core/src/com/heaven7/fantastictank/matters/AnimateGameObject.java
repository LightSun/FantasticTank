package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.support.GameObject;
import com.heaven7.fantastictank.support.Updateable;
/**
 * ��������/֡���������ߵ� ����
 * @author Administrator
 */
public abstract class AnimateGameObject extends GameObject implements Updateable{
	
	private float stateTime;
	private Animation anim;

	public AnimateGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/** ����֡������ʱ�� */
	@Override
	public void update(float deltaTime){
		this.stateTime += deltaTime;
	}
	
	/** ����-֡��Ĭ��ģʽ ,���������{@link Animation#setPlayMode(int)}*/
	public void draw(SpriteBatch batcher,Animation anim,float scaleWidth,float scaleHeight){
		if(this.anim==null)
			this.anim = anim;
		TextureRegion frame = anim.getKeyFrame(stateTime);
		float realWidth = bounds.width*scaleWidth;
		float realHeight = bounds.height*scaleHeight;
		batcher.draw(frame, position.x - realWidth, position.y - realHeight, realWidth, realHeight);
	}
	
	/** �����Ƿ�ѭ����ʽ����ʱ*/
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
