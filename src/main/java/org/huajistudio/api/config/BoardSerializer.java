package org.huajistudio.api.config;

import com.google.gson.*;
import org.huajistudio.ChessMaster;
import org.huajistudio.api.Board;
import org.huajistudio.api.ChessObject;
import org.huajistudio.api.event.board.BoardDeserializeEvent;
import org.huajistudio.api.event.board.BoardSerializeEvent;

import java.lang.reflect.Type;

public class BoardSerializer implements JsonSerializer<Board>, JsonDeserializer<Board> {
	@Override
	public JsonElement serialize(Board src, Type typeOfSrc, JsonSerializationContext context) {
		BoardSerializeEvent event = new BoardSerializeEvent(src);
		ChessMaster.postEvent(event);
		if (event.isCancelled())
			return new JsonArray();
		src = event.getBoard();
		JsonArray array = new JsonArray();
		src.iterator().forEachRemaining(object -> array.add(context.serialize(object)));
		return array;
	}

	@Override
	public Board deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (json.isJsonArray()) {
			Board board = new Board();
			for (JsonElement element : json.getAsJsonArray())
				board.putChess(context.deserialize(element, ChessObject.class));
			BoardDeserializeEvent event = new BoardDeserializeEvent(board, json.getAsJsonArray());
			ChessMaster.postEvent(event);
			if (event.isCancelled())
				return new Board();
			board = event.getBoard();
			return board;
		} else {
			throw new JsonParseException("Given JsonElement isn't a JsonArray");
		}
	}
}
