package org.huajistudio.chessmaster.render;

import org.huajistudio.chessmaster.api.render.Renderer;
import org.huajistudio.chessmaster.util.RenderUtil;
import org.lwjgl.bgfx.BGFXVertexDecl;

import java.nio.ByteBuffer;

public class BGFXRenderer implements Renderer {
	private BGFXVertexDecl decl;
	private ByteBuffer vertices;
	private short vbh;

	@Override
	public void render() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				decl = RenderUtil.createVertexDecl(false, true, 0);
//				vertices = MemoryUtil.memAlloc();
//				bgfx_make_ref(FloatBuffer.wrap())
//				bgfx_create_vertex_buffer()
			}
		}
	}

	@Override
	public String getName() {
		return "bgfx";
	}
}
