package com.mygdx.game.fsm;

import com.mygdx.model.NPC;

public enum NPCFSM {
    LEFT {
		@Override
		public void change(NPC npc) {

		}
    };
	
	public abstract void change(NPC npc);
}
