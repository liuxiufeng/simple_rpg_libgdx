package com.mygdx.res;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.model.Character;
import com.mygdx.utils.Config;

public class CharRes {
    public static void getReisen(Character chr) {
    	Texture walkSheet = new Texture("char/Doll_2.png");
    	int cols = 12;
    	int rows = 8;
    	TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/cols, walkSheet.getHeight()/rows);
    	TextureRegion [] walk = new TextureRegion[3];
    	int index = 0;
    	for(int i = 0; i < 4; i++) {
    		for (int j = 9; j < 9 + 3; j++) {
    			walk[index] = tmp[i][j];
    			index++;
    		}
    		if (i == 0) {
    			chr.setUp(new Animation(Config.FRAMETIME, walk));
    		} else if (i == 1) {
    			chr.setLeft(new Animation(Config.FRAMETIME, walk));
    		} else if (i == 2) {
    			chr.setRight(new Animation(Config.FRAMETIME, walk));
    		} else if (i == 3) {
    			chr.setDown(new Animation(Config.FRAMETIME, walk));
    		}
    		walk = new TextureRegion[3];
    		index = 0;
    	} 
    }
    
    public static void getMary(Character chr) {
    	Texture walkSheet = new Texture("animation/mary.png");
    	int cols = 3;
    	int rows = 4;
    	TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/cols, walkSheet.getHeight()/rows);
    	TextureRegion [] walk = new TextureRegion[3];
    	int index = 0;
    	for(int i = 0; i < 4; i++) {
    		for (int j = 0; j < 3; j++) {
    			walk[index] = tmp[i][j];
    			index++;
    		}
    		if (i == 0) {
    			chr.setUp(new Animation(Config.FRAMETIME, walk));
    		} else if (i == 1) {
    			chr.setLeft(new Animation(Config.FRAMETIME, walk));
    		} else if (i == 2) {
    			chr.setRight(new Animation(Config.FRAMETIME, walk));
    		} else if (i == 3) {
    			chr.setDown(new Animation(Config.FRAMETIME, walk));
    		}
    		walk = new TextureRegion[3];
    		index = 0;
    	} 
    }
}
