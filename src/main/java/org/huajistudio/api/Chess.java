package org.huajistudio.api;

import java.util.Collection;

public abstract class Chess {
	// TODO generate moves
    public static final Chess KING = new Chess() {
	    @Override
	    public Collection<Board.BoardPos> getMoves() {
		    return null;
	    }
    };
    public static final Chess QUEEN = new Chess() {
	    @Override
	    public Collection<Board.BoardPos> getMoves() {
		    return null;
	    }
    };
    public static final Chess POPE = new Chess() {
	    @Override
	    public Collection<Board.BoardPos> getMoves() {
		    return null;
	    }
    };
    public static final Chess KNIGHT = new Chess() {
	    @Override
	    public Collection<Board.BoardPos> getMoves() {
		    return null;
	    }
    };
    public static final Chess CASTLE = new Chess() {
	    @Override
	    public Collection<Board.BoardPos> getMoves() {
		    return null;
	    }
    };
    public static final Chess PAWN = new Chess() {
	    @Override
	    public Collection<Board.BoardPos> getMoves() {
		    return null;
	    }
    };

    public abstract Collection<Board.BoardPos> getMoves();
}
