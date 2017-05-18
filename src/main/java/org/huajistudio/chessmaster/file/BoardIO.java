package org.huajistudio.chessmaster.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.api.Board;
import org.huajistudio.chessmaster.api.Chess;
import org.huajistudio.chessmaster.api.config.BoardSerializer;
import org.huajistudio.chessmaster.api.config.ChessSerializer;
import org.huajistudio.chessmaster.api.event.board.BoardReadEvent;
import org.huajistudio.chessmaster.api.event.board.BoardWriteEvent;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public interface BoardIO {
	Gson GSON = new GsonBuilder().registerTypeAdapter(Board.class, new BoardSerializer()).registerTypeAdapter(Chess.class, new ChessSerializer()).create();

	static Optional<Board> readBoard(File file) {
		Board board = null;
		if (file.getName().endsWith(".gz")) try {
			board = GSON.fromJson(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), StandardCharsets.UTF_8), Board.class);
		} catch (IOException e) {
			ChessMaster.getLogger().error("Cannot read gzipped board!", e);
		}
		try {
			board = GSON.fromJson(FileUtils.readFileToString(file, StandardCharsets.UTF_8), Board.class);
		} catch (IOException e) {
			ChessMaster.getLogger().error("Cannot read board!", e);
		}
		BoardReadEvent readEvent = new BoardReadEvent(board, file);
		ChessMaster.postEvent(readEvent);
		return Optional.ofNullable(readEvent.isCancelled() ? null : readEvent.getBoard());
	}

	static void writeBoard(File file, Board board) {
		BoardWriteEvent writeEvent = new BoardWriteEvent(board, file);
		ChessMaster.postEvent(writeEvent);
		if (writeEvent.isCancelled())
			return;
		file = writeEvent.getBoardFile();
		board = writeEvent.getBoard();
		if (file.getName().endsWith(".gz"))
			try {
				GSON.toJson(board, new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(file)), StandardCharsets.UTF_8));
			} catch (IOException e) {
				ChessMaster.getLogger().error("Cannot write gzipped board!", e);
			}
		else
			try {
				GSON.toJson(board, new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			} catch (IOException e) {
				ChessMaster.getLogger().error("Cannot write board!", e);
			}
	}
}
