package com.heaven7.fantastictank.level.arttext.letter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.level.arttext.ArtText;
/**
 * A
 * @author Administrator
 */
public class AArtText extends ArtText {

	@Override
	protected float measureWidth() {
		return 3*UNIT_WIDTH;
	}

	@Override
	protected float measureHeight() {
		return 4*UNIT_HEIGHT;
	}

	@Override //A
	public List<Vector2> layout() {
		Vector2 p = getLeftBottomPosition();
		ArrayList<Vector2> vecs = new ArrayList<Vector2>();
		//"/\"
		vecs.add(VECTOR_POOL.obtain().set(p.x , p.y));
		vecs.add(VECTOR_POOL.obtain().set(p.x , p.y+UNIT_HEIGHT*2));
		vecs.add(VECTOR_POOL.obtain().set(p.x+UNIT_WIDTH , p.y+UNIT_HEIGHT*3));
		vecs.add(VECTOR_POOL.obtain().set(p.x+UNIT_WIDTH*2 , p.y+UNIT_HEIGHT*2));
		vecs.add(VECTOR_POOL.obtain().set(p.x+UNIT_WIDTH*2 , p.y));
		//"-"
		vecs.add(VECTOR_POOL.obtain().set(p.x , p.y+UNIT_HEIGHT));
		vecs.add(VECTOR_POOL.obtain().set(p.x+UNIT_WIDTH , p.y+UNIT_HEIGHT));
		vecs.add(VECTOR_POOL.obtain().set(p.x+UNIT_WIDTH*2 , p.y+UNIT_HEIGHT));
		
		return vecs;
	}

}
