package com.mygdx.component.event.Impl;

import com.badlogic.gdx.Gdx;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;
import com.mygdx.utils.Config;
import com.mygdx.model.Character;

public class MoveEvent extends EventBase {
	private BaseView view;
	float offset_x;
	float offset_y;
	int speed;
	int count;
	float stateTime;

	public MoveEvent(BaseView view, float offset_x, float offset_y, int speed, ActionListener listener) {
		super(listener);

		this.view = view;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		this.speed = speed;
		this.count = 0;
		stateTime = 0;
	}

	@Override
	public void update() {
		if (view instanceof Character) {
			Character ch = (Character) view;
			if (offset_x > 0) {
				ch.state = Character.RIGHTMOVE;
			} else if (offset_x < 0) {
				ch.state = Character.LEFTMOVE;
			}

			if (offset_y > 0) {
				ch.state = Character.UPMOVE;
			} else if (offset_y < 0) {
				ch.state = Character.DOWNMOVE;
			}
		}

		this.stateTime += Gdx.graphics.getDeltaTime();
		if (stateTime > Config.FRAMETIME) {
			this.stateTime -= Config.FRAMETIME;
			if (count < speed) {
				view.x += offset_x / speed;
				view.y += offset_y / speed;
				count++;
			} else {
				listener.callback(this);
			}
		}
	}

	@Override
	public void setListener(ActionListener listener) {
		this.listener = listener;
	}

}
