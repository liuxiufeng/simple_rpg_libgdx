package com.mygdx.component.view;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class BaseView {
	
    public abstract void update(float elapsedTime);
	
	public abstract void draw(Batch batch);
}
