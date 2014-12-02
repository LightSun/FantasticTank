package com.heaven7.fantastictank.mood;

/**
 * �������Ķ���
 * @author Administrator
 */
public interface Mooder {
	
	byte TYPE_NORMAL = 1;
	byte TYPE_BOSS   = 2;

	void setMood(Mood mood);
	Mood getMood();
	
	/**��һ֡�����������������*/
	void setShootAtOnce(boolean b);
	
	/*** ���������� n���� */
	void addShootProbability(int n);
	/**���ٵ���*/
	void setTrackFoeman(boolean track);
	
	/**������ײʱ,ͬ���ھ�*/
	void setCollideDieTogether(boolean together);
	boolean isCollideDieTogether();
	
	/**���뵽һ��ʱ���Ƿ����� distance ��СΪ 1 */
	void setEscapeByDistance(float distance,boolean escape);
	
	/**
	 * {@link Mooder#TYPE_NORMAL} for common autotank.
	 * <li>{@link Mooder#TYPE_BOSS} for boss. 
	 * <li> boss use the Mood.setNext();
	 * @return
	 */
	byte getType();
}
