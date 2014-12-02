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
 * 裁判
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
	 * @param tanks  群体buff时（且非施加给敌人），应用的本方对象
	 * @param hostileTanks  施加给敌人的群体Buff时，应用的敌人对象
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
	
	/** 处理t1和ts集合的其他对象碰撞
	 *  @param t1  可能作为ts的一份子
	 *  @param   */
	@SuppressWarnings("unchecked")
	public <T extends Tank> void handleTanksCollide(List<T> ts, Tank t1){
		boolean removed = ts.remove(t1); //先移除,如果存在
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
		//属于集合中的一员?
		if(removed){
		//没死亡就恢复
			if(!die) ts.add((T) t1);
		}
		//放回池子
		if(die ){
			mListener.onTankDied(t1.position.x, t1.position.y, t1);
			CacheHelper.freeSafe(t1);
		}
	}
	
	/** 处理坦克之间的碰撞(2个集合--相同也没关系)
	 *  <li>t2s 表示T2对象 t所在的集合*/
	public <T1 extends Tank,T2 extends Tank> void handleTanksCollide(
			List<T1> ts1, List<T2> ts2){
		int size = ts1.size();
		for(int i =0; i<size ;i++){
			T1 t1 = ts1.get(i);
			//已被标记死亡
			if(!t1.def.alive) continue;
			
			for(int j=0, len = ts2.size() ; j < len ; j++){
				T2 t2 = ts2.get(j);
				if(t1 == t2)       continue;
				if(!t2.def.alive)  continue;
				
				if(OverlapTester.overlapRectangles(t1.bounds, t2.bounds)){
					//同归于尽
					if( t2.def.type!= t1.def.type && (
							(t1 instanceof AutoTank && ((AutoTank) t1).isCollideDieTogether())
							|| (t2 instanceof AutoTank && ((AutoTank) t2).isCollideDieTogether()) 
							)  ){
						//标记死亡
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
		
		//不同的集合时，2个集合都需要检查
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
	
	/** 处理正方坦克碰撞static */
	public  void handleCollideStaticObject(MyTank t, List<? extends StaticObject> objs) {
		if(t.isHide()) return; //隐身
		
		int size = objs.size();
		for(int j=0 ; j<size ; j++){
			StaticObject obj = objs.get(j);
			Rectangle r = obj.getClippedInfo()!=null?obj.getClippedInfo().bounds:obj.bounds;
			if(OverlapTester.overlapRectangles(r, t.bounds)){
			       t.stay();  //MyTank都是人为控制
			}
		}
	}
	
	/** 处理坦克和静态事物的碰撞  */
	public void handleTankCollisions(List<? extends Tank> tanks,List<? extends StaticObject> objs){
		int size = objs.size();
		int tSize = tanks.size();
		for(int i=0;i<tSize ; i++){
			Tank t = tanks.get(i);
			if(t.isHide()) continue; //隐身
			
			for(int j=0 ; j<size ;j++){
				StaticObject obj = objs.get(j);
				Rectangle r = obj.getClippedInfo()!=null?obj.getClippedInfo().bounds:obj.bounds;
				if(OverlapTester.overlapRectangles(r, t.bounds)){
					if(t.isManual())
						t.stay();      //人为控制
					else 
						t.getCollidePolicy().handle(t, obj);
				}
			}
		}
	}

	/** 处理子弹----坦克碰撞 */
	public void handleBulletCollisions(List<Bullet> bullets,List<? extends Tank> tanks){
		int bSize = bullets.size();
		int tSize = tanks.size();
		for(int i=0;i<bSize ; i++){
			Bullet b = bullets.get(i);
			if(b.isHide()) continue; //隐身
			
			for(int j=0; j<tSize ;j++){
				Tank t = tanks.get(j);
				//隐身
				if(t.isHide())continue;	
				//无敌
				if(t.isUnrivaled()) continue; 
				//同一阵营
				if(b.def.type == t.def.type) continue; 
				
				//boss 绝对防御
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
						//子弹已死亡，不应该继续内循环
						break;
					}
				}
			}
		}
	}

	/** 恢复tank的open fire 次数 (如果允许).然后free bullet回池子
	 * @deprecated 已过时。恢复已交给poolEvent去做了*/
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
	    //居然能读到else. why(子弹产生new的也一样)？  
	    //b.setOwner(null);解决了问题(本来只允许开1枪，结果开了好几次，一串子弹...)	
	    // s复用缓存的bullet更是bug,why?
	    // Bug已修复，主要循环的时候。子弹死亡应该break当前子弹的循环
	}
	
	/** 处理子弹--碰--子弹 */
	public void handleBulletsCollisions(List<Bullet> bullets,List<Bullet> bullets2){
		int bSize = bullets.size();
		int b2Size = bullets2.size();
		for(int i=0;i<bSize ; i++){
			Bullet b = bullets.get(i);
			if(b.isHide()) continue; //隐身
			
			for(int j = 0; j < b2Size ; j++){
				Bullet b2 = bullets2.get(j);
				if(b2.isHide()) continue;	//隐身	
				if(b.def.type == b2.def.type) continue; //同一阵营
				
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
						break; //中断内循环
					}
				}
			}
		}
	}
	
	/** 处理子弹--and-静态事物  的碰撞*/
	public void handleStaticCollisions(List<Bullet> bullets,List<? extends StaticObject> objs){
		int bSize = bullets.size();
		int len = objs.size();
		for(int i=0;i<bSize ; i++){
			Bullet b = bullets.get(i);
			if(b.isHide()) continue; //隐身
			
			for(int j = 0; j < len ; j++){
				StaticObject obj = objs.get(j);
				//如果裁剪了---bounds会变化
				Rectangle r = obj.getClippedInfo()!=null?obj.getClippedInfo().bounds:obj.bounds;
				
				if(OverlapTester.overlapRectangles(b.bounds, r)){
					final Direction dir = b.getDirection();
					//不允许任意子弹攻击 /不接受此种子弹攻击
					if(obj.accepted()==null || !Arrays.asList(obj.accepted())
							.contains(b.getBulletType())){
						//不允许该子弹穿过
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
					//记录之前的攻击次数，攻击成功会消耗
					final int hitCount = b.def.hitCount;
					
					b.def.hitCount -- ;
					obj.def.beHitCount -- ;
					processCollideBulletGameObject(b, obj);
					
					if(obj.def.beHitCount==0){
						//对象死亡
						obj.def.alive = false;
						objs.remove(obj);
						len --;
						j -- ;
						CacheHelper.freeSafe(obj);
						ClipFactory.free(obj.getClippedInfo());
					}else if(obj.useAttackRecorder() && !b.isHide()) //隐藏成功了也不记录
						obj.attackRecorder.recordBeHitted(dir);
					
					if(b.def.hitCount < hitCount){//表示子弹攻击成功
						if(obj instanceof DirtWall){
							mListener.onHitDirtyWall(b.position.x,b.position.y);
						}else if(obj instanceof BrickWall){
							mListener.onHitBrickWall(b.position.x,b.position.y);
						}else if(obj instanceof SmallBrickWall){
							mListener.onHitSmallBrickWall(b.position.x,b.position.y);
						}else
							throw new RuntimeException("unknow static object being hitted!");
					}
					
					//子弹死亡
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
	
	/*** 根据2种确切的子弹类型去处理子弹之间的碰撞 */
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
	/**根据确切的子弹类型去处理 ： 子弹碰撞坦克或者静态事物*/
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
			
		case DieTogether://2个都死亡
			b.def.hitCount=0;
			obj.def.beHitCount = 0;
			break;

		default:
			throw new RuntimeException();
		}
	}

}
