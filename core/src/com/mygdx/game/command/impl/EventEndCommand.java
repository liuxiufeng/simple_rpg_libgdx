package com.mygdx.game.command.impl;

import com.mygdx.game.command.ICommand;
import com.mygdx.utils.GlobalManager;

public class EventEndCommand implements ICommand {
    
    public EventEndCommand() {
    }
	
	@Override
	public void execute() {
		GlobalManager.isEvent = false;
	}

}
