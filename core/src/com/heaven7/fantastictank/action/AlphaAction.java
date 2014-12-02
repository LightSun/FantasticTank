package com.heaven7.fantastictank.action;

import com.badlogic.gdx.graphics.Color;

public class AlphaAction extends TemporalAction {
	
	private float startAlpha;
	private float endAlpha;
	private Color color;
	
	@Override
	protected void begin(){
		this.color = getActor().getColor();
		this.startAlpha = this.color.a;
	}

	@Override
	protected void update(float percent) {
		color.a = startAlpha+(endAlpha - startAlpha) *percent;
	}
	
	@Override
	public void reset() {
		super.reset();
		color = null;
	}
	
	/** 设置 透明度，直接影响alphaEnd(在{@link #apply(long)} 之前必须先调用)*/
	public void setAlpha(float endAlpha){
		this.endAlpha = endAlpha;
	}

}
