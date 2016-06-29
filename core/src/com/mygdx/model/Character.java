package com.mygdx.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.view.BaseView;
import com.mygdx.utils.Config;
import com.mygdx.utils.EventManager;
import com.mygdx.utils.GlobalManager;

public class Character extends BaseView  {
	public static final int LEFT = 10;
	public static final int LEFTMOVE = 11;
	public static final int RIGHT = 20;
	public static final int RIGHTMOVE = 21;
	public static final int DOWN = 30;
	public static final int DOWNMOVE = 31;
	public static final int UP = 40;
	public static final int UPMOVE = 41;
	protected List<ActionEvent> events = new ArrayList<ActionEvent>();

	int speed;

	/*
	 * 所在的cell上
	 */
	public int cellX;
	public int cellY;

	public int targetX;
	public int targetY;
	public int state;
	Animation left;
	Animation right;
	Animation up;
	Animation down;
	float stateTime = 0;

	public boolean isAction = false;

	public int pressedKey = 0;

	public Character() {
		this.speed = 10;
		this.state = DOWN;
		this.height = Config.CELLWIDTH;
		this.width = Config.CELLWIDTH;
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public void setCell(int cellX, int cellY) {
		this.cellX = cellX;
		this.cellY = cellY;
		this.targetX = cellX;
		this.targetY = cellY;
		this.x = this.cellX * Config.CELLWIDTH;
		this.y = this.cellY * Config.CELLWIDTH;
	}

	public void setTarget(int cellX, int cellY) {
		this.targetX = cellX;
		this.targetY = cellY;
		this.stateTime = 0;
	}

	public void update(float elapsedTime) {
		stateTime += elapsedTime;
		if (!GlobalManager.isEvent) {
			for (ActionEvent event : events) {
				EventManager.getInstance().addEvents(event);
			}
		}
		events.clear();
	}

	public void draw(Batch batch) {
		TextureRegion currentFrame = null;
		switch (this.state) {
		case LEFT:
			currentFrame = left.getKeyFrames()[1];
			break;
		case LEFTMOVE:
			currentFrame = left.getKeyFrame(stateTime, true);
			break;
		case RIGHT:
			currentFrame = right.getKeyFrames()[1];
			break;
		case RIGHTMOVE:
			currentFrame = right.getKeyFrame(stateTime, true);
			break;
		case UP:
			currentFrame = up.getKeyFrames()[1];
			break;
		case UPMOVE:
			currentFrame = up.getKeyFrame(stateTime, true);
			break;
		case DOWN:
			currentFrame = down.getKeyFrames()[2];
			break;
		case DOWNMOVE:
			currentFrame = down.getKeyFrame(stateTime, true);
			break;
		}
		Sprite sprite = new Sprite(currentFrame);
		sprite.setAlpha(this.alpha);
		sprite.setPosition(x, y);
		sprite.draw(batch);
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
