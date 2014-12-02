package com.heaven7.fantastictank.support;

import com.badlogic.gdx.utils.Array;
/**
 * Ϊÿ����������,ͳ����(ͳ�ƹ���������ݵ�)
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
	
	/**���ָ������---����������+1*/
	public void recordBeHitted(Direction dir){
		for(int i=0,size = mNodes.size; i<size ;i++){
			if(mNodes.get(i).dir == dir){
				mNodes.get(i).hittedCount++;
				break;
			}
		}
	}
	
	/** ��¼ָ��������--���е��˴���*/
	public void recordHit(Direction dir){
		for(int i=0,size = mNodes.size; i<size ;i++){
			if(mNodes.get(i).dir == dir){
				mNodes.get(i).hitCount++;
				break;
			}
		}
	}
	
	/** ������������������������������ */
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
		public int hittedCount;  //�����д���
		public int hitCount;     //����(����)����
		
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
