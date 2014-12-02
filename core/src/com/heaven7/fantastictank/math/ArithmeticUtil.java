package com.heaven7.fantastictank.math;

import java.util.Random;

public class ArithmeticUtil {
	
	/** 随机不重复算法 */
	public static void generateRandomNoRepeat(Random r,int[] srcs,int[] dests){
		if(dests.length > srcs.length){
			throw new IllegalArgumentException("caused by dests.length>srcs.length");
		}
		int count = dests.length;
		
		for(int i=0, startLen = srcs.length; i<count; i++){
			int seed = r.nextInt(startLen-i); //得到一个位置  
			dests[i]=srcs[seed];              //得到位置的数值,赋值给结果数组
			srcs[seed] = srcs[startLen-1-i];  //将最后一个未用的数值放到，刚才的位置上
		}
		//System.out.println(Arrays.toString(dests));
    }
}
