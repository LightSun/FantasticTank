package com.heaven7.fantastictank.util;

import com.heaven7.fantastictank.action.FlickeringAction;
import com.heaven7.fantastictank.action.IActor;

public class ActionHelper {

	public static FlickeringAction flickering(IActor actor,float duration){
		FlickeringAction a = new FlickeringAction();
		a.setActor(actor);
		a.setDuration(duration);
		return a;
	}
}
