package com.mygdx.component.controller;

import java.util.ArrayList;
import java.util.List;

public class KeyProcess {
    private static List<Integer> pressedKeys;
    
    private static List<Integer> keyDownList;
    
    private static List<Integer> keyUpList;
    
    private static List<IKeyListener> keyListeners;
    
    public static boolean isProcess;
    
    static {
    	pressedKeys = new ArrayList<Integer>();
    	keyDownList = new ArrayList<Integer>();
    	keyUpList = new ArrayList<Integer>();
    	
    	keyListeners = new ArrayList<IKeyListener>();
    	isProcess = false;
    }
    
    public static void keyDown(int keycode) {
        if (!keyDownList.contains(keycode)) {
        	keyDownList.add(keycode);
        }
    }
    
    public static void keyUp(int keycode) {
    	if (!keyUpList.contains(keyUpList)) {
    		keyUpList.add(keycode);
    	}
    }
    
    public static synchronized void process () {
    	isProcess = true;
        for (int keycode : keyDownList) {
            for (IKeyListener lis : keyListeners) {
            	lis.keyDown(keycode);
            }
            
            if (!pressedKeys.contains(keycode)) {
            	pressedKeys.add(keycode);
            }
        }	
        
        keyDownList.clear();
        
        for (int keycode : keyUpList) {
        	for (IKeyListener lis : keyListeners) {
        		lis.keyUp(keycode);
        	}
        	
        	if (pressedKeys.contains(keycode)) {
        	    pressedKeys.remove((Object) keycode);
        	}
        }
        
        keyUpList.clear();
        
       isProcess = false;
    } 
    
    public static synchronized void clear() {
    	 pressedKeys.clear();
    	 keyListeners.clear();
    }
    
    public static void addListner(IKeyListener listener) {
    	keyListeners.add(listener);
    }
    
    public static void removeListner(IKeyListener listener) {
    	if (keyListeners.contains(listener)) {
    		keyListeners.remove(listener);
    	}
    }
}
