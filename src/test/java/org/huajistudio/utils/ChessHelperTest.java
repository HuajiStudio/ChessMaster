package org.huajistudio.utils;

import com.google.common.collect.Lists;
import org.huajistudio.api.Board;
import org.huajistudio.api.BoardPos;
import org.huajistudio.api.ChessObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

@RunWith(Parameterized.class)
public class ChessHelperTest {
	private ChessObject chess;
	private BoardPos pos;
	private Board board;
	private boolean expected;

	public ChessHelperTest(ChessObject chess, BoardPos pos, Board board, boolean expected) {
		this.chess = chess;
		this.pos = pos;
		this.board = board;
		this.expected = expected;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Collection<Object[]> ret = Lists.newArrayList();
		Board board = Board.start();
		ret.add(new Object[]{board.getChess(new BoardPos(0, 1)), new BoardPos(0, 3), board, true});
		return ret;
	}

	@Test
	public void canMoveTo() throws Exception {
		Assert.assertEquals(expected, ChessHelper.canMoveTo(chess, pos, board));
	}

}
