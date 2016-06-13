package com.mygdx.utils;

import com.mygdx.game.view.impl.CorridorView;
import com.mygdx.game.view.impl.TalkingView;

public class EventManager {
    public static final int TYPE_KEY = 0;
    public static final int TYPE_AUTO = 1;
    
    public static final void excuteEvent(int eventcode){
    	switch(eventcode) {
    	case 101:
    		GlobalManager.hero.setCell(2, 8);
    		GlobalManager.game.viewStack.pop();
            GlobalManager.game.viewStack.push(new CorridorView(GlobalManager.game));
    		break;
    	case 201:
    		GlobalManager.game.viewStack.push(new TalkingView(GlobalManager.game, "data/event/event01.json"));
    		break;
    	}
    }
}
