package com.heaven7.fantastictank.action;

import com.badlogic.gdx.graphics.Color;
/**
 * ÉÁË¸µÄaction
 * @author Administrator
 */
public class FlickeringAction extends TemporalAction {

	private boolean add;
	private float lastPercent;
	
	private Color c;
	private long startTime;
	
	@Override
	protected void begin(){
		this.c = getActor().getColor();
		add = c.r < 0.5f;
		startTime = System.currentTimeMillis();
	}
	
	@Override
	protected void update(float percent) {
		 final float dp = (percent - lastPercent)*getDuration()*5;
		 //System.out.println("dp = "+dp);
		 if(add){
			 c.add(dp, dp, dp, 0);
			 if(c.r == 1){
				 c.sub(dp*2, dp*2, dp*2, 0);
				 add = false;
			 }
		 }else{
			 c.sub(dp, dp, dp, 0);
			 if(c.r==0){
				 c.add(dp*2, dp*2, dp*2, 0);
				 add = true;
			 }
		 }
         lastPercent = percent;
	}
	@Override
	protected void end() {
		super.end();
		System.err.println("ÉÁË¸ cost time = "+(System.currentTimeMillis() - startTime)/1000+"s");
	}
	
	@Override
	public void reset() {
		super.reset();
		lastPercent = 0;
	}
}
