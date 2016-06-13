package com.mygdx.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.mygdx.res.FontData;

public class AssetManagerUtils {
	public static final String SIZE24 = "size24.ttf";
	
	private static AssetManagerUtils instance;
	private AssetManager manager;
	
    private AssetManagerUtils() {
    	manager = new AssetManager();
    }
    
    public static synchronized AssetManagerUtils getInstance() {
    	if(instance == null) {
    		instance = new AssetManagerUtils();
    	}
    	
    	return instance;
    }
    
    public void preLoad() {
    	FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		FreeTypeFontLoaderParameter size1Params = new FreeTypeFontLoaderParameter();
		size1Params.fontFileName = "data/fonts/MSYHBD.TTF";
		size1Params.fontParameters.size = 24;
		size1Params.fontParameters.characters = new FontData().create();
		manager.load(SIZE24, BitmapFont.class, size1Params);
		manager.update();
    }
    
    public BitmapFont getFont24() {
    	while(!manager.isLoaded(SIZE24));
    	return manager.get(SIZE24, BitmapFont.class);
    }
    
    public AssetManager getAssetManager() {
    	return this.manager;
    }
    
    public boolean update() {
     	return this.manager.update();
    }
}
