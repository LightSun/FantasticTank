package com.heaven7.fantastictank.support;

import com.badlogic.gdx.utils.Array;
/**
 * 为每个对象服务的,统计器(统计攻击相关数据的)
 * @author Administrator
 */
public class AttackRecorder {
	
	private Array<Node> mNodes;
	
	private Node nodeUp;
	private Node nodeDown;
	private Node nodeLeft;
	private Node nodeRight;

	public AttackRecorder() {
		initNodes();
	}
	
	/**标记指定方向---被攻击次数+1*/
	public void recordBeHitted(Direction dir){
		for(int i=0,size = mNodes.size; i<size ;i++){
			if(mNodes.get(i).dir == dir){
				mNodes.get(i).hittedCount++;
				break;
			}
		}
	}
	
	/** 记录指定方向上--击中敌人次数*/
	public void recordHit(Direction dir){
		for(int i=0,size = mNodes.size; i<size ;i++){
			if(mNodes.get(i).dir == dir){
				mNodes.get(i).hitCount++;
				break;
			}
		}
	}
	
	/** 参数：单个方向上允许攻击的最大次数 */
	public boolean needDie(int maxCount){
		if(  (nodeUp.hittedCount +nodeDown.hittedCount) >= maxCount
				|| (nodeLeft.hittedCount+nodeRight.hittedCount) >= maxCount)
			return true;
		return false;
	}
	
	public AttackRecorder reset(){
		for(int i=0,size = mNodes.size; i<size ;i++){
			Node node = mNodes.get(i);
			node.hitCount = 0;
			node.hittedCount = 0;
		}
		return this;
	}
	
	private void initNodes() {
		int len = Direction.values().length;
		mNodes = new Array<Node>(len);
		for(int i=0; i<len ;i++){
			Node node = new Node(Direction.values()[i]);
			mNodes.add(node);
			switch (node.dir) {
			case Down:
				nodeDown = node;
				break;
				
			case Up:
				nodeUp = node;
				break;
				
			case Left:
				nodeLeft = node;
				break;
				
			case Right:
				nodeRight = node;
				break;

			default:
				throw new RuntimeException();
			}
		}
	}

	public class Node{
		public Direction dir;
		public int hittedCount;  //被击中次数
		public int hitCount;     //击中(敌人)次数
		
		public Node(Direction dir) {
			super();
			this.dir = dir;
		}
	}


	public Node getNodeUp() {
		return nodeUp;
	}

	public Node getNodeDown() {
		return nodeDown;
	}

	public Node getNodeLeft() {
		return nodeLeft;
	}

	public Node getNodeRight() {
		return nodeRight;
	}
}
