package org.huajistudio.utils;

import org.huajistudio.api.Board;
import org.huajistudio.api.Chess;
import org.huajistudio.api.ChessObject;

class ChessHelper {

	public static final boolean BLACK = true;
	public static final boolean WHITE = false;

    public static boolean CanMoveTo(ChessObject chess, Board.BoardPos pos, Board board) {
    	ChessObject chessObjectAtDestination = board.chessObjects.get(pos);
    	if (chessObjectAtDestination != null)
		{
			if (chessObjectAtDestination.side == chess.side)
			{
				return false;
			}
		}


		if (chess.type == Chess.PAWN &&
			chess.side==BLACK &&
			chess.pos.y==1 &&
			pos.x==chess.pos.x&&pos.y==3)
		{
			return true;
		}
		if (chess.type == Chess.PAWN &&
			chess.side==WHITE &&
			chess.pos.y==6 &&
			pos.x==chess.pos.x&&pos.y==4)
		{
			return true;
		}

    	return chess.type.getMoves().contains(pos);
    }
}
