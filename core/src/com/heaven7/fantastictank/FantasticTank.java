package com.heaven7.fantastictank;

import com.badlogic.gdx.graphics.FPSLogger;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.res.Settings;
import com.heaven7.fantastictank.screens.LevelScreen;
import com.heaven7.fantastictank.support.AbsGame;

public class FantasticTank extends AbsGame{

	FPSLogger fps;
	
	@Override
	public void create() {
		Resource.load();
		Settings.load();
		fps = new FPSLogger();
		
		/*InputMultiplexer multiplexer = new InputMultiplexer();
		EventHandlerTester eventHandler = new EventHandlerTester();
		multiplexer.addProcessor(eventHandler);
		multiplexer.addProcessor(new GestureDetector(eventHandler));*/
		
		setScreen(new LevelScreen(this));
		//setScreen(new KilledCountScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
		//fps.log();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		getScreen().dispose();
	}

}
