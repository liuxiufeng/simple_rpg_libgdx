package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;

public interface IStateView {
	/**
	 * ���ݾ���ʱ����»���
	 * @param elapsedTime
	 */
    void update(float elapsedTime);
    /**
     * ����
     */
    void render(SpriteBatch batch);
    /**
     * ����ʱ��ʼ��
     */
    void onEnter();
    
    /**
     * �˳�
     */
    void onExit();
    
    public void addListener();
}
