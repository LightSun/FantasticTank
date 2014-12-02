package com.heaven7.fantastictank.util;

import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Explode;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Wood;
import com.heaven7.fantastictank.support.GameObject;
/**
 * 只能缓存GameObject子类
 * @author Administrator
 */
public class CacheHelper {
     
	private static final ObjectMap<Class<?>, Pool<?>> POOLS = new ObjectMap<Class<?>, Pool<?>>(8);
	
	private static final Pool<BrickWall> sBrickWallPool = new Pool<BrickWall>() {
		protected BrickWall newObject() {
			return new BrickWall(0, 0);
		}
	};
	private static final Pool<SmallBrickWall> sSmallBrickWallPool = new Pool<SmallBrickWall>() {
		protected SmallBrickWall newObject() {
			return new SmallBrickWall(0, 0);
		}
	};
	private static final Pool<DirtWall> sDirtWallPool = new Pool<DirtWall>() {
		protected DirtWall newObject() {
			return new DirtWall(0, 0);
		}
	};
	private static final Pool<Wood> sWoodPool = new Pool<Wood>() {
		protected Wood newObject() {
			return new Wood(0, 0);
		}
	};
	private static final Pool<Lake> sLakePool = new Pool<Lake>() {
		protected Lake newObject() {
			return new Lake(0, 0);
		}
	};
	private static final Pool<AutoTank> sAutoTankPool = new Pool<AutoTank>() {
		protected AutoTank newObject() {
			return new AutoTank(0, 0);
		}
	};
	private static final Pool<Explode> sExplodePool = new Pool<Explode>() {
		protected Explode newObject() {
			return new Explode(0, 0);
		}
	};
	private static final Pool<Bullet> sBulletPool = new Pool<Bullet>() {
		protected Bullet newObject() {
			return new Bullet(0, 0);//注意width 和 height
		}
	};
	static{
		POOLS.put(BrickWall.class, sBrickWallPool);
		POOLS.put(DirtWall.class, sDirtWallPool);
		POOLS.put(Lake.class, sLakePool);
		POOLS.put(Wood.class, sWoodPool);
		POOLS.put(AutoTank.class, sAutoTankPool);
		POOLS.put(Explode.class, sExplodePool);
		POOLS.put(Bullet.class, sBulletPool);
		POOLS.put(SmallBrickWall.class, sSmallBrickWallPool);
	}
	
	@SuppressWarnings("unchecked")
	private static <T>Pool<T> getPool(Class<T> clazz){
		Pool<T> pool = (Pool<T>) POOLS.get(clazz);
		if(pool==null)
			throw new RuntimeException("the class's pool didn't exist! class ="+clazz.getName());
		return pool;
	}
	
	/**如果该对象的pool存在则 缓存并返回true,否则false(null 也是)*/
	public static <T extends GameObject> boolean freeSafe(T t){
		if(t==null) return false;
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) t.getClass();
		if(POOLS.containsKey(clazz)){
			if(t.callPoolEvents())   t.clearPoolPoolEvents();
			getPool(clazz).free(t);
			return true;
		}
		return false;
	}
	
	public static <T extends GameObject>boolean freeAll(Class<T> clazz,List<T> ts){
		if(ts.size() ==0) return false;
		Pool<T> pool = getPool(clazz);
		if(pool ==null) return false;
		for(int i=0,size=ts.size(); i<size ; i++){
			T t = ts.get(i);
			if( t.callPoolEvents()) t.clearPoolPoolEvents();
			pool.free(t);
		}
		return true;
	}
	
	public static <T extends GameObject>boolean freeAll(Class<T> clazz,Array<T> arr){
		if(arr.size ==0) return false;
		Pool<T> pool = getPool(clazz);
		if(pool==null) return false;
		for(int i=0,size =arr.size ;i<size ;i++){
		    T t = arr.get(i);
		    if(t.callPoolEvents())   t.clearPoolPoolEvents();
		    pool.free(t);
		}
		return true;
	}
	/** 内部会自动调用 reset()*/
	public static <T extends GameObject>T obtain(Class<T> clazz){
		T t = getPool(clazz).obtain();
		t.reset();
		return t;
	}
	/** 内部会自动调用 reset()*/
	public static <T  extends GameObject>T obtain(float x,float y,Class<T> clazz){
		T t = obtain(clazz);
		t.setPosition(x, y);
		return t;
	}
	public static <T  extends GameObject>T obtain(float x,float y,
			          float width,float height,Class<T> clazz){
		T t = obtain(clazz);
		t.setPosition(x, y);
		t.setBounds(width, height);
		return t;
	}
}
