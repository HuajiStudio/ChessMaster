package org.huajistudio.chessmaster.util;

import org.huajistudio.chessmaster.ChessMaster;
import org.lwjgl.bgfx.BGFXMemory;
import org.lwjgl.bgfx.BGFXVertexDecl;

import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple4f;
import java.nio.ByteBuffer;

import static org.lwjgl.bgfx.BGFX.*;

public interface RenderUtil {
	static float[] toFloatArray(Tuple2f tuple) {
		return new float[]{tuple.getX(), tuple.getY()};
	}

	static float[] toFloatArray(Tuple3f tuple) {
		return new float[]{tuple.getX(), tuple.getY(), tuple.getZ()};
	}

	static float[] toFloatArray(Tuple4f tuple4f) {
		return new float[]{tuple4f.getX(), tuple4f.getY(), tuple4f.getZ(), tuple4f.getW()};
	}

	static BGFXVertexDecl createVertexDecl(boolean withNormals, boolean withColor, int numUVs) {

		BGFXVertexDecl decl = BGFXVertexDecl.calloc();

		bgfx_vertex_decl_begin(decl, ChessMaster.getRenderManager().getRenderer());

		bgfx_vertex_decl_add(decl,
			BGFX_ATTRIB_POSITION,
			3,
			BGFX_ATTRIB_TYPE_FLOAT,
			false,
			false);

		if (withNormals) {
			bgfx_vertex_decl_add(decl,
				BGFX_ATTRIB_NORMAL,
				3,
				BGFX_ATTRIB_TYPE_FLOAT,
				false,
				false);
		}

		if (withColor) {
			bgfx_vertex_decl_add(decl,
				BGFX_ATTRIB_COLOR0,
				4,
				BGFX_ATTRIB_TYPE_UINT8,
				true,
				false);
		}

		if (numUVs > 0) {
			bgfx_vertex_decl_add(decl,
				BGFX_ATTRIB_TEXCOORD0,
				2,
				BGFX_ATTRIB_TYPE_FLOAT,
				false,
				false);
		}

		bgfx_vertex_decl_end(decl);

		return decl;
	}

	static short createVertexBuffer(ByteBuffer buffer, BGFXVertexDecl decl, Object[]... vertices) {
		for (Object[] objects : vertices) {
			for (Object object : objects) {
				if (object instanceof Tuple2f)
					buffer.putFloat(((Tuple2f) object).getX()).putFloat(((Tuple2f) object).getY());
				else if (object instanceof Tuple3f)
					buffer.putFloat(((Tuple3f) object).getX()).putFloat(((Tuple3f) object).getY()).putFloat(((Tuple3f) object).getZ());
				else if (object instanceof Tuple4f)
					buffer.putFloat(((Tuple4f) object).getX()).putFloat(((Tuple4f) object).getY()).putFloat(((Tuple4f) object).getZ()).putFloat(((Tuple4f) object).getW());
				else if (object instanceof Integer)
					buffer.putInt(((int) object));
				else
					throw new IllegalArgumentException("Unknown type of vertex: " + object.getClass().getName() + ", valid type are:" +
						"javax.vecmath.Tuple2f/3f/4f & java.lang.Integer");
			}
		}

		if (buffer.remaining() != 0) {
			throw new RuntimeException("ByteBuffer size and number of arguments do not match");
		}

		buffer.flip();

		return createVertexBuffer(buffer, decl);
	}

	static short createVertexBuffer(ByteBuffer buffer, BGFXVertexDecl decl) {

		BGFXMemory vbhMem = bgfx_make_ref(buffer);

		return bgfx_create_vertex_buffer(vbhMem, decl, BGFX_BUFFER_NONE);
	}

	static short createIndexBuffer(ByteBuffer buffer, int[] indices) {

		for (int idx : indices) {
			buffer.putShort((short) idx);
		}

		if (buffer.remaining() != 0) {
			throw new RuntimeException("ByteBuffer size and number of arguments do not match");
		}

		buffer.flip();

		BGFXMemory ibhMem = bgfx_make_ref(buffer);

		return bgfx_create_index_buffer(ibhMem, BGFX_BUFFER_NONE);
	}
}
