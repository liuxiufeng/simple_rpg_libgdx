package com.mygdx.game.fsm;

import com.mygdx.game.view.impl.MapViewBase;
import com.mygdx.utils.GlobalManager;

public enum MainMenuState {
	NewGame {
		@Override
		public void pre() {

		}

		@Override
		public void next() {

		}

		@Override
		public void execute() {
            GlobalManager.game.viewStack.pop();
            GlobalManager.game.viewStack.push(new MapViewBase(GlobalManager.game));
		}
	};
	
	public abstract void pre();

	public abstract void next();

	public abstract void execute();

}
