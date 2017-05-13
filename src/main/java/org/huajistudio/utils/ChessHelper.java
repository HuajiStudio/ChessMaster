package org.huajistudio.utils;

import org.huajistudio.api.*;
import java.lang,Math;

class ChessHelper {
    public static boolean CanMoveTo(ChessObject chess, Board.BoardPos pos, Board board)
    {
        int curx = chess.pos.x;
        int cury = chess.pos.y;
        int endx = pos.x;
        int endy = pos.y;
        boolean flag = true;
        switch (chess.type)
        {
            case ChessType.KING:
                if (Math.abs(curx-endx)>1 && Math.abs(cury-endy)>1)
                {
                    flag = false;
                }
                break;
                
            case ChessType.QUEEN:
                
                
                break;
                
            case ChessType.POPE:
                
                
                break;
                
            case ChessType.KNIGHT:
                
                
                break;
                
            case ChessType.CASTLE:
                
                
                break;
                
            case ChessType.PAWN:
                
                
                break;
        }
    }
}
