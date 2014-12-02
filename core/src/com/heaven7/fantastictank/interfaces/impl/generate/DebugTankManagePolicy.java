package com.heaven7.fantastictank.interfaces.impl.generate;

import com.heaven7.fantastictank.interfaces.ITankManagePolicy;
/**
 * used for debug
 * @author Administrator
 */
public class DebugTankManagePolicy implements ITankManagePolicy {

	@Override
	public boolean allowGenerateAutoTank(int currentSize) {
		return currentSize <=1;
	}

	@Override
	public boolean allowGenerateTankBoss(int level) {
		return (level % 10 ==0);
	}

}
