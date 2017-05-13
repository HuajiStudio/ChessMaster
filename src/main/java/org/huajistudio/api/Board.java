package org.huajistudio.api;

import io.reactivex.Observable;
import org.huajistudio.utils.ChessHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Board implements Iterable<ChessObject> {
	private Map<BoardPos, ChessObject> chessObjects;

	public Board() {
		this.chessObjects = new HashMap<>();
	}

	public ChessObject getChess(BoardPos pos) {
		return chessObjects.get(pos);
	}

	public void putChess(ChessObject object) {
		chessObjects.put(object.pos, object);
	}

	public void move(ChessObject chess, BoardPos dest) {
		Observable.just(new Move(chess.pos, dest, chess, this)).filter(ChessHelper::canMove).subscribe(move -> {
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
