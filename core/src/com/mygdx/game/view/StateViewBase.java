package com.mygdx.game.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.component.controller.IKeyListener;

public class StateViewBase implements IStateView {
	protected float stateTime;
	protected final float FRAME = 0.033f;
    protected List<IKeyListener> keyListeners;
    protected List<IKeyListener> removeListeners;
	
	public StateViewBase(){
		stateTime = 0;
		keyListeners = new ArrayList<IKeyListener>();
		removeListeners = new ArrayList<IKeyListener>();
	}
	
	public void addKeyListener(IKeyListener keyListener) {
		if (!this.keyListeners.contains(keyListener)) {
			this.keyListeners.add(keyListener);
		}
	}
	
	public void removeKeyListners(IKeyListener keyListener) {
	    if (keyListeners.contains(keyListener) && !removeListeners.contains(keyListener))	{
	    	removeListeners.remove(keyListener);
	    }
	}
	
	public List<IKeyListener> getKeyListeners() {
		if (removeListeners.size() > 0) {
			for (IKeyListener lis : removeListeners) {
				if (this.keyListeners.contains(lis)) {
					this.keyListeners.remove(lis);
				}
			}
		}
		
		removeListeners.clear();
		
		return this.keyListeners;
	}

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}
}
