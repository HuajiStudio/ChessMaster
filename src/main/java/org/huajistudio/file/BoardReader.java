package org.huajistudio.file;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.huajistudio.ChessMaster;
import org.huajistudio.api.Board;
import org.huajistudio.api.BoardObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

public class BoardReader {
	public static Board readBoard(File file) {
		Gson gson = new Gson();
		BoardObject boardObject = new BoardObject();
		if (file.toString().endsWith(".gz")) {
			try {
				boardObject = gson.fromJson(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), StandardCharsets.UTF_8), BoardObject.class);
			} catch (IOException e) {
				ChessMaster.getLogger().error("Cannot read gzipped board!", e);
			}
		}
		try {
			boardObject = gson.fromJson(FileUtils.readFileToString(file, StandardCharsets.UTF_8), BoardObject.class);
		} catch (IOException e) {
			ChessMaster.getLogger().error("Cannot read board!", e);
		}
		Board board = new Board();
		Arrays.stream(boardObject.getChessObjects()).forEach(board::putChess);
		return board;
	}
}
