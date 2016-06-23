package com.mygdx.game.view.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.component.controller.IKeyListener;
import com.mygdx.component.controller.KeyProcess;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.IStateView;
import com.mygdx.game.view.StateViewBase;
import com.mygdx.utils.AssetManagerUtils;
import com.mygdx.utils.Config;
import com.mygdx.utils.TextureFlyweightFactory;

public class TalkingView extends StateViewBase implements IStateView, IKeyListener {
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
	SpriteBatch mBatch;
	IStateView preView;

	public TalkingView(IStateView preView,  String path) {
		super();
		this.preView = preView;
		String jsonStr = null;
		try {
			jsonStr = new String(Gdx.files.internal(path).readBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		nodes = JSON.parseArray(jsonStr);
		this.onEnter();
	}
	
	public TalkingView(String path) {
		super();
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
		if (preView != null) {
			preView.update(elapsedTime);
		}
		
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
		if (ttBackground != null) {
			batch.draw(ttBackground, 0, 0);
		}
	    
		if (preView != null) {
			preView.render(batch);
		}
		
		if (ttChr != null) {
			batch.draw(ttChr, 0, 0);
		}
		if (ttDilogBox != null) {
			batch.draw(ttDilogBox, 0, 0);
		}
		int size = show.size() - 1;
		for (int i = 0; i < show.size(); i++) {
			if (i == size) {
				font.draw(batch, show.get(i).substring(0, len), 20, 200 - i * 50);
			} else {
				font.draw(batch, show.get(i), 20, 200 - i * 50);
			}
		}

		font.draw(batch, name, 20, 250);
	}

	@Override
	public void onEnter() {
		JSONObject node = nodes.getJSONObject(0);
		ttDilogBox = new Texture("widgets/dialog1.png");
		font = AssetManagerUtils.getInstance().getFont24();
		show = new ArrayList<String>();
		this.name = "";

		runScript(node);
		mBatch = new SpriteBatch();
	}

	@Override
	public void onExit() {
		MyGdxGame.removeState();
		ttDilogBox.dispose();
		if (ttBackground != null) {
		    ttBackground.dispose();
		}

		TextureFlyweightFactory.getInstance().dispose();
		
		if (sdBgm != null) {
			sdBgm.dispose();
		}
		if (sdEffect != null) {
			sdEffect.dispose();
		}
		
	}

	private void ExitOrNext() {
		switch (type) {
		case "talk":
			if (index == content.length - 1) {
				if (next == -1) {
					this.onExit();
				} else {
					runScript(nodes.getJSONObject(next));
				}
			}
			break;
		default:
			if (next == -1) {
				this.onExit();
			}
		}
	}

	private void runScript(JSONObject object) {
		type = object.getString("type");
		next = object.getIntValue("next");
		String path;
		switch (type) {
		case "talk":
			path = object.getString("leftimage");
			ttChr = TextureFlyweightFactory.getInstance().getTexture(path);
			name = object.getString("name");
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
		case "change":
			if (ttBackground != null) {
				ttBackground.dispose();
			}
			ttBackground = new Texture(object.getString("background"));
			path = object.getString("bgm");
			if (path != null && !path.isEmpty()) {
				if (sdBgm != null) {
					sdBgm.dispose();
				}
				sdBgm = Gdx.audio.newSound(Gdx.files.internal(path));
			}
			if (next != -1) {
				runScript(nodes.getJSONObject(next));
			}
			break;
		}
	}

	@Override
	public void keyDown(int keycode) {
		if (keycode == Config.KEYCONFIRM) {
		    this.ExitOrNext();
		}
	}

	@Override
	public void keyUp(int keycode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addListener() {
	    KeyProcess.addListner(this);	
	}

}
