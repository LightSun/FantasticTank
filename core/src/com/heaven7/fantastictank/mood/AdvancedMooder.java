package com.heaven7.fantastictank.mood;
/**
 * ӵ�и߼�������
 * @author Administrator
 */
public interface AdvancedMooder extends Mooder {
	
	/**�����Ƿ���������(������������)*/
	void setActivateAttack(boolean driving);

	/** ����ֵ��С��ָ���İٷֱ� */
	void scaleLife(float rate);

	/** ���Է���(���������˺�) */
	void setAbsoluteDefense(boolean real);

	/** ���������ŵ�ָ���ı�����may >1 */
	void scaleAttack(float f);
	
	/** �������ŵ�ָ���ı�����may >1 */
	void scaleDefense(float f);

	/** �ָ����� ���µ�����ֵ */
	void restoreChildrenLife();
}
