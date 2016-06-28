package com.mygdx.component.event.Impl;

import java.util.Map;

import com.mygdx.component.event.ActionListener;
import com.mygdx.component.view.BaseView;
import com.mygdx.model.Character;
import com.mygdx.model.NPC;
import com.mygdx.utils.MapUtils;
public class TalkEvent extends EventBase {
	private Character ch;
	
    public TalkEvent(Character ch) {
        this.ch = ch;	
    }
    
    @Override
    public void excute() {
        Map<String, BaseView> views = MapUtils.viewMap;
        for(String key: views.keySet()) {
        	BaseView view = views.get(key);
        	if (view instanceof NPC) {
        		((NPC)view).talked(ch.cellX, ch.cellY, ch.state);
        	}
        }
        
        this.listener.callback(this);
    }

	@Override
	public void before() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after() {
		// TODO Auto-generated method stub
	}
}
