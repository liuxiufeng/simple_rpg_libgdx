package com.mygdx.component.event.Impl;

import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;

public abstract class EventBase implements ActionEvent {

	protected ActionListener listener;
	
	public EventBase() {
	}
	

	@Override
	public void setListener(ActionListener listener) {
			this.listener = listener;
	}
}
