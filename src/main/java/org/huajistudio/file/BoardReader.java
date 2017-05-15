package org.huajistudio.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.huajistudio.ChessMaster;
import org.huajistudio.api.Board;
import org.huajistudio.api.config.BoardSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

public class BoardReader {
	public static Optional<Board> readBoard(File file) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Board.class, new BoardSerializer()).create();
		Board board = null;
		if (file.toString().endsWith(".gz")) try {
			board = gson.fromJson(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), StandardCharsets.UTF_8), Board.class);
		} catch (IOException e) {
			ChessMaster.getLogger().error("Cannot read gzipped board!", e);
		}
		try {
			board = gson.fromJson(FileUtils.readFileToString(file, StandardCharsets.UTF_8), Board.class);
		} catch (IOException e) {
			ChessMaster.getLogger().error("Cannot read board!", e);
		}
		return Optional.ofNullable(board);
	}
}
