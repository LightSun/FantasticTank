package com.heaven7.fantastictank.mood;



/**
 * 情绪对象（ 应用状态模式去切换--心情/情绪--每种状态内部去切换）
 *(高傲,一般，兴奋，自信，低落，愤怒) 喜，怒，哀，惧，爱，恶，欲。
 *<li> 已设计的情绪: 普通的Normal,兴奋happy，哀伤Sad，害怕Fear，愤怒Angry.
 *   boss特有: 自信，傲慢，暴怒，爱心
 *   <li> 还有一种设计手法，就是全部用新的类的方式去定义这些mood对象
 * @author Administrator
 */
public abstract class Mood {
	
	public static final int MODE_AUTO_FIT = 1;  /* 自动切换,当 switchNext = true */
	public static final int MODE_TIME = 2;      /* 时间到时--自动切换 */
	public static final int DEFAULT_INTERVAL_TIME = 10; //TODO 10 for test

	protected boolean switchNext; //下一帧时,是否切换
	protected Mood next;
	protected long lastTime;
	/**默认30秒切换一次*/
	private int interalTime = DEFAULT_INTERVAL_TIME;
	private int mode = MODE_TIME;
	
	public int getIntervalTime() {
		return interalTime;
	}
	/**设置间隔时间（秒计），只有mode = {@link #MODE_TIME}时有效*/
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
	/**设置下一次是否自动切换(mode自动变为 #MODE_AUTO_FIT)*/
	public void setSwitchNext(boolean switchNext) {
		this.switchNext = switchNext;
		if(mode != MODE_AUTO_FIT)
			this.mode = MODE_AUTO_FIT;
	}
	public void setNext(Mood next) {
		this.next = next;
	}
	
	public void update(Mooder m){
		//System.out.println("当前： lastTime ="+lastTime+", mood = "+m.getMood().toString());
		
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
	/** 默认下一个情绪，cann't be null*/
	protected abstract Mood defaultNext();
	protected abstract void apply(Mooder m);
	protected abstract void cancel(Mooder m);
	
	
	/** 喜 */
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
	
	/** 怒 */
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
	/** 哀,低落的，悲伤的(可能悔恨所致) */
	public static final Mood Sad = new Mood() {
		
		public String toString() {
			return "Sad";
		};
		public void apply(Mooder m) {
			//System.err.println("允许自爆了...");
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
			//System.err.println("不能自爆了...");
		}
	};
	/** 惧 */
	public static final Mood Fear = new Mood() {
		public String toString() {
			return "Fear";
		};
		public Mood defaultNext() {
			return Normal;
		}
		protected void apply(Mooder m) {
			//System.err.println("能自爆？..."+m.isCollideDieTogether());
			m.setEscapeByDistance(ESCAPE_DISTANCE,true);
		}
		@Override
		protected void cancel(Mooder m) {
			m.setEscapeByDistance(ESCAPE_DISTANCE,false);
		}
	};
	/** 爱 */
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
	/** 高傲、自负(一般Boss才有) */
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
	/** 自信 (绝对防御)*/
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
     /** fury 暴怒 */
	public static final Mood Fery = new Mood() {
		
		static final int RATE = 2;//比例2倍似的增长
		
		public String toString() {
			return "Fery";
		};
		public Mood defaultNext() {
			return Normal;
		}
		protected void apply(Mooder m) {
			if(m.getType() == Mooder.TYPE_NORMAL)
				throw new IllegalStateException("only boss support this Mood = "+toString());
			//攻击翻倍，防御减半
			((AdvancedMooder)m).scaleAttack(RATE);
			((AdvancedMooder)m).scaleDefense(1f/RATE);
		}
		@Override
		protected void cancel(Mooder m) {
			((AdvancedMooder)m).scaleAttack(1f/RATE);
			((AdvancedMooder)m).scaleDefense(RATE);
		}
	};
	
	
	
	/**常规的*/
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
	
	public static final float ESCAPE_DISTANCE = 2f; //逃跑的距离
}
