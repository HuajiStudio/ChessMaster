package org.huajistudio.file;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.huajistudio.ChessMaster;
import org.huajistudio.api.Board;
import org.huajistudio.api.BoardObject;
import org.huajistudio.api.ChessObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class BoardReader {
	public static Board readBoard(File file) {
		Gson gson = new Gson();
		if (file.toString().endsWith(".gz")) {
			try {
				BoardObject boardObject = gson.fromJson(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), StandardCharsets.UTF_8), BoardObject.class);
				Board board = new Board();
				for (ChessObject chessObject : boardObject.getChessObjects())
					board.chessObjects.put(chessObject.pos, chessObject);
				return board;
			} catch (IOException e) {
				ChessMaster.LOGGER.error("Cannot read board!", e);
			}
		}
		try {
			BoardObject boardObject = gson.fromJson(FileUtils.readFileToString(file, Charset.forName("UTF-8")), BoardObject.class);
			Board board = new Board();
			for (ChessObject chessObject : boardObject.getChessObjects())
				board.chessObjects.put(chessObject.pos, chessObject);
			return board;
		} catch (IOException e) {
			ChessMaster.LOGGER.error("Cannot read board!", e);
		}
		return null;
	}
}
