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
	private final int NOTMOVE = -3333;
	private int speed;
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
	/*
	 * *0 停 *1 走 1* 左 ，2*右， 3*下 ，4*上
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
		this.speed = 10;
		this.state = 30;
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
	}

	public void update(float elapsedTime, MapObjects mapObjects) {
		stateTime += elapsedTime;
		boolean isPressed = false;
		int changeState = 0;

		if (Gdx.input.isKeyPressed(Keys.X)) {
			this.speed = 5;
		} else {
			this.speed = 10;
		}
		
		if (this.targetX == this.NOTMOVE && this.targetY == this.NOTMOVE) {
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				changeState = 41;
				isPressed = true;
				this.setTarget(this.cellX, this.cellY + 1);
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				changeState = 31;
				isPressed = true;
				this.setTarget(this.cellX, this.cellY - 1);
			} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				changeState = 11;
				isPressed = true;
				this.setTarget(this.cellX - 1, this.cellY);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				changeState = 21;
				isPressed = true;
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

		} else {
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
				stateTime = 0;
			}
		}

		if (isPressed) {
			if (changeState != 0 && changeState != this.state) {
				this.state = changeState;
				this.stateTime = 0;
			}
		} else {
			this.state = (this.state / 10) * 10;
		}

	}

	public TextureRegion getFrame() {
		switch (this.state) {
		case 10:
			return left.getKeyFrames()[1];
		case 11:
			return left.getKeyFrame(stateTime, true);
		case 20:
			return right.getKeyFrames()[1];
		case 21:
			return right.getKeyFrame(stateTime, true);
		case 30:
			return up.getKeyFrames()[1];
		case 31:
			return up.getKeyFrame(stateTime, true);
		case 40:
			return down.getKeyFrames()[2];
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
