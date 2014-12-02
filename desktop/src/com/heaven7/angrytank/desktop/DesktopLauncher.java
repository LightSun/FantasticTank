package com.heaven7.angrytank.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.FantasticTank;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)Constant.VIEWPORT_WIDTH;
		config.height = (int) Constant.VIEWPORT_HEIGHT;
		config.title = "Fantastic Tank";
		new LwjglApplication(new FantasticTank(), config);
	}
}
