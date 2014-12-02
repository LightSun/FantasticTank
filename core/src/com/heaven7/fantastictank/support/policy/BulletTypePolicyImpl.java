package com.heaven7.fantastictank.support.policy;

import java.util.ArrayList;
import java.util.List;

import com.heaven7.fantastictank.Constant;
import com.heaven7.fantastictank.matters.Bullet.BulletType;
import com.heaven7.fantastictank.support.Direction;

public class BulletTypePolicyImpl extends BulletTypePolicy {

	public BulletTypePolicyImpl(){}
	public BulletTypePolicyImpl(BulletType type) {
		super(type);
	}

	@Override
	public int getHitCount() {
		switch (getBulletType()) {
		case Normal:
			return 1;
			
		case Penetrable: 
			return 1;
			
		case Laddered:
			return 2;
			
		case Rebounding:
			return 4;
			
		case DieTogether:
			return 8;

		default:
			throw new RuntimeException("unknow bullet type = "+getBulletType());
		}
	}

	@Override
	public List<Direction> getDirectionList(Direction tankDir,
			float up,float down,float left,float right) throws 
			UnsupportedOperationException {
		switch (getBulletType()) {
		case Normal:
		case Penetrable: 
		case Rebounding:
		case DieTogether:
			throw new UnsupportedOperationException("called [ getDirectionList() ] 该类子弹不支持路径方式");
			
			//实际运行时总会少一个方向。why?
		case Laddered:{
			List<Direction> dirs = new ArrayList<Direction>();
			switch (tankDir) {
			case Down: 
				if(left >= right){ 
					dirs.add(Direction.Down);
					dirs.add(Direction.Left);
					dirs.add(Direction.Down);
					dirs.add(Direction.Left);
					dirs.add(Direction.Down);
					dirs.add(Direction.Left);
				}else{
					dirs.add(Direction.Down);
					dirs.add(Direction.Right);
					dirs.add(Direction.Down);
					dirs.add(Direction.Right);
					dirs.add(Direction.Down);
					dirs.add(Direction.Right);
				}
				break;
			case Up:
				if(left >= right){
					dirs.add(Direction.Up);
					dirs.add(Direction.Left);
					dirs.add(Direction.Up);
					dirs.add(Direction.Left);
					dirs.add(Direction.Up);
					dirs.add(Direction.Left);
				}else{
					dirs.add(Direction.Up);
					dirs.add(Direction.Right);
					dirs.add(Direction.Up);
					dirs.add(Direction.Right);
					dirs.add(Direction.Up);
					dirs.add(Direction.Right);
				}
				break;
			case Right:
				if(up >= down){
					dirs.add(Direction.Right);
					dirs.add(Direction.Up);
					dirs.add(Direction.Right);
					dirs.add(Direction.Up);
					dirs.add(Direction.Right);
					dirs.add(Direction.Up);
				}else{
					dirs.add(Direction.Right);
					dirs.add(Direction.Down);
					dirs.add(Direction.Right);
					dirs.add(Direction.Down);
					dirs.add(Direction.Right);
					dirs.add(Direction.Down);
				}
				break;
			case Left:
				if(up >= down){
					dirs.add(Direction.Left);
					dirs.add(Direction.Up);
					dirs.add(Direction.Left);
					dirs.add(Direction.Up);
					dirs.add(Direction.Left);
					dirs.add(Direction.Up);
				}else{
					dirs.add(Direction.Left);
					dirs.add(Direction.Down);
					dirs.add(Direction.Left);
					dirs.add(Direction.Down);
					dirs.add(Direction.Left);
					dirs.add(Direction.Down);
				}
				break;

			default:
				throw new RuntimeException("wrong dir");
			}
			return dirs;
		}

		default:
			throw new RuntimeException("unknow bullet type = "+getBulletType());
		}
	}

	@Override
	public float getFlightLength() {
		switch (getBulletType()) {
			case Normal:
			case Penetrable: 
			case Rebounding:
			case DieTogether:
				return FOR_EVER_IF_POSSIBBLE;
				
			case Laddered:
				return Constant.COMMON_WIDTH * 7.5f;
				
			default:
					throw new RuntimeException();
		}
	}

}
