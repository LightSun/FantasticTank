package com.heaven7.fantastictank.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.support.Direction;
/**
 * SpirteBatcher绘制需要的信息
 * @author Administrator
 */
public class DrawInfo {
	public TextureRegion region;
	public Direction dir;             //指示当前TextureRegion代表的方向
	public float     degree;          //旋转度数（以向上为准--然后旋转到指定方向）
	//public boolean   clockwise;       //是否顺时针(默认顺时针)
	private static final DrawInfo TMP = new DrawInfo();
	
	private DrawInfo() {}
	
	public DrawInfo set(TextureRegion region, Direction dir, float degree){
		this.region = region;
		this.dir = dir;
		this.degree = degree;
		return this;
	}
	
	@Override
	public String toString() {
		return "DrawInfo [region=" + region + ", dir=" + dir + ", degree="
				+ degree + "]";
	}

	public static <T extends Bullet>DrawInfo get(T t){
		TextureRegion region = null;
		Direction dir = Direction.Up; //纹理先向上对齐
		float  degree =0;      
		
		switch (t.getBulletType()) {
		case Normal:
			region = Resource.bullet_normal_top;
			break;
			
		case Penetrable:
			region = Resource.bullet_better_blue;
			break;
			
		case Laddered:
			region = Resource.bullet_better_green;
			break;
			
		case Rebounding:
			region = Resource.bullet_better_dark_blue; 
			break;
			
		case DieTogether:
			region = Resource.bullet_better_red;
			break;

		default:
			throw new RuntimeException();
		}
        degree += getRotateDegree(t.getDirection());
		
        return TMP.set(region, dir, degree);
	}
	public static <T extends Tank>DrawInfo get(T t){
		TextureRegion region = null;
		Direction dir = null;
		float  degree =0;      //旋转多少度才能向上对齐+到指定方向需要多少度
		
		switch (t.getTankType()) {
		case Honest:
			region = Resource.t_green_top;
			dir = Direction.Up;
			break;
			
		case Fast:
			region = Resource.t2_white_top;
			dir = Direction.Up;
			break;
			
		case Slower:
			region = Resource.t1_white_bottom;
			dir = Direction.Down;
			degree = 180;
			break;
			
		case Solid:
			region = Resource.t4_white_top;
			dir = Direction.Up;
			break;
		case King:
			//TODO 国王
		default:
			break;
		}
		
		degree += getRotateDegree(t.getDirection());
		
		return TMP.set(region, dir, degree);
	}

	/** 从基于方向向上--旋转到指定的方向--返回这个角度--顺时针旋转 */
	private static float getRotateDegree(Direction dir){
		//因为我是以方向向上为准的--这里是根据实际的运行调节的（结论是：）
		/** 实际方向---纹理方向（坦克炮台的方向）
		 * 左 --- 下
                                 右 --- 上
                                 上 ----右
                                 下 ----左
                                 先是右上面这个Bug修改的参数
		 */
		switch (dir) {
		case Up : //根据合适的
			return 0-90+180;
		case Right:
			return 90+90-180;
		case Down:
			return 180-90+180;
		case Left:
			return 270 +90-180; 

		default:
			throw new RuntimeException();
		}
	}

	
}
