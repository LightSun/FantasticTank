package com.heaven7.fantastictank.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.GameWorld;
import com.heaven7.fantastictank.WorldRender;
import com.heaven7.fantastictank.bean.SkillCount;
import com.heaven7.fantastictank.interfaces.ILevelGenerator;
import com.heaven7.fantastictank.interfaces.WorldStateListener;
import com.heaven7.fantastictank.interfaces.impl.AudioPlayWorldListener;
import com.heaven7.fantastictank.interfaces.impl.WorldEventListenerAdapter;
import com.heaven7.fantastictank.interfaces.impl.WorldListenerGroup;
import com.heaven7.fantastictank.level.GameOverLevel;
import com.heaven7.fantastictank.level.RandomLevel;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.MyTank;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.res.Settings;
import com.heaven7.fantastictank.support.AbsGame;
import com.heaven7.fantastictank.support.DefaultScreen;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.GameState;
import com.heaven7.fantastictank.support.policy.TankTypePolicy;
/**
 * 只锟斤拷{@link InputProcessor} 锟斤拷锟斤拷 {@link GestureListener} 
 * 锟睫凤拷实锟斤拷锟斤拷锟斤拷锟斤拷拽锟斤拷锟斤拷锟街碉拷锟酵憋拷锟斤拷锟斤拷锟叫э拷锟�
 * @author Administrator
 */
public class GameScreen extends DefaultScreen{
	
	static final String SCORE ="Score ";
	static final String LIFE  ="Relife ";
	static final String LEVEL ="Level  ";
	
	GameState state;
	GameWorld world;
	WorldRender renderer;
	
	OrthographicCamera guiCam; //锟斤拷锟斤拷锟斤拷锟斤拷锟�
	Vector3 touchPoint;
	SpriteBatch batcher;
	
	
	public GameScreen(AbsGame game) {
		super(game);
		state = GameState.Ready;
		guiCam = new OrthographicCamera(Constant.VIEWPORT_WIDTH, Constant.VIEWPORT_HEIGHT);
	    guiCam.position.set(Constant.VIEWPORT_WIDTH/2, Constant.VIEWPORT_HEIGHT/2, 0);
	    touchPoint = Pools.obtain(Vector3.class);
	    batcher = Pools.obtain(SpriteBatch.class);
	    
	    WorldListenerGroup group = new WorldListenerGroup();
	    group.add(new AudioPlayWorldListener());
	    group.add(scoreListener);
	    world = new GameWorld(group);
	    world.setStateListener(mStateListener);
		renderer = new WorldRender(batcher,world);
	}

	@Override
	public void draw(float delta) {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glClearColor(0, 0, 0, 1);

		renderer.render();

		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.enableBlending();
		batcher.begin();
		switch (state) {
		case Ready:
			presentReady();
			break;
		case Running:
			presentRunning();
			break;
		case Paused:
			presentPaused();
			break;
		case LevelEnd:
			presentLevelEnd();
			break;
		case GameOver:
			presentGameOver();
			break;
		}
		batcher.end();
		//ScreenshotFactory.saveScreenshot("c:/");//for desktop
		//ScreenshotFactory.saveScreenshot("/sdcard/images");
		//gl.glReadPixels(x, y, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pixels);
	}
	
	private void presentGameOver() {
		// TODO Auto-generated method stub
		
	}

	private void presentLevelEnd() {
		// TODO Auto-generated method stub
		
	}

	private void presentPaused() {
		// TODO Auto-generated method stub
		
	}

	private void presentReady() {
		// TODO Auto-generated method stub
		
	}

	private void presentRunning() {
		// 锟矫分ｏ拷锟截匡拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		String scoreStr = SCORE + SkillCount.get1().score;
		String LevelStr = LEVEL + SkillCount.Level;
		String lifeStr = LIFE + SkillCount.get1().relive;
		//float scoreHeight = Resource.font.getBounds(scoreStr).height;
		float levelWidth = Resource.font.getBounds(LevelStr).width;
		float levelHeight = Resource.font.getBounds(LevelStr).height;
		//float lifeWidth = Resource.font.getBounds(lifeStr).width;
		
		Resource.font.draw(batcher, scoreStr, 20 , Constant.VIEWPORT_HEIGHT -10);
		Resource.font.draw(batcher, LevelStr, Constant.VIEWPORT_WIDTH - 20-levelWidth , Constant.VIEWPORT_HEIGHT -10);
		Resource.font.draw(batcher, lifeStr, Constant.VIEWPORT_WIDTH - 20 -levelWidth , Constant.VIEWPORT_HEIGHT -20-levelHeight);
	}

	@Override
	public void update(float delta) {
		switch (state) {
		case Ready:
			updateReady();
			break;
		case Running:
			updateRunning(delta);
			break;
		case Paused:
			updatePaused();
			break;
		case LevelEnd:
			updateLevelEnd();
			break;
		case GameOver:
			updateGameOver();
			break;
		}
	}
	
	private void updateRunning(float delta) {
		/*if (Gdx.input.justTouched()) {
			for(int i=0;i<20;i++){
				if(!Gdx.input.isTouched(i)) continue;
				//锟斤拷touchpoint锟斤拷锟斤拷转锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
				guiCam.unproject(touchPoint.set(Gdx.input.getX(i), Gdx.input.getY(i), 0));
				if(touchPoint.x < Constant.VIEWPORT_WIDTH/2)
					continue;
				//锟斤拷锟�(锟揭帮拷锟斤拷锟斤拷)
				if(world.mMytanks.size()>0){
					Bullet b = world.mMytanks.get(0).shoot();
					if(b!=null)
					    world.mGoodBullets.add(b);
				}
				//锟斤拷锟节匡拷锟斤拷锟斤拷锟接筹拷锟斤拷
			}
		}*/
		world.update(delta);
	}
	
	private boolean shootIfNeed(){
		if(world.mMytanks.size()>0){
			Bullet b = world.mMytanks.get(0).shoot();
			if(b!=null)
			    return world.mGoodBullets.add(b);
		}
		return false;
	}

	private void updateGameOver() {
		world.mMytanks.clear();
		world.setLevelGenerator(new GameOverLevel());
		world.reset(true);
		if (Gdx.input.justTouched()){
			setGlobalScreen(this);
			game.setScreen(new LevelScreen(game));
		}
	}

	private void updateLevelEnd() {
		// TODO Auto-generated method stub
		
	}

	private void updatePaused() {
		
	}

	private void updateReady() {
		state = GameState.Running;
	}
	
	@Override
	public void pause() {
		if (state == GameState.Running) state = GameState.Paused;
	}

	@Override
	public void resume() {
		if (state == GameState.Paused) state = GameState.Running;
	}
	
	@Override
	public void show() {
		if(getData(Constant.KEY_GAME_STATE)!=null){
		    state = (GameState) removeData(Constant.KEY_GAME_STATE);
		    SkillCount.resetAll();
		}else{
			GESTURE_GROUP.addAll(shootListener,moveListener);
		    state = GameState.Ready;
		    if (Settings.soundEnabled)
				Resource.gameMusic.play();
		    //TODO debug 锟斤拷为锟斤拷式
		    ILevelGenerator levelGenerator = new RandomLevel();
			//ILevelGenerator levelGenerator = new DebugLevel();
			//ILevelGenerator levelGenerator = new FontLevel();
			world.setLevelGenerator(levelGenerator);
			world.reset(false);
		}
	}
	
	@Override
	public void hide() {
		GESTURE_GROUP.clear();
		if(Resource.gameMusic.isPlaying())
		      Resource.gameMusic.stop();
	}
	
	@Override
	public void dispose() {
		batcher.dispose();
	}
	
	private final WorldEventListenerAdapter scoreListener = new WorldEventListenerAdapter() {
		public void onTankDied(float x, float y,Tank t) {
			SkillCount.get1().score += TankTypePolicy.get(t.getTankType()).computeScore();
		}
	};
	private final WorldStateListener mStateListener = new WorldStateListener() {
		public void onLevelEnd() {
			//锟斤拷锟斤拷统锟斤拷
			SkillCount.Level++;
			setGlobalScreen(GameScreen.this);
			game.setScreen(new KilledCountScreen(game));
		}
		public void onGameOver() {
			setGlobalScreen(GameScreen.this);
			game.putData(Constant.KEY_GAME_STATE, GameState.GameOver);
			game.setScreen(new KilledCountScreen(game));
		}
	};
	
	private final GestureListener shootListener = new GestureAdapter(){
		public boolean tap(float x, float y, int count, int button) {
			if( x > Constant.VIEWPORT_WIDTH /2){
			   if(shootIfNeed())  Resource.playSound(Resource.shoot);
			}
			return false;
		}
	};
	private final GestureListener moveListener = new GestureAdapter(){
		boolean dragging = false; //锟角凤拷锟斤拷拽锟斤拷
		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			if( x > Constant.VIEWPORT_WIDTH /2){
				// 只准锟斤拷锟斤拷锟较讹拷 System.err.println("pan false: x = "+x+", y ="+y);
				return false;
			}
			if(world.mMytanks.size()>0){
				MyTank myTank = world.mMytanks.get(0);
				if(!dragging){
					float absDeltaX = Math.abs(deltaX);
					float absDeltaY = Math.abs(deltaY);
					if(absDeltaX >= absDeltaY)
						myTank.switchDirection(deltaX >0 ? Direction.Right :Direction.Left);
					else
						myTank.switchDirection(deltaY >0 ? Direction.Down :Direction.Up);
					myTank.update(Gdx.graphics.getDeltaTime()>0.1f?0.1f:Gdx.graphics.getDeltaTime());
					
					dragging = true;
					Resource.playSound(Resource.move);
				}else
					myTank.update(Gdx.graphics.getDeltaTime()>0.1f?0.1f:Gdx.graphics.getDeltaTime());
			}
			return false;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			dragging = false;
			return false;
		}
	};
}
