package com.heaven7.fantastictank.wisdom;

import java.util.ArrayList;

import com.heaven7.fantastictank.matters.Tank.TankType;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.util.Random2;

/**
 * 智慧中心
 * @author Administrator
 */
public class WisdomManager{

	public static final int DEFAULT_SIZE = 50;
	private static WisdomManager wm;
	
	public static final Random2 R = new Random2(); 
	private ArrayList<Random2>  mRandoms;
	private ArrayList<Node>  mNodes;
	
	private int mAllocateSzie;
	
	public static WisdomManager get(){
		return wm!=null?wm:(wm = new WisdomManager(DEFAULT_SIZE));
	}
	
	public WisdomManager (int size){
		int allocate = mPolicy.allocate(size);
		this.mAllocateSzie = allocate;
		mRandoms = new ArrayList<Random2>(allocate);
		mNodes = new ArrayList<WisdomManager.Node>(allocate);
		Random2 r ;
		for(int i=0; i<allocate ;i++){
			r = new Random2();
			mRandoms.add(r);
			mNodes.add(new Node(r));
		}
	}
	
	/** 返回 [0,100)*/
	public int random100(){
		return random(0, 100);
	}
	/** 返回 [0,max)*/
	public int random(int max){
		return random(0, max);
	}
	
	public int random(int max,long seed){
		Random2 r = getAndMoveLast();
		r.setSeed(seed);
		int next = r.nextInt(max);
		mNodes.get(mNodes.size()-1).assignedCount++;
		return next;
	}
	
	/** 返回 [min,max)*/
	public int random(int min,int max){
		Random2 r = getAndMoveLast();
		int next = r.nextInt(max-min);
		mNodes.get(mNodes.size()-1).assignedCount++;
		return next+min;
	}
	/** [0.0,1.0) */
	public float nextFloat(){
		Random2 r = getAndMoveLast();
		float val = r.nextFloat();
		mNodes.get(mNodes.size()-1).assignedCount++;
		return val;
	}
	
	private Random2 getAndMoveLast(){
		int index = R.nextInt(mAllocateSzie);
		Node node = mNodes.remove(index);
		//过期就更新随机数
		boolean expiration = false;
		if(mPolicy.overDeadline(node.assignedCount)){
			node.r.changeSeed(1);
		    R.changeSeed(1);
			node.assignedCount =0;
			expiration = true;
		}
		mNodes.add(node);
		mRandoms.add(mRandoms.remove(index));
		//没过期直接返回
		if(!expiration)
			return node.r;
		
		//再次获取
		Random2 r = mRandoms.remove(index);
		mRandoms.add(r);
		mNodes.add(mNodes.remove(index));
		return r;
	}
	
	/**@return [0,max)*/
	public static int randomInt(int max){
		return get().random(max);
	}
	public static int random(int[] arr){
		return arr[WisdomManager.get().random(arr.length)];
	}
	public static <T>T random(T[] ts){
		return ts[WisdomManager.get().random(ts.length)];
	}
	
	public static TankType randomTankType(){
		return random(TankType.values());
	}
	
	public static Direction randomDirection(){
		return random(Direction.values());
	}
	public static Direction randomDirection(Direction preDir,
			 boolean mayEqualPre){
		Direction[] dirs = Direction.values();
		if(preDir!=null && !mayEqualPre){
			for(int len=dirs.length,i=len-1 ; i>=0 ;i-- ){
				if(preDir == dirs[i]){
					//swap value between the i and end-1
					if(i!=len-1){
						Direction d = dirs[len-1] ; //末尾
						dirs[len-1] = preDir; //i
						dirs[i] = d;                //为i重新赋值
					}
					break;
				}
			}
		}
		int index = WisdomManager.get().random(mayEqualPre?dirs.length:(dirs.length-1));
		return dirs[index];
	}
	
	private final AppointPolicy mPolicy = new AppointPolicy() {
		public int allocate(int total) {
			return total<=3 ? 1:(total>=20 ? 4: total/4);
		}
		public boolean overDeadline(int count){
			return count >= 5;
		}
	};
	
	class Node{
		public Random2 r;
		public int assignedCount;      /* 指派次数 */
		
		public Node(Random2 r) {
			super();
			this.r = r;
		}
	}
}
