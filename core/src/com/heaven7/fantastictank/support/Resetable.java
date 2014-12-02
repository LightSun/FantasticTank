package com.heaven7.fantastictank.support;
/**
 * 可重置的
 * @author Administrator
 */
public interface Resetable {

	void reset();
	/** 标记是否存在池子中 (用于坦克死亡后,可攻击标记问题)---仍然有bug：eg，坦克死亡了，马上被复用时，之前射击出去的子弹还没死亡...*/
	//void setPooled(boolean pooled);
	//boolean isPooled();
}
