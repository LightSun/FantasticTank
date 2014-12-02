package com.heaven7.fantastictank.support;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Logger;

/**
 * ·����¼����path��ҪΪ�ӵ������
 * @author Administrator
 */
public class PathRecorder{
	
	/** �������� */
	static final float BOOBOO = 0.08f ;  
	public static final int NO_CUT = 0;

	private final float  totalLength;
	private float  passedLength;           //���߹��ĳ���
	 
	private final boolean cutable;         //�Ƿ�֧�ַָ�
	private final int fragmentCount;       //�ֶε��ܸ���
	private int passFragmentCount;         //�����ߵĵĵڼ���
	
	//private final float fragmentLength;    //�ֶ�,ÿ�εĳ���(���֧��)--Ŀǰ���Ǿ���,��Ӧ���Ǿ��Ե�
	
	/*��������ҿ��ظ� --���ı䷽��ʱ��������node����*/
	private ArrayList<Path> mPaths = new ArrayList<Path>(4);  
	private final List<Direction> mDirs;
	private boolean dead = false;
	
	/** ����:·��/���򼯺ϣ� �ܳ��� */
	public PathRecorder(List<Direction> dirs,float totalLength) {
		super();
		this.mDirs = dirs;
		int fragmentCount = dirs.size();
		if(fragmentCount == 0)
			throw new IllegalArgumentException("dirs.size() must > 0");
		else if(fragmentCount == 1){
			cutable = false;
		}else{
			cutable = true;
		}
		this.totalLength = totalLength;
		this.fragmentCount = fragmentCount;
	}
	
	/**��¼ָ�������� �Ѿ��ߵľ���(�����Ǹ���)*/
	public void record(Direction dir,float distance){
		passedLength += distance;
		if(this.mPaths.size()==0){
			passFragmentCount = 1;
			this.mPaths.add(new Path(dir, distance).setIndex(1));
		}else{
			Path path = mPaths.get(mPaths.size()-1);
			if(path.dir == dir)
				path.passedLength += distance;
			else{
				passFragmentCount ++;
				this.mPaths.add(new Path(dir, distance).setIndex(passFragmentCount));
			}
		}
	}
	
	public boolean isDead(){
		return  dead || (passedLength - totalLength) >= 0 ;
	}
	
	/** true, if���ڿ����л�������  */
	public boolean canTurnDirection(){
		if(!cutable) return false;
		if(passFragmentCount > fragmentCount)
			return false;
		if(isDead()) return false;
		
		if(mPaths.size() ==0) return false;
		//���һ����
		if(passFragmentCount == mDirs.size() -1)
			return false;
		
		//����ö�·���ϣ����ߵľ��뻹����ÿ�ε�ƽ��ֵ��Χ��,false	
		Path path = mPaths.get(mPaths.size()-1);
		float delta = totalLength /fragmentCount - path.passedLength;
		if(Math.abs(delta) > BOOBOO)
			return false;
		
		return true;
	}
	
	/** �����һ������ (һ���� {@link #record(Direction, float)} ֮ǰ����),��Ҫ�ڴ�֮ǰ����canTurnDirection
	 * <li>���·�ߵ�dirs�����꣨�������������һ�Σ����򷵻�null*/
	public Direction nextDirection(){
		//if(!canTurnDirection()) return null;
	    if(passFragmentCount <= mDirs.size()){
			return mDirs.get(passFragmentCount);
		}else{
			logger.debug("called [ nextDirection() ] passFragmentCount >= mDirs.size()");
			dead = true;
		}
		return null;
	}
	
	/** ÿ���ڵ㣬ָ���������߹��ľ���  */
	public class Path{
		/**�������ӵ��ĵڼ�С��--1��ʼ���ӵ�*/
		public int index;         
		public Direction dir;
		public float passedLength;
		
		public Path(Direction dir, float passedLength) {
			super();
			this.dir = dir;
			this.passedLength = passedLength;
		}
		
		public Path setIndex( int index){
			this.index = index;
			return this;
		}
	}
	
	private Logger logger = new Logger(PathRecorder.class.getSimpleName(),Logger.DEBUG);
}
