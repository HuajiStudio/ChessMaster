package org.huajistudio.utils;

import org.huajistudio.api.Board;
import org.huajistudio.api.BoardPos;
import org.huajistudio.api.ChessObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.Collections;

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

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Collections.emptyList();
	}

	@Test
	public void canMoveTo() throws Exception {
		Assert.assertEquals(expected, ChessHelper.canMoveTo(chess, pos, board));
	}

}
