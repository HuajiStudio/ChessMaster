package org.huajistudio.chessmaster.client.render.opengl;

import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.file.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import ro.fortsoft.pf4j.PluginWrapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public interface OpenGLUtils {
	static int bindTexture(ResourceLocation location) {
		ChessMaster.getLogger().info("Binding texture " + location);
		int textureId = glGenTextures();
		URL imageURL = null;
		if (location.getDomain().equals("chessmaster"))
			imageURL = ChessMaster.class.getResource("assets/chessmaster/textures/" + location.getLocation());
		else {
			PluginWrapper wrapper = ChessMaster.getPluginManager().getPlugin(location.getDomain());
			if (wrapper == null)
				ChessMaster.getLogger().error("No such plugin called " + location.getDomain(), new NullPointerException());
			else
				imageURL = wrapper.getPluginClassLoader().getResource("assets/" + location.getDomain() + "/textures/" + location.getLocation());
		}
		try {
			//noinspection ConstantConditions
			BufferedImage image = ImageIO.read(imageURL);
			ChessMaster.getLogger().info("Readed image " + image);

			int[] pixels = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

			for(int y = 0; y < image.getHeight(); y++){
				for(int x = 0; x < image.getWidth(); x++){
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
					buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
					buffer.put((byte) (pixel & 0xFF));               // Blue component
					buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
				}
			}

			buffer.flip();

			ChessMaster.getLogger().info("Create buffer of " + imageURL + " : " + buffer);

			GL13.glActiveTexture(textureId);

			glBindTexture(GL_TEXTURE_2D, textureId);

			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

			//Setup wrap mode
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

			//Setup texture scaling filtering
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

			GL30.glGenerateMipmap(GL_TEXTURE_2D);
		} catch (Exception e) {
			ChessMaster.getLogger().error("Cannot bind texture " + location, e);
		}
		OpenGLRenderer.getResourceTextureIdMap().put(location, textureId);
		return textureId;
	}
}
