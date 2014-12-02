package com.heaven7.fantastictank.level.arttext;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.heaven7.fantastictank.level.AbsLevel;

/**
 * �������� ����
 * <li>ע��Ϊ��֤���ֵĿɹ۲���,�������׸��հ׵ľ���(�պñ߽�����)
 * <li> ��С�������ֵĵ�ԪΪUNIT_WIDTH ��1/4-
 * <li> ��ʵ��������ĸ�Լ����ֵ�ArtText��please ctrl+t
 * @author Administrator
 */
public abstract class ArtText {
	
	protected static final float MINX        = AbsLevel.getMinX();
	protected static final float MINY        = AbsLevel.getMinY();
	protected static final float MAXX        = AbsLevel.getMaxX();
	protected static final float MAXY        = AbsLevel.getMaxY();
	//��Ԫ����(�����)
	protected static final float WORLD_UNIT_WIDTH  = AbsLevel.getUnitWidth();
	protected static final float WORLD_UNIT_HEIGHT = AbsLevel.getUnitHeight();
	
	//�������ֵ���С��Ԫ���С
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
	/**  ���ֵ�:�󣬵ײ� ��position(����Ǳ߽���Σ�������������ֵĵ�Ԫ--��Ϊ�������ֵĵ�Ԫ���Ҹĳ���1��{@link ArtText#MIN_WIDTH})*/
	private final Vector2 leftBottomPosition = new Vector2(); 
	
	/** ���ز����������ֵ���׵�postion */
	public Vector2 getLeftBottomPosition() {
		if(leftBottomPosition.x + measureWidth() > MAXX || leftBottomPosition.y+measureHeight()> MAXY)
			throw new IllegalStateException("cann't position the artText to the position = "+leftBottomPosition);
		return tmp.set(leftBottomPosition.x + MIN_WIDTH/2, leftBottomPosition.y+MIN_HEIGHT/2);
	}
	/**ע�⣬x��y���ܳ�Խ��������ķ�Χ
	 * @param x  λ�������position, x , y(���Ե�Ԫ����world�ĵ�Ԫ��)
	 * @param y
	 * <p>�����ȵ��ô˷���</p>*/
	public void setRealLeftBottomPosition(float x,float y) {
		 //�������ֵ�����½�potion��ͬ������(�൱����������������ײ�������--��Position���ĵ�)
		if(x< MINX || x >= MAXX ||y<MINY ||y>=MAXY) throw new IllegalArgumentException();
		this.leftBottomPosition.set(x, y );
	}
	
	/*** ���ڰ���Ǳ߽磬������������֡��������½�position�������õ����½�*/
	public Vector2 getRealLeftBottomPosition(){
		//System.out.println("getRealLeftBottomPosition(): "+leftBottomPosition);
	    return tmp.set(leftBottomPosition.x, leftBottomPosition.y);
	}
	
	/** �������ӿհ׾��ε������Ͻ�����,x,y */
	public Vector2 getRealRightTopPosition(){
		final float measureWidth = measureWidth();
		final float measureHeight = measureHeight();
		final float resolveEdgeWidth = resolveEdgeWidth(measureWidth);
		final float resolveEdgeHeight = resolveEdgeHeight(measureHeight);
		//System.out.println("measureWidth = "+measureWidth+", measureHeight = "+measureHeight);
		//System.out.println("resolveEdgeWidth = "+resolveEdgeWidth+", resolveEdgeHeight = "+resolveEdgeHeight);
		//MIN_WIDTH ��������� ��= ż����MIN_WIDTH
		final float x = (resolveEdgeWidth == MIN_WIDTH ? measureWidth :
			   (measureWidth + WORLD_UNIT_WIDTH/2)) +leftBottomPosition.x; 
		final float y = (resolveEdgeHeight == MIN_HEIGHT ? measureHeight:
			(measureHeight + WORLD_UNIT_HEIGHT/2)) +leftBottomPosition.y; 
		if(x >MAXX ) throw new IllegalStateException("cannot postion this arttext maxX ="+x);
		if(y >MAXY ) throw new IllegalStateException("cannot postion this arttext maxY ="+y);
		//System.out.println("getRealRightTopPosition(): x = " +x+",y = "+y);
		return tmp.set(x,y);
	}
	
	/**�����Ŀ�---������---������*/
	protected abstract float measureWidth();
	/**�����ĸ�---������---������*/
	protected abstract float measureHeight();
	
	/**���ֺ󷵻������γ����ֵĵ�ļ���(���������հ׾���) */
	public abstract List<Vector2> layout();
	
	/**���ָ���� x,y�Ƿ� ��  ���������ֵĸ��Ƿ�Χ--- ���ص�
	 * <li>note�����Ƿ�Χ���������α߿�*/
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
	
	/**���ݲ����Ŀ�ȣ�����õ�һ���߽�����ұߵĿ�ȴ�С*/
	protected static final float resolveEdgeWidth(float width){
		//ʵ��ռ�õĵ�Ԫ������,������ż�ԡ�������Ե�� �� �������ֵ���С��Ԫwidth�����������unit_width
		int size = (int) (width / MIN_WIDTH);
		if(size %2 == 1) return WORLD_UNIT_WIDTH;
		else  return MIN_WIDTH;
	}
	/**���ݲ����Ŀ�ȣ�����õ�һ���߽�����ϱߵĸ߶ȴ�С*/
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
