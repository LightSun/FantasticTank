package com.heaven7.libgdx.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
/**
 * 方便处理2d的BitmapFont(该对象绘制都是以左上角为起点)
 * @author heaven7
 */
public class FontHelper {
		
	private final float mBaseline; 
	private final BitmapFont mFont;

	/** y 为 字体最 左上角 y坐标（同{@link FontLinePosition#SecondTop}）*/
	public FontHelper (BitmapFont font, float y){
		this.mBaseline = y - font.getCapHeight();
		this.mFont = font;
	}
	
	/**获取指定位置的y轴值
	 *  @see FontLinePosition*/
	public float getTargetY(FontLinePosition p){
		final BitmapFont mFont = this.mFont;
		switch (p) {
		case Top:
			return mBaseline + mFont.getCapHeight()+ mFont.getAscent();
		case SecondTop:
			return mBaseline + mFont.getCapHeight();
		case ThirdTop:
			return mBaseline + mFont.getXHeight(); 
			
		case BaseLine:
			return mBaseline;
			
		case SecondBottom:
			return mBaseline + mFont.getDescent();
			
		case Bottom:
			return mBaseline + mFont.getCapHeight() - mFont.getLineHeight();

		default:
			throw new RuntimeException();
		}
	}
	
	/***
	 * 字体中代表Ascent，descent等线的位置 (y轴值)，分
	 * <li>高3线 : {@link LinePosition#Top}  ,  {@linkplain LinePosition#SecondTop}  ,
	 * {@linkplain LinePosition#ThirdTop}
	 * <li>低3线 ：   {@linkplain LinePosition#BaseLine}  , {@linkplain LinePosition#SecondBottom} 
	 * {@linkplain LinePosition#Bottom} 
	 */
	public static enum FontLinePosition{
		/**最顶部  = baseline + BitmapFont.capHeight()+ font.getAscent() */
		Top,
		/** RealTop 文本字体实际占用的顶部 ，相当于第2高 ( = baseline + BitmapFont.capHeight())*/
		SecondTop,
		/**第3高 ( = baseline + font.getXHeight() )*/
		ThirdTop,
		
		/** 基线  thirdBottom（ ）  */
		BaseLine,
		/** 比bottom稍微高点 ,比baseline低点 ( = baseline + font.getDescent(): descent 一般是负数 ) */
		SecondBottom,
		/**最底部 (  = baseline + BitmapFont.capHeight() - font.getLineHeight() ) */
		Bottom;
	}
}
