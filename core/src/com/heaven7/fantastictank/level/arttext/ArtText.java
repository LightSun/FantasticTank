package com.heaven7.fantastictank.level.arttext;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.heaven7.fantastictank.level.AbsLevel;

/**
 * 艺术文字 基类
 * <li>注意为保证文字的可观察性,在外层会套个空白的矩形(刚好边界则无)
 * <li> 减小艺术文字的单元为UNIT_WIDTH 的1/4-
 * <li> 已实现所有字母以及数字的ArtText，please ctrl+t
 * @author Administrator
 */
public abstract class ArtText {
	
	protected static final float MINX        = AbsLevel.getMinX();
	protected static final float MINY        = AbsLevel.getMinY();
	protected static final float MAXX        = AbsLevel.getMaxX();
	protected static final float MAXY        = AbsLevel.getMaxY();
	//单元格宽高(世界的)
	protected static final float WORLD_UNIT_WIDTH  = AbsLevel.getUnitWidth();
	protected static final float WORLD_UNIT_HEIGHT = AbsLevel.getUnitHeight();
	
	//艺术文字的最小单元格大小
	protected static final float MIN_WIDTH  = WORLD_UNIT_WIDTH /2;
	protected static final float MIN_HEIGHT = WORLD_UNIT_HEIGHT /2;
	
	protected static final float UNIT_WIDTH  = MIN_WIDTH;
	protected static final float UNIT_HEIGHT = MIN_HEIGHT;
	
	public static final Pool<Vector2> VECTOR_POOL = new Pool<Vector2>() {
		protected Vector2 newObject() {
			return new Vector2();
		}
	};
	protected final Vector2 tmp = new Vector2();
	/**  文字的:左，底部 的position(半边是边界矩形，半边是艺术文字的单元--因为艺术文字的单元格我改成了1半{@link ArtText#MIN_WIDTH})*/
	private final Vector2 leftBottomPosition = new Vector2(); 
	
	/** 返回布局艺术文字的左底的postion */
	public Vector2 getLeftBottomPosition() {
		if(leftBottomPosition.x + measureWidth() > MAXX || leftBottomPosition.y+measureHeight()> MAXY)
			throw new IllegalStateException("cann't position the artText to the position = "+leftBottomPosition);
		return tmp.set(leftBottomPosition.x + MIN_WIDTH/2, leftBottomPosition.y+MIN_HEIGHT/2);
	}
	/**注意，x和y不能超越世界坐标的范围
	 * @param x  位于世界的position, x , y(所以单元格是world的单元格)
	 * @param y
	 * <p>必须先调用此方法</p>*/
	public void setRealLeftBottomPosition(float x,float y) {
		 //艺术文字的左底下角potion不同于世界(相当于艺术文字真正左底部的坐标--非Position中心点)
		if(x< MINX || x >= MAXX ||y<MINY ||y>=MAXY) throw new IllegalArgumentException();
		this.leftBottomPosition.set(x, y );
	}
	
	/*** 由于半边是边界，半边是艺术文字。所以左下角position就是设置的左下角*/
	public Vector2 getRealLeftBottomPosition(){
		//System.out.println("getRealLeftBottomPosition(): "+leftBottomPosition);
	    return tmp.set(leftBottomPosition.x, leftBottomPosition.y);
	}
	
	/** 返回增加空白矩形的最右上角坐标,x,y */
	public Vector2 getRealRightTopPosition(){
		final float measureWidth = measureWidth();
		final float measureHeight = measureHeight();
		final float resolveEdgeWidth = resolveEdgeWidth(measureWidth);
		final float resolveEdgeHeight = resolveEdgeHeight(measureHeight);
		//System.out.println("measureWidth = "+measureWidth+", measureHeight = "+measureHeight);
		//System.out.println("resolveEdgeWidth = "+resolveEdgeWidth+", resolveEdgeHeight = "+resolveEdgeHeight);
		//MIN_WIDTH 代表测量宽 度= 偶数个MIN_WIDTH
		final float x = (resolveEdgeWidth == MIN_WIDTH ? measureWidth :
			   (measureWidth + WORLD_UNIT_WIDTH/2)) +leftBottomPosition.x; 
		final float y = (resolveEdgeHeight == MIN_HEIGHT ? measureHeight:
			(measureHeight + WORLD_UNIT_HEIGHT/2)) +leftBottomPosition.y; 
		if(x >MAXX ) throw new IllegalStateException("cannot postion this arttext maxX ="+x);
		if(y >MAXY ) throw new IllegalStateException("cannot postion this arttext maxY ="+y);
		//System.out.println("getRealRightTopPosition(): x = " +x+",y = "+y);
		return tmp.set(x,y);
	}
	
	/**测量的宽---不包含---外层矩形*/
	protected abstract float measureWidth();
	/**测量的高---不包含---外层矩形*/
	protected abstract float measureHeight();
	
	/**布局后返回所有形成文字的点的集合(不包含外层空白矩形) */
	public abstract List<Vector2> layout();
	
	/**检查指定的 x,y是否 与  当艺术文字的覆盖范围--- 有重叠
	 * <li>note：覆盖范围包括外层矩形边框*/
	public boolean overlap(float x,float y){
		final float minX = getRealLeftBottomPosition().x;
		final float minY = getRealLeftBottomPosition().y;
		final float maxX = getRealRightTopPosition().x;
		final float maxY = getRealRightTopPosition().y;
		
		return x>=minX && x<=maxX && y>=minY && y<=maxY;
	}
	
	//=========== static methods =========== //
	
	public static void freeAll(List<Vector2> vecs){
		for(int i=0,size=vecs.size() ;i<size ;i++){
			VECTOR_POOL.free(vecs.get(i));
		}
	}
	
	/**根据测量的宽度，计算得到一个边界矩形右边的宽度大小*/
	protected static final float resolveEdgeWidth(float width){
		//实际占用的单元格数量,根据奇偶性。决定边缘格 以 艺术文字的最小单元width还是用世界的unit_width
		int size = (int) (width / MIN_WIDTH);
		if(size %2 == 1) return WORLD_UNIT_WIDTH;
		else  return MIN_WIDTH;
	}
	/**根据测量的宽度，计算得到一个边界矩形上边的高度大小*/
	protected static final float resolveEdgeHeight(float height){
		int size = (int) (height / MIN_HEIGHT);
		if(size %2 == 1) return WORLD_UNIT_HEIGHT;
		else  return MIN_HEIGHT;
	}
	
	protected static final float resolveMeasureWidth(int count){
		return count*UNIT_WIDTH;
	}
	protected static final float resolveMeasureHeight(int count){
		return count*UNIT_HEIGHT;
	}
}
