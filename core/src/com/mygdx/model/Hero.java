package com.mygdx.model;

import com.mygdx.component.controller.IKeyListener;
import com.mygdx.component.event.Impl.MapEvent;
import com.mygdx.component.event.Impl.MoveEvent;
import com.mygdx.component.event.Impl.TalkEvent;
import com.mygdx.utils.Config;

public class Hero extends Character implements IKeyListener {
	private TalkEvent talkevent = new TalkEvent(this);
	
	public Hero() {
		super();
	}

	@Override
	public void update(float elapsedTime) {
	    super.update(elapsedTime);	
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
				events.add(new MoveEvent(this, (this.targetX - this.cellX) * Config.CELLWIDTH,
						(this.targetY - this.cellY) * Config.CELLWIDTH, this.speed));
			}
		}

		if (changeState != 0 && changeState != this.state) {
			this.state = changeState;
			this.stateTime = 0;
		}
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
			events.add(talkevent);
			events.add(new MapEvent(MapEvent.TYPE_KEY));
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
}
