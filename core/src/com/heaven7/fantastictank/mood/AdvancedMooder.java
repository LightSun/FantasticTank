package com.heaven7.fantastictank.mood;
/**
 * 拥有高级情绪的
 * @author Administrator
 */
public interface AdvancedMooder extends Mooder {
	
	/**设置是否主动攻击(激活主动攻击)*/
	void setActivateAttack(boolean driving);

	/** 生命值缩小到指定的百分比 */
	void scaleLife(float rate);

	/** 绝对防御(吸收所有伤害) */
	void setAbsoluteDefense(boolean real);

	/** 攻击力缩放到指定的倍数，may >1 */
	void scaleAttack(float f);
	
	/** 防御缩放到指定的倍数，may >1 */
	void scaleDefense(float f);

	/** 恢复所有 属下的生命值 */
	void restoreChildrenLife();
}
