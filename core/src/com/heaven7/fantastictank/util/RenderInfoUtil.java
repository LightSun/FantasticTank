package com.heaven7.fantastictank.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.res.Resource;
/**
 * use this in libgdx have bugs please use {@link DrawInfo} instead
 * @author Administrator
 */
@Deprecated
public class RenderInfoUtil {

	public static RenderInfo getByBullet(Bullet b) {
		TextureRegion region = null;
		float width = 0;
		float height = 0;
		
		switch (b.getBulletType()) {
		
		case Normal:{
			width = Constant.BULLET_WIDTH_NORMAL;
			height = Constant.BULLET_HEIGHT_NORMAL;
			switch (b.getDirection()) {
			case Up:
				region = Resource.bullet_normal_top;
				break;
			case Down:
				region = Resource.bullet_normal_bottom;
				break;
			case Left:
				region = Resource.bullet_normal_left;
				break;
			case Right:
				region = Resource.bullet_normal_right;
				break;

			default:
				throw new RuntimeException("unknow bullet type");
			}
		}
			break;

		case Laddered:
			region = Resource.bullet_better_green;
			width = Constant.BULLET_WIDTH_GREEN;
			height = Constant.BULLET_HEIGHT_GREEN;
			break;
		case Penetrable:
			region = Resource.bullet_better_blue;
			width = Constant.BULLET_WIDTH_BLUE;
			height = Constant.BULLET_HEIGHT_BLUE;
			break;
		case Rebounding:
			region = Resource.bullet_better_dark_blue;
			width = Constant.BULLET_WIDTH_DARK_BLUE;
			height = Constant.BULLET_HEIGHT_DARK_BLUE;
			break;
		case DieTogether:
			region = Resource.bullet_better_red;
			width = Constant.BULLET_WIDTH_RED;
			height = Constant.BULLET_HEIGHT_RED;
			break;
			
		default:
			throw new RuntimeException();
		}
		return new RenderInfo(region, width, height);
	}

	public static <T extends Tank>RenderInfo getByTank(T t) {
		TextureRegion region =null;
		float width  = 0;
		float height = 0;
		int   flag   = 0 ;
		
		switch (t.getTankType()) {
		case Honest:{
			switch (t.getDirection()) {
			case Down:
				region = Resource.t_green_top;
				width  = Constant.COMMON_WIDTH ;
				height = - Constant.COMMON_HEIGHT ;//竖直翻转纹理
				flag = RenderInfo.FLIP_VERTICAL;
				break;
				
			case Up:
				region = Resource.t_green_top;
				width = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;
			case Left:
				region = Resource.t_green_left;
				width  = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;
			case Right:
				region = Resource.t_green_left;
				width  = -Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				flag   = RenderInfo.FLIP_HORIZONTAL;
				break;

			default:
				throw new RuntimeException();
			}
		}
			break;
			
		case Slower:
		{
			switch (t.getDirection()) {
			case Down:
				region = Resource.t1_white_bottom;
				width = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;
				
			case Up:
				region = Resource.t1_white_bottom;
				width  = Constant.COMMON_WIDTH ;
				height = -Constant.COMMON_HEIGHT;
				flag   = RenderInfo.FLIP_VERTICAL;
				break;
			case Left:
				region = Resource.t1_white_left;
				width  = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;
			case Right:
				region = Resource.t1_white_left;
				width  = -Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				flag   = RenderInfo.FLIP_HORIZONTAL;
				break;

			default:
				throw new RuntimeException();
			}
		}
			break;
			
		case Fast:
		{
			switch (t.getDirection()) {
			case Down:
				region = Resource.t2_white_top;
				width  = Constant.COMMON_WIDTH ;
				height = -Constant.COMMON_HEIGHT;//竖直翻转纹理
				flag   = RenderInfo.FLIP_VERTICAL;
				break;
				
			case Up:
				region = Resource.t2_white_top;
				width  = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;
			case Left:
				region = Resource.t2_white_right;
				width  = -Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				flag   = RenderInfo.FLIP_HORIZONTAL;
				break;
			case Right:
				region = Resource.t2_white_right;
				width  = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;

			default:
				throw new RuntimeException();
			}
		}
			break;
		case Solid:
		{
			switch (t.getDirection()) {
			case Down:
				region = Resource.t4_white_top;
				width  = Constant.COMMON_WIDTH ;
				height = -Constant.COMMON_HEIGHT;//竖直翻转纹理
				flag   = RenderInfo.FLIP_VERTICAL;
				break;
				
			case Up:
				region = Resource.t4_white_top;
				width  = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;
			case Left:
				region = Resource.t4_white_right;
				width  = -Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				flag   = RenderInfo.FLIP_HORIZONTAL;
				break;
			case Right:
				region = Resource.t4_white_right;
				width  = Constant.COMMON_WIDTH ;
				height = Constant.COMMON_HEIGHT;
				break;

			default:
				throw new RuntimeException();
			}
		}
			break;
		case King:
			//暂时不支持
		default:
			throw new RuntimeException("unsupport tank type");
		}
		
		return new RenderInfo(region, width, height);
	}
	
	public static class RenderInfo{
		public static final int FLIP_HORIZONTAL = 1;
		public static final int FLIP_VERTICAL   = 2;
		
	    public TextureRegion region;
	    public float width;
	    public float height;
	    /**{@link #FLIP_HORIZONTAL}  or {@link #FLIP_VERTICAL} */
	    public int flag;
	    
		public RenderInfo(TextureRegion region, float width, float height) {
			super();
			this.region = region;
			this.width = width;
			this.height = height;
		}
		@Override
		public String toString() {
			return "RenderInfo [region=" + region + ", width=" + width
					+ ", height=" + height + "]";
		}
		
	}
}
