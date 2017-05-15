package org.huajistudio.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.huajistudio.ChessMaster;
import org.huajistudio.api.Board;
import org.huajistudio.api.config.BoardSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public interface BoardIO {
	static Optional<Board> readBoard(File file) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Board.class, new BoardSerializer()).create();
		Board board = null;
		if (file.getName().endsWith(".gz")) try {
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

	static void writeBoard(File file, Board board) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Board.class, new BoardSerializer()).create();
		if (file.getName().endsWith(".gz"))
			try {
				gson.toJson(board, new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(file)), StandardCharsets.UTF_8));
			} catch (IOException e) {
				ChessMaster.getLogger().error("Cannot write gzipped board!", e);
			}
		else
			try {
				gson.toJson(board, new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			} catch (IOException e) {
				ChessMaster.getLogger().error("Cannot write board!", e);
			}
	}
}
