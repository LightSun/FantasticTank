package com.heaven7.fantastictank;

public class Constant {

	//通用宽高
	public static final float COMMON_WIDTH  = 1f;
	public static final float COMMON_HEIGHT = 1f;
	
	//子弹宽高
	public static final float BULLET_WIDTH_NORMAL        = COMMON_WIDTH/ 8;
	public static final float BULLET_HEIGHT_NORMAL       = COMMON_WIDTH/ 8;
	public static final float BULLET_WIDTH_BLUE          = COMMON_WIDTH/ 4;
	public static final float BULLET_HEIGHT_BLUE         = COMMON_WIDTH/ 4;
	public static final float BULLET_WIDTH_GREEN         = COMMON_WIDTH/ 4;
	public static final float BULLET_HEIGHT_GREEN        = COMMON_WIDTH/ 4;
	public static final float BULLET_WIDTH_DARK_BLUE     = COMMON_WIDTH/ 4;
	public static final float BULLET_HEIGHT_DARK_BLUE    = COMMON_WIDTH/ 4;
	public static final float BULLET_WIDTH_RED           = COMMON_WIDTH/ 4;
	public static final float BULLET_HEIGHT_RED          = COMMON_WIDTH/ 4;
	
	//子弹速度
	public static final float BULLET_VELOCITY = 3f;
	
	//静态对象(@StaticObject)的纹理宽高。像素计算
	public static final int STATIC_OBJ_TEXTURE_WIDTH_PIX = 32;
	public static final int STATIC_OBJ_TEXTURE_HEIGHT_PIX = 32;
	
	public static final int WORLD_WIDTH  = 20;
	public static final int WORLD_HEIGHT = 12;
	//坦克的复活次数
	public static final int RELIVE_COUNT_COMMON = 2;
	
	//视口
	public static final float VIEWPORT_WIDTH = 854;
	public static final float VIEWPORT_HEIGHT = 480;
	
	//截体的宽高（@WorldRender）
	public static final float FRUSTUM_WIDTH = WORLD_WIDTH;
	public static final float FRUSTUM_HEIGHT = WORLD_HEIGHT;
	//击杀25过关
	protected static final int KILLED_LEVEL_END = 25;
	
	//keys
	public static final String KEY_GAME_STATE = "game_state";
}
