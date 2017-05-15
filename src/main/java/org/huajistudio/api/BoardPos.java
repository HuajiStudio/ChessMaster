package org.huajistudio.api;

public class BoardPos {
	private int x, y;

	public BoardPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BoardPos && ((BoardPos) obj).x == x && ((BoardPos) obj).y == y;
	}

	@Override
	public String toString() {
		return "BoardPos{x=" + x + ", y=" + y + "}";
	}

	@Override
	public int hashCode() {
		return x * 8 + y;
	}

	public int getX() {
		return x;
	}

	public BoardPos setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public BoardPos setY(int y) {
		this.y = y;
		return this;
	}
}
