package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.view.impl.CorridorView;
import com.mygdx.game.view.impl.MapViewBase;
import com.mygdx.game.view.impl.TalkingView;
import com.mygdx.model.NPC;

public class EventManager {
	public static final int TYPE_KEY = 0;
	public static final int TYPE_AUTO = 1;

	public static final void excuteEvent(int eventcode) {
		switch (eventcode) {
		case 202:
			GlobalManager.game.viewStack.push(new TalkingView(GlobalManager.game, "data/event/event02.json"));
			break;
		}
	}

	public static final void excuteEvent(int eventcode, MapViewBase view) {
		switch (eventcode) {
		case 101:
			GlobalManager.hero.setCell(2, 8);
			GlobalManager.game.viewStack.pop();
			GlobalManager.game.viewStack.push(new CorridorView(GlobalManager.game));
			break;
		case 201:
			GlobalManager.game.viewStack.push(new TalkingView(GlobalManager.game, "data/event/event01.json"));
			break;
		case 203:
			if (GlobalManager.eventState == 0) {
				view.isEvnet = true;
				Gdx.input.setInputProcessor(null);
				GlobalManager.eventState = 1;
				final NPC npc = view.getNPC("reisen");
				new Thread(new Runnable() {

					@Override
					public void run() {
						npc.state = NPC.DOWNMOVE;
						npc.setTarget(npc.cellX, npc.cellY - 1);
						while (npc.targetX != NPC.NOTMOVE || npc.targetY != NPC.NOTMOVE) {
							System.out.println(npc.targetY);
						}
						;
						npc.setTarget(npc.cellX, npc.cellY - 1);
						while (npc.targetX != NPC.NOTMOVE || npc.targetY != NPC.NOTMOVE) {
							System.out.println(npc.targetY);
						}
						;
						GlobalManager.hero.state = NPC.UP;
						GlobalManager.eventState = 2;
					}
				}).start();
			} else if (GlobalManager.eventState == 2) {
				GlobalManager.eventState = 3;
				GlobalManager.game.viewStack.push(new TalkingView(GlobalManager.game, "data/event/event02.json"));
				view.isEvnet = false;
			} else if (GlobalManager.eventState == 3) {
				GlobalManager.hero.setCell(2, 8);
				GlobalManager.game.viewStack.pop();
				GlobalManager.game.viewStack.push(new CorridorView(GlobalManager.game));
			}
			break;
		}
	}
}
