package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.res.FontData;

public class FontUtils {
	private static BitmapFont bitmapFont;

	public static BitmapFont getFont() {
		if (bitmapFont == null) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/MSYHBD.TTF"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 24;
			parameter.characters = new FontData().create() + FreeTypeFontGenerator.DEFAULT_CHARS;
			bitmapFont = generator.generateFont(parameter); 
			generator.dispose();
		}
		return bitmapFont;
	}
}
