package com.heaven7.fantastictank.interfaces;

import com.heaven7.fantastictank.matters.StaticObject;
import com.heaven7.fantastictank.matters.Tank;

/**
 * ̹����ײ��̬����ʱ�Ĵ������,����̹��֮��
 * @author Administrator
 */
public interface CollideHandlePolicy {

	<T extends Tank>void handle(T t,StaticObject obj);
	<T extends Tank>void handle(T t,Tank other);
}
