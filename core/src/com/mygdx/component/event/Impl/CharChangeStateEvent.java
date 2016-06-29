package com.mygdx.component.event.Impl;
import com.mygdx.model.Character;
public class CharChangeStateEvent extends EventBase {
    
	private Character chr;
    private int state;
	
	public CharChangeStateEvent(Character chr, int state) {
	    this.chr = chr;
	    this.state = state;
	}
	
	@Override
	public void before() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excute() {
		this.chr.state = this.state;
		this.listener.callback(this);
	}

	@Override
	public void after() {
		// TODO Auto-generated method stub
		
	}

}
