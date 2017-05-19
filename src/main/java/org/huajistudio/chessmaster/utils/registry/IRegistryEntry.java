package org.huajistudio.chessmaster.utils.registry;

import javassist.ClassPool;
import javassist.CtField;
import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.file.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public interface IRegistryEntry {
	ResourceLocation getRegistryName();
	default void setRegistryName(ResourceLocation registryName) {
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
}
