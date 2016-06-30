package com.mygdx.component.event.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.command.impl.EventEndCommand;
import com.mygdx.game.command.impl.EventStartCommand;
import com.mygdx.game.view.impl.MapViewBase;
import com.mygdx.model.Hero;
import com.mygdx.model.NPC;
import com.mygdx.utils.Config;
import com.mygdx.utils.GlobalManager;
import com.mygdx.utils.MapUtils;

public class CinemaEvent extends EffectEventBase implements ActionListener {

	private List<List<ActionEvent>> eventList;
	
	private List<ActionEvent> removeEvents;

	private Map<String, BaseView> viewMap;
	

	public CinemaEvent() {
		eventList = new ArrayList<List<ActionEvent>>();
		removeEvents = new ArrayList<ActionEvent>();
	}

	@Override
	public void before() {
		new EventStartCommand().execute();
		
		viewMap = MapUtils.viewMap;
		
		BaseView view = viewMap.get("reisen");
		NPC reisen = (NPC) view;
		Hero hero = GlobalManager.hero;

		List<ActionEvent> events = new ArrayList<ActionEvent>();
		TalkingEffectEvent talkevent = new TalkingEffectEvent("data/event/event03-1.json");
		talkevent.setListener(this);
		talkevent.before();
		events.add(talkevent);
		eventList.add(events);
		
		events = new ArrayList<ActionEvent>();
		hero.targetY = hero.targetY - 5;
		CinemaMoveEvent moveEvent = new CinemaMoveEvent(hero, 0, -5 * Config.CELLWIDTH, 5 * 5);
        moveEvent.setListener(this);
        events.add(moveEvent);
        moveEvent = new CinemaMoveEvent(reisen, -5 * Config.CELLWIDTH, 0, 5 * 5);
        moveEvent.setListener(this);
        events.add(moveEvent);
        
		eventList.add(events);
        
		events = new ArrayList<ActionEvent>();
		CharChangeStateEvent stateEvent = new CharChangeStateEvent(hero, Hero.RIGHT);
		stateEvent.setListener(this);
        
		events.add(stateEvent);
		eventList.add(events);
		
		events = new ArrayList<ActionEvent>();
		talkevent = new TalkingEffectEvent("data/event/event03-2.json");
		talkevent.setListener(this);
		talkevent.before();
		events.add(talkevent);
		eventList.add(events);
        
		events = new ArrayList<ActionEvent>();
		moveEvent = new CinemaMoveEvent(hero, 0, -5 * Config.CELLWIDTH, 5 * 5);
        moveEvent.setListener(this);
        events.add(moveEvent);
        
        /*fadeOut = new ScreenFadeOutEffect(25 * Config.FRAMETIME);
        fadeOut.setListener(this);
        events.add(fadeOut);*/
        
        eventList.add(events);
        
	}

	@Override
	public void excute() {
		if (eventList.size() == 0) {
			this.listener.callback(this);
			return;
		}

		List<ActionEvent> events = eventList.get(0);
		for (ActionEvent event : events) {
			event.excute();
		}
		
		for (ActionEvent event : removeEvents) {
			if (events.contains(event)) {
				events.remove(event);
			}
		}
		
		if (events.size() == 0) {
			eventList.remove(0);
		}
		
		removeEvents.clear();
	}

	@Override
	public void after() {
		new EventEndCommand().execute();
		Hero hero = GlobalManager.hero;
		hero.alpha = 0.5f;
	    MyGdxGame.switchState(new MapViewBase());
	}

	@Override
	public void render(Batch batch) {
		if (eventList.size() == 0) {
			return;
		}
		List<ActionEvent> events = eventList.get(0);
		for (ActionEvent event : events) {
		     if (event instanceof EffectEventBase) {
		    	 ((EffectEventBase) event).render(batch);
		     }
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
