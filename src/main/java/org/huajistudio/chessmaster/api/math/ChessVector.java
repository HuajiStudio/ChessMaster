package org.huajistudio.chessmaster.api.math;

public class ChessVector {
	/**
	 * Vector's direction
	 */
	private Direction direction;

	/**
	 * Vector's size
	 * {@link java.lang.Integer#MAX_VALUE INT_MAX} for infinite.
	 */
	private int size;

	public ChessVector(Direction direction, int size) {
		this.direction = direction;
		this.size = size;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChessVector that = (ChessVector) o;

		if (size != that.size) return false;
		return direction == that.direction;
	}

	@Override
	public String toString() {
		return "ChessVector{" +
			"direction=" + direction +
			", size=" + size +
			'}';
	}
}
