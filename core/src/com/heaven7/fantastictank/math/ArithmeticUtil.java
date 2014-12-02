package com.heaven7.fantastictank.math;

import java.util.Random;

public class ArithmeticUtil {
	
	/** ������ظ��㷨 */
	public static void generateRandomNoRepeat(Random r,int[] srcs,int[] dests){
		if(dests.length > srcs.length){
			throw new IllegalArgumentException("caused by dests.length>srcs.length");
		}
		int count = dests.length;
		
		for(int i=0, startLen = srcs.length; i<count; i++){
			int seed = r.nextInt(startLen-i); //�õ�һ��λ��  
			dests[i]=srcs[seed];              //�õ�λ�õ���ֵ,��ֵ���������
			srcs[seed] = srcs[startLen-1-i];  //�����һ��δ�õ���ֵ�ŵ����ղŵ�λ����
		}
		//System.out.println(Arrays.toString(dests));
    }
}
