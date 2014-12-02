package com.heaven7.fantastictank.interfaces.impl.generate;

import com.heaven7.fantastictank.interfaces.ITankManagePolicy;

public class DefaultTankManagePolicy implements ITankManagePolicy {

	@Override
	public boolean allowGenerateAutoTank(int currentSize) {
		return currentSize <=6;
	}

	@Override
	public boolean allowGenerateTankBoss(int level) {
		return (level % 10 ==0);
	}

}
