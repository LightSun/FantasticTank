package com.heaven7.fantastictank.support;
/**
 * ������(����---��ֻ�ǲ���Ⱦ,�������ײ����)
 * @author Administrator
 */
public class HideTool{

	/**������߼���*/
	private boolean hide; 
	private int mFlag;
	private float value;
	
	/** ����Ĵ���({@link #hide(int, float)}) */
	private int count;
	
	/**�����������������-1����������� ����*/
	private final int maxCount;
	
	public HideTool(int maxCount) {
		this.maxCount = maxCount;
	}
	public HideTool(){
		this(COUNT_FOR_EVER);
	}

	/** return true,������� �ɹ� 
	 * @param flag {@link #FLAG_DISTANCE} or {@link #FLAG_TIME}*/
	public boolean hide(int flag,float value){
		//�����������
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
	/**�Ƿ���������(ͨ�����ڴ������Ƶ���)*/
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
	
	//��ʱ����߾���������������Ƿ�ʱ����ʾ��
	public static final int FLAG_TIME     = 0x1;  /*�����־��ʱ����� */
	public static final int FLAG_DISTANCE = 0x2;  /*�����־�Ծ������ */
	public static final int COUNT_FOR_EVER = -1;
	
}
