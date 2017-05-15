package org.huajistudio.api;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

public class BoardTest {
	@Test
	public void testMoving() {
		Board board = new Board();
		ChessObject object = new ChessObject();
		object.pos = new BoardPos(0, 0);
		object.type = Chess.QUEEN;
		object.side = false;
		board.putChess(object);
		board.move(object, new BoardPos(1, 1));
		Assert.assertTrue(Lists.newArrayList(board).stream().anyMatch(chess -> chess.pos.getX() == 1 && chess.pos.getY() == 1));
	}
}