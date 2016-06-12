package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IStateView {
	/**
	 * 根据经过时间更新画面
	 * @param elapsedTime
	 */
    void update(float elapsedTime);
    /**
     * 绘制
     */
    void render(SpriteBatch batch);
    /**
     * 进入时初始化
     */
    void onEnter();
    
    /**
     * 退出
     */
    void onExit();
}
