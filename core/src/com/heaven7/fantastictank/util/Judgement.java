package com.heaven7.fantastictank.util;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.interfaces.WorldEventListener;
import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.matters.Bullet.TypeSum;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.MyTank;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;
import com.heaven7.fantastictank.matters.TankBoss;
import com.heaven7.fantastictank.support.Direction;
import com.heaven7.fantastictank.support.GameObject;
import com.heaven7.fantastictank.support.HideTool;
/**
 * ����
 * @author Administrator
 */
public class Judgement {
	
	final WorldEventListener mListener;
	
	public Judgement(WorldEventListener listener) {
		super();
		this.mListener = listener;
	}
	
	/**
	 * @param buff
	 * @param tanks  Ⱥ��buffʱ���ҷ�ʩ�Ӹ����ˣ���Ӧ�õı�������
	 * @param hostileTanks  ʩ�Ӹ����˵�Ⱥ��Buffʱ��Ӧ�õĵ��˶���
	 */
	public <T extends Tank,H extends Tank>void checkBuffTankCollision(
			Buff buff, List<T> tanks,List<H> hostileTanks) {
		if(buff==null) return;
		
		int size = tanks.size();
		for (int i = 0; i < size; i++) {
			Tank t = tanks.get(i);
			if(t.isHide()) continue;
			
			if(OverlapTester.overlapRectangles(buff.bounds, t.bounds)){
				if(buff.applyToHostile()){
					if(buff.scope() == Buff.SCOPE_PERSONAL) throw new IllegalStateException();
					buff.applyAll(hostileTanks);
				}else{
					if(buff.scope() == Buff.SCOPE_GROUP){
						buff.applyAll(tanks);
					}else
				        buff.apply(t);
				}
				buff.def.alive = false;
				mListener.onCatchBuff(t,buff);
			}
		}
	}
	
	/** ����t1��ts���ϵ�����������ײ
	 *  @param t1  ������Ϊts��һ����
	 *  @param   */
	@SuppressWarnings("unchecked")
	public <T extends Tank> void handleTanksCollide(List<T> ts, Tank t1){
		boolean removed = ts.remove(t1); //���Ƴ�,�������
		boolean  die = false;
		
		int size = ts.size();
		for(int i=0; i<size ; i++){
			T t = ts.get(i);
			if(t == t1) continue;
			
			if(OverlapTester.overlapRectangles(t1.bounds, t.bounds)){
				if( t.def.type!= t1.def.type && (
						(t1 instanceof AutoTank && ((AutoTank) t1).isCollideDieTogether())
						|| (t instanceof AutoTank && ((AutoTank) t).isCollideDieTogether()) 
						)  ){
					ts.remove(t);
					size --;
					i--;
					mListener.onTankDied(t.position.x, t.position.y, t);
					CacheHelper.freeSafe(t);
					die = true;
					break;
				}else{
					 if(!t1.isManual()){
						 t1.getCollidePolicy().handle(t1, t);
					 }else
						 t.getCollidePolicy().handle(t, t1);
				}
			}
		}
		//���ڼ����е�һԱ?
		if(removed){
		//û�����ͻָ�
			if(!die) ts.add((T) t1);
		}
		//�Żس���
		if(die ){
			mListener.onTankDied(t1.position.x, t1.position.y, t1);
			CacheHelper.freeSafe(t1);
		}
	}
	
	/** ����̹��֮�����ײ(2������--��ͬҲû��ϵ)
	 *  <li>t2s ��ʾT2���� t���ڵļ���*/
	public <T1 extends Tank,T2 extends Tank> void handleTanksCollide(
			List<T1> ts1, List<T2> ts2){
		int size = ts1.size();
		for(int i =0; i<size ;i++){
			T1 t1 = ts1.get(i);
			//�ѱ��������
			if(!t1.def.alive) continue;
			
			for(int j=0, len = ts2.size() ; j < len ; j++){
				T2 t2 = ts2.get(j);
				if(t1 == t2)       continue;
				if(!t2.def.alive)  continue;
				
				if(OverlapTester.overlapRectangles(t1.bounds, t2.bounds)){
					//ͬ���ھ�
					if( t2.def.type!= t1.def.type && (
							(t1 instanceof AutoTank && ((AutoTank) t1).isCollideDieTogether())
							|| (t2 instanceof AutoTank && ((AutoTank) t2).isCollideDieTogether()) 
							)  ){
						//�������
						t1.def.alive = false; 
						t2.def.alive = false;
						break;
					}else{
						 if(!t1.isManual()){
							 t1.getCollidePolicy().handle(t1, t2);
						 }else
							 t2.getCollidePolicy().handle(t2, t1);
					}
				}
			}
		}
		
		size = ts1.size();
		for(int i = 0; i<size ;i++){
			T1 t1 = ts1.get(i);
			if(!t1.def.alive){
				ts1.remove(t1);
				size --;
				i--;
				mListener.onTankDied(t1.position.x, t1.position.y, t1);
				CacheHelper.freeSafe(t1);
			}
		}
		
		//��ͬ�ļ���ʱ��2�����϶���Ҫ���
		if(ts2 != ts1){
			size = ts2.size();
			for(int i = 0; i<size ;i++){
				T2 t2 = ts2.get(i);
				if(!t2.def.alive){
					ts2.remove(t2);
					size --;
					i--;
					mListener.onTankDied(t2.position.x, t2.position.y, t2);
					CacheHelper.freeSafe(t2);
				}
			}
		}
	}
	
	/** ��������̹����ײstatic */
	public  void handleCollideStaticObject(MyTank t, List<? extends StaticObject> objs) {
		if(t.isHide()) return; //����
		
		int size = objs.size();
		for(int j=0 ; j<size ; j++){
			StaticObject obj = objs.get(j);
			Rectangle r = obj.getClippedInfo()!=null?obj.getClippedInfo().bounds:obj.bounds;
			if(OverlapTester.overlapRectangles(r, t.bounds)){
			       t.stay();  //MyTank������Ϊ����
			}
		}
	}
	
	/** ����̹�˺;�̬�������ײ  */
	public void handleTankCollisions(List<? extends Tank> tanks,List<? extends StaticObject> objs){
		int size = objs.size();
		int tSize = tanks.size();
		for(int i=0;i<tSize ; i++){
			Tank t = tanks.get(i);
			if(t.isHide()) continue; //����
			
			for(int j=0 ; j<size ;j++){
				StaticObject obj = objs.get(j);
				Rectangle r = obj.getClippedInfo()!=null?obj.getClippedInfo().bounds:obj.bounds;
				if(OverlapTester.overlapRectangles(r, t.bounds)){
					if(t.isManual())
						t.stay();      //��Ϊ����
					else 
						t.getCollidePolicy().handle(t, obj);
				}
			}
		}
	}

	/** �����ӵ�----̹����ײ */
	public void handleBulletCollisions(List<Bullet> bullets,List<? extends Tank> tanks){
		int bSize = bullets.size();
		int tSize = tanks.size();
		for(int i=0;i<bSize ; i++){
			Bullet b = bullets.get(i);
			if(b.isHide()) continue; //����
			
			for(int j=0; j<tSize ;j++){
				Tank t = tanks.get(j);
				//����
				if(t.isHide())continue;	
				//�޵�
				if(t.isUnrivaled()) continue; 
				//ͬһ��Ӫ
				if(b.def.type == t.def.type) continue; 
				
				//boss ���Է���
				if(t instanceof TankBoss && ((TankBoss)t).isAbsoluteDefense())
					continue ;
				
				if(OverlapTester.overlapRectangles(b.bounds, t.bounds)){
					b.def.hitCount --;
					t.def.beHitCount --;
					
					processCollideBulletGameObject(b, t);
					if(t.def.beHitCount== 0){
						t.def.alive = false;
						tanks.remove(t);
						tSize--;
						j--;
						mListener.onTankDied(b.position.x,b.position.y,t);
						CacheHelper.freeSafe(t);
					}else
						mListener.onHitTank(b.position.x,b.position.y,t);
					
					if(b.def.hitCount == 0){
						b.def.alive = false;
						bullets.remove(b);
						bSize--;
						i--;
						resotreOpenFireCount(b,"[handleBulletCollisions()]");
						//�ӵ�����������Ӧ�ü�����ѭ��
						break;
					}
				}
			}
		}
	}

	/** �ָ�tank��open fire ���� (�������).Ȼ��free bullet�س���
	 * @deprecated �ѹ�ʱ���ָ��ѽ���poolEventȥ����*/
	public static void resotreOpenFireCount(Bullet b,String tag) {
	    	CacheHelper.freeSafe(b);
		/*
		Tank owner = b.getOwner();
	    if(owner!=null){
		    owner.decreaseFiredCount();
		    owner.increaseOpenFireCount();
		   // b.setOwner(null);
	    	CacheHelper.freeSafe(b);
	    	int count = owner.getOpenFireCount();
	    	//System.out.println("b.owner = Mytank ? "+(owner instanceof MyTank)+", tag = "+tag);
	    	if(count!=1)
	    	    System.err.println("tank openfireCount: "+count);
	    }
*/	    //owner ==null may be the poolListenr.called
	    /*else{
	    	 System.err.println("haven't owner(bullet) : b.id = "+b.mId +",tag = "+tag );
	    }*/
	    //��Ȼ�ܶ���else. why(�ӵ�����new��Ҳһ��)��  
	    //b.setOwner(null);���������(����ֻ����1ǹ��������˺ü��Σ�һ���ӵ�...)	
	    // s���û����bullet����bug,why?
	    // Bug���޸�����Ҫѭ����ʱ���ӵ�����Ӧ��break��ǰ�ӵ���ѭ��
	}
	
	/** �����ӵ�--��--�ӵ� */
	public void handleBulletsCollisions(List<Bullet> bullets,List<Bullet> bullets2){
		int bSize = bullets.size();
		int b2Size = bullets2.size();
		for(int i=0;i<bSize ; i++){
			Bullet b = bullets.get(i);
			if(b.isHide()) continue; //����
			
			for(int j = 0; j < b2Size ; j++){
				Bullet b2 = bullets2.get(j);
				if(b2.isHide()) continue;	//����	
				if(b.def.type == b2.def.type) continue; //ͬһ��Ӫ
				
				if(OverlapTester.overlapRectangles(b.bounds, b2.bounds)){
					b.def.hitCount --;
					b2.def.hitCount --;
					
					processBulletsCollide(b, b2);
					if(b2.def.hitCount== 0){
						b2.def.alive = false;
						bullets2.remove(b2);
						b2Size--;
						j--;
						resotreOpenFireCount(b2,"handleBulletsCollisions()");
					}
					if(b.def.hitCount == 0){
						b.def.alive = false;
						bullets.remove(b);
						bSize--;
						i--;
						resotreOpenFireCount(b,"handleBulletsCollisions()");
						break; //�ж���ѭ��
					}
				}
			}
		}
	}
	
	/** �����ӵ�--and-��̬����  ����ײ*/
	public void handleStaticCollisions(List<Bullet> bullets,List<? extends StaticObject> objs){
		int bSize = bullets.size();
		int len = objs.size();
		for(int i=0;i<bSize ; i++){
			Bullet b = bullets.get(i);
			if(b.isHide()) continue; //����
			
			for(int j = 0; j < len ; j++){
				StaticObject obj = objs.get(j);
				//����ü���---bounds��仯
				Rectangle r = obj.getClippedInfo()!=null?obj.getClippedInfo().bounds:obj.bounds;
				
				if(OverlapTester.overlapRectangles(b.bounds, r)){
					final Direction dir = b.getDirection();
					//�����������ӵ����� /�����ܴ����ӵ�����
					if(obj.accepted()==null || !Arrays.asList(obj.accepted())
							.contains(b.getBulletType())){
						//��������ӵ�����
						if(!obj.allowAcross(b)){
							b.def.alive = false;
							bullets.remove(b);
							bSize --;
							i--;
							resotreOpenFireCount(b,"allowAcross()");
							mListener.onAcrossFailed(b,obj);
							break; 
						}
						continue; 
					}
					//��¼֮ǰ�Ĺ��������������ɹ�������
					final int hitCount = b.def.hitCount;
					
					b.def.hitCount -- ;
					obj.def.beHitCount -- ;
					processCollideBulletGameObject(b, obj);
					
					if(obj.def.beHitCount==0){
						//��������
						obj.def.alive = false;
						objs.remove(obj);
						len --;
						j -- ;
						CacheHelper.freeSafe(obj);
						ClipFactory.free(obj.getClippedInfo());
					}else if(obj.useAttackRecorder() && !b.isHide()) //���سɹ���Ҳ����¼
						obj.attackRecorder.recordBeHitted(dir);
					
					if(b.def.hitCount < hitCount){//��ʾ�ӵ������ɹ�
						if(obj instanceof DirtWall){
							mListener.onHitDirtyWall(b.position.x,b.position.y);
						}else if(obj instanceof BrickWall){
							mListener.onHitBrickWall(b.position.x,b.position.y);
						}else if(obj instanceof SmallBrickWall){
							mListener.onHitSmallBrickWall(b.position.x,b.position.y);
						}else
							throw new RuntimeException("unknow static object being hitted!");
					}
					
					//�ӵ�����
					if(b.def.hitCount==0){
						b.def.alive = false;
						bullets.remove(b);
						bSize--;
						i--;
						resotreOpenFireCount(b,"handleStaticCollisions()");
						break;
					}
				}
			}
		}
		
	}
	
	/*** ����2��ȷ�е��ӵ�����ȥ�����ӵ�֮�����ײ */
	private void processBulletsCollide(Bullet b1,Bullet b2){
		BulletType type1 = b1.getBulletType();
		BulletType type2 = b2.getBulletType();
		final int v = type1.value + type2.value;
		final boolean type1Lager = type1.value >= type2.value;
		if(v == TypeSum.SUM_BY_INDEX_1_1){
			//2 normal
		}else if(v == TypeSum.SUM_BY_INDEX_1_2){
			if(type1Lager){
				if(b1.hide(HideTool.FLAG_DISTANCE, Constant.COMMON_WIDTH+0.5f)){
					b1.def.hitCount +=1;
					b2.def.hitCount +=1;
				}
			}else{
				if(b2.hide(HideTool.FLAG_DISTANCE, Constant.COMMON_WIDTH+0.5f)){
					b1.def.hitCount +=1;
					b2.def.hitCount +=1;
				}
			}
		}else if( v == TypeSum.SUM_BY_INDEX_1_3){
			//normal --ladder
		}else if( v == TypeSum.SUM_BY_INDEX_1_4){
			if(type1Lager) b1.switchDirection(b1.getDirection().reverse());
			else  b2.switchDirection(b2.getDirection().reverse());
		}else if( v == TypeSum.SUM_BY_INDEX_1_5){
			b1.def.hitCount =0;
			b2.def.hitCount =0;
		}else if( v == TypeSum.SUM_BY_INDEX_2_2){
			if(b1.hide(HideTool.FLAG_DISTANCE, Constant.COMMON_WIDTH+0.5f)
					|| b2.hide(HideTool.FLAG_DISTANCE, Constant.COMMON_WIDTH+0.5f)){
				b1.def.hitCount +=1;
				b2.def.hitCount +=1;
			}
		}else if( v == TypeSum.SUM_BY_INDEX_2_3 ){
			if(type1Lager){
				if(b2.hide(HideTool.FLAG_DISTANCE, Constant.COMMON_WIDTH+0.5f)){
					b1.def.hitCount +=1;
					b2.def.hitCount +=1;
				}
			}else{
				if(b1.hide(HideTool.FLAG_DISTANCE, Constant.COMMON_WIDTH+0.5f)){
					b1.def.hitCount +=1;
					b2.def.hitCount +=1;
				}
			}
		}else if( v == TypeSum.SUM_BY_INDEX_2_4){
			if(type1Lager) b1.switchDirection(b1.getDirection().reverse());
			else  b2.switchDirection(b2.getDirection().reverse());
		}else if( v ==TypeSum.SUM_BY_INDEX_2_5){
			b1.def.hitCount =0;
			b2.def.hitCount =0;
		}else if( v == TypeSum.SUM_BY_INDEX_3_3){
			//2 ladder
		}else if( v == TypeSum.SUM_BY_INDEX_3_4){
			if(type1Lager) b1.switchDirection(b1.getDirection().reverse());
			else  b2.switchDirection(b2.getDirection().reverse());
		}else if( v == TypeSum.SUM_BY_INDEX_3_5){
			b1.def.hitCount =0;
			b2.def.hitCount =0;
		}else if( v == TypeSum.SUM_BY_INDEX_4_4){
			b1.switchDirection(b1.getDirection().reverse());
			b2.switchDirection(b2.getDirection().reverse());
		}else if( v == TypeSum.SUM_BY_INDEX_4_5){
			b1.def.hitCount =0;
			b2.def.hitCount =0;
		}else if( v == TypeSum.SUM_BY_INDEX_5_5){
			b1.def.hitCount =0;
			b2.def.hitCount =0;
	    }
	}
	/**����ȷ�е��ӵ�����ȥ���� �� �ӵ���ײ̹�˻��߾�̬����*/
	private void processCollideBulletGameObject(Bullet b, GameObject obj){
		switch (b.getBulletType()) {
		case Normal:
		case Laddered:
			break;
			
		case Penetrable:
			if(b.hide(HideTool.FLAG_DISTANCE, Constant.COMMON_WIDTH+0.5f)){
				b.def.hitCount++;
				obj.def.beHitCount++;
			}
			break;
			
		case Rebounding:
			b.switchDirection(b.getDirection().reverse());
			break;
			
		case DieTogether://2��������
			b.def.hitCount=0;
			obj.def.beHitCount = 0;
			break;

		default:
			throw new RuntimeException();
		}
	}

}
