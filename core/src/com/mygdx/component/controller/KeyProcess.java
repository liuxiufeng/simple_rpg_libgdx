package com.mygdx.component.controller;

import java.util.ArrayList;
import java.util.List;

public class KeyProcess {
	private static List<Integer> pressedKeys;

	private static List<Integer> keyDownList;

	private static List<Integer> keyUpList;

	public static boolean isProcess;

	static {
		pressedKeys = new ArrayList<Integer>();
		keyDownList = new ArrayList<Integer>();
		keyUpList = new ArrayList<Integer>();

		isProcess = false;
	}

	public static void keyDown(int keycode) {
		if (!keyDownList.contains(keycode) && !isProcess) {
			keyDownList.add(keycode);
		}
	}

	public static void keyUp(int keycode) {
		if (!keyUpList.contains(keyUpList) && !isProcess ) {
			keyUpList.add(keycode);
		}
	}

	public static synchronized void process(List<IKeyListener> keyListeners) {
		if (keyListeners == null || keyListeners.size() < 1) {
			clear();
			return;
		}
		
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
		keyDownList.clear();
		keyUpList.clear();
		pressedKeys.clear();
	}
}
