package com.heaven7.fantastictank.wisdom;
/**
 * 指派策略
 * @author Administrator
 */
public interface AppointPolicy {

	/**根据总的total分配一定的数量*/
	int allocate(int total);
	
	/**是否过期*/
	boolean overDeadline(int count);
}
