package com.mygdx.model;

import com.mygdx.game.view.impl.CorridorView;
import com.mygdx.game.view.impl.TalkingView;
import com.mygdx.utils.GlobalManager;

public class EventManager {
    public static final  int TYPE_KEY = 0;
    public static final int TYPE_AUTO = 1;
    
    public static final void excuteEvent(int eventcode){
    	switch(eventcode) {
    	case 101:
    		GlobalManager.hero.x = 64;
    		GlobalManager.hero.y = 250;
    		GlobalManager.game.viewStack.pop();
            GlobalManager.game.viewStack.push(new CorridorView(GlobalManager.game));
    		break;
    	case 201:
    		GlobalManager.game.viewStack.push(new TalkingView(GlobalManager.game, "data/event/event01.json"));
    		break;
    	}
    }
}
