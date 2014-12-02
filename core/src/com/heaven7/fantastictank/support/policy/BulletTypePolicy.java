package com.heaven7.fantastictank.support.policy;

import java.util.List;

import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.MatterDef;
/**
 * 发射的子弹类型的策略
 * @author Administrator
 */
public abstract class BulletTypePolicy {
	
	public static final int FOR_EVER_IF_POSSIBBLE = -1;
	
	private BulletType mType;
	
	public BulletTypePolicy(){}
	public BulletTypePolicy(BulletType type) {
		this.mType = type;
	}
	public BulletType getBulletType() {
		return mType;
	}
	public void setBulletType(BulletType type){
		this.mType = type;
	}
	
	public abstract float getFlightLength();
	
	/**获取攻击的次数 {@link MatterDef#hitCount},反弹类子弹很有用*/
	public abstract int getHitCount();
	
	/**【路径类型方有效】  获取为每种子弹约定的方向集合---将表示子弹如何运动 (x,y代表子弹诞生时的坐标)
	 * <li> 参数代表了离上下左右的距离
	 * @param startDir 起始方向/坦克方向
	 * @throws UnsupportedOperationException 如果当前子弹类型不支持*/
	public abstract List<Direction> getDirectionList(Direction startDir,
			float up,float down,float left,float right) 
					throws UnsupportedOperationException;
}
