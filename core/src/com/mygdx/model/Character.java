package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character {
    private int speed;
    public int x;
    public int y;
    /*
     * 10 ◊ÛÕ£, 11 ◊Û≈‹, 20 ”“Õ££¨ 21 ”“≈‹£¨ 40£¨…œÕ££¨ 41…œ≈‹£¨ 30 œ¬Õ££¨ 31£¨ œ¬≈‹
     */
    public int state;
    private Animation left;
    private Animation right;
    private Animation up;
    private Animation down;
    private float stateTime = 0;
    private float width;
	private float height;
    
    public Character() {
    	this.speed = 200;
    	this.state = 30;
    }
    
    public void setSize(float width, float height) {
    	this.width = width;
    	this.height = height;
    }

    public void update(float elapsedTime) {
    	stateTime += elapsedTime;
    	boolean isPressed = false;
    	int changeState = 31;
    	if (Gdx.input.isKeyPressed(Keys.UP)) {
            changeState = 41;
            isPressed = true;
            y+= speed * elapsedTime;
    	} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            changeState = 31;
            isPressed = true;
            y-= speed* elapsedTime;
    	} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            changeState = 11;
            isPressed = true;
            x-= speed* elapsedTime;
    	} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            changeState = 21;
            isPressed = true;
            x+= speed* elapsedTime;
    	}
    	
    	if (isPressed) {
    		if (changeState != this.state) {
    			this.state = changeState;
    			this.stateTime = 0;
    		}
    	} else {
    		this.state = (this.state / 10) * 10;
    	}
    	
    }
    
    public TextureRegion getFrame(){
    	switch (this.state) {
    	case 10:
    		return left.getKeyFrame(0);
    	case 11:
    		return left.getKeyFrame(stateTime, true);
    	case 20:
    		return right.getKeyFrame(0);
    	case 21:
    		return right.getKeyFrame(stateTime, true);
    	case 30:
    		return up.getKeyFrame(0);
    	case 31:
    		return up.getKeyFrame(stateTime, true);
    	case 40:
    		return down.getKeyFrame(0);
    	case 41:
    		return down.getKeyFrame(stateTime, true);
    	}
		return null;
    }
    
	public Animation getRight() {
		return right;
	}

	public void setRight(Animation right) {
		this.right = right;
	}

	public Animation getLeft() {
		return left;
	}

	public void setLeft(Animation left) {
		this.left = left;
	}

	public Animation getUp() {
		return up;
	}

	public void setUp(Animation up) {
		this.up = up;
	}

	public Animation getDown() {
		return down;
	}

	public void setDown(Animation down) {
		this.down = down;
	}

	public float getWidth() {
		return this.getRight().getKeyFrame(0).getRegionWidth();
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return this.getRight().getKeyFrame(0).getRegionHeight();
	}

	public void setHeight(float height) {
		this.height = height;
	}
    

}
