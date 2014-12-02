package com.heaven7.fantastictank.level.arttext.letter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class RArtText extends PArtText {

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
		//"P"”“∞Î‘≤
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH, p.y+UNIT_HEIGHT *4));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*2, p.y+UNIT_HEIGHT *4));
		
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*3, p.y+UNIT_HEIGHT *3));
		
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH, p.y+UNIT_HEIGHT *2));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*2, p.y+UNIT_HEIGHT *2));
		//"\" rµƒ”“œ¬Ω«
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*2, p.y+UNIT_HEIGHT));
		vecs.add(VECTOR_POOL.obtain().set(p.x +UNIT_WIDTH*3, p.y));
		
		return vecs;
	}

}
