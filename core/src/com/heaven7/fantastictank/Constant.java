package com.heaven7.fantastictank;

public class Constant {

	//ͨ�ÿ��
	public static final float COMMON_WIDTH  = 1f;
	public static final float COMMON_HEIGHT = 1f;
	
	//�ӵ����
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
	
	//�ӵ��ٶ�
	public static final float BULLET_VELOCITY = 3f;
	
	//��̬����(@StaticObject)�������ߡ����ؼ���
	public static final int STATIC_OBJ_TEXTURE_WIDTH_PIX = 32;
	public static final int STATIC_OBJ_TEXTURE_HEIGHT_PIX = 32;
	
	public static final int WORLD_WIDTH  = 20;
	public static final int WORLD_HEIGHT = 12;
	//̹�˵ĸ������
	public static final int RELIVE_COUNT_COMMON = 2;
	
	//�ӿ�
	public static final float VIEWPORT_WIDTH = 854;
	public static final float VIEWPORT_HEIGHT = 480;
	
	//����Ŀ�ߣ�@WorldRender��
	public static final float FRUSTUM_WIDTH = WORLD_WIDTH;
	public static final float FRUSTUM_HEIGHT = WORLD_HEIGHT;
	//��ɱ25����
	protected static final int KILLED_LEVEL_END = 25;
	
	//keys
	public static final String KEY_GAME_STATE = "game_state";
}
