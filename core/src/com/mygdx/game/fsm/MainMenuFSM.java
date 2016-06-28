package com.mygdx.game.fsm;

import com.mygdx.component.controller.IKeyListener;
import com.mygdx.utils.Config;

public class MainMenuFSM implements IKeyListener {
	private MainMenuState state;
	
	public MainMenuFSM() {
		this.state = MainMenuState.NewGame;
	}
	
	public void keydown(int keycode) {
	    if (keycode == Config.KEYUP) {
	    	this.state.pre(this);
	    } else if (keycode == Config.KEYDOW) {
	    	this.state.next(this);
	    } else if (keycode == Config.KEYCONFIRM) {
	    	this.state.execute();
	    }
	}
	
	public void setState(MainMenuState state) {
		this.state = state;
	}

	@Override
	public void keyDown(int keycode) {
		this.keydown(keycode);
	}

	@Override
	public void keyUp(int keycode) {
		// TODO Auto-generated method stub
		
	}

}
