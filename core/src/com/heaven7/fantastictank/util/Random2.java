package com.heaven7.fantastictank.util;

import java.util.Random;

public class Random2 {

	private final Random r = new Random();
	private long seed;
	private boolean mReachMin;
	private boolean mReachMax = true ;
	
	public Random2 setSeed(long seed){
		r.setSeed(seed);
		this.seed = seed;
		return this;
	}
	
	public long getSeed(){
		return seed;
	}
	
	/** 改变种子的值，参数是无符号数*/
	public  void changeSeed(int val){
		if(mReachMax){ 
			addSeed(-val);
			if(seed == Long.MIN_VALUE){
				mReachMin =true;
				mReachMax =false;
			}
		}else if(mReachMin){
			addSeed(val);
			if(seed == Long.MAX_VALUE){
				mReachMin =false;
				mReachMax =true;
			}
		}
	}
	
	public Random2 addSeed(long value){
		setSeed(this.seed+value);
		return this;
	}
	
	public int nextInt(){
		return r.nextInt();
	}
	
	public int nextInt(int n){
		return r.nextInt(n);
	}
	
	public int next100(){
		return r.nextInt(100);
	}

	public float nextFloat() {
		return r.nextFloat();
	}
}
