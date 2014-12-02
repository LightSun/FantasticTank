package com.heaven7.fantastictank.level;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.level.arttext.ArtText;
import com.heaven7.fantastictank.level.arttext.ArtTextGroup;
import com.heaven7.fantastictank.level.arttext.ArtTextHelper;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Wood;
import com.heaven7.fantastictank.util.CacheHelper;

/**
 * 字形关卡生成器
 * @author Administrator
 */
public class FontLevel extends AbsLevel {

	@Override
	public void build() {
		clearAll();
		ArtTextGroup group  = new ArtTextGroup();
		//ArtTextGroup art1 = ArtTextHelper.newGroup("12345");
		//ArtTextGroup art3 = ArtTextHelper.newGroup("6789");
		//ArtTextGroup art2 = ArtTextHelper.getGroup("love");
		ArtTextGroup art1 = ArtTextHelper.newGroup("game",true);
		ArtTextGroup art2 = ArtTextHelper.getGroup("over");
		group.setMode(ArtTextGroup.MODE_VERTICAL);
		group.addArtText(art2);
		//group.addArtText(art3);
		group.addArtText(art1);
		//group.addArtText(ArtTextHelper.get("i"));
		
		float px = 1.5f;
		float py = 1.5f;
		group.setRealLeftBottomPosition(px, py);

		float halfWidth = getUnitWidth() / 2;
		float x, y;
		float halfHeight = getUnitHeight() / 2;
		int val;

		for (int i = 0, len = Xarr.length; i < len; i++) {

			x = Xarr[i] + halfWidth;

			for (int j = 0, len2 = Yarr.length; j < len2; j++) {
				y = Yarr[j] + halfHeight;
				
				//x,y均在字体范围内
				if(group.overlap(x, y)){
					continue;
				}
				if (isRetainPosition(x, y)) {
				   // mAutoTanks.add(obtainRandomAutoTank(x, y));
				}else if(isRetainPositionForManual(x, y)){
					//人工的
				}else{
					val = getWisdomManager().random100();
					if (val > 95) {
						mLakes.add(CacheHelper.obtain(x, y,Lake.class));
					} else if (val > 90 && val <= 95) {
						mBrickWalls.add(CacheHelper.obtain(x, y,BrickWall.class));
					} else if (val > 80 && val <= 90) {
						mWoods.add(CacheHelper.obtain(x, y, Wood.class));
					} else if (val >= 30 && val < 60) {
						// 什么都没有
					} else if (val < 30)
						mDirtWalls.add(CacheHelper.obtain(x, y, DirtWall.class));
				}
				
			}
		}
        //字体
		List<Vector2> vecs = group.layout();
		for(int i=0, size = vecs.size(); i<size ;i++){
			mSmallBrickWalls.add(CacheHelper.obtain(vecs.get(i).x, vecs.get(i).y, SmallBrickWall.class));
		}
		ArtText.freeAll(vecs);
	}

}
