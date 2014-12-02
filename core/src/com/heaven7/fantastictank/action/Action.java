package com.heaven7.fantastictank.action;

public abstract class Action{
	
	private IActor actor;

	/**默认调用*/
	public void reset(){
		
	}
	
    /**每一帧都需要调用,返回true表示apply完成*/
	public abstract boolean apply(float deltaTime);
	
	// ============override ================== //
	

	// ============= like java bean method ============//
	
	public IActor getActor() {
		return actor;
	}
	public void setActor(IActor actor) {
		this.actor = actor;
	}

	
}
