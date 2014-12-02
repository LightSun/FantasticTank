package com.heaven7.fantastictank.support;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.heaven7.fantastictank.action.IActor;

/**
 * 包含 <li>中心点位置{@link #position} <li>范围{@link #bounds} <li>其他属性定义
 * {@link MatterDef} <li>统计器 {@link #attackRecorder} if
 * {@link #useAttackRecorder()} <li>纹理颜色{@link Color}
 * 
 * @author heaven7
 */
public class GameObject implements Resetable, IActor {
	public final Vector2 position; // 中心点
	public final Rectangle bounds;
	/** 攻击相关统计器 */
	public AttackRecorder attackRecorder;
	public final MatterDef def;
	
	/** 默认白色 */
	private final Color mColor = new Color(Color.WHITE);
	private Array<PoolEvent> mPoolEvents;

	/** 缓存到池子的事件 */
	public static abstract class PoolEvent {
		/** 观察Pool事件的对象 */
		private final GameObject poolObserver;
		
		public PoolEvent(GameObject observer) {
			this.poolObserver	= observer;	
		}
		public GameObject getObserver(){
			return poolObserver;
		}
		
		/**
		 * @param obj  被pool的对象 (当前对象)
		 */
		public abstract void beforePooled(GameObject obj);
	}

	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width,
				height);
		def = new MatterDef();
		if (useAttackRecorder())
			attackRecorder = new AttackRecorder();
	}

	/** 根据指定的x,y重置当前对象，宽高不动 */
	public void setPosition(Vector2 vec) {
		setPosition(vec.x, vec.y);
	}

	/** 根据指定的x,y重置当前对象，宽高不动 */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
		if (bounds.width != 0) { // 这里最好设置下bounds
			this.bounds.set(position.x - bounds.width / 2, position.y
					- bounds.height / 2, bounds.width, bounds.height);
		}
	}

	/** 设置bounds(Rectangle),左下角基于position但宽高重置 */
	public void setBounds(float width, float height) {
		this.bounds.set(position.x - width / 2, position.y - height / 2, width,
				height);
	}

	/** 是否统计被攻击的情况（某些对象比如砖块等。在根据被攻击的方向需要重置位置和大小） */
	public boolean useAttackRecorder() {
		return false;
	}

	/*
	 * public void addAction(Action a){ if(mActions==null) mActions = new
	 * Array<Action>(0); a.setActor(this); mActions.add(a); }
	 */
	
	//============= PoolEvent ================//
	
	public void addPoolEvent(PoolEvent l){
		if(this.mPoolEvents==null)
			mPoolEvents = new Array<GameObject.PoolEvent>(0);
		this.mPoolEvents.add(l);
	}
	public void clearPoolPoolEvents(){
		this.mPoolEvents.clear();
	}
	public void removePoolEvent(PoolEvent pe){
		this.mPoolEvents.removeValue(pe, true);
	}
	public Array<PoolEvent> getPoolEvents(){
		return mPoolEvents;
	}
	/**返回true表示调用pool listeners成功*/
	public boolean callPoolEvents(){
		if(mPoolEvents ==null)  return false;
		for(int i=0,size =mPoolEvents.size ;i<size ;i++){
			mPoolEvents.get(i).beforePooled(this);
		}
		return true;
	}
	
	/**根据指定的观察者清除 对应的pool event*/
	public void removePoolEventsByObserver(GameObject obj) {
		for(int i=0,size =mPoolEvents.size ;i<size ;i++){
			PoolEvent pe = mPoolEvents.get(i);
			if(pe.getObserver() == obj){
				mPoolEvents.removeIndex(i);
				i--;
				size --;
			}
		}
	}
			 
	// ========== 纹理颜色================//
	@Override
	public Color getColor() {
		return mColor;
	}

	public void setColor(Color color) {
		this.mColor.set(color);
	}

	public void setAlpha(float alpha) {
		this.mColor.a = alpha;
	}

	/** 减少颜色分量 */
	@Override
	public void decColor(float deltaR, float delatG, float deltaB, float deltaA) {
		this.mColor.r -= deltaR;
		this.mColor.g -= delatG;
		this.mColor.b -= deltaB;
		this.mColor.a -= deltaA;
	}

	@Override
	public void addColor(float deltaR, float delatG, float deltaB, float deltaA) {
		this.mColor.r += deltaR;
		this.mColor.g += delatG;
		this.mColor.b += deltaB;
		this.mColor.a += deltaA;
	}

	public void setColor(float r, float g, float b, float a) {
		this.mColor.r = r;
		this.mColor.g = g;
		this.mColor.b = b;
		this.mColor.a = a;
	}

	/** 纹理颜色是否是白色 */
	public boolean isWhiteColor() {
		return this.mColor.equals(Color.WHITE);
	}

	// =========================================//

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [position=" + position + "]";
	}

	/** 不会重置position 和 bounds */
	@Override
	public void reset() {
		if (this.attackRecorder != null)
			attackRecorder.reset();
		this.def.reset();
		if (!this.mColor.equals(Color.WHITE))
			this.mColor.set(Color.WHITE);
		if(mPoolEvents!=null) mPoolEvents.clear();
	}
}
