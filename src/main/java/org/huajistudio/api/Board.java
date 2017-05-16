package org.huajistudio.api;

import com.google.common.collect.Maps;
import io.reactivex.Observable;
import org.huajistudio.ChessMaster;
import org.huajistudio.api.event.chess.ChessCreateEvent;
import org.huajistudio.api.event.chess.ChessMoveEvent;
import org.huajistudio.utils.ChessHelper;

import java.util.Iterator;
import java.util.Map;

public class Board implements Iterable<ChessObject> {
	private Map<BoardPos, ChessObject> chessObjects;

	public Board() {
		this.chessObjects = Maps.newHashMapWithExpectedSize(64);
	}

	public static Board start() {
		Board board = new Board();
		board.putChess(new ChessObject(new BoardPos(0, 0), Chess.CASTLE, ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(1, 0), Chess.KNIGHT, ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(2, 0), Chess.POPE,   ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(3, 0), Chess.QUEEN,  ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(4, 0), Chess.KING,   ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(5, 0), Chess.POPE,   ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(6, 0), Chess.KNIGHT, ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(7, 0), Chess.CASTLE, ChessHelper.WHITE));
		board.putChess(new ChessObject(new BoardPos(0, 7), Chess.CASTLE, ChessHelper.BLACK));
		board.putChess(new ChessObject(new BoardPos(1, 7), Chess.KNIGHT, ChessHelper.BLACK));
		board.putChess(new ChessObject(new BoardPos(2, 7), Chess.POPE,   ChessHelper.BLACK));
		board.putChess(new ChessObject(new BoardPos(3, 7), Chess.KING,   ChessHelper.BLACK));
		board.putChess(new ChessObject(new BoardPos(4, 7), Chess.QUEEN,  ChessHelper.BLACK));
		board.putChess(new ChessObject(new BoardPos(5, 7), Chess.POPE,   ChessHelper.BLACK));
		board.putChess(new ChessObject(new BoardPos(6, 7), Chess.KNIGHT, ChessHelper.BLACK));
		board.putChess(new ChessObject(new BoardPos(7, 7), Chess.CASTLE, ChessHelper.BLACK));
		for (int i = 0; i < 8; ++i) {
			board.putChess(new ChessObject(new BoardPos(i, 1), Chess.PAWN, ChessHelper.WHITE));
			board.putChess(new ChessObject(new BoardPos(i, 6), Chess.PAWN, ChessHelper.BLACK));
		}
		return board;
	}
	public ChessObject getChess(BoardPos pos) {
		return chessObjects.get(pos);
	}

	public void putChess(ChessObject object) {
		ChessCreateEvent createEvent = new ChessCreateEvent(object);
		ChessMaster.postEvent(createEvent);
		if (!createEvent.isCancelled())
			chessObjects.put(object.pos, object);
	}

	public void move(ChessObject chess, BoardPos dest) {
		Observable.just(new Move(chess.pos, dest, chess, this)).filter(ChessHelper::canMove).filter(move -> {
			ChessMoveEvent moveEvent = new ChessMoveEvent(this, move);
			ChessMaster.postEvent(moveEvent);
			return !moveEvent.isCancelled();
		}).subscribe(move -> {
			ChessObject chess1 = chessObjects.get(move.origin);
			chess1.pos = move.end;
			chessObjects.put(move.end, chess1);
			chessObjects.remove(move.origin);
		}).dispose();
	}

	@Override
	public Iterator<ChessObject> iterator() {
		return chessObjects.values().iterator();
	}
}
