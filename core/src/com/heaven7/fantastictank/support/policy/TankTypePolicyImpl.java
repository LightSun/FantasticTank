package com.heaven7.fantastictank.support.policy;

import com.heaven7.fantastictank.matters.Tank.TankType;

public class TankTypePolicyImpl extends TankTypePolicy {

	public TankTypePolicyImpl(){}
	public TankTypePolicyImpl(TankType type) {
		super(type);
	}

	@Override
	public float getBaseSpeed() {
		switch (getTankType()) {
		case Honest: //TODO debug全部4
			return 2f;
		
		case Fast:
			return 4f;
			
		case Slower:
			return 4f;
			
		case Solid:
			return 4f;
			
		case King:
			//TODO 王者级别坦克

		default:
			return 5f;
		}
	}

	@Override
	public int computeScore() {
		switch (getTankType()) {
		case Honest: 
			return 0;
		
		case Fast:
			return 300;
			
		case Slower:
			return 100;
			
		case Solid:
			return 400;
			
		case King:
			return 10000;

		default:
			throw new RuntimeException();
		}
	}

}
