package com.heaven7.fantastictank.support;


/**
 * �������Զ��壬eg: ײ����ض���,��Ӫ��
 * @author Administrator
 */
public class MatterDef {
	
	public static final int FOR_EVER_IF_ALLOW = -1 ;
	public static final int NEVER = 0 ;
	
	public String name="";

	/**���Թ������˵Ĵ���(ÿ�ι������˥��,һ��ֻ���ӵ���Ч,Ϊ0���ӵ�����)��*/
	public int hitCount   =  0;
	
	/**��������һ�㶼��1��boss����*/
	public byte attack = 1;
	
	/**���Ա������Ĵ��� ��Ĭ��4(�ӵ������ڴ�����)*/
	public int beHitCount = 4;
	
	/** �����������ܱ�������������������ü���(Ĭ��ֵ4--��������һ��) */
	public int maxBeHitCount = 4;
	
	/**��ǰ���Կ���Ĵ���,Ĭ��1��1���ӵ������ͻظ�1��,Ҳ���ǿ���һ�λ𣬸��ӵ�δ����������ǲ��ܼ��������*/
	//public final AtomicInteger openFireCount = new AtomicInteger(1) ;//������2���̷߳�����
	
	/**�Ƿ���,Ĭ��true*/
	public boolean alive = true;
	
	/** ��Ӫ ,Ĭ��FactionType.West*/
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
