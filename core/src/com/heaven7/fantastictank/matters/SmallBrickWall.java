package com.heaven7.fantastictank.matters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.util.ArrayUtil;
/**
 * 相当于1/4的brickWall
 * @author Administrator
 */
public class SmallBrickWall extends StaticObject {

	public SmallBrickWall(float x, float y) {
		super(x, y, Constant.COMMON_WIDTH/2+0.06f, Constant.COMMON_HEIGHT/2+0.06f);
		reset();
	}

	@Override
	public BulletType[] accepted() {
		return ArrayUtil.asArray(BulletType.Rebounding,BulletType.DieTogether,BulletType.Penetrable);
	}
	
	@Override
	public void reset() {
		super.reset();
		def.maxBeHitCount = def.beHitCount = 1;
	}

	@Override
	public TextureRegion getOriginalTextureRegion() {
		return Resource.smallBrickWall;
	}

}
