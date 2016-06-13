package com.mygdx.game.command;

import java.util.HashMap;

public interface ICommand {
    public void execute(HashMap<String, Object> message);
}
