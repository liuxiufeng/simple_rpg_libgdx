package com.mygdx.utils;

import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.res.FontData;

public class FontUtils {
	private static BitmapFont bitmapFont;

	public static BitmapFont getFont() {
		if (bitmapFont == null) {
			Gdx.app.log("LoadFont", new Date().toString());
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/MSYHBD.TTF"));
			Gdx.app.log("LoadFont", new Date().toString());
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 24;
			parameter.characters = new FontData().create();
			bitmapFont = generator.generateFont(parameter); 
			Gdx.app.log("LoadFont", new Date().toString());
			generator.dispose();
		}
		return bitmapFont;
	}
}
