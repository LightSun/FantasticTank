package com.heaven7.fantastictank.level.arttext.digit;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.level.arttext.ArtText;

public class FourArtText extends ArtText {

	@Override
	protected float measureWidth() {
		return 4*UNIT_WIDTH;
	}

	@Override
	protected float measureHeight() {
		return 5*UNIT_HEIGHT;
	}

	@Override
	public List<Vector2> layout() {
		Vector2 p = getLeftBottomPosition();

		ArrayList<Vector2> vecs = new ArrayList<Vector2>();
		// Êý×Ö4 µÄ"-"
		vecs.add(VECTOR_POOL.obtain().set(p.x ,               p.y+ UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH ,   p.y+ UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*2 , p.y+ UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*3 , p.y+ UNIT_HEIGHT *2));
		//vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*4 , p.y+ UNIT_HEIGHT *2));
		//2¸ö"|"
		vecs.add(VECTOR_POOL.obtain().set(p.x ,               p.y+ UNIT_HEIGHT*3));
		vecs.add(VECTOR_POOL.obtain().set(p.x ,               p.y+ UNIT_HEIGHT*4));
		
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2,  p.y ));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2,  p.y+ UNIT_HEIGHT));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2,  p.y+ UNIT_HEIGHT*3));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2,  p.y+ UNIT_HEIGHT*4));
		return vecs;
	}

}
