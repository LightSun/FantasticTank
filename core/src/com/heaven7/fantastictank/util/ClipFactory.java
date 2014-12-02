package com.heaven7.fantastictank.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.support.AttackRecorder.Node;
/**
 * �ڲ���ʵ�ֻ������ {@link #free(ClippedInfo)}
 * @author Administrator
 */
public class ClipFactory {
	
	public static final Pool<TextureRegion> REGION_POOl = new Pool<TextureRegion>(30) {
		protected TextureRegion newObject() {
			return new TextureRegion();
		}
	};
	public static final Pool<Rectangle> RECTANGLE_POOl = new Pool<Rectangle>(30) {
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	public static final Pool<ClippedInfo> CLIPINFO_POOL = new Pool<ClippedInfo>(30) {
		protected ClippedInfo newObject() {
			ClippedInfo info =new ClippedInfo();
			info.bounds = RECTANGLE_POOl.obtain();
			info.region = REGION_POOl.obtain();
			return info;
		}
	};
	
	public static void free(ClippedInfo info) {
		if(info ==null) return;
		CLIPINFO_POOL.free(info);
	}

	/** ��̬��������ܱ�����  (����false ��ʾ��֧�ֲü� ����������)--�ü��ɹ����Զ����ý�ȥ
	 * <li> �˷���Ӧ�������ж������ and �����ײ֮�� ������֮��
	 *   @param dir �������ķ���
	 *   @return false if need die!*/
	public static <T extends StaticObject> boolean clip(T obj){
		if(!obj.useAttackRecorder()) 
			throw new RuntimeException(" Clip this object is unsupported!"+obj);
	    if(obj.attackRecorder.needDie(obj.def.maxBeHitCount)){
	    	obj.def.alive = false;
	    	return false;
	    }
	    int max = obj.def.maxBeHitCount;
		Node nLeft  = obj.attackRecorder.getNodeLeft();
		Node nRight = obj.attackRecorder.getNodeRight();
		Node nUp    = obj.attackRecorder.getNodeUp();
		Node nDown  = obj.attackRecorder.getNodeDown();
		
		float width  = (max - nLeft.hittedCount - nRight.hittedCount) * obj.bounds.width /max ;
		float height = (max - nUp.hittedCount  - nDown.hittedCount) * obj.bounds.height /max ;
		//�ü�ǰ--���½ǵ�x,y
		float lbX = obj.bounds.x;
		float lbY = obj.bounds.y;
		//���½�xֻ����߹��������й�---ͬ��yֻ�� �±߹����������(֮ǰ�����--��ߺ�������2������)
		float x = lbX + nRight.hittedCount *  obj.bounds.width / max;
	    float y = lbY + nUp.hittedCount *  obj.bounds.height / max;
	    
	    float cx = x + width /2;
	    float cy = y + height /2;
	    
	    // ====== compute real Texture/TextureRegion ====//
	    
	    //compute the relative x and y about the TextureRegion(�������Ͻ�Ϊԭ��)
	    int relativeX = nRight.hittedCount * Constant.STATIC_OBJ_TEXTURE_WIDTH_PIX  / max;
	    int relativeY = nDown.hittedCount   * Constant.STATIC_OBJ_TEXTURE_HEIGHT_PIX / max;
	    
	    int textureWidth = (max - nLeft.hittedCount - nRight.hittedCount)
	    		* Constant.STATIC_OBJ_TEXTURE_WIDTH_PIX  / max ;
	    int textureHeigt = (max - nUp.hittedCount - nDown.hittedCount)
	    		* Constant.STATIC_OBJ_TEXTURE_HEIGHT_PIX / max ;
	    //���Ż�������ÿ��new����
	    ClippedInfo info = obj.getClippedInfo();
	    if(info==null){
	    	info = CLIPINFO_POOL.obtain();
	    }
	    info.bounds.set(x, y, width, height);
    	info.region.setRegion(obj.getOriginalTextureRegion(), 
		        relativeX, relativeY, textureWidth, textureHeigt);
	    info.cx = cx;
	    info.cy = cy;
	    info.width = width;
	    info.height = height;
	    
	    obj.setClippedInfo(info);
		return true;
	}
	/** ���ڱ���ü������������ */
	public static class ClippedInfo{
		/** ���п�� and ���½����� */
		public Rectangle bounds;
		//�ü������������(ʣ���)--��������ԭ�������Ͻ�
		public TextureRegion region;
		//����4����������Ϸ������     matter����ģ��������ü���ģ�
		public float cx; //���ĵ�x,y
		public float cy;
		public float width;
		public float height;
		
		private ClippedInfo(){}
	}
	
	
}
