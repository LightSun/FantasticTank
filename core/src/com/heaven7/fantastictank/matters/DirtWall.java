package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.res.Resource;

public class DirtWall extends StaticObject {

	public DirtWall(float x, float y) {
		super(x, y);
	}

	@Override
	public boolean useAttackRecorder() {
		return true;
	}

	@Override
	public BulletType[] accepted() {
		return BulletType.values();
	}

	@Override
	public TextureRegion getOriginalTextureRegion() {
		return Resource.dirtWall;
	}
	
}
