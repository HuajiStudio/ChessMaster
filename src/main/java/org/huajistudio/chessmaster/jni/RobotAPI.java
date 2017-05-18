package org.huajistudio.chessmaster.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface RobotAPI extends Library {
	RobotAPI INSTANCE = Native.loadLibrary(RobotAPI.class);

	/**
	 * Calculate a bot-controlled move.
	 * @param board serialized board json data
	 * @param depth calculate depth
	 * @return valid move json
	 */
	String calculateMove(String board, int depth);
}
