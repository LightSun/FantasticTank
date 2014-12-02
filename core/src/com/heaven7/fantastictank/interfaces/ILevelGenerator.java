package com.heaven7.fantastictank.interfaces;

import java.util.List;

import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Wood;
/**
 * 关卡生成器
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
	
	/**根据指定的坐标生成autotank,默认生成的是随机属性的(取决于当前的关卡生成器)*/
	AutoTank createAutoTank(float x,float y);
	
	/** 根据此时的关卡情况是否 允许生成新的autotank */
	boolean allowGenerateAutoTank(); 
	
	/** 给自动化坦克预留生产基地*/
	boolean isRetainPosition(float x, float y);
	/** 给人工坦克预留的位置 */
	boolean isRetainPositionForManual(float x, float y);
	/** 是否是boss 关卡*/
// 	boolean isBossLevel(int level);
	
	//
	void setTankManagePolicy(ITankManagePolicy policy); 
	ITankManagePolicy getTankManagePolicy();
	
}
