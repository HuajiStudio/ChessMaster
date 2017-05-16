package org.huajistudio.api.config;

import com.google.gson.*;
import org.huajistudio.api.Chess;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ChessSerializer implements JsonSerializer<Chess>, JsonDeserializer<Chess> {
	public static final Map<String, Chess> STRING_CHESS_MAP = new HashMap<>();
	public static final Map<Chess, String> CHESS_STRING_MAP = new HashMap<>();

	static {
		STRING_CHESS_MAP.put("KING", Chess.KING);
		STRING_CHESS_MAP.put("QUEEN", Chess.QUEEN);
		STRING_CHESS_MAP.put("POPE", Chess.POPE);
		STRING_CHESS_MAP.put("KNIGHT", Chess.KNIGHT);
		STRING_CHESS_MAP.put("CASTLE", Chess.CASTLE);
		STRING_CHESS_MAP.put("PAWN", Chess.PAWN);
		STRING_CHESS_MAP.forEach((s, chess) -> CHESS_STRING_MAP.put(chess, s));
	}

	@Override
	public JsonElement serialize(Chess src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(CHESS_STRING_MAP.get(src));
	}

	@Override
	public Chess deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!json.isJsonPrimitive())
			throw new JsonParseException("Given Element isn't a JsonPrimitive");
		if (!((JsonPrimitive) json).isNumber())
			throw new JsonParseException("Given Element isn't a JsonString");
		return STRING_CHESS_MAP.get(json.getAsString());
	}
}
