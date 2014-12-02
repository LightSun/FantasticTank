package com.heaven7.fantastictank.interfaces.impl;

import com.heaven7.fantastictank.interfaces.CollideHandlePolicy;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;
/**
 * @author Administrator
 */
public class CollideHanldePolicyImpl implements CollideHandlePolicy {

	int count;
	int count2;
	@Override
	public <T extends Tank> void handle(T t, StaticObject obj) {
		count +=1;
		 t.stay();
		if(count ==10){
			t.randowDirection(false);
			count = 0;
		}
	}
	
	@Override
	public <T extends Tank> void handle(T t, Tank other) {
		boolean oneManual = false;
		boolean otherManual = false;
		
		if(t.isManual())
			oneManual = true;
		else
			t.stay();
		
		if(other.isManual())
			otherManual = true;
		else  
			other.stay();
		//都是人工控制的---不再继续处理
		if(oneManual && otherManual)  return;
		
		final boolean onePaused = t.isPaused();
		final boolean otherPaused = other.isPaused();
		count2++;
		if(count2 == 10){
			if(!oneManual && !onePaused){
			    t.switchDirection(t.getDirection().reverse());
			}else if(!otherManual && !otherPaused){
				other.switchDirection(other.getDirection().reverse());
			}
			count2 = 0;
		}else{
			if(count2 % 9 ==8){
				if(!oneManual && !onePaused)
				     t.randowDirection(false);
				else if(!otherManual && !otherPaused)
					 other.randowDirection(false);
			}
		}
	}

}
