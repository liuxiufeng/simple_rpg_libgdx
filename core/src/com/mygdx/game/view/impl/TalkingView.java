package com.mygdx.game.view.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.utils.Config;
import com.mygdx.utils.FontUtils;

public class TalkingView extends StateViewBase implements IStateView, InputProcessor {
	private final int maxLen = 30;

	Texture ttDilogBox;
	Texture ttBackground;
	Texture ttChr;
	Sound sdBgm;
	Sound sdEffect;
	JSONArray nodes;
	String name;
	int next;
	String type;
	float stateTime;
	String[] content;
	List<String> show;
	int index;
	int len;
	private BitmapFont font;

	public TalkingView(MyGdxGame game, String path) {
		super(game);
		String jsonStr = null;
		try {
			jsonStr = new String(Gdx.files.internal(path).readBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		nodes = JSON.parseArray(jsonStr);
		this.onEnter();
	}

	@Override
	public void update(float elapsedTime) {
		if (type.equals("talk")) {
			len = (int) (stateTime / (Config.FRAMETIME * 2));
			len = Math.min(content[index].length(), len);
			stateTime += elapsedTime;
			if (len == content[index].length()) {
				if (index < content.length - 1) {
					if (show.size() == 3) {
						show.remove(0);
						show.add(content[index + 1]);
					} else {
						show.add(content[index + 1]);
					}
					index++;
					len = 0;
					stateTime = 0;
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(ttBackground, 0, 0);
		batch.draw(ttChr, 0, 0);
		batch.draw(ttDilogBox, 0, 0);
		int size = show.size() - 1;
		for (int i = 0; i < show.size(); i++) {
			if (i == size) {
				font.draw(batch, show.get(i).substring(0, len), 20, 200 - i * 50);
			} else {
				font.draw(batch, show.get(i), 20, 200 - i * 50);
			}
		}

		font.draw(batch, name,  20, 250);
	}

	@Override
	public void onEnter() {
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(),
				Gdx.graphics.getBackBufferHeight(), true);

		Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(),
				Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
		ttBackground = new Texture(pixmap);
		pixmap.dispose();

		ttDilogBox = new Texture("widgets/dialog1.png");
		font = FontUtils.getFont();
		show = new ArrayList<String>();
		runScript(nodes.getJSONObject(0));
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void onExit() {
		ttDilogBox.dispose();
		ttBackground.dispose();
		ttChr.dispose();
		if (sdBgm != null) {
			sdBgm.dispose();
		}
		if (sdEffect != null) {
		    sdEffect.dispose();
		}
		
		this.game.viewStack.pop();
		Gdx.input.setInputProcessor((InputProcessor)this.game.viewStack.lastElement());
	}

	private void runScript(JSONObject object) {
		type = object.getString("type");
		switch (type) {
		case "talk":
			ttChr = new Texture(object.getString("leftimage"));
			name = object.getString("name");
			next = object.getIntValue("next");
			String all = object.getString("context");
			int tmpLen = all.length() / this.maxLen + 1;
			stateTime = 0;
			index = 0;
			if (all.length() % this.maxLen == 0) {
				tmpLen--;
			}
			content = new String[tmpLen];
			for (int i = 0; i < tmpLen; i++) {
				if (i == tmpLen - 1) {
					content[i] = all.substring(i * this.maxLen);
				} else {
					content[i] = all.substring(i * this.maxLen, (i + 1) * this.maxLen);
				}
			}
            
			show.clear();
			show.add(content[0]);
			break;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (index == content.length - 1) {
			if (next == -1) {
				this.onExit();
			} else {
			    runScript(nodes.getJSONObject(next));
			}
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (index == content.length - 1) {
			if (next == -1) {
				this.onExit();
			} else {
			    runScript(nodes.getJSONObject(next));
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}