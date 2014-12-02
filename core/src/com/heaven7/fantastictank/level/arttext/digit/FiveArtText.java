package com.heaven7.fantastictank.level.arttext.digit;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.level.arttext.ArtText;

public class FiveArtText extends ArtText {

	@Override
	protected float measureWidth() {
		return 3*UNIT_WIDTH;
	}

	@Override
	protected float measureHeight() {
		return 6*UNIT_HEIGHT;
	}

	@Override
	public List<Vector2> layout() {
		Vector2 p = getLeftBottomPosition();

		ArrayList<Vector2> vecs = new ArrayList<Vector2>();
		// 数字5 的3个"-"
		vecs.add(VECTOR_POOL.obtain().set(p.x , p.y));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH , p.y));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2 , p.y));
		
		vecs.add(VECTOR_POOL.obtain().set(p.x ,                p.y + UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH ,   p.y + UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2 , p.y + UNIT_HEIGHT *2));
		
		vecs.add(VECTOR_POOL.obtain().set(p.x ,                p.y + UNIT_HEIGHT *4));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH ,   p.y + UNIT_HEIGHT *4));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2 , p.y + UNIT_HEIGHT *4));
		//2个"|"
		vecs.add(VECTOR_POOL.obtain().set(p.x , p.y + UNIT_HEIGHT *3));
		vecs.add(VECTOR_POOL.obtain().set(p.x , p.y + UNIT_HEIGHT *5));
		vecs.add(VECTOR_POOL.obtain().set(p.x + UNIT_WIDTH*2 , p.y + UNIT_HEIGHT));
		return vecs;
	}

}
