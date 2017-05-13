package org.huajistudio.api;

public class BoardPos {
    public int x, y;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoardPos && ((BoardPos) obj).x == x && ((BoardPos) obj).y == y;
    }

    @Override
    public String toString() {
        return "BoardPos{x=" + x + ", y=" + y + "}";
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
