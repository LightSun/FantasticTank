package com.heaven7.fantastictank.support;


/**
 * 事物属性定义，eg: 撞击相关定义,阵营等
 * @author Administrator
 */
public class MatterDef {
	
	public static final int FOR_EVER_IF_ALLOW = -1 ;
	public static final int NEVER = 0 ;
	
	public String name="";

	/**可以攻击敌人的次数(每次攻击后会衰减,一般只有子弹有效,为0则子弹死亡)，*/
	public int hitCount   =  0;
	
	/**攻击力，一般都是1，boss特殊*/
	public byte attack = 1;
	
	/**可以被攻击的次数 ，默认4(子弹不存在此属性)*/
	public int beHitCount = 4;
	
	/** 单个方向上能被攻击的最大次数，计算裁剪的(默认值4--横竖方向都一样) */
	public int maxBeHitCount = 4;
	
	/**当前可以开火的次数,默认1（1个子弹死亡就回复1）,也就是开了一次火，该子弹未死亡情况下是不能继续开火的*/
	//public final AtomicInteger openFireCount = new AtomicInteger(1) ;//至少有2个线程访问了
	
	/**是否存活,默认true*/
	public boolean alive = true;
	
	/** 阵营 ,默认FactionType.West*/
	public FactionType type = FactionType.West;
	
	public void reset(){
		name = "";
		hitCount   = 0;
		beHitCount = 4;
		maxBeHitCount = 4;
		alive = true;
		type = FactionType.West;
		attack = 1;
	}
	
}
