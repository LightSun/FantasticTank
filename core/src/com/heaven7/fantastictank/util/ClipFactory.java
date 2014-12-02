package com.heaven7.fantastictank.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.support.AttackRecorder.Node;
/**
 * 内部已实现缓存机制 {@link #free(ClippedInfo)}
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

	/** 静态事物如果能被攻击  (返回false 表示不支持裁剪 或者已死亡)--裁剪成功会自动设置进去
	 * <li> 此方法应该在所有对象更新 and 检查碰撞之后 被调用之后
	 *   @param dir 被攻击的方向
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
		//裁剪前--左下角的x,y
		float lbX = obj.bounds.x;
		float lbY = obj.bounds.y;
		//左下角x只跟左边攻击次数有关---同理y只跟 下边攻击次数相关(之前想错了--左边和向左是2回事情)
		float x = lbX + nRight.hittedCount *  obj.bounds.width / max;
	    float y = lbY + nUp.hittedCount *  obj.bounds.height / max;
	    
	    float cx = x + width /2;
	    float cy = y + height /2;
	    
	    // ====== compute real Texture/TextureRegion ====//
	    
	    //compute the relative x and y about the TextureRegion(纹理左上角为原点)
	    int relativeX = nRight.hittedCount * Constant.STATIC_OBJ_TEXTURE_WIDTH_PIX  / max;
	    int relativeY = nDown.hittedCount   * Constant.STATIC_OBJ_TEXTURE_HEIGHT_PIX / max;
	    
	    int textureWidth = (max - nLeft.hittedCount - nRight.hittedCount)
	    		* Constant.STATIC_OBJ_TEXTURE_WIDTH_PIX  / max ;
	    int textureHeigt = (max - nUp.hittedCount - nDown.hittedCount)
	    		* Constant.STATIC_OBJ_TEXTURE_HEIGHT_PIX / max ;
	    //已优化，避免每次new对象
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
	/** 用于保存裁剪后的纹理区域 */
	public static class ClippedInfo{
		/** 含有宽高 and 左下角坐标 */
		public Rectangle bounds;
		//裁剪后的纹理区域(剩余的)--纹理坐标原点是左上角
		public TextureRegion region;
		//下面4个参数是游戏世界中     matter对象的（被攻击裁剪后的）
		public float cx; //中心点x,y
		public float cy;
		public float width;
		public float height;
		
		private ClippedInfo(){}
	}
	
	
}
