package com.heaven7.fantastictank.action;

import com.badlogic.gdx.graphics.Color;

public interface IActor {

	Color getColor();
	//���Ӻͼ���ָ������ɫ����
	void decColor(float deltaR,float delatG,float deltaB,float deltaA);
	void addColor(float deltaR,float delatG,float deltaB,float deltaA);
}
