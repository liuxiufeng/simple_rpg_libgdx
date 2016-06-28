package com.mygdx.component.event;

public interface ActionEvent {
	public void before();
	
    public void excute();
    
    public void after();

    public void setListener(ActionListener listener); 
}
