package org.huajistudio.jni;

import org.huajistudio.api.Move;
import org.huajistudio.api.Board;

public class RobotAPI {
	public native Move calculateMove(Board board, int depth);

	static {
		System.loadLibrary("robot_api");
	}
}
