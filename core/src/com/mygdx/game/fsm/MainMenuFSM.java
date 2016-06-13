package com.mygdx.game.fsm;

import com.mygdx.utils.Config;

public class MainMenuFSM {
	private MainMenuState state;
	
	public MainMenuFSM() {
		this.state = MainMenuState.NewGame;
	}
	
	public void keydown(int keycode) {
	    if (keycode == Config.KEYUP) {
	    	this.state.pre();
	    } else if (keycode == Config.KEYDOW) {
	    	this.state.next();
	    } else if (keycode == Config.KEYCONFIRM) {
	    	this.state.execute();
	    }
	}

}
