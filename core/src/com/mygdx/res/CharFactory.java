package com.mygdx.res;

import com.mygdx.model.Character;

public class CharFactory {
	public static final String Reisen = "reisen";
    public static final String Mary = "mary";
	
    public static CharFactory instance;
    
    private CharFactory() {
    	
    };
    
    public static CharFactory getInstance() {
    	if (instance == null ) {
    		instance = new CharFactory();
    	}
    	
    	return instance;
    }
    
    public void getChar(String name, Character chr) {
        if (Reisen.equals(name))  {
        	CharRes.getReisen(chr);
        }
        if (Mary.equals(name)) {
        	CharRes.getMary(chr);
        }
    }
}
