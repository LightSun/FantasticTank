package com.heaven7.fantastictank.level.arttext.letter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.level.arttext.ArtText;

public class PArtText extends ArtText {

	@Override
	protected float measureWidth() {
		return resolveMeasureWidth(4);
	}

	@Override
	protected float measureHeight() {
		return resolveMeasureHeight(5);
	}

	@Override
	public List<Vector2> layout() {
		Vector2 p = getLeftBottomPosition();
		ArrayList<Vector2> vecs = new ArrayList<Vector2>();
		//"|"
		vecs.add(VECTOR_POOL.obtain().set(p.x, p.y));
		vecs.add(VECTOR_POOL.obtain().set(p.x, p.y +UNIT_HEIGHT));
		vecs.add(VECTOR_POOL.obtain().set(p.x, p.y+UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x, p.y+UNIT_HEIGHT *3));
		vecs.add(VECTOR_POOL.obtain().set(p.x, p.y+UNIT_HEIGHT *4));
		//"P"�Ұ�Բ
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH, p.y+UNIT_HEIGHT *4));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*2, p.y+UNIT_HEIGHT *4));
		
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*3, p.y+UNIT_HEIGHT *3));
		
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH, p.y+UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*2, p.y+UNIT_HEIGHT *2));
		
		return vecs;
	}

}
