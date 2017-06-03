package org.huajistudio.chessmaster.api.event.client;

public class KeyEvent {
	private final long window;
	private final int key;
	private final int scancode;
	private final int mods;

	/**
	 * Will be posted when a key is pressed, repeated or released.
	 *
	 * @param window   the window that received the event
	 * @param key      the keyboard key that was pressed or released
	 * @param scancode the system-specific scancode of the key
	 * @param mods     bitfield describing which modifiers keys were held down
	 */
	public KeyEvent(long window, int key, int scancode, int mods) {
		this.window = window;
		this.key = key;
		this.scancode = scancode;
		this.mods = mods;
	}

	public long getWindow() {
		return window;
	}

	public int getKey() {
		return key;
	}

	public int getScancode() {
		return scancode;
	}

	public int getMods() {
		return mods;
	}

	public static class Press extends KeyEvent {
		public Press(long window, int key, int scancode, int mods) {
			super(window, key, scancode, mods);
		}
	}

	public static class Release extends KeyEvent {
		public Release(long window, int key, int scancode, int mods) {
			super(window, key, scancode, mods);
		}
	}

	public static class Repeat extends KeyEvent {
		public Repeat(long window, int key, int scancode, int mods) {
			super(window, key, scancode, mods);
		}
	}
}
