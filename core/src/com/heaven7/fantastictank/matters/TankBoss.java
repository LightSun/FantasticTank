package com.heaven7.fantastictank.matters;

import com.heaven7.fantastictank.mood.AdvancedMooder;
import com.heaven7.fantastictank.wisdom.WisdomManager;

/** compare to {@link AutoTank}:
 * <li> add {@link #mActivatedAttack} \ {@link #mAbsoluteDefense} \ {@link #mReceiver}
 * <li> implements {@link AdvancedMooder}
 * <li> when collide occured,you must care about {@link #isAbsoluteDefense()} (means 是否绝对防御)
 * @author heaven7
 * @see AdvancedMooder
 * @see CommandReceiver
 */
public class TankBoss extends AutoTank implements AdvancedMooder {

	private static final float BossWidth = 2f - THRESHOLD;
	private static final float BossHeight = 2f - THRESHOLD;

	/** boss必须激活才能主动攻击 */
	protected boolean mActivatedAttack;
	private boolean mAbsoluteDefense;
	private CommandReceiver mReceiver;
	
	public static interface CommandReceiver{
		void restoreChildrenLife(TankBoss boss);
	}

	public TankBoss(float x, float y, float width, float height) {
		super(x, y, width, height);
		setTankType(TankType.King);
		this.def.beHitCount = 20;
	}

	public TankBoss(float x, float y) {
		this(x, y, BossWidth, BossHeight);
	}

	@Override
	public void update(float deltaTime) {
		// 检查定身
		if (isPaused())
			return;
		if (getMood() != null)
			getMood().update(this);

		setLastX(position.x);
		setLastY(position.y);

		// 跟踪敌人
		if (mTrackForman) {
			step--;
			if (step == 0) {
				switchDirection(getRelativeDirection());
				step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
			}
		}
		// 是否逃跑；即远离敌人
		else if (mEscape && mKnowFoeman
				&& this.position.dst(mFoemanPositon) <= mCareDistance) {
			// 检查距离是否在逃跑的距离内
			step--;
			if (step == 0) {
				switchDirection(getRelativeDirection().reverse());
				step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
			}
		} else {
			step--;
			if (step == 0) {
				randowDirection(true);
				step = 20 + WisdomManager.randomInt(20)+ WisdomManager.randomInt(70);
			}
		}

		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		setBoundByPosition();

		if (mActivatedAttack && mShootAtOnce) {
			if (getOpenFireCount() > 0) {
				decreaseOpenFireCount();
				addBullet(shoot());
			}
		} else if (mActivatedAttack && isInRange(WisdomManager.randomInt(100))) {
			if (getOpenFireCount() > 0) {
				decreaseOpenFireCount();
				addBullet(shoot());
			}
		}

		doWithHideIfNeed(deltaTime);
		processEdge();
	}

	@Override
	public void reset() {
		super.reset();
		setTankType(TankType.King);
		this.def.beHitCount = 20;
		mActivatedAttack = false;
		mAbsoluteDefense = false;
		mReceiver = null;
	}

	@Override
	public byte getType() {
		return TYPE_BOSS;
	}

	@Override
	public void setActivateAttack(boolean driving) {
		this.mActivatedAttack = driving;
	}

	@Override
	public void scaleLife(float rate) {
		this.def.beHitCount *= rate;
	}

	@Override
	public void setAbsoluteDefense(boolean absoluteDefense) {
		this.mAbsoluteDefense = absoluteDefense;
	}

	@Override
	public void scaleAttack(float f) {
		this.def.attack *= f;//TODO apply attack
		System.out
				.println("after scaleAttack: boss.def.attack = " + def.attack);
	}

	@Override
	public void scaleDefense(float f) {
		// 防御减半效果 = 生命减半
		this.def.beHitCount *= f;
		System.out.println("after scaleDefense: boss.def.beHitCount = "
				+ def.beHitCount);
	}

	@Override
	public void restoreChildrenLife() {
		if(mReceiver!=null) mReceiver.restoreChildrenLife(this);
	}
	// =========== like bean methods ==============
	
	/** 是否处于绝对防御状态 */
	public boolean isAbsoluteDefense() {
		return mAbsoluteDefense;
	}
	
	public CommandReceiver getCommandSender() {
		return mReceiver;
	}
	/** must set first to receive command*/
	public void setCommandSender(CommandReceiver sender) {
		this.mReceiver = sender;
	}
}
