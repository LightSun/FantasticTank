package com.heaven7.fantastictank.interfaces;
/**
 * Tank �������
 * @author heaven7
 */
public interface ITankManagePolicy {

	 boolean allowGenerateAutoTank(int currentSize);
	 /** @param level ��ǰ���ٹؿ�*/
	 boolean allowGenerateTankBoss(int level);
}
