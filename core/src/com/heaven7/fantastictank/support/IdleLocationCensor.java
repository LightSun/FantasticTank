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
 * ����λ��--���Ա(���󽫽���ύ������)
 * @author Administrator
 */
public class IdleLocationCensor {
	
	private final Array<Vector2> mIdleResult = new Array<Vector2>();
	private final Procurator mProcurator;
	
	/**���Ա�����ĵĽ�����������*/
	public IdleLocationCensor(Procurator mProcurator) {
		super();
		this.mProcurator = mProcurator;
	}

	/** ���autotank�ı���λ�� 
	 * @param ts  ����Ҫ���Ķ���*/
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
	
	/** ���� */
	public static interface Procurator{
		void onIdle(float x, float y);
	}
}
