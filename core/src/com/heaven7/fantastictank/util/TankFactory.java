package com.heaven7.fantastictank.util;

import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.MyTank;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.policy.BulletTypePolicy;
import com.heaven7.fantastictank.support.policy.TankTypePolicy;

public class TankFactory {

	public static AutoTank createAutoTank(float x,float y,Direction dir,
			TankTypePolicy tPolicy,BulletTypePolicy policy){
		AutoTank tank = new AutoTank(x, y);
		tank.setDirection(dir);
		tank.setTankType(tPolicy.getTankType());
		tank.setBulletType(policy.getBulletType());
		tank.setBulletTypePolicy(policy);
		tank.setVelocity(tPolicy.getBaseSpeed());
		return tank;
	}
	
	public static MyTank createMyTank(float x,float y,Direction dir,
			TankTypePolicy tPolicy,BulletTypePolicy policy){
		MyTank tank = new MyTank(x, y);
		tank.setDirection(dir);
		tank.setTankType(tPolicy.getTankType());
		tank.setBulletType(policy.getBulletType());
		tank.setBulletTypePolicy(policy);
		tank.setVelocity(tPolicy.getBaseSpeed());
		return tank;
	}
}
