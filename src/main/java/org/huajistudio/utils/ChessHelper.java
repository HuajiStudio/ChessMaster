package org.huajistudio.utils;

import org.huajistudio.api.Board;
import org.huajistudio.api.Chess;
import org.huajistudio.api.ChessObject;

class ChessHelper {
    public static boolean CanMoveTo(ChessObject chess, Board.BoardPos pos, Board board) {
    	ChessObject chessObjectAt = board.chessObjects.get(pos);
    	if (chessObjectAt != null)
    		if (chessObjectAt.side == chess.side)
    			return false;
    	if (chess.type == Chess.PAWN)
    		return true; // TODO special judge
    	return chess.type.getMoves().contains(pos);
    }
}
