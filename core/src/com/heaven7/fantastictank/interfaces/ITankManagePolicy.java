package com.heaven7.fantastictank.interfaces;
/**
 * Tank 管理策略
 * @author heaven7
 */
public interface ITankManagePolicy {

	 boolean allowGenerateAutoTank(int currentSize);
	 /** @param level 当前多少关卡*/
	 boolean allowGenerateTankBoss(int level);
}
