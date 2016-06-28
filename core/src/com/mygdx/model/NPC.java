package com.mygdx.model;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.impl.TalkingView;

public class NPC extends Character {
	private String eventPath;

	public NPC() {
		super();
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
		
		if (eventPath != null && !"".equals(eventPath)) {
			MyGdxGame.addState(new TalkingView(MyGdxGame.getView(), this.eventPath));
		}
	}

	public String getEventPath() {
		return this.eventPath;
	}

	public void setEventPath(String eventPath) {
		this.eventPath = eventPath;
	}

	public void setStateTime(float time) {
		this.stateTime = 0;
	}
}
