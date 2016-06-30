package com.mygdx.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.event.Impl.CinemaEvent;
import com.mygdx.component.event.Impl.EffectEventBase;
import com.mygdx.component.event.Impl.TalkingEffectEvent;
import com.mygdx.game.command.impl.EventEndCommand;
import com.mygdx.game.command.impl.EventStartCommand;

public class EventManager implements ActionListener {
	private static EventManager instance;
	private List<ActionEvent> events;
	private List<ActionEvent> addEvents;
	private List<ActionEvent> removeEvents;

	private EventManager() {
		events = new ArrayList<ActionEvent>();
		removeEvents = new ArrayList<ActionEvent>();
		addEvents = new ArrayList<ActionEvent>();
	};

	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}

		return instance;
	}

	public void process() {

		events.addAll(addEvents);
		addEvents.clear();

		if (events == null || events.size() == 0) {
			return;
		}

		for (ActionEvent event : events) {
			event.excute();
		}

		for (ActionEvent event : removeEvents) {
			if (events.contains(event)) {
				if (event instanceof TalkingEffectEvent) {
				    new EventEndCommand().execute();
				}
				events.remove(event);
			}
		}

		removeEvents.clear();
	}
	
	public void render(Batch batch) {
		for (ActionEvent event : events) {
		    if (event instanceof EffectEventBase) {
                ((EffectEventBase) event).render(batch);
		    }
		}
	}

	public void addEvents(ActionEvent event) {
		if (!events.contains(event) && !addEvents.contains(event)) {
			if (event instanceof TalkingEffectEvent) {
			    new EventStartCommand().execute();
			}
			event.setListener(this);
			addEvents.add(event);
			event.before();
		}
	}

	@Override
	public void callback(ActionEvent caller) {
		caller.after();

		
		if (events.contains(caller) && !removeEvents.contains(caller)) {
			removeEvents.add(caller);
		}
	}

}
