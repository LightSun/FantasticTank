package com.heaven7.fantastictank.support;
/**
 * 隐身工具(假死---即只是不渲染,不检查碰撞而已)
 * @author Administrator
 */
public class HideTool{

	/**隐身或者假死*/
	private boolean hide; 
	private int mFlag;
	private float value;
	
	/** 隐身的次数({@link #hide(int, float)}) */
	private int count;
	
	/**允许隐身的最大次数，-1代表可以任意 隐身*/
	private final int maxCount;
	
	public HideTool(int maxCount) {
		this.maxCount = maxCount;
	}
	public HideTool(){
		this(COUNT_FOR_EVER);
	}

	/** return true,如果隐身 成功 
	 * @param flag {@link #FLAG_DISTANCE} or {@link #FLAG_TIME}*/
	public boolean hide(int flag,float value){
		//隐身次数限制
		if(!allowHide()){
			hide = false;
			return false;
		}
		if(flag !=FLAG_TIME && flag !=FLAG_DISTANCE){
			throw new IllegalArgumentException("wrong flag");
		}
		this.mFlag = flag;
		this.value = value ;
		hide = true;
		count += 1;
		return true;
	}
	
	public void reduceValue(float value){
		this.value -= value;
		if(value <= 0)
			hide = false;
	}
	/**是否允许隐身(通常由于次数限制倒置)*/
	public boolean allowHide(){
		if(maxCount == COUNT_FOR_EVER)
			return true;
		return this.maxCount > count;
	}
	public boolean isHide(){
		return hide && this.value>0;
	}
	public void setHide(boolean hide){
		this.hide = hide;
	}
	
	public int getHideCount(){
		return count;
	}
	public int getFlag(){
		return mFlag;
	}
	
	public void reset(){
		hide = false;
		mFlag = 0;
		value = 0;
		count = 0;
	}
	
	//以时间或者距离来计算隐身后是否到时间显示了
	public static final int FLAG_TIME     = 0x1;  /*隐身标志以时间计算 */
	public static final int FLAG_DISTANCE = 0x2;  /*隐身标志以距离计算 */
	public static final int COUNT_FOR_EVER = -1;
	
}
