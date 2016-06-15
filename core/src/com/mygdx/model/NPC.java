package com.mygdx.model;

import com.badlogic.gdx.maps.MapObjects;
import com.mygdx.utils.Config;
import com.mygdx.utils.EventManager;

public class NPC extends Character {
	private int eventNum;

	public NPC() {
		super();
	}

	@Override
	public void update(float elapsedTime) {

		stateTime += elapsedTime;
		boolean isPressed = false;
		int changeState = 0;

		if (this.targetX == this.NOTMOVE && this.targetY == this.NOTMOVE) {

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

	public void talked(int heroX, int heroY, int heroState) {
		if (this.cellX == heroX + 1 && this.cellY == heroY && heroState == RIGHT) {
			this.state = LEFT;
		} else if (this.cellX == heroX - 1 && this.cellY == heroY && heroState == LEFT) {
			this.state = RIGHT;
		} else if (this.cellX == heroX && this.cellY == heroY + 1 && heroState == UP) {
			this.state = DOWN;
		} else if (this.cellX == heroX && this.cellY == heroY - 1 && heroState == DOWN) {
			this.state = UP;
		} else {
			return;
		}
		if (eventNum != 0) {
			EventManager.excuteEvent(eventNum);
		}
	}

	public int getEventNum() {
		return eventNum;
	}

	public void setEventNum(int eventNum) {
		this.eventNum = eventNum;
	}

	public void setStateTime(float time) {
		this.stateTime = 0;
	}
}
