package com.heaven7.fantastictank.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.IntMap;
import com.heaven7.fantastictank.event.GestureListenerGroup;
/**
 * @author Administrator
 */
public abstract class DefaultScreen  implements Screen {
	protected AbsGame game;
	private IntMap<Object> data;
	private DefaultScreen lastScreen;
	
	protected static final GestureListenerGroup GESTURE_GROUP;
	private static DefaultScreen sScreen;
	
	static{
		GESTURE_GROUP = new GestureListenerGroup();
		Gdx.input.setInputProcessor(new GestureDetector(GESTURE_GROUP));
		//Gdx.input.setInputProcessor(STAGE);
	}

	public DefaultScreen(AbsGame game) {
		this.game = game;
	}
	
	public static void setGlobalScreen(DefaultScreen screen){
		sScreen = screen;
	}
	public static DefaultScreen getGlobalScreen(){
		return sScreen;
	}
	
	public void setLastScreen(DefaultScreen ds){
		this.lastScreen = ds;
	}
	public DefaultScreen getLastScreen(){
		return this.lastScreen;
	}
	
	public Object putData(int key,Object val){
		checkMap();
		return this.data.put(key, val);
	}
	private void checkMap() {
		if(this.data ==null) data = new IntMap<Object>();
	}

	public Object getData(int key){
		checkMap();
		return this.data.get(key);
	}
	public Object getData(String key){
		checkMap();
		return this.data.get(key.hashCode());
	}
	public Object removeData(int key){
		checkMap();
		return this.data.remove(key);
	}
	public Object removeData(String key){
		checkMap();
		return this.data.remove(key.hashCode());
	}
	public Object putData(String key,Object val){
		checkMap();
		return this.data.put(key.hashCode(), val);
	}
	public void putAllData(IntMap<Object> vals){
		checkMap();
		this.data.putAll(vals);
	}
	public void clearData(){
		this.data.clear();
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() { 
	}

	@Override
	public void dispose() {
	}
	
	@Override
	public void render(float delta) {
		//System.out.println("delta = "+delta);
		if(delta > 0.1f) delta = 0.1f;
		update(delta);
		draw(delta);
		
	}

	public abstract void draw(float delta);

	public abstract void update(float delta);
}
