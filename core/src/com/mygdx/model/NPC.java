package com.mygdx.model;

import com.mygdx.utils.EventManager;

public class NPC extends Character {
	private int eventNum;

	public NPC() {
		super();
	}
	
	@Override
	public void update(float elapsedTime) {
		stateTime += elapsedTime;
		if (actionList.size() > 0) {
			this.isAction = true;
			actionList.get(0).update();
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

	@Override
	public void callback(com.mygdx.component.event.ActionEvent caller) {

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

	@Override
	public void keyDown(int keycode) {

	}

	@Override
	public void keyUp(int keycode) {

	}
}
