package com.heaven7.fantastictank.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.heaven7.fantastictank.matters.Tank;

public class ArrayUtil {
	
	public static final Pool<Rectangle> RECT_CACHE = new Pool<Rectangle>(10) {
		protected Rectangle newObject() {
			return new Rectangle() ;
		}
	}; 
	
	public static <T> boolean contains(T[] ts,T t){
		int len = ts.length;
		for(int i=0; i<len;i++){
			if(ts[i].equals(t)) return true;
		}
		return false;
	}

	public static <T>T[] asArray(T ...types){
		return types; 
	}
	
	/**注意如果是不同类型的--需要自行检查*/
	public static<T extends Tank,O extends Tank> List<Tank> merge(
			List<T> ts1,List<O> ts2){
		List<Tank> ts = new ArrayList<Tank>();
		ts.addAll(ts1);
		ts.addAll(ts2);
		return ts;
	}
	
	/** 合并数组*/
	@SuppressWarnings("unchecked") public static <T,S extends T> T[] merge(S[] t1,S[]t2){
		Object[] ts = new Object[t1.length+t2.length];
		System.arraycopy(t1, 0, ts, 0, t1.length);
		System.arraycopy(t2, 0, ts, t1.length, t2.length);
		return (T[]) ts;
	}
	
	public static Rectangle convert(Vector2 position,float width,float height){
		Rectangle r = RECT_CACHE.obtain();
		r.set(position.x - width/2, position.y-height/2, width, height);
		return r;
	}
	
	public static Array<Rectangle> convert(Vector2[] vecs,float width,float height){
		int len = vecs.length;
		Array<Rectangle> arr = new Array<Rectangle>(len);
		for(int i=0; i<len ;i++){
			arr.add(convert(vecs[i], width, height));
		}
		return arr;
	}
	
}
