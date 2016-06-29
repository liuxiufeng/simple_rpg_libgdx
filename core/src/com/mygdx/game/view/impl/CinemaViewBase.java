package com.mygdx.game.view.impl;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.component.event.Impl.CinemaEvent;
import com.mygdx.model.NPC;
import com.mygdx.res.CharRes;
import com.mygdx.utils.EventManager;
import com.mygdx.utils.GlobalManager;

public class CinemaViewBase extends MapViewBase {
	private boolean isEventSend;
	public CinemaViewBase() {
		super();
	}
	
	@Override
	public void onEnter() {
		isEventSend = false;
		
	    tiledMap = new TmxMapLoader().load("map/corner.tmx");
	    this.ch.setCell(1, 15);
	    
	    NPC npc = new NPC();
		CharRes.getReisen(npc);
		npc.setCell(7, 10);
		
		views.put("reisen", npc);
	}
	
	@Override
	public void update(float elapsedTime){
		super.update(elapsedTime);
		
		if (!isEventSend && !GlobalManager.isEvent) {
			EventManager.getInstance().addEvents(new CinemaEvent());
			isEventSend = true;
		}
		
	}
}
