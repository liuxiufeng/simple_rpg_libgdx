package com.mygdx.component.view;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class BaseView {
	public float x;
	
	public float y;
	
	public float width;
	
	public float height;
	
	public float alpha = 1.0f;
	
    public abstract void update(float elapsedTime);
	
	public abstract void draw(Batch batch);
}
