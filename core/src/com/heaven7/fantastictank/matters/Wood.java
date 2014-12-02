package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.res.Resource;
/**
 * 树林(一般子弹无法攻击到)
 * @author Administrator
 */
public class Wood extends StaticObject {

	public Wood(float x, float y) {
		super(x, y);
		reset();
	}
	
	@Override
	public boolean useAttackRecorder() {
		return true;
	}

	@Override
	public BulletType[] accepted() {
		return null;
	}

	@Override
	public TextureRegion getOriginalTextureRegion() {
		return Resource.wood;
	}
	
	@Override
	public boolean allowAcross(Bullet b) {
		return true;
	}
	
	@Override
	public void reset() {
		super.reset();
		def.maxBeHitCount = def.beHitCount = 2;
	}

}
