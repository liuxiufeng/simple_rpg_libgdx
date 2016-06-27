package com.mygdx.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.mygdx.component.controller.IKeyListener;
import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.event.Impl.CollisionDectEvent;
import com.mygdx.component.event.Impl.MapEvent;
import com.mygdx.component.event.Impl.MoveEvent;
import com.mygdx.component.event.Impl.TalkEvent;
import com.mygdx.component.view.BaseView;
import com.mygdx.utils.Config;

public class Character extends BaseView implements IKeyListener, ActionListener {
	public static final int LEFT = 10;
	public static final int LEFTMOVE = 11;
	public static final int RIGHT = 20;
	public static final int RIGHTMOVE = 21;
	public static final int DOWN = 30;
	public static final int DOWNMOVE = 31;
	public static final int UP = 40;
	public static final int UPMOVE = 41;

	int speed;

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

	boolean isAction = false;

	int pressedKey = 0;

	List<ActionEvent> actionList = new ArrayList<ActionEvent>();
	
	private TalkEvent talkevent = new TalkEvent(this, this);
    MapEvent mapEvnet = new MapEvent(this);
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
		if (actionList.size() > 0) {
			this.isAction = true;
			actionList.get(0).update();
		}
		updateMove();
	}

	public void updateMove() {
		int changeState = 0;
		if (!this.isAction) {
			if (this.pressedKey == Config.KEYUP) {
				changeState = UPMOVE;
				this.setTarget(this.cellX, this.cellY + 1);
			} else if (this.pressedKey == Config.KEYDOW) {
				changeState = DOWNMOVE;
				this.setTarget(this.cellX, this.cellY - 1);
			} else if (this.pressedKey == Config.KEYLEFT) {
				changeState = LEFTMOVE;
				this.setTarget(this.cellX - 1, this.cellY);
			} else if (this.pressedKey == Config.KEYRIGHT) {
				changeState = RIGHTMOVE;
				this.setTarget(this.cellX + 1, this.cellY);
			} else {
				this.state = (this.state / 10) * 10;
			}

			if (this.targetX != this.cellX || this.targetY != this.cellY) {
				CollisionDectEvent event = new CollisionDectEvent(this, this.targetX * Config.CELLWIDTH,
						this.targetY * Config.CELLWIDTH, this);
				this.actionList.add(event);
			}
		} 

		if (changeState != 0 && changeState != this.state) {
			this.state = changeState;
			this.stateTime = 0;
		}
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

		batch.draw(currentFrame, x, y);
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

	@Override
	public void keyDown(int keycode) {
		if (keycode == Config.KEYDOW || keycode == Config.KEYUP || keycode == Config.KEYRIGHT
				|| keycode == Config.KEYLEFT) {
			this.pressedKey = keycode;
		}

		if (keycode == Config.KEYCANCEL) {
			this.speed = 5;
		}
		
		if (keycode == Config.KEYCONFIRM) {
			if (!this.actionList.contains(talkevent)) {
			    this.actionList.add(talkevent);
			} 
		    
			mapEvnet.triggerEvnt(MapEvent.TYPE_KEY);
		}
	}

	@Override
	public void keyUp(int keycode) {
		if (this.pressedKey == keycode) {
			this.pressedKey = 0;
		}

		if (keycode == Config.KEYCANCEL) {
			this.speed = 10;
		}
	}

	@Override
	public void callback(com.mygdx.component.event.ActionEvent caller) {
		this.isAction = false;
		this.actionList.remove(0);
		if (caller instanceof CollisionDectEvent) {
			if (caller.getResult()) {
				this.isAction = true;
				MoveEvent event = new MoveEvent(this, (this.targetX - this.cellX) * Config.CELLWIDTH,
						(this.targetY - this.cellY) * Config.CELLWIDTH, this.speed, this);
				this.actionList.add(0, event);
				event.update();
			} else {
				this.setTarget(cellX, cellY);
			}
		} else if (caller instanceof MoveEvent) {
			this.isAction = true;
			this.setCell(this.targetX, this.targetY);
			mapEvnet.triggerEvnt(MapEvent.TYPE_AUTO);
			this.actionList.add(mapEvnet);
			mapEvnet.update();
		}
	}

	public void addAction(ActionEvent action) {
		this.actionList.add(action);
	}

}
