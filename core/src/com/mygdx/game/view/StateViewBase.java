package com.mygdx.game.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.component.controller.IKeyListener;

public class StateViewBase implements IStateView {
	protected float stateTime;
	protected final float FRAME = 0.033f;
    protected List<IKeyListener> keyListeners;
	
	public StateViewBase(){
		stateTime = 0;
		keyListeners = new ArrayList<IKeyListener>();
	}
	
	protected void addKeyListener(IKeyListener keyListener) {
	    this.keyListeners.add(keyListener);
	}
	
	public List<IKeyListener> getKeyListeners() {
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
