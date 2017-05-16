package org.huajistudio.utils;

import org.huajistudio.api.*;
import org.huajistudio.api.math.ChessVector;
import java.util.Iterator;

public interface ChessHelper {
	boolean BLACK = true;
	boolean WHITE = false;

	static boolean canMove(Move move) {
		return canMoveTo(move.turned, move.end, move.board);
	}

    static boolean canMoveTo(ChessObject chess, BoardPos pos, Board board) {
    	ChessObject chessObjectAtDestination = board.getChess(pos);
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

                        //Castling
            if (chess.getType() == Chess.KING)
            {
                if (chess.isMoved() == false)
                {
                    if (chessObjectAtDestination.getSide() == chess.getSide() && 
                        chessObjectAtDestination.getType() == Chess.CASTLE && 
                        chessObjectAtDestination.isMoved() == false)
                    {
                        //Check if there are things blocking the way
                        int start = chess.getPos().getX();
                        int end = chessObjectAtDestination.getPos().getX();
                        if (start > end)
                        {
                            //Swap
                            start = start + end;
                            end = start - end;
                            start = start - end;
                        }
                        start++;
                        end--;
                        for(int i = start; i <= end; i++)
                        {
                            BoardPos between = new BoardPos(i,chess.getPos().getY());
                            if(board.getChess(between) != null)
                            {
                                return false;
                            }
                        }
                        
                        //Check if the king is threatened
                        Iterator chessObjectsOnBoard = board.iterator();
                        while (chessObjectsOnBoard.hasNext())
                        {
                            ChessObject aChessObjectOnBoard = (ChessObject) chessObjectsOnBoard.next();
                            if (aChessObjectOnBoard.getSide() == chess.getSide())
                            {
                                continue;
                            }
                            if (threatenBy(chess,aChessObjectOnBoard,board) == true)
                            {
                                return false;
                            }
                        }
                        
                        //Dear king : If U R reading this, then U R free to do the castling
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }

		return false;
    }
    
        static boolean threatenBy(ChessObject chessOne, ChessObject chessTwo, Board board)
    {
        BoardPos posOne = chessOne.getPos();
        BoardPos posTwo = chessTwo.getPos();
        if (chessTwo.getType() == Chess.CASTLE)
        {
            if (posOne.getX() == posTwo.getX())
            {
                int start = posOne.getY();
                int end = posTwo.getY();
                if (start == end - 1 || start == end + 1)
                {
                    return true;
                }
                else if (start < end)
                {
                    start++;
                    end--;
                }
                else if (start > end)
                {
                    //Swap the two elements
                    start = start + end;
                    end = start - end;
                    start = start - end;
                    start++;
                    end--;
                }
                for(int i = start; i <= end; i++)
                {
                    BoardPos pos = new BoardPos(posOne.getX(),i);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else if(posOne.getY() == posTwo.getY())
            {
                int start = posOne.getX();
                int end = posTwo.getX();
                if (start == end - 1 || start == end + 1)
                {
                    return true;
                }
                else if (start < end)
                {
                    start++;
                    end--;
                }
                else if (start > end)
                {
                    //Swap the two elements
                    start = start + end;
                    end = start - end;
                    start = start - end;
                    start++;
                    end--;
                }
                for(int i = start; i <= end; i++)
                {
                    BoardPos pos = new BoardPos(posOne.getX(),i);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
            return true;//If the brave chessOne survives the bombardment of this code, then he is worthy to achieve the truth
        }
        else if (chessTwo.getType() == Chess.KING)
        {
            return Math.abs(posOne.getX()-posTwo.getX()) <= 1 && Math.abs(posOne.getY()-posTwo.getY()) <= 1;
        }
        else if (chessTwo.getType() == Chess.KNIGHT)
        {
            return (Math.abs(posOne.getX()-posTwo.getX()) == 2 && Math.abs(posOne.getY() - posTwo.getY()) == 1) || 
                    (Math.abs(posOne.getX()-posTwo.getX()) == 1 && Math.abs(posOne.getY() - posTwo.getY()) == 2);
        }
        else if (chessTwo.getType() == Chess.PAWN)
        {
            if (chessTwo.getSide() == BLACK)
            {
                return chessTwo.getPos().move(1, -1).equals(posOne) || chessTwo.getPos().move(-1, -1).equals(posOne);
            }
            else
            {
                return chessTwo.getPos().move(1, 1).equals(posOne) || chessTwo.getPos().move(-1, 1).equals(posOne);
            }
        }
        else if (chessTwo.getType() == Chess.POPE)
        {
            if (posOne.getX() + posOne.getY() == posTwo.getX() + posTwo.getY())
            {
                int diagonal = posOne.getX() + posOne.getY();
                int startX = posOne.getX();
                int endX = posTwo.getX();
                if (startX == endX - 1 || startX == endX + 1)
                {
                    return true;
                }
                else if (startX < endX)
                {
                    startX++;
                    endX--;
                }
                else if (startX > endX)
                {
                    //Swap the two elements
                    startX = startX + endX;
                    endX = startX - endX;
                    startX = startX - endX;
                    startX++;
                    endX--;
                }
                for(int i = startX; i <= endX; i++)
                {
                    BoardPos pos = new BoardPos(i,diagonal - i);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else if(posOne.getX() - posOne.getY() == posTwo.getX() - posTwo.getY())
            {
                int diagonal = posOne.getX() - posOne.getY();
                int startX = posOne.getX();
                int endX = posTwo.getX();
                if (startX == endX - 1 || startX == endX + 1)
                {
                    return true;
                }
                else if (startX < endX)
                {
                    startX++;
                    endX--;
                }
                else if (startX > endX)
                {
                    //Swap the two elements
                    startX = startX + endX;
                    endX = startX - endX;
                    startX = startX - endX;
                    startX++;
                    endX--;
                }
                for(int i = startX; i <= endX; i++)
                {
                    BoardPos pos = new BoardPos(i,i - diagonal);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
            return true;//If the brave chessOne survives the bombardment of this code, then he is worthy to achieve the truth
        }
        else if (chessTwo.getType() == Chess.QUEEN)
        {
            //Ready? Go!
            if (posOne.getX() == posTwo.getX())
            {
                int start = posOne.getY();
                int end = posTwo.getY();
                if (start == end - 1 || start == end + 1)
                {
                    return true;
                }
                else if (start < end)
                {
                    start++;
                    end--;
                }
                else if (start > end)
                {
                    //Swap the two elements
                    start = start + end;
                    end = start - end;
                    start = start - end;
                    start++;
                    end--;
                }
                for(int i = start; i <= end; i++)
                {
                    BoardPos pos = new BoardPos(posOne.getX(),i);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else if(posOne.getY() == posTwo.getY())
            {
                int start = posOne.getX();
                int end = posTwo.getX();
                if (start == end - 1 || start == end + 1)
                {
                    return true;
                }
                else if (start < end)
                {
                    start++;
                    end--;
                }
                else if (start > end)
                {
                    //Swap the two elements
                    start = start + end;
                    end = start - end;
                    start = start - end;
                    start++;
                    end--;
                }
                for(int i = start; i <= end; i++)
                {
                    BoardPos pos = new BoardPos(posOne.getX(),i);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else if (posOne.getX() + posOne.getY() == posTwo.getX() + posTwo.getY())
            {
                int diagonal = posOne.getX() + posOne.getY();
                int startX = posOne.getX();
                int endX = posTwo.getX();
                if (startX == endX - 1 || startX == endX + 1)
                {
                    return true;
                }
                else if (startX < endX)
                {
                    startX++;
                    endX--;
                }
                else if (startX > endX)
                {
                    //Swap the two elements
                    startX = startX + endX;
                    endX = startX - endX;
                    startX = startX - endX;
                    startX++;
                    endX--;
                }
                for(int i = startX; i <= endX; i++)
                {
                    BoardPos pos = new BoardPos(i,diagonal - i);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else if(posOne.getX() - posOne.getY() == posTwo.getX() - posTwo.getY())
            {
                int diagonal = posOne.getX() - posOne.getY();
                int startX = posOne.getX();
                int endX = posTwo.getX();
                if (startX == endX - 1 || startX == endX + 1)
                {
                    return true;
                }
                else if (startX < endX)
                {
                    startX++;
                    endX--;
                }
                else if (startX > endX)
                {
                    //Swap the two elements
                    startX = startX + endX;
                    endX = startX - endX;
                    startX = startX - endX;
                    startX++;
                    endX--;
                }
                for(int i = startX; i <= endX; i++)
                {
                    BoardPos pos = new BoardPos(i,i - diagonal);
                    if(board.getChess(pos) != null)
                    {
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
            return true;//LOL
        }
        return true;
    }
}
