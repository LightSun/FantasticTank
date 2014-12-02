package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.support.FactionType;
import com.heaven7.fantastictank.support.GameObject;
import com.heaven7.fantastictank.util.ClipFactory.ClippedInfo;
/**
 * 静态对象
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
	
	/** 当前对象裁剪后的信息--包括纹理区域，坐标，宽高 */
	public ClippedInfo getClippedInfo(){
		return mClippedInfo;
	}
	public void setClippedInfo(ClippedInfo info){
		this.mClippedInfo = info;
	}
	
	/** 在不接受该种子弹攻击时，是否允许子弹穿过这个staticObject
	 * @see {@link BulletType }
	 * @see #accepted()
	 * */
	public boolean allowAcross(Bullet b){
		return false;
	}
	/** 能被哪些类型的子弹所攻击,为null表示不能被任意子弹攻击 */
	public abstract BulletType[] accepted();
	public abstract TextureRegion getOriginalTextureRegion();
	
	@Override
	public void reset() {
		super.reset();
		mClippedInfo = null;
		def.type = FactionType.Neutral;
	}
}
