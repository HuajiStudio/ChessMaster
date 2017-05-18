package org.huajistudio.chessmaster.jni;

import org.huajistudio.chessmaster.api.Move;
import org.huajistudio.chessmaster.api.Board;

public class RobotAPI {
	public native Move calculateMove(Board board, int depth);

	static {
		System.loadLibrary("robot_api");
	}
}
