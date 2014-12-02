package com.heaven7.fantastictank.support.policy;

import java.util.List;

import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.MatterDef;
/**
 * ������ӵ����͵Ĳ���
 * @author Administrator
 */
public abstract class BulletTypePolicy {
	
	public static final int FOR_EVER_IF_POSSIBBLE = -1;
	
	private BulletType mType;
	
	public BulletTypePolicy(){}
	public BulletTypePolicy(BulletType type) {
		this.mType = type;
	}
	public BulletType getBulletType() {
		return mType;
	}
	public void setBulletType(BulletType type){
		this.mType = type;
	}
	
	public abstract float getFlightLength();
	
	/**��ȡ�����Ĵ��� {@link MatterDef#hitCount},�������ӵ�������*/
	public abstract int getHitCount();
	
	/**��·�����ͷ���Ч��  ��ȡΪÿ���ӵ�Լ���ķ��򼯺�---����ʾ�ӵ�����˶� (x,y�����ӵ�����ʱ������)
	 * <li> �������������������ҵľ���
	 * @param startDir ��ʼ����/̹�˷���
	 * @throws UnsupportedOperationException �����ǰ�ӵ����Ͳ�֧��*/
	public abstract List<Direction> getDirectionList(Direction startDir,
			float up,float down,float left,float right) 
					throws UnsupportedOperationException;
}
