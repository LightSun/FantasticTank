package com.heaven7.fantastictank.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.bean.SkillCount;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.res.Settings;
import com.heaven7.fantastictank.support.AbsGame;
import com.heaven7.fantastictank.support.DefaultScreen;
/**
 * 技术统计screen
 * @author Administrator
 */
public class KilledCountScreen extends DefaultScreen {
	
	OrthographicCamera guiCam; //正交摄像机
	SpriteBatch batcher;
	private long startTime;
	
	static final int DELTA_HEIGHT = 20;
	static final String HIGH_SCORE ="HIGH-SCORE       ";
	static final String LEVEL      ="STAGE    ";
	static final String PLAYER_1   ="PLAYER_1";
	
	static final String SCORE      ="";
	static final String KILL_NORMAL="Kill Normal ";
	static final String KILL_BOSS  ="Kill Boss ";
	static final String RELIVE     ="Relive ";
	static final String BABY       ="Baby "; //BB=俘虏的叛军

	public KilledCountScreen(AbsGame game) {
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
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.enableBlending();
		batcher.begin();
		
		drawSkillCount();
		batcher.end();
	}

	private void drawSkillCount() {
		String highScore = HIGH_SCORE + Settings.highscores[0];
		String level = LEVEL + SkillCount.Level;
		
		String player1 = PLAYER_1;
		String score = SCORE + SkillCount.get1().score;
		String killedNormal = KILL_NORMAL + SkillCount.get1().killedCommon;
		String killedBoss   = KILL_BOSS + SkillCount.get1().killedBossCount;
		String relive = RELIVE + SkillCount.get1().relive;
		String baby = BABY + SkillCount.get1().friends;
		
		 final BitmapFont font = Resource.font;
		 final Color color = font.getColor();
		
		 TextBounds highScoreBounds = font.getBounds(highScore);
		 TextBounds levelBounds = font.getBounds(level);
		 TextBounds player1Bounds = font.getBounds(player1);
		 TextBounds scoreBounds = font.getBounds(score);
		 TextBounds killNormalBounds = font.getBounds(killedNormal);
		 TextBounds killBossBounds = font.getBounds(killedBoss);
		 TextBounds reliveBounds = font.getBounds(relive);
		 TextBounds babyBounds = font.getBounds(baby);
		 
		 float base = Constant.VIEWPORT_HEIGHT - 40;
		 
		 font.setColor(Color.RED);
		 font.draw(batcher, highScore, Constant.VIEWPORT_WIDTH/2- highScoreBounds.width/2, base);
		
		 font.setColor(Color.WHITE);
		 font.draw(batcher, level, Constant.VIEWPORT_WIDTH/3 , 
				 (base=base - highScoreBounds.height - DELTA_HEIGHT));
		 
		 font.setColor(Color.RED);
		 font.draw(batcher, player1, 50, 
				 (base = base - levelBounds.height - DELTA_HEIGHT));
		 
		 font.setColor(Color.ORANGE);
		 font.draw(batcher, score, 50 + player1Bounds.width - scoreBounds.width , 
				 (base = base - player1Bounds.height - DELTA_HEIGHT));
		 
		 font.setColor(Color.WHITE);
		 font.draw(batcher, killedNormal, 60, (base = base - scoreBounds.height - 1.5f*DELTA_HEIGHT));
		 font.draw(batcher, killedBoss, 60, (base = base - killNormalBounds.height - 1.5f*DELTA_HEIGHT));
		 
		 font.draw(batcher, relive, 60, (base = base - killBossBounds.height - 2*DELTA_HEIGHT));
		 font.draw(batcher, baby, 60, (base = base - reliveBounds.height - 1.5f*DELTA_HEIGHT));
		 
		 font.setColor(color);
	}


	@Override
	public void update(float delta) {
		if(startTime == 0)
		    startTime = System.currentTimeMillis();
		if((System.currentTimeMillis() - startTime)> 10000){
			game.putData(Constant.KEY_GAME_STATE, getData(Constant.KEY_GAME_STATE));
			clearData();
			game.setScreen(getGlobalScreen());
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		batcher.dispose();
	}

}
