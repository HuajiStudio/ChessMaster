package org.huajistudio.utils;

import org.huajistudio.api.*;
import org.huajistudio.api.math.ChessVector;

public interface ChessHelper {
	boolean BLACK = true;
	boolean WHITE = false;

	static boolean canMove(Move move) {
		return canMoveTo(move.turned, move.end, move.board);
	}

	static boolean canMoveTo(ChessObject chess, BoardPos pos, Board board) {
		ChessObject chessObjectAtDestination = board.getChess(pos);

		//Castling
		if (chess.getType() == Chess.KING) {
			if (!chess.isMoved() && chessObjectAtDestination != null) {
				if (chessObjectAtDestination.getSide() == chess.getSide() &&
					chessObjectAtDestination.getType() == Chess.CASTLE &&
					!chessObjectAtDestination.isMoved()) {
					//Check if there are things blocking the way
					int start = chess.getPos().getX();
					int end = chessObjectAtDestination.getPos().getX();
					if (start > end) {
						//Swap
						start = start + end;
						end = start - end;
						start = start - end;
					}
					start++;
					end--;
					for (int i = start; i <= end; i++) {
						BoardPos between = new BoardPos(i, chess.getPos().getY());
						if (board.getChess(between) != null) return false;
					}

					//Check if the king is threatened
					// TODO check threatened

					//Dear king : If U R reading this, then U R free to do the castling
					return true;
				} else return false;
			} else {
				return false;
			}
		}

		if (chessObjectAtDestination != null)
			if (chessObjectAtDestination.getSide() == chess.getSide())
				return false;

		int deltaX = pos.getX() - chess.getPos().getX();
		int deltaY = pos.getY() - chess.getPos().getY();

		for (ChessVector vector : chess.getType().getMovableVectors()) {
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

		if (chess.getType() == Chess.PAWN)
			if (chess.getSide() == WHITE) if (!chess.isMoved())
				return chess.getPos().move(0, 1).equals(pos) || chess.getPos().move(0, 2).equals(pos);
			else
				return chess.getPos().move(0, 1).equals(pos);
			else if (!chess.isMoved())
				return chess.getPos().move(0, -1).equals(pos) || chess.getPos().move(0, -2).equals(pos);
			else
				return chess.getPos().move(0, -1).equals(pos);

		return false;
	}

	static boolean isThreatened(ChessObject object, Board board) {
		for (ChessObject chessObject : board)
			if (chessObject.getType() != object.getType())
				if (isThreatenedBy(object, chessObject, board))
					return true;
		return false;
	}

	static boolean isThreatenedBy(ChessObject object, ChessObject enemy, Board board) {
		return canMoveTo(enemy, object.getPos(), board);
	}
}
