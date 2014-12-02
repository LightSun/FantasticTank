package com.heaven7.fantastictank.mood;



/**
 * �������� Ӧ��״̬ģʽȥ�л�--����/����--ÿ��״̬�ڲ�ȥ�л���
 *(�߰�,һ�㣬�˷ܣ����ţ����䣬��ŭ) ϲ��ŭ�������壬����������
 *<li> ����Ƶ�����: ��ͨ��Normal,�˷�happy������Sad������Fear����ŭAngry.
 *   boss����: ���ţ���������ŭ������
 *   <li> ����һ������ַ�������ȫ�����µ���ķ�ʽȥ������Щmood����
 * @author Administrator
 */
public abstract class Mood {
	
	public static final int MODE_AUTO_FIT = 1;  /* �Զ��л�,�� switchNext = true */
	public static final int MODE_TIME = 2;      /* ʱ�䵽ʱ--�Զ��л� */
	public static final int DEFAULT_INTERVAL_TIME = 10; //TODO 10 for test

	protected boolean switchNext; //��һ֡ʱ,�Ƿ��л�
	protected Mood next;
	protected long lastTime;
	/**Ĭ��30���л�һ��*/
	private int interalTime = DEFAULT_INTERVAL_TIME;
	private int mode = MODE_TIME;
	
	public int getIntervalTime() {
		return interalTime;
	}
	/**���ü��ʱ�䣨��ƣ���ֻ��mode = {@link #MODE_TIME}ʱ��Ч*/
	public void setIntervalTime(int intervalTime) {
		interalTime = intervalTime;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		if(mode !=MODE_AUTO_FIT && mode !=MODE_TIME)
			throw new IllegalArgumentException("wrong mode");
		this.mode = mode;
	}
	/**������һ���Ƿ��Զ��л�(mode�Զ���Ϊ #MODE_AUTO_FIT)*/
	public void setSwitchNext(boolean switchNext) {
		this.switchNext = switchNext;
		if(mode != MODE_AUTO_FIT)
			this.mode = MODE_AUTO_FIT;
	}
	public void setNext(Mood next) {
		this.next = next;
	}
	
	public void update(Mooder m){
		//System.out.println("��ǰ�� lastTime ="+lastTime+", mood = "+m.getMood().toString());
		
		switch (getMode()) {
		case MODE_AUTO_FIT:
			if(switchNext){
			   	cancel(m);
			    Mood moon = m.getType() == Mooder.TYPE_NORMAL? defaultNext()
			    		:(next!=null?next:defaultNext());
				m.setMood(moon);
				moon.apply(m);
			}
			break;
			
		case MODE_TIME:
			 if(lastTime!=0 && (System.currentTimeMillis()- lastTime) >= getIntervalTime()*1000){
				 cancel(m);
				 Mood moon = m.getType() == Mooder.TYPE_NORMAL? defaultNext()
				    		:(next!=null?next:defaultNext());
				 m.setMood(moon);
				 moon.apply(m);
				 lastTime = System.currentTimeMillis();
			 }
			 
			 if(lastTime == 0)  lastTime = System.currentTimeMillis();
			break;

		default:
			throw new RuntimeException();
		}
	};
	/** Ĭ����һ��������cann't be null*/
	protected abstract Mood defaultNext();
	protected abstract void apply(Mooder m);
	protected abstract void cancel(Mooder m);
	
	
	/** ϲ */
	public static final Mood Happy = new Mood() {
		
		public String toString() {
			return "Happy";
		};
		protected void apply(Mooder m) {
			m.addShootProbability(5); 
		}
		public Mood defaultNext() {
			return Angry;
		}
		@Override
		protected void cancel(Mooder m) {
			m.addShootProbability(-5);
		}
	};
	
	/** ŭ */
	public static final Mood Angry = new Mood() {
		
		public String toString() {
			return "Angry";
		};
		public void apply(Mooder m) {
			m.setShootAtOnce(true);
		}
		public Mood defaultNext() {
			return Sad;
		}
		@Override
		protected void cancel(Mooder m) {
			m.setShootAtOnce(false);
		}
	};
	/** ��,����ģ����˵�(���ܻں�����) */
	public static final Mood Sad = new Mood() {
		
		public String toString() {
			return "Sad";
		};
		public void apply(Mooder m) {
			//System.err.println("�����Ա���...");
			m.setTrackFoeman(true);
			m.setCollideDieTogether(true);
		}
		public Mood defaultNext() {
			return Fear;
		}
		@Override
		protected void cancel(Mooder m) {
			m.setTrackFoeman(false);
			m.setCollideDieTogether(false);
			//System.err.println("�����Ա���...");
		}
	};
	/** �� */
	public static final Mood Fear = new Mood() {
		public String toString() {
			return "Fear";
		};
		public Mood defaultNext() {
			return Normal;
		}
		protected void apply(Mooder m) {
			//System.err.println("���Ա���..."+m.isCollideDieTogether());
			m.setEscapeByDistance(ESCAPE_DISTANCE,true);
		}
		@Override
		protected void cancel(Mooder m) {
			m.setEscapeByDistance(ESCAPE_DISTANCE,false);
		}
	};
	/** �� */
	public static final Mood Love = new Mood() {
		
		public String toString() {
			return "Love";
		};
		public Mood defaultNext() {
			return Normal;
		}
		protected void apply(Mooder m) {
			if(m.getType() == Mooder.TYPE_NORMAL)
				throw new IllegalStateException("only boss support Mood.love");
			((AdvancedMooder)m).scaleLife(0.8f);
			((AdvancedMooder)m).restoreChildrenLife();
		}
		@Override /** for ever mood , cancel do nothing */
		protected void cancel(Mooder m) {
		}
	};
	/** �߰����Ը�(һ��Boss����) */
	public static final Mood Concelt = new Mood() {
		
		public String toString() {
			return "Concelt";
		};
		public Mood defaultNext() {
			return Normal;
		}
		protected void apply(Mooder m) {
			if(m.getType() == Mooder.TYPE_NORMAL)
				throw new IllegalStateException("only boss support this Mood = "+toString());
			((AdvancedMooder)m).setActivateAttack(false);
		}
		@Override
		protected void cancel(Mooder m) {
			((AdvancedMooder)m).setActivateAttack(true);
		}
	};
	/** ���� (���Է���)*/
	public static final Mood Confidence = new Mood() {
		
		public String toString() {
			return "Confidence";
		};
		public Mood defaultNext() {
			return Normal;
		}
		protected void apply(Mooder m) {
			if(m.getType() == Mooder.TYPE_NORMAL)
				throw new IllegalStateException("only boss support this Mood = "+toString());
			((AdvancedMooder)m).setAbsoluteDefense(true);
		}
		@Override
		protected void cancel(Mooder m) {
			((AdvancedMooder)m).setAbsoluteDefense(false);
		}
	};
     /** fury ��ŭ */
	public static final Mood Fery = new Mood() {
		
		static final int RATE = 2;//����2���Ƶ�����
		
		public String toString() {
			return "Fery";
		};
		public Mood defaultNext() {
			return Normal;
		}
		protected void apply(Mooder m) {
			if(m.getType() == Mooder.TYPE_NORMAL)
				throw new IllegalStateException("only boss support this Mood = "+toString());
			//������������������
			((AdvancedMooder)m).scaleAttack(RATE);
			((AdvancedMooder)m).scaleDefense(1f/RATE);
		}
		@Override
		protected void cancel(Mooder m) {
			((AdvancedMooder)m).scaleAttack(1f/RATE);
			((AdvancedMooder)m).scaleDefense(RATE);
		}
	};
	
	
	
	/**�����*/
	public static final Mood Normal = new Mood() {
		public Mood defaultNext() {
			return Happy;
		}
		protected void apply(Mooder m) {
			//do nothing 
		}
		@Override
		protected void cancel(Mooder m) {
		}
		public String toString() {
			return "Normal";
		};
	};
	
	public static final float ESCAPE_DISTANCE = 2f; //���ܵľ���
}
