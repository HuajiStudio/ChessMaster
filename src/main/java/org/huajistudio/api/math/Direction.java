package org.huajistudio.api.math;

public enum Direction {
	UP(1, 0), DOWN(-1, 0), LEFT(0, -1), RIGHT(0, 1),
	UP_LEFT(1, -1), DOWN_LEFT(-1, -1), UP_RIGHT(1, 1), DOWN_RIGHT(-1, 1),
	KNIGHT_1(1, 2), KNIGHT_2(2, 1),
	KNIGHT_3(-1, 2), KNIGHT_4(-2, 1),
	KNIGHT_5(1, -2), KNIGHT_6(2, -1),
	KNIGHT_7(-1, -2), KNIGHT_8(-2, -1);

	private int offsetX;
	private int offsetY;

	Direction(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
}
