package com.heaven7.fantastictank.action;

public abstract class Action{
	
	private IActor actor;

	/**Ĭ�ϵ���*/
	public void reset(){
		
	}
	
    /**ÿһ֡����Ҫ����,����true��ʾapply���*/
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
