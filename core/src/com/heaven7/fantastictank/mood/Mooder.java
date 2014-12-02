package com.heaven7.fantastictank.mood;

/**
 * 有情绪的对象
 * @author Administrator
 */
public interface Mooder {
	
	byte TYPE_NORMAL = 1;
	byte TYPE_BOSS   = 2;

	void setMood(Mood mood);
	Mood getMood();
	
	/**下一帧立即攻击（如果允许）*/
	void setShootAtOnce(boolean b);
	
	/*** 添加射击概率 n个点 */
	void addShootProbability(int n);
	/**跟踪敌人*/
	void setTrackFoeman(boolean track);
	
	/**设置碰撞时,同归于尽*/
	void setCollideDieTogether(boolean together);
	boolean isCollideDieTogether();
	
	/**距离到一定时，是否逃跑 distance 最小为 1 */
	void setEscapeByDistance(float distance,boolean escape);
	
	/**
	 * {@link Mooder#TYPE_NORMAL} for common autotank.
	 * <li>{@link Mooder#TYPE_BOSS} for boss. 
	 * <li> boss use the Mood.setNext();
	 * @return
	 */
	byte getType();
}
