package org.huajistudio.jni;

public class RobotAPI {
	public native Move calculateMove(Board board, int depth);

	static {
		System.loadLibrary("robot_api");
	}
}
