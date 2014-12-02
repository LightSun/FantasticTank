package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.support.FactionType;
import com.heaven7.fantastictank.support.GameObject;
import com.heaven7.fantastictank.util.ClipFactory.ClippedInfo;
/**
 * ��̬����
 * @author Administrator
 */
public abstract class StaticObject extends GameObject {

	private ClippedInfo mClippedInfo;
	
	public StaticObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		mClippedInfo = null;
		def.type = FactionType.Neutral;
	}
	
	public StaticObject(float x, float y) {
		this(x, y, Constant.COMMON_WIDTH, Constant.COMMON_HEIGHT);
	}
	
	/** ��ǰ����ü������Ϣ--���������������꣬��� */
	public ClippedInfo getClippedInfo(){
		return mClippedInfo;
	}
	public void setClippedInfo(ClippedInfo info){
		this.mClippedInfo = info;
	}
	
	/** �ڲ����ܸ����ӵ�����ʱ���Ƿ������ӵ��������staticObject
	 * @see {@link BulletType }
	 * @see #accepted()
	 * */
	public boolean allowAcross(Bullet b){
		return false;
	}
	/** �ܱ���Щ���͵��ӵ�������,Ϊnull��ʾ���ܱ������ӵ����� */
	public abstract BulletType[] accepted();
	public abstract TextureRegion getOriginalTextureRegion();
	
	@Override
	public void reset() {
		super.reset();
		mClippedInfo = null;
		def.type = FactionType.Neutral;
	}
}
