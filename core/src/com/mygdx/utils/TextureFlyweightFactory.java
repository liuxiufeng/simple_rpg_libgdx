package com.mygdx.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class TextureFlyweightFactory {
    private static TextureFlyweightFactory instance;
	private Map<String, Texture> textureMap;
    
	private TextureFlyweightFactory() {
		textureMap = new HashMap<String, Texture>();
    }
	
	public static synchronized TextureFlyweightFactory getInstance() {
	    if (instance == null) {
	        instance = new 	TextureFlyweightFactory();
	    } 
	    return instance;
	}
	
	public Texture getTexture(String path) {
		if (path == null) {
			return null;
		}
		
		if (textureMap.containsKey(path)) {
			return textureMap.get(path);
		} else {
			Texture texture = new Texture(path);
			textureMap.put(path, texture);
			return texture;
		}
	}
	
	public void dispose(){
		for (String key : textureMap.keySet()) {
			textureMap.get(key).dispose();
		}
		textureMap.clear();
	}
}
