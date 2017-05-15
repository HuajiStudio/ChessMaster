package org.huajistudio.utils;

import org.huajistudio.api.*;
import org.huajistudio.api.math.ChessVector;
import org.huajistudio.api.math.Direction;
import org.omg.CORBA.Object;

import java.util.Objects;

public interface ChessHelper {

	boolean BLACK = true;
	boolean WHITE = false;

	static boolean canMove(Move move) {
		return canMoveTo(move.turned, move.end, move.board);
	}

    static boolean canMoveTo(ChessObject chess, BoardPos pos, Board board) {
    	ChessObject chessObjectAtDestination = board.getChess(pos);
    	if (chessObjectAtDestination != null)
			if (chessObjectAtDestination.side == chess.side)
				return false;

		int deltaX = pos.x - chess.pos.x;
	    int deltaY = pos.y - chess.pos.y;

	    for (ChessVector vector : chess.type.getMovableVectors()) {
	    	if (deltaX != 0 && vector.getDirection().getOffsetX() != 0) {
			    if (deltaX % vector.getDirection().getOffsetX() != 0)
				    continue;
			    int s = deltaX / vector.getDirection().getOffsetX();
			    if (s * deltaY == vector.getDirection().getOffsetY())
				    return true;
		    } else if (deltaY != 0 && vector.getDirection().getOffsetY() != 0) {
		    	if (deltaY % vector.getDirection().getOffsetY() != 0)
		    		continue;
			    int s = deltaY / vector.getDirection().getOffsetY();
			    if (s * deltaX == vector.getDirection().getOffsetX())
			    	return true;
		    }
	    }

	    if (chess.type == Chess.PAWN)
	    	if (chess.side == BLACK) if (chess.pos.y == 1)
			    return chess.pos.move(0, 1).equals(pos) || chess.pos.move(0, 2).equals(pos);
		    else
			    return chess.pos.move(0, 1).equals(pos);
		    else if (chess.pos.y == 6)
			    return chess.pos.move(0, -1).equals(pos) || chess.pos.move(0, -2).equals(pos);
		    else
			    return chess.pos.move(0, -1).equals(pos);

		return false;
    }
}
