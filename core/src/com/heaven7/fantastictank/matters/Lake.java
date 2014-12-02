package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.res.Resource;
/**
 * 所有子弹无法攻击
 * @author Administrator
 */
public class Lake extends StaticObject {

	public Lake(float x, float y) {
		super(x, y);
	}

	@Override
	public BulletType[] accepted() {
		return null;
	}

	@Override
	public TextureRegion getOriginalTextureRegion() {
		return Resource.lake;
	}

}
