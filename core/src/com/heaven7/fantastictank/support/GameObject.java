package com.heaven7.fantastictank.support;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.heaven7.fantastictank.action.IActor;

/**
 * ���� <li>���ĵ�λ��{@link #position} <li>��Χ{@link #bounds} <li>�������Զ���
 * {@link MatterDef} <li>ͳ���� {@link #attackRecorder} if
 * {@link #useAttackRecorder()} <li>������ɫ{@link Color}
 * 
 * @author heaven7
 */
public class GameObject implements Resetable, IActor {
	public final Vector2 position; // ���ĵ�
	public final Rectangle bounds;
	/** �������ͳ���� */
	public AttackRecorder attackRecorder;
	public final MatterDef def;
	
	/** Ĭ�ϰ�ɫ */
	private final Color mColor = new Color(Color.WHITE);
	private Array<PoolEvent> mPoolEvents;

	/** ���浽���ӵ��¼� */
	public static abstract class PoolEvent {
		/** �۲�Pool�¼��Ķ��� */
		private final GameObject poolObserver;
		
		public PoolEvent(GameObject observer) {
			this.poolObserver	= observer;	
		}
		public GameObject getObserver(){
			return poolObserver;
		}
		
		/**
		 * @param obj  ��pool�Ķ��� (��ǰ����)
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

	/** ����ָ����x,y���õ�ǰ���󣬿�߲��� */
	public void setPosition(Vector2 vec) {
		setPosition(vec.x, vec.y);
	}

	/** ����ָ����x,y���õ�ǰ���󣬿�߲��� */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
		if (bounds.width != 0) { // �������������bounds
			this.bounds.set(position.x - bounds.width / 2, position.y
					- bounds.height / 2, bounds.width, bounds.height);
		}
	}

	/** ����bounds(Rectangle),���½ǻ���position��������� */
	public void setBounds(float width, float height) {
		this.bounds.set(position.x - width / 2, position.y - height / 2, width,
				height);
	}

	/** �Ƿ�ͳ�Ʊ������������ĳЩ�������ש��ȡ��ڸ��ݱ������ķ�����Ҫ����λ�úʹ�С�� */
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
	/**����true��ʾ����pool listeners�ɹ�*/
	public boolean callPoolEvents(){
		if(mPoolEvents ==null)  return false;
		for(int i=0,size =mPoolEvents.size ;i<size ;i++){
			mPoolEvents.get(i).beforePooled(this);
		}
		return true;
	}
	
	/**����ָ���Ĺ۲������ ��Ӧ��pool event*/
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
			 
	// ========== ������ɫ================//
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

	/** ������ɫ���� */
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

	/** ������ɫ�Ƿ��ǰ�ɫ */
	public boolean isWhiteColor() {
		return this.mColor.equals(Color.WHITE);
	}

	// =========================================//

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [position=" + position + "]";
	}

	/** ��������position �� bounds */
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
