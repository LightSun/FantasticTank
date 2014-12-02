package com.heaven7.fantastictank.support;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;

public abstract class AbsGame extends Game {

	private final IntMap<Object> data = new IntMap<Object>();

	/**
	 * {@inheritDoc}
	 * <li> 增加map数据的传递{@link AbsGame#putData(String, Object)},自动传递到下一个screen
	 */
	@Override
	public void setScreen(Screen screen) {
		if(! (screen instanceof DefaultScreen)){
			throw new RuntimeException("screen must extends DefaultScreen");
		}
		if(data.size!=0){
			((DefaultScreen)screen).putAllData(this.data);
			clearData();
		}
		super.setScreen(screen);
	}
	
	public Object putData(int key,Object val){
		return this.data.put(key, val);
	}
	public Object putData(String key,Object val){
		return this.data.put(key.hashCode(), val);
	}
	public void putAllData(IntMap<Object> vals){
		this.data.putAll(vals);
	}
	/**返回{@link #data}的副本*/
	public IntMap<Object> getData(){
		IntMap<Object> map = this.data;
		IntMap<Object> cloneMap = new IntMap<Object>();
		
		for (Entry<Object> entry : map.entries())
			cloneMap.put(entry.key, entry.value);
	
		return cloneMap;
	}
	public void clearData(){
		this.data.clear();
	}
}
