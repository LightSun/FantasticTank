package com.heaven7.fantastictank.matters;

import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.DynamicGameObject;
import com.heaven7.fantastictank.support.PathRecorder;

/**
 * BulletType
 * 
 * <pre>
 * Normal     常规子弹       --- 类似以前玩的坦克大战的普通子弹
 * Penetrable 穿甲弹           --- 穿透1个对象，攻击到下一个对象。类似隔山打牛
 * Laddered   路径类的子弹--- 折线攻击
 * Rebounding 反弹子弹       --- 击中目标后方向相反
 * Trackable  跟踪弹           --- 跟踪最近的目标--直到击中（一般一击必杀）---同时自身也消失
 * 第2阶段可能增加，速度，射程，攻击力等Buff
 * 
 * <pre>
 * @author heaven7
 */
public class Bullet extends DynamicGameObject {

	private BulletType mBulletType;
	private PathRecorder mRecorder;
	private Tank mOwner;
	public int mId;
	static int id;

	public Bullet(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	/**only used for cache*/
	public Bullet(float x, float y) {
		super(x, y,0,0);
		id++;
		mId = id;
	}

	@Override
	public void reset() {
		super.reset();
		mRecorder = null;
	    mBulletType =null;
	    mOwner = null;
	}
	
	protected void update0(float deltaTime) {
		// 不调用，空实现
	}

	@Override
	public void update(float deltaTime) {
		// System.out.println("bullet dir = "+getDirection());
		if (this.def.hitCount == 0) {
			this.def.alive = false;
			return;
		}

		setLastX(position.x);
		setLastY(position.y);

		if (mRecorder != null) {
			if (mRecorder.isDead()) {
				this.def.alive = false;
				return;
			}
			if (mRecorder.canTurnDirection()) {
				Direction dir = mRecorder.nextDirection();
				if (dir != null) 	switchDirection(dir);				
			}
		}

		// 更新坐标
		float deltaX = velocity.x * deltaTime;
		float deltaY = velocity.y * deltaTime;

		this.position.add(deltaX, deltaY);
		setBoundByPosition();

		doWithHideIfNeed(deltaTime);

		if (mRecorder != null)
			mRecorder.record(getDirection(),
					Math.abs(deltaX != 0 ? deltaX : deltaY));

		processEdge();
	}

	@Override
	protected boolean processEdge0() {
		this.def.alive = false;
		return true;
	}

	// ====== bean methods ======//

	public BulletType getBulletType() {
		return mBulletType;
	}

	public void setBulletType(BulletType bulletType) {
		this.mBulletType = bulletType;
	}

	public void setOwner(Tank t) {
		mOwner = t;
	}

	/** may dead */
	public Tank getOwner() {
		return mOwner;
	}

	// =======================================//

	public PathRecorder getPathRecorder() {
		return mRecorder;
	}
	public void setPathRecorder(PathRecorder recorder) {
		this.mRecorder = recorder;
	}
	//==========================//

	// normal,blue,green,dark_blue,red
	/** 子弹类型,
	 * <li>【普通narmal，穿透的Penetrable，折线路径(梯形Laddered),
	 * <li>反弹子弹Rebounding, 死亡弹（碰撞都死亡）DieTogether】
	 *  */
	public static enum BulletType {
		//2个type之和sum：  2,6,14,30,62-- 4,8,16,32--10,18,34 -- 22,38 -- 46
		//2^n - 1 : 1,3,7,15,31,63,127...
		Normal(1){
			public BulletType next() {
				return Penetrable;
			}
			public String toString(){
				return "Normal";
			}
		}, 
		Penetrable(2){
			public BulletType next() {
				return Laddered;
			}
			public String toString(){
				return "Penetrable";
			}
		}, 
		Laddered(3){
			public BulletType next() {
				return Rebounding;
			}
			public String toString(){
				return "Laddered";
			}
		}, 
		Rebounding(4){
			public BulletType next() {
				return DieTogether;
			}
			public String toString(){
				return "Rebounding";
			}
		}, 
		DieTogether(5){
			public BulletType next() {
				return Penetrable;
			}
			public String toString(){
				return "DieTogether";//TODO 
			}
		};

		final int index;
		public final int value;
		
        /**index 从0开始*/
		private BulletType(int index) {
			this.index = index;
			value = caculate(index);
		}
		/** next 永远不会返回  {@link #Normal}--2,3,4,5,2,3,4,5循环*/
		public abstract BulletType next();
		public static final int minIndex(){
			return Normal.index;
		}
		public static final int maxIndex(){
			return DieTogether.index;
		}
		public static int caculate(int index){
			return (int) Math.pow(2, index) - 1;// 2^n -1
		}
	}
	//枚举2种BulletType.value之和
	public static final class TypeSum{
		private static final int V1 = BulletType.caculate(1);
		private static final int V2 = BulletType.caculate(2);
		private static final int V3 = BulletType.caculate(3);
		private static final int V4 = BulletType.caculate(4);
		private static final int V5 = BulletType.caculate(5);
		
		public static final int SUM_BY_INDEX_1_1 = V1*2; //2个都是 index=1的value和
		public static final int SUM_BY_INDEX_2_2 = V2*2;
		public static final int SUM_BY_INDEX_3_3 = V3*2;
		public static final int SUM_BY_INDEX_4_4 = V4*2;
		public static final int SUM_BY_INDEX_5_5 = V5*2;
		
		public static final int SUM_BY_INDEX_1_2 = V1+V2;
		public static final int SUM_BY_INDEX_1_3 = V1+V3;
		public static final int SUM_BY_INDEX_1_4 = V1+V4;
		public static final int SUM_BY_INDEX_1_5 = V1+V5;
		
		public static final int SUM_BY_INDEX_2_3 = V2+V3;
		public static final int SUM_BY_INDEX_2_4 = V2+V4;
		public static final int SUM_BY_INDEX_2_5 = V2+V5;
		
		public static final int SUM_BY_INDEX_3_4 = V3+V4;
		public static final int SUM_BY_INDEX_3_5 = V3+V5;
		
		public static final int SUM_BY_INDEX_4_5 = V4+V5;
	}
}
