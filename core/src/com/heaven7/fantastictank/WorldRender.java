package com.heaven7.fantastictank;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heaven7.fantastictank.matters.AutoTank;
import com.heaven7.fantastictank.matters.BrickWall;
import com.heaven7.fantastictank.matters.Buff;
import com.heaven7.fantastictank.matters.Bullet;
import com.heaven7.fantastictank.matters.DirtWall;
import com.heaven7.fantastictank.matters.Lake;
import com.heaven7.fantastictank.matters.MyTank;
import com.heaven7.fantastictank.matters.SmallBrickWall;
import com.heaven7.fantastictank.matters.Wood;
import com.heaven7.fantastictank.res.Resource;
import com.heaven7.fantastictank.util.ClipFactory.ClippedInfo;
import com.heaven7.fantastictank.util.DrawInfo;
/**
 * @author Administrator
 */
public class WorldRender {

	private OrthographicCamera cam;
	private SpriteBatch batcher;
	private GameWorld world;
	private Color defaultColor = new Color();

	public WorldRender(SpriteBatch batcher, GameWorld world) {
		cam = new OrthographicCamera(Constant.FRUSTUM_WIDTH, Constant.FRUSTUM_HEIGHT);
	    cam.position.set(Constant.FRUSTUM_WIDTH/2, Constant.FRUSTUM_HEIGHT/2, 0);
	    this.batcher = batcher;
	    this.world = world;
	    this.defaultColor.set(batcher.getColor());
	}

	public void render() {
		cam.update();
		batcher.setProjectionMatrix(cam.combined);
		renderObjects();
	}

	private void renderObjects() {
		batcher.enableBlending();
		batcher.begin();
		
		renderDirtWalls();
		renderBrickWalls();
		renderSmallBrickWalls();
		rendeLakes();
		
		renderTanks();
		renderBullets();
		renderWoods();
		renderExplodes();
		renderBuff();
		
		batcher.end();
	}

	private void renderSmallBrickWalls() {
		int size = world.mSmallBrickWalls.size();
		for (int i = 0; i < size; i++) {
			SmallBrickWall lake = world.mSmallBrickWalls.get(i);
			batcher.draw(Resource.smallBrickWall, lake.bounds.x, lake.bounds.y,
					lake.bounds.width, lake.bounds.height);
		}
	}

	private void renderBuff() {
		if(world.mBuff!=null && world.mBuff.def.alive){
			batcher.setColor(world.mBuff.getColor());
			batcher.draw(Buff.getRegionById(world.mBuff.getId()), 
					world.mBuff.bounds.x, world.mBuff.bounds.y, 1, 1);
			batcher.setColor(defaultColor);
		}
	}

	private void renderExplodes() {
		
		int size = world.mExplodes.size();
		for (int i = 0; i < size; i++) {
			float scaleWidth  = 1f;
			float scaleHeight = 1f;
			if(i==0 ||i == size-1){ //爆炸4帧，第一帧和最后帧宽高变小
				scaleWidth = 0.5f;
				scaleHeight = 0.5f;
			}
			world.mExplodes.get(i).draw(batcher, Resource.anim_explode, 
					scaleWidth, scaleHeight);
		}
	}

	private void rendeLakes() {
		int size = world.mLakes.size();
		for (int i = 0; i < size; i++) {
			Lake lake = world.mLakes.get(i);
			batcher.draw(Resource.lake, lake.bounds.x, lake.bounds.y,
					Constant.COMMON_WIDTH, Constant.COMMON_HEIGHT);
		}
	}

	private void renderWoods() {
		int size = world.mWoods.size();
		for (int i = 0; i < size; i++) {
			Wood wood = world.mWoods.get(i);
			ClippedInfo info = wood.getClippedInfo();
			if(info==null)			
			      batcher.draw(Resource.wood, wood.bounds.x, wood.bounds.y,
					     Constant.COMMON_WIDTH, Constant.COMMON_HEIGHT);
			else{
				batcher.draw(info.region, info.bounds.x, info.bounds.y, 
						info.bounds.width, info.bounds.height);
			}
		}
	}

	private void renderBrickWalls() {
		int size = world.mBrickWalls.size();
		for (int i = 0; i < size; i++) {
			BrickWall wall = world.mBrickWalls.get(i);
			ClippedInfo info = wall.getClippedInfo();
			if(info==null)			
			      batcher.draw(Resource.brickWall, wall.bounds.x, wall.bounds.y,
					     Constant.COMMON_WIDTH, Constant.COMMON_HEIGHT);
			else{
				batcher.draw(info.region, info.bounds.x, info.bounds.y, 
						info.bounds.width, info.bounds.height);
			}
		}
	}

	private void renderDirtWalls() {
		int size = world.mDirtWalls.size();
		for (int i = 0; i < size; i++) {
			DirtWall wall = world.mDirtWalls.get(i);
			ClippedInfo info = wall.getClippedInfo();
			if(info==null)			
			      batcher.draw(Resource.dirtWall, wall.bounds.x, wall.bounds.y,
					     Constant.COMMON_WIDTH, Constant.COMMON_HEIGHT);
			else{
				batcher.draw(info.region, info.bounds.x, info.bounds.y, 
						info.bounds.width, info.bounds.height);
			}
		}
	}

	private void renderBullets() {
		int size = world.mBullets.size();
		for (int i = 0; i < size; i++) {
			Bullet b = world.mBullets.get(i);
			if(b.isHide()) continue;
			
			float x = b.position.x - b.bounds.width/2;
			float y = b.position.y - b.bounds.height/2;
			
			DrawInfo info = DrawInfo.get(b);
			batcher.draw(info.region, x, y, b.bounds.width/2, b.bounds.height/2,
					b.bounds.width, b.bounds.height, 1, 1, info.degree, true);
		}
		
		size = world.mGoodBullets.size();
		for (int i = 0; i < size; i++) {
			Bullet b = world.mGoodBullets.get(i);
			if(b.isHide()) continue;
			
			float x = b.position.x - b.bounds.width/2;
			float y = b.position.y - b.bounds.height/2;
			
			DrawInfo info = DrawInfo.get(b);
			batcher.draw(info.region, x, y, b.bounds.width/2, b.bounds.height/2,
					b.bounds.width, b.bounds.height, 1, 1, info.degree, true);
		}
	}
	
	private void renderTanks() {
		int size = world.mAutoTanks.size();
		for (int i = 0; i < size; i++) {
			AutoTank t = world.mAutoTanks.get(i);
			
			if(t.isHide())  continue;
			if(!t.isWhiteColor())
				batcher.setColor(t.getColor());
		    float x = t.position.x - t.bounds.width/2;
			float y = t.position.y - t.bounds.height/2;
			
			DrawInfo info = DrawInfo.get(t);
			batcher.draw(info.region, x, y, t.bounds.width/2, t.bounds.height/2,
					t.bounds.width, t.bounds.height, 1, 1, info.degree, true);
			batcher.setColor(defaultColor);
		}
		
		size = world.mMytanks.size();
		for (int i = 0; i < size; i++) {
			MyTank t = world.mMytanks.get(i);
			if(t.isHide()) continue;
			
			float x = t.position.x - t.bounds.width/2;
		    float y = t.position.y - t.bounds.height/2;
		    DrawInfo info = DrawInfo.get(t);
		    
		    batcher.setColor(t.getColor());
			batcher.draw(info.region, x, y, t.bounds.width/2, t.bounds.height/2,
					t.bounds.width, t.bounds.height, 1, 1, info.degree, true);
			batcher.setColor(defaultColor);
		}
	}

	
}
