package com.heaven7.fantastictank.level.arttext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * 艺术文字组合:暂时不支持ArtTextGroup嵌套ArtTextGroup 
 * <li>注意：文字是不能相互重叠的
 * <li> 2个文字/字母之间会多一个空行（保证可观察性）
 * <li> 目前是只支持水平的组合
 * @author Administrator
 */
public class ArtTextGroup extends ArtText {

	public static final int MODE_HORIZONTAL = 1;
	public static final int MODE_VERTICAL = 2;
	/** 组合方式 ,默认水平*/
	private int mode = MODE_HORIZONTAL;
	private final ArrayList<ArtText> texts = new ArrayList<ArtText>(8);
	
	public int getMode() {
		return mode;
	}
	/** 只支持水平和竖直2种布局  {@link ArtTextGroup#MODE_HORIZONTAL} ,{@link ArtTextGroup#MODE_VERTICAL}
	 * 默认 水平**/
	public void setMode(int mode) {
		if(mode!=MODE_HORIZONTAL && mode!=MODE_VERTICAL) throw new IllegalArgumentException();
		this.mode = mode;
	}

	@Override
	public void setRealLeftBottomPosition(float x, float y) {
		super.setRealLeftBottomPosition(x, y);
		float w = 0;
		float h = 0;
		
		int size = texts.size();
		for (int i = 0; i < size; i++) {
		    ArtText art = texts.get(i);
		    
		    if(mode == MODE_HORIZONTAL)
		       art.setRealLeftBottomPosition(x+w, y);
		    else
		       art.setRealLeftBottomPosition(x, y+h);
		    
		    w +=art.measureWidth() + MIN_WIDTH;
		    h +=art.measureHeight() + MIN_HEIGHT;
		}
	}

	@Override
	public float measureWidth() {
		int size = texts.size();
		if(size==0) return 0;
		
		float width = 0;
		for (int i = 0; i < size; i++) {
			float measureWidth = texts.get(i).measureWidth();
			if(mode == MODE_HORIZONTAL){
				width += measureWidth + (i==size-1?0:MIN_WIDTH);
			}else{
				//竖直布局--最大宽度
				if(measureWidth >width)
					width = measureWidth;
			}
		}
		return width;
	}

	@Override
	public float measureHeight() {
		int size = texts.size();
		if(size==0) return 0;
		
		float height = 0;
		for (int i = 0; i < size; i++) {
			float measureHeight = texts.get(i).measureHeight();
			if(mode == MODE_VERTICAL){
				height += measureHeight+(i==size-1? 0 : MIN_HEIGHT);
			}else{
				if(measureHeight >height )
					height = measureHeight;
			}
		}
		return height;
	}

	@Override
	public List<Vector2> layout() {
		ArrayList<Vector2> vecs = new ArrayList<Vector2>();
		int size = texts.size();
		for (int i = 0; i < size; i++) {
			vecs.addAll(texts.get(i).layout());
		}
		return vecs;
	}
	
	@Override
	public boolean overlap(float x, float y) {
		int size = texts.size();
		for (int i = 0; i < size; i++) {
			if(texts.get(i).overlap(x, y)){
				return true;
			}
		}
		return false;
	}

	// ========= 组合模式特有方法 ===========//
	
	public boolean addArtText(ArtText text) {
		return this.texts.add(text);
	}
	
	public void addAllArtText(ArtText... texts) {
		for(ArtText text : texts){
			this.texts.add(text);
		}
	}
	
	public boolean addAllArtText(Collection<ArtText> texts){
		return this.texts.addAll(texts);
	}

	public void removeArtText(ArtText text) {
		this.texts.remove(text);
	}

	public void clear() {
		this.texts.clear();
	}
}
