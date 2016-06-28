package com.mygdx.component.event.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;
import com.mygdx.model.Character;
import com.mygdx.model.Hero;
import com.mygdx.utils.Config;
import com.mygdx.utils.EventManager;
import com.mygdx.utils.MapUtils;
import com.mygdx.utils.RectUtils;

public class MoveEvent extends EventBase {
	private BaseView view;
	private float offset_x;
	private float offset_y;
	private int speed;
	private int count;
	private float stateTime;
	private boolean result;

	public MoveEvent(BaseView view, float offset_x, float offset_y, int speed) {
		this.view = view;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		this.speed = speed;
		this.count = 0;
		stateTime = 0;

		if (view instanceof Character) {
			Character ch = (Character) view;
			ch.isAction = true;
		}
	}
	
	public MoveEvent(BaseView view, float offset_x, float offset_y, int speed, ActionListener listener) {
		this.view = view;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		this.speed = speed;
		this.count = 0;
		stateTime = 0;
        
		this.listener = listener;
	    this.result = true;
		
		if (view instanceof Character) {
			Character ch = (Character) view;
			ch.isAction = true;
		}
	}

	@Override
	public void excute() {
		if (!result) {
			listener.callback(this);
			return;
		}

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

	@Override
	public void before() {
		Rectangle chRect = new Rectangle();
		chRect.x = view.x + offset_x;
		chRect.y = view.y + offset_y;
		chRect.width = view.width;
		chRect.height = view.height;

		result = true;

		for (MapObject obj : MapUtils.collisionObjecs) {
			if (obj instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject) obj).getRectangle();
				if (RectUtils.isOverlap(chRect, rect)) {
					result = false;
				}
			}
		}

		if (result) {
			for (String key : MapUtils.viewMap.keySet()) {
				BaseView view = MapUtils.viewMap.get(key);

				Rectangle viewRect = new Rectangle();
				viewRect.x = view.x;
				viewRect.y = view.y;
				viewRect.width = view.width;
				viewRect.height = view.height;

				if (RectUtils.isOverlap(chRect, viewRect)) {
					result = false;
				}
			}
		}

		if (!result) {
			if (view instanceof Character) {
				Character ch = (Character) view;
				ch.targetX = ch.cellX;
				ch.targetY = ch.cellY;
			}
		}
	}

	@Override
	public void after() {
		if (view instanceof Character) {
			Character ch = (Character) view;
			ch.state = (ch.state * 10) / 10;
		}
		
		if (view instanceof Hero) {
			Character ch = (Character) view;
			ch.setCell(ch.targetX, ch.targetY);
			EventManager.getInstance().addEvents(new MapEvent(MapEvent.TYPE_AUTO));
		}
	}

}
