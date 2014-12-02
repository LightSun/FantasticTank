package com.heaven7.fantastictank.support.policy;

import com.heaven7.fantastictank.matters.Tank.TankType;

public abstract class TankTypePolicy {
	private static final TankTypePolicy DEFAULT = new TankTypePolicyImpl();

	private TankType type;
	
	public TankTypePolicy(){}
	public TankTypePolicy(TankType type){
		this.type = type;
	}
	
	public TankType getTankType(){
		return type;
	}
	public void setTankType(TankType type){
		this.type =type;
	}
	
	public static TankTypePolicy get(TankType type){
		DEFAULT.setTankType(type);
		return DEFAULT;
	}
	
	/** �����ٶ� */
	public abstract float getBaseSpeed();
	
	/** ����ɱʱ����÷� */
	public abstract int computeScore();
}
