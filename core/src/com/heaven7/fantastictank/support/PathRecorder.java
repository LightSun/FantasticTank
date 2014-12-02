package com.heaven7.fantastictank.support;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Logger;

/**
 * 路径记录器，path主要为子弹服务的
 * @author Administrator
 */
public class PathRecorder{
	
	/** 允许的误差 */
	static final float BOOBOO = 0.08f ;  
	public static final int NO_CUT = 0;

	private final float  totalLength;
	private float  passedLength;           //已走过的长度
	 
	private final boolean cutable;         //是否支持分割
	private final int fragmentCount;       //分段的总个数
	private int passFragmentCount;         //正在走的的第几段
	
	//private final float fragmentLength;    //分段,每段的长度(如果支持)--目前都是均分,不应该是绝对的
	
	/*多个方向，且可重复 --不改变方向时不会增加node对象*/
	private ArrayList<Path> mPaths = new ArrayList<Path>(4);  
	private final List<Direction> mDirs;
	private boolean dead = false;
	
	/** 参数:路径/方向集合， 总长度 */
	public PathRecorder(List<Direction> dirs,float totalLength) {
		super();
		this.mDirs = dirs;
		int fragmentCount = dirs.size();
		if(fragmentCount == 0)
			throw new IllegalArgumentException("dirs.size() must > 0");
		else if(fragmentCount == 1){
			cutable = false;
		}else{
			cutable = true;
		}
		this.totalLength = totalLength;
		this.fragmentCount = fragmentCount;
	}
	
	/**记录指定方向上 已经走的距离(不能是负数)*/
	public void record(Direction dir,float distance){
		passedLength += distance;
		if(this.mPaths.size()==0){
			passFragmentCount = 1;
			this.mPaths.add(new Path(dir, distance).setIndex(1));
		}else{
			Path path = mPaths.get(mPaths.size()-1);
			if(path.dir == dir)
				path.passedLength += distance;
			else{
				passFragmentCount ++;
				this.mPaths.add(new Path(dir, distance).setIndex(passFragmentCount));
			}
		}
	}
	
	public boolean isDead(){
		return  dead || (passedLength - totalLength) >= 0 ;
	}
	
	/** true, if现在可以切换方向了  */
	public boolean canTurnDirection(){
		if(!cutable) return false;
		if(passFragmentCount > fragmentCount)
			return false;
		if(isDead()) return false;
		
		if(mPaths.size() ==0) return false;
		//最后一段了
		if(passFragmentCount == mDirs.size() -1)
			return false;
		
		//如果该段路程上，行走的距离还不到每段的平均值范围内,false	
		Path path = mPaths.get(mPaths.size()-1);
		float delta = totalLength /fragmentCount - path.passedLength;
		if(Math.abs(delta) > BOOBOO)
			return false;
		
		return true;
	}
	
	/** 获得下一个方向 (一般在 {@link #record(Direction, float)} 之前调用),需要在次之前调用canTurnDirection
	 * <li>如果路线的dirs已走完（包括正在走最后一段），则返回null*/
	public Direction nextDirection(){
		//if(!canTurnDirection()) return null;
	    if(passFragmentCount <= mDirs.size()){
			return mDirs.get(passFragmentCount);
		}else{
			logger.debug("called [ nextDirection() ] passFragmentCount >= mDirs.size()");
			dead = true;
		}
		return null;
	}
	
	/** 每个节点，指定方向上走过的距离  */
	public class Path{
		/**所属的子弹的第几小段--1开始增加的*/
		public int index;         
		public Direction dir;
		public float passedLength;
		
		public Path(Direction dir, float passedLength) {
			super();
			this.dir = dir;
			this.passedLength = passedLength;
		}
		
		public Path setIndex( int index){
			this.index = index;
			return this;
		}
	}
	
	private Logger logger = new Logger(PathRecorder.class.getSimpleName(),Logger.DEBUG);
}
