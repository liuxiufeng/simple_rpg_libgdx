package com.mygdx.res;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum BackGroundRes {
    VALLEY {
    	public Sprite getSprit() {
    		Texture texture = new Texture("background/menu.jpg");
    		return new Sprite(texture);
    	}
    };
	public abstract Sprite getSprit();
}
