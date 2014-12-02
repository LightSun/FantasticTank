package com.heaven7.fantastictank.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.bean.SkillCount;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.support.AbsGame;
import com.heaven7.fantastictank.support.DefaultScreen;
//like  LEVEL  1
public class LevelScreen extends DefaultScreen {
	
	static final String LEVEL = "STAGE     ";
	
	OrthographicCamera guiCam; //Õý½»ÉãÏñ»ú
	SpriteBatch batcher;
	
	public LevelScreen(AbsGame game) {
		super(game);
		guiCam = new OrthographicCamera(Constant.VIEWPORT_WIDTH, Constant.VIEWPORT_HEIGHT);
	    guiCam.position.set(Constant.VIEWPORT_WIDTH/2, Constant.VIEWPORT_HEIGHT/2, 0);
	    batcher = new SpriteBatch();
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {

	}

	@Override
	public void draw(float delta) {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glClearColor(0.2f, 0.5f, 0.5f, 0.33f);
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.enableBlending();
		batcher.begin();
		
		String s = LEVEL + SkillCount.Level;
		float levelWidth = Resource.font.getBounds(s).width;
		float levelHeight = Resource.font.getBounds(s).height;
		Resource.font.draw(batcher, s, Constant.VIEWPORT_WIDTH/2 -levelWidth/2 , 
				Constant.VIEWPORT_HEIGHT/2 - levelHeight/2);
		batcher.end();
	}

	@Override
	public void update(float delta) {
		if (Gdx.input.justTouched()) {
			Resource.playSound(Resource.enter_game);
			GameScreen gs;
			Screen s = getGlobalScreen();
			if(s instanceof GameScreen){
				gs = (GameScreen) s;
				setGlobalScreen(null);
			}else
				gs = new GameScreen(game);
			game.setScreen(gs);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		batcher.dispose();
	}
}
