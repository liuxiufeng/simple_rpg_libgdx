package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.utils.Config;
import com.mygdx.utils.RectUtils;

public class Character {
	public static final int LEFT = 10;
	public static final int LEFTMOVE = 11;
	public static final int RIGHT = 20;
	public static final int RIGHTMOVE = 21;
	public static final int DOWN = 30;
	public static final int DOWNMOVE = 31;
	public static final int UP = 40;
	public static final int UPMOVE = 41;

	public static final int NOTMOVE = -3333;

	int speed;
	public float x;
	public float y;
	/*
	 * 所在的cell上
	 */
	public int cellX;
	public int cellY;

	/**
	 * 目标cell,不移动时为-3333;
	 */
	public int targetX;
	public int targetY;
	public int state;
	Animation left;
	Animation right;
	Animation up;
	Animation down;
	float stateTime = 0;
	float width;
	float height;

	public Character() {
		this.speed = 10;
		this.state = DOWN;
		this.setTarget(NOTMOVE, NOTMOVE);
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public void setCell(int cellX, int cellY) {
		this.cellX = cellX;
		this.cellY = cellY;
		this.x = cellX * Config.CELLWIDTH;
		this.y = cellY * Config.CELLWIDTH;
	}

	public void setTarget(int cellX, int cellY) {
		this.targetX = cellX;
		this.targetY = cellY;
		this.stateTime = 0;
	}

	public void update(float elapsedTime) {
		stateTime += elapsedTime;
		boolean isPressed = false;

		if (Gdx.input.isKeyPressed(Config.KEYCANCEL)) {
			this.speed = 5;
		} else {
			this.speed = 10;
		}

		if (this.targetX != this.NOTMOVE && this.targetY != this.NOTMOVE) {
			isPressed = true;
			float per = (stateTime / Config.FRAMETIME) / this.speed;
			if (per > 1) {
				per = 1;
			}
			this.x = this.cellX * Config.CELLWIDTH + Config.CELLWIDTH * (this.targetX - this.cellX) * per;
			this.y = this.cellY * Config.CELLWIDTH + Config.CELLWIDTH * (this.targetY - this.cellY) * per;
			if (this.x == this.targetX * Config.CELLWIDTH && this.y == this.targetY * Config.CELLWIDTH) {
				this.setCell(targetX, targetY);
				this.setTarget(NOTMOVE, NOTMOVE);
			}
		}

		if (!isPressed) {
			this.state = (this.state / 10) * 10;
		}

	}
	
	public void updateMove(MapObjects mapObjects) {
		int changeState = 0;
		if (this.targetX == this.NOTMOVE && this.targetY == this.NOTMOVE) {
			if (Gdx.input.isKeyPressed(Config.KEYUP)) {
				changeState = UPMOVE;
				this.setTarget(this.cellX, this.cellY + 1);
			} else if (Gdx.input.isKeyPressed(Config.KEYDOW)) {
				changeState = DOWNMOVE;
				this.setTarget(this.cellX, this.cellY - 1);
			} else if (Gdx.input.isKeyPressed(Config.KEYLEFT)) {
				changeState = LEFTMOVE;
				this.setTarget(this.cellX - 1, this.cellY);
			} else if (Gdx.input.isKeyPressed(Config.KEYRIGHT)) {
				changeState = RIGHTMOVE;
				this.setTarget(this.cellX + 1, this.cellY);
			}

			if (this.targetX != this.NOTMOVE && this.targetY != this.NOTMOVE) {
				Rectangle chRect = new Rectangle();
				chRect.x = this.targetX * Config.CELLWIDTH;
				chRect.y = this.targetY * Config.CELLWIDTH;
				chRect.width = this.getWidth();
				chRect.height = this.getHeight();
				for (MapObject obj : mapObjects) {
					if (obj instanceof RectangleMapObject) {
						Rectangle rect = ((RectangleMapObject) obj).getRectangle();
						if (RectUtils.isOverlap(chRect, rect)) {
							this.setTarget(NOTMOVE, NOTMOVE);
						}
					}
				}
			}

		}
		
		if (changeState != 0 && changeState != this.state) {
			this.state = changeState;
			this.stateTime = 0;
		}
	}

	public TextureRegion getFrame() {
		switch (this.state) {
		case LEFT:
			return left.getKeyFrames()[1];
		case LEFTMOVE:
			return left.getKeyFrame(stateTime, true);
		case RIGHT:
			return right.getKeyFrames()[1];
		case RIGHTMOVE:
			return right.getKeyFrame(stateTime, true);
		case UP:
			return up.getKeyFrames()[1];
		case UPMOVE:
			return up.getKeyFrame(stateTime, true);
		case DOWN:
			return down.getKeyFrames()[2];
		case DOWNMOVE:
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
