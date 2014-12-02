package com.heaven7.fantastictank.bean;

import com.heaven7.fantastictank.Constant;

/**
 * 技术统计
 * @author Administrator
 */
public class SkillCount {
    //得分，击杀统计 ,投靠的数量,最后到达的关卡，复活次数
	public int score;
	public int killedCommon;
	public int killedBossCount;
	
	public int friends;
	public static int Level = 1;
	public int relive = Constant.RELIVE_COUNT_COMMON;
	
	private static final SkillCount COUNT1 = new SkillCount();
	private static final SkillCount COUNT2 = new SkillCount();
	
	private SkillCount(){}
	
	public static SkillCount get1(){
		return COUNT1;
	}
	public static SkillCount get2(){
		return COUNT2;
	}

	public static void resetAll() {
		Level = 1;
		COUNT1.reset();
		COUNT2.reset();
	}
	private void reset(){
		 score = 0;
		 killedCommon = 0;
		 killedBossCount =0;
		 friends = 0;
		 relive = Constant.RELIVE_COUNT_COMMON;
	}
}
