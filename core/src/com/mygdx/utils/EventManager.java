package com.mygdx.utils;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.impl.MapViewBase;
import com.mygdx.game.view.impl.TalkingView;
import com.mygdx.model.NPC;
import com.mygdx.model.Character;

public class EventManager {
	public static final int TYPE_KEY = 0;
	public static final int TYPE_AUTO = 1;
    
	
	public static final void excuteEvent(int eventcode) {
		switch (eventcode) {
		case 202:
			MyGdxGame.addState(new TalkingView( MyGdxGame.getView(), "data/event/event02.json"));
			break;
		}
	}
}
