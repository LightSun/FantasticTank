package com.heaven7.fantastictank.wisdom;
/**
 * ָ�ɲ���
 * @author Administrator
 */
public interface AppointPolicy {

	/**�����ܵ�total����һ��������*/
	int allocate(int total);
	
	/**�Ƿ����*/
	boolean overDeadline(int count);
}
