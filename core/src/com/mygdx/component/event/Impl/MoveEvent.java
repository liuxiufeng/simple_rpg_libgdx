package com.mygdx.component.event.Impl;

import com.mygdx.component.event.ActionEvent;
import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;

public class MoveEvent implements ActionEvent {
    private BaseView view;
    private ActionListener listener;
    float offset_x;
    float offset_y;
    int speed;
    int count;
    
    public MoveEvent(BaseView view, float offset_x, float offset_y, int speed) {
    	this.view = view;
    	this.offset_x = offset_x;
    	this.offset_y = offset_y;
    	this.speed = speed;
    	this.count = 0;
    }
    
	
	@Override
	public void update() {
		if ( count <= speed ) {
			view.x += offset_x * count / speed;
			view.y += offset_y * count / speed;
		} else {
		    listener.callback(this);	
		}
	}

	@Override
	public void setListener(ActionListener listener) {
		this.listener = listener;
	}
}
