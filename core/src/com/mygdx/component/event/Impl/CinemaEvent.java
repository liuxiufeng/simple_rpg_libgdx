package com.mygdx.component.event.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.command.impl.EventEndCommand;
import com.mygdx.game.command.impl.EventStartCommand;
import com.mygdx.game.view.impl.MapViewBase;
import com.mygdx.utils.Config;
import com.mygdx.utils.MapUtils;
import com.mygdx.model.Character;

public class CinemaEvent extends EffectEventBase implements ActionListener {
	public static final String TALK = "talk";
	public static final String MOVE = "move";
	public static final String STATE = "state";
	public static final String FADEOUT = "fadeout";

	public static final String A_ALPHA = "alpha";
	public static final String A_SWITCH = "switch";

	private List<List<ActionEvent>> eventList;

	private List<ActionEvent> removeEvents;

	private Map<String, BaseView> viewMap;

	private String path;

	private JSONObject event;

	public CinemaEvent() {
		eventList = new ArrayList<List<ActionEvent>>();
		removeEvents = new ArrayList<ActionEvent>();
	}

	public CinemaEvent(String path) {
		eventList = new ArrayList<List<ActionEvent>>();
		removeEvents = new ArrayList<ActionEvent>();
		this.path = path;
	}

	@Override
	public void before() {
		new EventStartCommand().execute();
		viewMap = MapUtils.viewMap;

		String json = null;
		try {
			json = new String(Gdx.files.internal(path).readBytes(), "UTF-8");
			event = JSON.parseObject(json);
		} catch (Exception e) {
			this.listener.callback(this);
			e.printStackTrace();
			return;
		}

		List<ActionEvent> events = new ArrayList<ActionEvent>();
		JSONArray array = (JSONArray) event.get("before");
		for (Object oStep : array) {
			JSONArray step = (JSONArray) oStep;
			events = new ArrayList<ActionEvent>();
			for (Object oAction : step) {
				JSONObject action = (JSONObject) oAction;
				String type = action.getString("type");
				if (TALK.equals(type)) {
					TalkingEffectEvent talkevent = new TalkingEffectEvent(action.getString("path"));
					talkevent.setListener(this);
					talkevent.before();
					events.add(talkevent);
				} else if (MOVE.equals(type)) {
					BaseView view = viewMap.get(action.getString("chr"));
					CinemaMoveEvent moveEvent = new CinemaMoveEvent(view,
							action.getInteger("offset_x") * Config.CELLWIDTH,
							action.getInteger("offset_y") * Config.CELLWIDTH, action.getInteger("speed"));
					moveEvent.setListener(this);
					events.add(moveEvent);
				} else if (STATE.equals(type)) {
					Character chr = (Character) viewMap.get(action.getString("chr"));
					int state = 0;
					switch (action.getString("state")) {
					case "right":
						state = Character.RIGHT;
						break;
					case "rightmove":
						state = Character.RIGHTMOVE;
						break;
					case "left":
						state = Character.LEFT;
						break;
					case "leftmove":
						state = Character.LEFTMOVE;
						break;
					case "up":
						state = Character.UP;
						break;
					case "upmove":
						state = Character.UPMOVE;
						break;
					case "down":
						state = Character.DOWN;
						break;
					case "downmove":
						state = Character.DOWNMOVE;
						break;
					default:
						state = 0;
					}
					CharChangeStateEvent stateEvent = new CharChangeStateEvent(chr, state);
					stateEvent.setListener(this);
					events.add(stateEvent);
				} else if (FADEOUT.equals(type)) {
					ScreenFadeOutEffect fadeOut = new ScreenFadeOutEffect(action.getInteger("time") * Config.FRAMETIME);
					fadeOut.setListener(this);
					events.add(fadeOut);
				}
			}
			eventList.add(events);
		}
	}

	@Override
	public void excute() {
		List<ActionEvent> events = eventList.get(0);
		for (ActionEvent event : events) {
			event.excute();
		}
	}

	@Override
	public void after() {
		new EventEndCommand().execute();
		JSONArray after = event.getJSONArray("after");
		for (Object obj : after) {
			JSONObject action = (JSONObject) obj;
			String type = action.getString("type");
			if (A_ALPHA.equals(type)) {
				BaseView view = viewMap.get(action.getString("id"));
				view.alpha = action.getFloatValue("value");
			} else if (A_SWITCH.equals(type)) {
				MyGdxGame.switchState(new MapViewBase());
			}
		}
	}

	@Override
	public void render(Batch batch) {
		List<ActionEvent> events = eventList.get(0);
		for (ActionEvent event : events) {
			if (event instanceof EffectEventBase) {
				((EffectEventBase) event).render(batch);
			}
		}

		for (ActionEvent event : removeEvents) {
			if (events.contains(event)) {
				events.remove(event);
			}
			event.after();
		}

		if (events.size() == 0) {
			eventList.remove(0);
		}

		removeEvents.clear();

		if (eventList.size() == 0) {
			this.listener.callback(this);
			return;
		}
	}

	@Override
	public void callback(ActionEvent caller) {
		if (eventList != null && eventList.size() > 0) {
			if (eventList.get(0).contains(caller)) {
				removeEvents.add(caller);
			}
		}
	}

}
