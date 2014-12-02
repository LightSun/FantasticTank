package com.heaven7.libgdx.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
/**
 * ���㴦��2d��BitmapFont(�ö�����ƶ��������Ͻ�Ϊ���)
 * @author heaven7
 */
public class FontHelper {
		
	private final float mBaseline; 
	private final BitmapFont mFont;

	/** y Ϊ ������ ���Ͻ� y���꣨ͬ{@link FontLinePosition#SecondTop}��*/
	public FontHelper (BitmapFont font, float y){
		this.mBaseline = y - font.getCapHeight();
		this.mFont = font;
	}
	
	/**��ȡָ��λ�õ�y��ֵ
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
	 * �����д���Ascent��descent���ߵ�λ�� (y��ֵ)����
	 * <li>��3�� : {@link LinePosition#Top}  ,  {@linkplain LinePosition#SecondTop}  ,
	 * {@linkplain LinePosition#ThirdTop}
	 * <li>��3�� ��   {@linkplain LinePosition#BaseLine}  , {@linkplain LinePosition#SecondBottom} 
	 * {@linkplain LinePosition#Bottom} 
	 */
	public static enum FontLinePosition{
		/**���  = baseline + BitmapFont.capHeight()+ font.getAscent() */
		Top,
		/** RealTop �ı�����ʵ��ռ�õĶ��� ���൱�ڵ�2�� ( = baseline + BitmapFont.capHeight())*/
		SecondTop,
		/**��3�� ( = baseline + font.getXHeight() )*/
		ThirdTop,
		
		/** ����  thirdBottom�� ��  */
		BaseLine,
		/** ��bottom��΢�ߵ� ,��baseline�͵� ( = baseline + font.getDescent(): descent һ���Ǹ��� ) */
		SecondBottom,
		/**��ײ� (  = baseline + BitmapFont.capHeight() - font.getLineHeight() ) */
		Bottom;
	}
}
