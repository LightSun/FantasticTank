package com.heaven7.fantastictank.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.support.Direction;
/**
 * SpirteBatcher������Ҫ����Ϣ
 * @author Administrator
 */
public class DrawInfo {
	public TextureRegion region;
	public Direction dir;             //ָʾ��ǰTextureRegion����ķ���
	public float     degree;          //��ת������������Ϊ׼--Ȼ����ת��ָ������
	//public boolean   clockwise;       //�Ƿ�˳ʱ��(Ĭ��˳ʱ��)
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
		Direction dir = Direction.Up; //���������϶���
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
		float  degree =0;      //��ת���ٶȲ������϶���+��ָ��������Ҫ���ٶ�
		
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
			//TODO ����
		default:
			break;
		}
		
		degree += getRotateDegree(t.getDirection());
		
		return TMP.set(region, dir, degree);
	}

	/** �ӻ��ڷ�������--��ת��ָ���ķ���--��������Ƕ�--˳ʱ����ת */
	private static float getRotateDegree(Direction dir){
		//��Ϊ�����Է�������Ϊ׼��--�����Ǹ���ʵ�ʵ����е��ڵģ������ǣ���
		/** ʵ�ʷ���---������̹����̨�ķ���
		 * �� --- ��
                                 �� --- ��
                                 �� ----��
                                 �� ----��
                                 �������������Bug�޸ĵĲ���
		 */
		switch (dir) {
		case Up : //���ݺ��ʵ�
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
