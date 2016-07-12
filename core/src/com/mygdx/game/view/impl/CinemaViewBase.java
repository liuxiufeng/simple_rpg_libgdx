package com.mygdx.game.view.impl;

import com.mygdx.component.event.Impl.CinemaEvent;
import com.mygdx.utils.EventManager;
import com.mygdx.utils.GlobalManager;

public class CinemaViewBase extends MapViewBase {
	private boolean isEventSend;
	private String path;
	public CinemaViewBase(String path, String mapid) {
		super(mapid);
		this.path = path;
	}
	
	@Override
	public void onEnter() {
		isEventSend = false;
	    super.onEnter();	
	}
	
	@Override
	public void update(float elapsedTime){
		super.update(elapsedTime);
		
		if (!isEventSend && !GlobalManager.isEvent) {
			EventManager.getInstance().addEvents(new CinemaEvent(this.path));
			isEventSend = true;
		}
		
	}
	
	@Override
	public void addListener() {
		
	}
}
