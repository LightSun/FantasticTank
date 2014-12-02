package com.heaven7.fantastictank.level.arttext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * �����������:��ʱ��֧��ArtTextGroupǶ��ArtTextGroup 
 * <li>ע�⣺�����ǲ����໥�ص���
 * <li> 2������/��ĸ֮����һ�����У���֤�ɹ۲��ԣ�
 * <li> Ŀǰ��ֻ֧��ˮƽ�����
 * @author Administrator
 */
public class ArtTextGroup extends ArtText {

	public static final int MODE_HORIZONTAL = 1;
	public static final int MODE_VERTICAL = 2;
	/** ��Ϸ�ʽ ,Ĭ��ˮƽ*/
	private int mode = MODE_HORIZONTAL;
	private final ArrayList<ArtText> texts = new ArrayList<ArtText>(8);
	
	public int getMode() {
		return mode;
	}
	/** ֻ֧��ˮƽ����ֱ2�ֲ���  {@link ArtTextGroup#MODE_HORIZONTAL} ,{@link ArtTextGroup#MODE_VERTICAL}
	 * Ĭ�� ˮƽ**/
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
				//��ֱ����--�����
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

	// ========= ���ģʽ���з��� ===========//
	
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
