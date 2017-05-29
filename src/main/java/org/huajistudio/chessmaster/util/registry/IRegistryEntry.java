package org.huajistudio.chessmaster.util.registry;

import javassist.ClassPool;
import javassist.CtField;
import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.file.ResourceLocation;
import ro.fortsoft.pf4j.PluginWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public interface IRegistryEntry {
	ResourceLocation getRegistryName();
	default void setRegistryName(ResourceLocation registryName) {
		//noinspection ThrowableNotThrown
		try {
			PluginWrapper plugin = ChessMaster.getPluginManager().whichPlugin(Class.forName(new Throwable().getStackTrace()[1].getClassName()));
			if (plugin == null) {
				if (!registryName.getDomain().equals("chessmaster"))
					ChessMaster.getLogger().warn("Attempt to register a unknown domain location {}", registryName.toString());
			}
			else if (!plugin.getPluginId().equals(registryName.getDomain()))
				ChessMaster.getLogger().warn("Attempt to use {} as domain instead of caller class {}");
		} catch (ClassNotFoundException ignored) {
			if (!registryName.getDomain().equals("chessmaster"))
				ChessMaster.getLogger().warn("Attempt to register a unknown domain location {}", registryName.toString());
		}
		try {
			Field javaField = getClass().getDeclaredField("registryName");
			javaField.setAccessible(true);
			javaField.set(this, registryName);
		} catch (NoSuchFieldException ignored) {
			try {
				CtField field = CtField.make("registryName", ClassPool.getDefault().get(getClass().getName()));
				field.setType(ClassPool.getDefault().get(ResourceLocation.class.getName()));
				field.setModifiers(Modifier.PRIVATE);
				Field javaField = getClass().getDeclaredField("registryName");
				javaField.setAccessible(true);
				javaField.set(this, registryName);
			} catch (Exception e) {
				ChessMaster.getLogger().error("Cannot make generated registryName field, please make it by yourself");
			}
		} catch (IllegalAccessException e) {
			ChessMaster.getLogger().error("Cannot set registryName field", e);
		}
	}
	default void setRegistryName(String domain, String location) {
		setRegistryName(new ResourceLocation(domain, location));
	}
	default void setRegistryName(String registryName) {
		setRegistryName(new ResourceLocation(registryName));
	}
}
