package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.util.ArrayUtil;
/**
 * 石墙(一般子弹打不动)
 * @author Administrator
 */
public class BrickWall extends StaticObject {

	public BrickWall(float x, float y, float width, float height) {
		super(x, y, width, height);
		reset();
	}
	public BrickWall(float x, float y) {
		super(x, y);
		reset();
	}

	@Override
	public boolean useAttackRecorder() {
		return true;
	}

	@Override
	public BulletType[] accepted() {
		return ArrayUtil.asArray(BulletType.Rebounding,BulletType.DieTogether);
	}

	@Override
	public TextureRegion getOriginalTextureRegion() {
		return Resource.brickWall;
	}
	
	@Override
	public void reset() {
		super.reset();
		def.maxBeHitCount = def.beHitCount = 2;
	}
}
