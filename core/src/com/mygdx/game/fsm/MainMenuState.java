package com.mygdx.game.fsm;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.impl.MapViewBase;

public enum MainMenuState {
	NewGame {
		@Override
		public void pre(MainMenuFSM fsm) {

		}

		@Override
		public void next(MainMenuFSM fsm) {

		}

		@Override
		public void execute() {
		    System.out.println("Start New Game");
			MyGdxGame.switchState(new MapViewBase());
		}
	};
	
	public abstract void pre(MainMenuFSM fsm);

	public abstract void next(MainMenuFSM fsm);

	public abstract void execute();

}
