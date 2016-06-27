package com.mygdx.component.event.Impl;

import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;

public class EventBase implements ActionEvent {

	protected ActionListener listener;
	
	protected boolean result;
	
	public EventBase(ActionListener listener) {
		this.listener = listener;
		result = true;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(ActionListener listener) {
			this.listener = listener;
	}

	@Override
	public boolean getResult() {
		return result;
	}
}
