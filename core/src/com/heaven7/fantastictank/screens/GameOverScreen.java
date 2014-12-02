package com.heaven7.fantastictank.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pools;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.level.GameOverLevel;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.support.AbsGame;
import com.heaven7.fantastictank.support.DefaultScreen;
/**
 * gameover,but 绘制有问题？无法绘制出来
 * @author Administrator
 */
@Deprecated
public class GameOverScreen extends DefaultScreen {
	
	OrthographicCamera guiCam; //正交摄像机
	SpriteBatch batcher;
	List<SmallBrickWall> walls;

	public GameOverScreen(AbsGame game) {
		super(game);
		guiCam = new OrthographicCamera(Constant.VIEWPORT_WIDTH, Constant.VIEWPORT_HEIGHT);
	    guiCam.position.set(Constant.VIEWPORT_WIDTH/2, Constant.VIEWPORT_HEIGHT/2, 0);
	    batcher = Pools.obtain(SpriteBatch.class);
	    
	    GameOverLevel level = new GameOverLevel();
	    level.build();
	    this.walls = level.getSmallBrickWalls();
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
		
		renderGameOver();
		
		batcher.end();
	}

	private void renderGameOver() {
		int size = walls.size();
		for (int i = 0; i < size; i++) {
			SmallBrickWall wall = walls.get(i);
			batcher.draw(Resource.smallBrickWall, wall.bounds.x, wall.bounds.y,
					wall.bounds.width, wall.bounds.height);
		}
	}

	@Override
	public void update(float delta) {

	}

}
