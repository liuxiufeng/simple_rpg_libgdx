package com.mygdx.utils;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.impl.MapViewBase;
import com.mygdx.game.view.impl.TalkingView;
import com.mygdx.model.NPC;
import com.mygdx.model.Character;

public class EventManager {
	public static final int TYPE_KEY = 0;
	public static final int TYPE_AUTO = 1;
	public static int[][] eventMap;
    
	public static void triggerEvnt(int type) {
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
		case EventManager.TYPE_KEY:
			if (eventcode / 100 != 1) {
				return;
			}
			break;
		case EventManager.TYPE_AUTO:
			if (eventcode / 100 != 2) {
				return;
			}
			break;
		}
		EventManager.excuteEvent(eventcode);
	}
	
	public static final void excuteEvent(int eventcode) {
		switch (eventcode) {
		case 202:
			MyGdxGame.addState(new TalkingView( MyGdxGame.getView(), "data/event/event02.json"));
			break;
		}
	}

	public static final void excuteEvent(int eventcode, MapViewBase view) {
		switch (eventcode) {
		case 101:
			if (GlobalManager.hero.state == Character.DOWN || GlobalManager.hero.state == Character.DOWNMOVE) {
				GlobalManager.hero.setCell(2, 8);
			}
			break;
		case 201:
			break;
		case 203:
			if (GlobalManager.eventState == 0) {
				GlobalManager.eventState = 1;
				final NPC npc = view.getNPC("reisen");
				new Thread(new Runnable() {
					@Override
					public void run() {
						npc.state = NPC.DOWNMOVE;
						npc.setTarget(npc.cellX, npc.cellY - 1);
						while (npc.targetX != NPC.NOTMOVE || npc.targetY != NPC.NOTMOVE) {
						}
						;
						npc.setTarget(npc.cellX, npc.cellY - 1);
						while (npc.targetX != NPC.NOTMOVE || npc.targetY != NPC.NOTMOVE) {
						}
						;
						while (npc.targetX != NPC.NOTMOVE || npc.targetY != NPC.NOTMOVE) {
						}
						;
						if (npc.cellX  != GlobalManager.hero.cellX) {
							if (npc.cellX < GlobalManager.hero.cellX) {
								npc.state = NPC.RIGHTMOVE;
							} else {
								npc.state = NPC.LEFTMOVE;
							}
							npc.setTarget(GlobalManager.hero.cellX, npc.cellY);
						}
						while (npc.targetX != NPC.NOTMOVE || npc.targetY != NPC.NOTMOVE) {
						}
						;

						npc.state = NPC.DOWN;
						GlobalManager.hero.state = NPC.UP;
						GlobalManager.eventState = 2;
					}
				}).start();
			} else if (GlobalManager.eventState == 2) {
				MyGdxGame.addState(new TalkingView( MyGdxGame.getView(), "data/event/event02.json"));
				GlobalManager.eventState = 3;
			} else if (GlobalManager.eventState == 3) {
				eventMap[0][6] = 101;
				eventMap[0][7] = 101;
				eventMap[0][8] = 101;
			}
			break;
		}
	}
}
