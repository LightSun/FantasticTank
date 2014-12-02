package com.heaven7.fantastictank.support;

import com.badlogic.gdx.utils.Array;
import com.heaven7.fantastictank.level.AbsLevel;
import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.buff.ForeverBuff;
import com.heaven7.fantastictank.wisdom.WisdomManager;

/**
 * buff���ɼ�¼��/������ ,ÿһ���¹ؿ����ɺ���Ҫ����{@link #increaseLeveIndex()} 
 * @author Administrator
 */
public class BuffController implements Updateable{
	
	public static final int GENERATE_INTERVAL = 10; /* buff���ɵ�ʱ����(��) */
	
	public static final int FIAG_RANDOM_X  = 1; 
	public static final int FIAG_RANDOM_Y  = 2; 
	public static final int FIAG_RANDOM_XY = 3; 
	
	private int flag = FIAG_RANDOM_XY;
	private Array<Buff> mBuffs = new Array<Buff>();
	
	private int level = 0;
	private long startTime;   /*  ��ǰ�ؿ���ʼ��ʱ��   */
	private long currentTime;
	private long lastTime;
	
	private BuffController(){}
	
	public static BuffController get(){
		return Creator.INSTANCE;
	}
	
	/** ���ô˷������Զ��������buff */
	public BuffController begin(){
		 currentTime = startTime = System.currentTimeMillis();
		 lastTime = 0;
		 if(mBuffs.size>0 )
		      mBuffs.clear();
		 return this;
	}
	@Override
	public void update(float delta){
		currentTime = System.currentTimeMillis();
		for(int i= 0, size = mBuffs.size ; i<size; i++){
			Buff buff = mBuffs.get(i);
			//buff���Ե�/���õ�buff/buff����
			if(buff instanceof ForeverBuff || buff.isExpired()){
				buff.cancel();
				mBuffs.removeIndex(i);
				i--;
				size --;
			}
		}
	}
	
	/** �Ƿ񵽴���ָ����ʱ���� */
	public boolean isReachedInterval(){
		if(lastTime ==0){
		   if((currentTime - startTime) > GENERATE_INTERVAL*1000){
			   lastTime = currentTime;
			  return true; 
		   }
		}else{
			 if((currentTime - lastTime) > GENERATE_INTERVAL*1000){
				  lastTime = currentTime;
				  return true; 
			 }
		}
		return false;	
	}
	
	public Buff generate(int id){
		Buff buff = Buff.getBuff(id, getX(), getY());
		buff.setStartTime(System.currentTimeMillis());
		mBuffs.add(buff);
		//System.out.println(buff.toString());
		return buff;
	}
	public Buff generateRandomBuff(){
		Buff buff = Buff.getBuff(WisdomManager.get().random(88), getX(), getY());
		buff.setStartTime(System.currentTimeMillis());
		mBuffs.add(buff);
		//System.out.println(buff.toString());
		return buff;
	}
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	/**�ؿ�+1*/
	public void increaseLeve(){
		level++;
	}
	public void seteLeve(int level){
		this.level = level;
	}
	public int getLevel(){
		return level;
	}
	
	public float getX(){
		switch (flag) {
		case FIAG_RANDOM_X:
		case FIAG_RANDOM_XY:
			return WisdomManager.random(AbsLevel.Xarr)+
					AbsLevel.getMinX();
			
		case FIAG_RANDOM_Y:
			if((level + AbsLevel.getMinX()) >AbsLevel.getMaxX()){
				if(level == 0 ) throw new RuntimeException();
				return level % AbsLevel.Xarr.length+AbsLevel.getMinX();
			}else
				return level + AbsLevel.getMinX();
			
		default:
			throw new RuntimeException();
		}
	}
	public float getY(){
		//y�������һ�в�Ҫ
		switch (flag) {
		case FIAG_RANDOM_X:{
			if((level - AbsLevel.getMinY()) > AbsLevel.getMaxY()-AbsLevel.getUnitHeight()){
				if(level == 0 ) throw new RuntimeException();
				float val = level % AbsLevel.Yarr.length - AbsLevel.getMinY();
				if(val<0) val = val+ AbsLevel.getMinY()*2;
				return val;
			}else
				return level - AbsLevel.getMinY();
		}
			
		case FIAG_RANDOM_XY:
		case FIAG_RANDOM_Y:
			float val = WisdomManager.random(AbsLevel.Yarr) - AbsLevel.getMinY();
			if(val<0) val = val + AbsLevel.getMinY()*2;
			return val;
			
		default:
			throw new RuntimeException();
		}
	}
	private static class Creator{
		public static final BuffController INSTANCE = new BuffController();
	}
	
}
