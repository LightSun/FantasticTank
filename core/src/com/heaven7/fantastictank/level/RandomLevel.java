package com.heaven7.fantastictank.level;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.level.arttext.ArtText;
import com.heaven7.fantastictank.level.arttext.ArtTextHelper;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Wood;
import com.heaven7.fantastictank.util.CacheHelper;

public class RandomLevel extends AbsLevel {

	@Override
	public void build() {
		clearAll();
		
		ArtText artText = ArtTextHelper.getGroup("family");
		float px = 2.5f;
		float py = 4.5f;
		artText.setRealLeftBottomPosition(px, py);

		float halfWidth = getUnitWidth() / 2;
		float x, y;
		float halfHeight = getUnitHeight() / 2;
		int val;

		for (int i = 0, len = Xarr.length; i < len; i++) {

			x = Xarr[i] + halfWidth;

			for (int j = 0, len2 = Yarr.length; j < len2; j++) {
				y = Yarr[j] + halfHeight;
				
				//x,y均在字体范围内
				if(artText.overlap(x, y)){
					continue;
				}
				
				if (isRetainPosition(x, y)) {
				    mAutoTanks.add(obtainRandomAutoTank(x, y));
				}else if(isRetainPositionForManual(x, y)){
					//人工的
				}else {
					val = getWisdomManager().random100();
					if (val > 92) {
						mLakes.add(CacheHelper.obtain(x, y,Lake.class));
					} else if (val > 80 && val <= 92) {
						mBrickWalls.add(CacheHelper.obtain(x, y,BrickWall.class));
					} else if (val > 70 && val <= 80) {
						mWoods.add(CacheHelper.obtain(x, y, Wood.class));
					} else if (val >= 30 && val < 60) {
						// 什么都没有
					} else if (val < 30)
						mDirtWalls.add(CacheHelper.obtain(x, y, DirtWall.class));
				}
			}
		}
		
		  //字体
		List<Vector2> vecs = artText.layout();
		for(int i=0, size = vecs.size(); i<size ;i++){
			mSmallBrickWalls.add(CacheHelper.obtain(vecs.get(i).x, vecs.get(i).y, SmallBrickWall.class));
		}
		ArtText.freeAll(vecs);
	}
	
}
