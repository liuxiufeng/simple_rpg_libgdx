package com.mygdx.game.command.impl;

import com.mygdx.game.command.ICommand;
import com.mygdx.model.Hero;
import com.mygdx.utils.GlobalManager;

public class EventStartCommand implements ICommand {
    Hero hero;
    public EventStartCommand() {
        hero = GlobalManager.hero;	
    }
	@Override
	public void execute() {
	    hero.pressedKey = 0;
	    GlobalManager.isEvent = true;
	};
}
