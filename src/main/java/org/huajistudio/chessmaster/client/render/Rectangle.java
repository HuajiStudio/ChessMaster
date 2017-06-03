package org.huajistudio.chessmaster.client.render;

import javax.vecmath.Vector2f;

/**
 * A 2D-vertex based rectangle storage.
 */
public class Rectangle {
	private Vector2f start;
	private float width;
	private float height;

	public Rectangle(Vector2f start, float width, float height) {
		this.start = start;
		this.width = width;
		this.height = height;
	}

	public Rectangle(float x, float y, float width, float height) {
		this.start = new Vector2f(x, y);
		this.width = width;
		this.height = height;
	}

	public Vector2f getStart() {
		return start;
	}

	public void setStart(Vector2f start) {
		this.start = start;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Vector2f getLeftUp() {
		return start;
	}

	public Vector2f getRightUp() {
		Vector2f clone = (Vector2f) start.clone();
		clone.x += width;
		return clone;
	}

	public Vector2f getLeftDown() {
		Vector2f clone = (Vector2f) start.clone();
		clone.y -= height;
		return clone;
	}

	public Vector2f getRightDown() {
		Vector2f clone = (Vector2f) start.clone();
		clone.x += width;
		clone.y -= height;
		return clone;
	}
}
