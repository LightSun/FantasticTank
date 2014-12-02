package com.heaven7.fantastictank.level;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.heaven7.fantastictank.interfaces.impl.generate.DebugTankManagePolicy;
import com.heaven7.fantastictank.level.arttext.ArtText;
import com.heaven7.fantastictank.level.arttext.letter.JArtText;
import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.util.CacheHelper;

/**
 * 用于测试:比如纹理颜色 影响 纹理？
 * @author Administrator
 */
public class DebugLevel extends AbsLevel {
	
	public DebugLevel() {
		setTankManagePolicy(new DebugTankManagePolicy());
	}

	@Override
	public void build() {
		clearAll();
		ArtText artText = new JArtText();
		float px = 1.5f;
		float py = 1.5f;
		artText.setRealLeftBottomPosition(px, py);
		float minX = artText.getRealLeftBottomPosition().x;
		float minY = artText.getRealLeftBottomPosition().y;
		float maxX = artText.getRealRightTopPosition().x;
		float maxY = artText.getRealRightTopPosition().y;

		float halfWidth = getUnitWidth() / 2;
		float x, y;
		float halfHeight = getUnitHeight() / 2;
		int val;
		
		for (int i = 0, len = Xarr.length; i < len; i++) {

			x = Xarr[i] + halfWidth;
			boolean includeX = x>=minX && x<=maxX;

			for (int j = 0, len2 = Yarr.length; j < len2; j++) {
				y = Yarr[j] + halfHeight;
				
				boolean includeY = y>=minY && y<=maxY;
				//x,y均在字体范围内
				if(includeY && includeX){
					continue;
				}
				
				val = getWisdomManager().random100();
				if(mAutoTanks.size()== 0){ 
					AutoTank t = obtainRandomAutoTank(x, y);
					
					mAutoTanks.add(t);
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
