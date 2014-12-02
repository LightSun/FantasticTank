package com.heaven7.fantastictank.interfaces;

import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;

/**
 * 坦克碰撞静态对象时的处理策略,或者坦克之间
 * @author Administrator
 */
public interface CollideHandlePolicy {

	<T extends Tank>void handle(T t,StaticObject obj);
	<T extends Tank>void handle(T t,Tank other);
}
