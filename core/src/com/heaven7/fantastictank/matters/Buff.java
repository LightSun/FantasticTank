package com.heaven7.fantastictank.matters;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.buff.AddLifeBuff;
import com.heaven7.fantastictank.matters.buff.DoubleFireBuff;
import com.heaven7.fantastictank.matters.buff.PauseBuff;
import com.heaven7.fantastictank.matters.buff.SafeBuff;
import com.heaven7.fantastictank.matters.buff.StarBuff;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.support.GameObject;
/**
 * Buff����(Ҳ�����Ǽ���buff)
 * <pre>����ok
	 *if(buff == null)
		   buff = new AbsBuff(0.5f, 0.5f);
		batcher.setColor(buff.getColor());
		batcher.draw(Resource.wood, buff.position.x, buff.position.y, 1, 1);
		batcher.setColor(defaultColor);
		buff.update();
	 </pre>
 * @author Administrator
 */
public abstract class Buff extends GameObject {
	
	public static final int DEFAULT_VALID_TIME = 30; //30��
	private static final IntMap<TextureRegion> REGIONS = new IntMap<TextureRegion>();
	private static final IntMap<Buff> BUFFS = new IntMap<Buff>();
	    
    public static final int ID_ADD_LIFE    = 1 ;
    public static final int ID_PAUSE_TIME  = 2 ;
    public static final int ID_STAR        = 3 ;
    public static final int ID_SAFE        = 4 ;
    public static final int ID_DOUBLE_FIRE = 5 ;
    
    public static final byte SCOPE_PERSONAL = 1;
    public static final byte SCOPE_GROUP    = 2;
	
	private static final float THRESHOLD_DELTA_COLOR = 0.1f; //��ɫ��������
	private boolean add;         /* ��������addColor����decColor */
	private final List<Tank> targets = new ArrayList<Tank>();
	private long startTime;
	
	public Buff(float x, float y) {
		super(x, y, Constant.COMMON_WIDTH, Constant.COMMON_HEIGHT);
	}
	
	/** buffÿ֡������õġ� */
	public void update(){
		if(!add){
			decColor(THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, 0);
			if(getColor().r < 0){
				add = true;
				addColor(THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, 0);
			}
		}else{
			addColor(THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, THRESHOLD_DELTA_COLOR, 0);
			if(getColor().r > 1){
				add = false;
				decColor(THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, THRESHOLD_DELTA_COLOR*2, 0);
			}
		}
	}
	/** Ӧ��buff��Ӧ��buff���Ե�ʱ����*/
    public abstract void apply(Tank t);
   
    /** ÿ��buff��id���֤�� */
    public abstract int getId();
    
    /**����Ӧ��buff*/
    public <T extends Tank >void applyAll(List<T> ts){
    	for(int i =0, size = ts.size() ; i < size ;i++){
    		apply(ts.get(i));
    	}
    }
    
    /** ����֮ǰӦ�õ�buff(�����Ե�buff���������κ�Ӱ��) */
    public void cancel(){
    	
    }
    /**buff�Ƿ�Ӧ�ø�����,Ĭ��false*/
    public boolean applyToHostile(){
    	return false;
    }
    /**����buff�������򣺸���/Ⱥ��. Ĭ��{@link #SCOPE_PERSONAL}*/
    public byte scope(){
    	return SCOPE_PERSONAL;
    }
    
    @Override
    public void reset() {
    	super.reset();
    	this.targets.clear();
    	this.add = false; 
    	startTime= 0;
    }
    
    //====== bean methods ===========//
    public List<Tank> getTargets() {
		return targets;
	}
	public void addTarget(Tank target) {
		this.targets.add(target);
	}
	public void clearTargets() {
		this.targets.clear();
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	/**buffʱ���Ƿ����(����buff��������),Ĭ��false*/
	public boolean isExpired(){
		return false;
	}
	
	//================================//
	
	/** eg: {@link #ID_ADD_LIFE} --idҲ������һ�������(������͵õ�һ�����ֵ)*/
    public static TextureRegion getRegionById(int id){
    	 TextureRegion region = REGIONS.get(id);
    	 if(region==null)
    		 region = REGIONS.get(id % REGIONS.size +1);
    	 return region;
    }
    
    /**�������õ�buff����ȥ����free(����Pool�������)--idҲ������һ�������(������͵õ�һ�����ֵ)*/
    public static Buff getBuff(int id,float x,float y){
    	Buff buff = BUFFS.get(id);
    	if(buff == null) buff = BUFFS.get(id % BUFFS.size +1); //
    	buff.setPosition(x, y);
    	buff.reset();
    	return buff;
    }
    
    static{
    	REGIONS.put(ID_ADD_LIFE, Resource.buff_addlife);
    	REGIONS.put(ID_PAUSE_TIME, Resource.buff_pause_time);
    	REGIONS.put(ID_STAR, Resource.buff_star);
    	REGIONS.put(ID_SAFE, Resource.buff_safe);
    	REGIONS.put(ID_DOUBLE_FIRE, Resource.buff_double_fire);
    	
    	BUFFS.put(ID_ADD_LIFE, new AddLifeBuff(0, 0));
    	BUFFS.put(ID_PAUSE_TIME, new PauseBuff(0, 0));
    	BUFFS.put(ID_STAR, new StarBuff(0, 0));
    	BUFFS.put(ID_SAFE, new SafeBuff(0, 0));
    	BUFFS.put(ID_DOUBLE_FIRE, new DoubleFireBuff(0, 0));
    }
    
}
