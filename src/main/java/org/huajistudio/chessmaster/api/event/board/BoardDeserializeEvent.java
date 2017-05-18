package org.huajistudio.chessmaster.api.event.board;

import com.google.gson.JsonArray;
import org.huajistudio.chessmaster.api.Board;

public class BoardDeserializeEvent extends BoardEvent {
	private JsonArray array;

	public BoardDeserializeEvent(Board board, JsonArray array) {
		super(board);
		this.array = array;
	}

	public JsonArray getArray() {
		return array;
	}

	public void setArray(JsonArray array) {
		this.array = array;
	}
}
