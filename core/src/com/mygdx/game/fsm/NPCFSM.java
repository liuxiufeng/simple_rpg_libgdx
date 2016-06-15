package com.mygdx.game.fsm;

import com.mygdx.model.NPC;

public enum NPCFSM {
    LEFT {
		@Override
		public void change(NPC npc) {
		    npc.setTarget(npc.cellX + 1, npc.cellY);	
		}
    };
	
	public abstract void change(NPC npc);
}
