package org.huajistudio.chessmaster.robot;

import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.api.Move;
import org.huajistudio.chessmaster.file.BoardIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class ChessRobot {
	private int port = 6489;
	private Process robotProcess;
	private AsynchronousSocketChannel channel;

	public ChessRobot() throws IOException {
		robotProcess = new ProcessBuilder().command("ChessRobot").start();
		initSocket();
	}

	public ChessRobot(int port) throws IOException {
		robotProcess = new ProcessBuilder().command("ChessRobot " + port).start();
		initSocket();
	}

	private void initSocket() throws IOException {
		channel = AsynchronousSocketChannel.open();
		channel.connect(new InetSocketAddress("127.0.0.1", port), channel, new CompletionHandler<Void, AsynchronousSocketChannel>() {
			@Override
			public void completed(Void result, AsynchronousSocketChannel attachment) {
				ChessMaster.getLogger().info("Connected to chess robot at localhost:" + port);
			}

			@Override
			public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
				ChessMaster.getLogger().error("Un-able to connect chess robot", exc);
			}
		});

	}

	public void readMove(AsynchronousSocketChannel sockChannel, Consumer<Move> consumer) {
		final ByteBuffer buf = ByteBuffer.allocate(2048);

		sockChannel.read(buf, sockChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>(){
			@Override
			public void completed(Integer result, AsynchronousSocketChannel channel) {
				consumer.accept(BoardIO.GSON.fromJson(new String(buf.array(), StandardCharsets.UTF_8), Move.class));
			}

			@Override
			public void failed(Throwable exc, AsynchronousSocketChannel channel) {
				ChessMaster.getLogger().error("Un-able to read from chess robot", exc);
			}

		});

	}
	private void sendObject(AsynchronousSocketChannel sockChannel, Object object) {
		ByteBuffer buf = ByteBuffer.wrap(BoardIO.GSON.toJson(object).getBytes(StandardCharsets.UTF_8));
		buf.flip();
		sockChannel.write(buf, sockChannel, new CompletionHandler<Integer, AsynchronousSocketChannel >() {
			@Override
			public void completed(Integer result, AsynchronousSocketChannel channel ) {
				//after message written
				//NOTHING TO DO
			}

			@Override
			public void failed(Throwable exc, AsynchronousSocketChannel channel) {
				ChessMaster.getLogger().error("Un-able to send to chess robot", exc);
			}
		});
	}
}
