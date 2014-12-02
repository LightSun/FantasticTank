package com.heaven7.fantastictank.interfaces;

/**
 * 世界状态监听
 * @author Administrator
 */
public interface WorldStateListener {

	/** 游戏结束*/
	void onGameOver();
	/**当前关卡已过关*/
	void onLevelEnd();
}
