package com.heaven7.fantastictank.level;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.level.arttext.ArtText;
import com.heaven7.fantastictank.level.arttext.ArtTextGroup;
import com.heaven7.fantastictank.level.arttext.ArtTextHelper;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.util.CacheHelper;

public class GameOverLevel extends AbsLevel {

	@Override
	public void build() {
		clearAll();

		ArtTextGroup group  = new ArtTextGroup();
		ArtTextGroup art1 = ArtTextHelper.newGroup("game");
		ArtTextGroup art2 = ArtTextHelper.getGroup("over");
		group.setMode(ArtTextGroup.MODE_VERTICAL);
		group.addArtText(art2);
		group.addArtText(art1);
		group.setRealLeftBottomPosition(4.5f, 4.5f);
		
		  //×ÖÌå
		List<Vector2> vecs = group.layout();
		for(int i=0, size = vecs.size(); i<size ;i++){
			mSmallBrickWalls.add(CacheHelper.obtain(vecs.get(i).x, vecs.get(i).y, SmallBrickWall.class));
		}
		ArtText.freeAll(vecs);
	}

}
