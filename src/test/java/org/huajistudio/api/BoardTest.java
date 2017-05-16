package org.huajistudio.api;

import com.google.common.collect.Lists;
import org.huajistudio.utils.ChessHelper;
import org.junit.Assert;
import org.junit.Test;

public class BoardTest {
	@Test
	public void testMoving() {
		Board board = new Board();
		ChessObject object = new ChessObject(new BoardPos(0, 0), Chess.QUEEN, ChessHelper.WHITE);
		board.putChess(object);
		board.move(object, new BoardPos(1, 1));
		Assert.assertTrue(Lists.newArrayList(board).stream().anyMatch(chess -> chess.getPos().getX() == 1 && chess.getPos().getY() == 1));
	}
}
