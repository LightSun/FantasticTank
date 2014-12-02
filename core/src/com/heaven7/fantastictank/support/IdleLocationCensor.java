package com.heaven7.fantastictank.support;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.heaven7.fantastictank.level.AbsLevel;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.util.ArrayUtil;
import com.heaven7.fantastictank.util.OverlapTester;

/**
 * 空闲位置--检查员(检查后将结果提交给检查官)
 * @author Administrator
 */
public class IdleLocationCensor {
	
	private final Array<Vector2> mIdleResult = new Array<Vector2>();
	private final Procurator mProcurator;
	
	/**检查员将关心的结果报告给检察官*/
	public IdleLocationCensor(Procurator mProcurator) {
		super();
		this.mProcurator = mProcurator;
	}

	/** 检查autotank的保留位置 
	 * @param ts  所有要检查的对象*/
	public <T extends Tank>void checkRetainAutoPosition(List<T> ts){
		int size = ts.size();
		if(size ==0) return;
		
		for(int j=0,len = AbsLevel.AUTO_RETAIN_POS.length ; j<len ;j++){
			Vector2 v = AbsLevel.AUTO_RETAIN_POS[j];
			
			boolean idle = true;
			for(int i=0 ; i<size ;i++ ){
				T t = ts.get(i);
				Rectangle r = ArrayUtil.convert(v, t.bounds.width, t.bounds.height);
				if(OverlapTester.overlapRectangles(r, t.bounds)){
					idle = false;
					ArrayUtil.RECT_CACHE.free(r);
					break;
				}	
				ArrayUtil.RECT_CACHE.free(r);
			}
			if(idle)
				mIdleResult.add(v);
		}
		
		size = mIdleResult.size;
		for( int i =0; i<size ; i++){
			mProcurator.onIdle(mIdleResult.get(i).x, mIdleResult.get(i).y);
		}
		mIdleResult.clear();
	}
	
	/** 检查官 */
	public static interface Procurator{
		void onIdle(float x, float y);
	}
}
