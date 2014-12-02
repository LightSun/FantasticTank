package com.heaven7.fantastictank.interfaces;

import java.util.List;

import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Wood;
/**
 * �ؿ�������
 * @author Administrator
 */
public interface ILevelGenerator {
	 
	void build();
	void clearAll();
	
	List<AutoTank>   getAutotanks();
	List<Lake>       getLakes();
	List<Wood>       getWoods();
	List<DirtWall>   getDirtWalls();
	List<BrickWall>  getBrickWalls();
	List<SmallBrickWall> getSmallBrickWalls();
	
	/**����ָ������������autotank,Ĭ�����ɵ���������Ե�(ȡ���ڵ�ǰ�Ĺؿ�������)*/
	AutoTank createAutoTank(float x,float y);
	
	/** ���ݴ�ʱ�Ĺؿ�����Ƿ� ���������µ�autotank */
	boolean allowGenerateAutoTank(); 
	
	/** ���Զ���̹��Ԥ����������*/
	boolean isRetainPosition(float x, float y);
	/** ���˹�̹��Ԥ����λ�� */
	boolean isRetainPositionForManual(float x, float y);
	/** �Ƿ���boss �ؿ�*/
// 	boolean isBossLevel(int level);
	
	//
	void setTankManagePolicy(ITankManagePolicy policy); 
	ITankManagePolicy getTankManagePolicy();
	
}
