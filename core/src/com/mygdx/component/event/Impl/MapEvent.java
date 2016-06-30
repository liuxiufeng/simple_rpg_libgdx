package com.mygdx.component.event.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.command.impl.EventEndCommand;
import com.mygdx.game.command.impl.EventStartCommand;
import com.mygdx.game.view.impl.CorridorView;
import com.mygdx.game.view.impl.TalkingView;
import com.mygdx.model.Character;
import com.mygdx.model.NPC;
import com.mygdx.utils.Config;
import com.mygdx.utils.GlobalManager;
import com.mygdx.utils.MapUtils;

public class MapEvent extends EventBase implements ActionListener {
	public static final int TYPE_KEY = 0;
	public static final int TYPE_AUTO = 1;
	
	private List<ActionEvent> eventList;
    
	private int[][] eventMap;
	
	private Character ch; 
	
	private Map<String, BaseView> viewMap;
	
	private int eventcode;
	
	private int type;
	
	public MapEvent(int type) {
		eventList = new ArrayList<ActionEvent>();
		this.type = type;
	}
	
	public  void triggerEvnt(int type) {
		this.eventMap = MapUtils.eventMap;
		this.viewMap = MapUtils.viewMap;
		ch = GlobalManager.hero;
		if (eventMap == null) {
			return;
		}
        
		Character ch = GlobalManager.hero;
		
		if (ch.cellY > eventMap.length - 1 || ch.cellY < 0 || ch.cellX > eventMap[0].length - 1 || ch.cellX < 0) {
			return;
		}

		int eventcode = eventMap[ch.cellY][ch.cellX];

		if (eventcode == 0) {
			return;
		}

		switch (type) {
		case MapEvent.TYPE_KEY:
			if (eventcode / 100 != 1) {
				return;
			}
			break;
		case MapEvent.TYPE_AUTO:
			if (eventcode / 100 != 2) {
				return;
			}
			break;
		}
	    
		new EventStartCommand().execute();
		
		this.eventcode = eventcode;
	    ch.state = (ch.state / 10) * 10; 
		if (eventcode == 203) {
			BaseView view =  viewMap.get("reisen");
			NPC reisen = (NPC) view;
			eventList.add(new MoveEvent(view, 0, -Config.CELLWIDTH, 10, this));
			eventList.add(new MoveEvent(view, 0, -Config.CELLWIDTH, 10, this));
			eventList.add(new MoveEvent(view, ch.x - view.x, 0, 10, this));
		}
		
		if (eventcode == 101) {
			if (GlobalManager.hero.state == Character.DOWN || GlobalManager.hero.state == Character.DOWNMOVE) {
				GlobalManager.hero.setCell(2, 8);
				MyGdxGame.switchState(new CorridorView());
			}
		}
	}

	@Override
    public void excute() {
		if (eventList.size() > 0) {
			eventList.get(0).excute();
		} else {
			this.listener.callback(this);
		}
	}
	
	@Override
	public void callback(ActionEvent caller) {
		caller.after();
	    eventList.remove(0);
	}
    
	@Override
	public void after() {
		if (this.eventcode != 0) {
			new EventEndCommand().execute();
		}

		if (this.eventcode == 203) {
			this.eventcode = 0;
			BaseView view =  viewMap.get("reisen");
			NPC reisen = (NPC) view;
			reisen.setCell(ch.cellX, ch.cellY + 1);
			reisen.state = NPC.DOWN;
			ch.state = Character.UP;
			eventMap[0][6] = 101;
			eventMap[0][7] = 101;
			eventMap[0][8] = 101;
			MyGdxGame.addState(new TalkingView( MyGdxGame.getView(), "data/event/event02.json"));
		}
	}

	@Override
	public void before() {
		triggerEvnt(type);
	}

}
