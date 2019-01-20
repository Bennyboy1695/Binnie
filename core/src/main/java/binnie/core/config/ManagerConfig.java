package binnie.core.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;
import binnie.core.util.Log;

public class ManagerConfig extends ManagerBase {
	private final Map<Class<?>, Configuration> configurations;

	public ManagerConfig() {
		this.configurations = new LinkedHashMap<>();
	}

	public void registerConfiguration(Class<?> cls, AbstractMod mod) {
		if (cls.isAnnotationPresent(ConfigFile.class)) {
			this.loadConfiguration(cls, mod);
		}
	}

	public void loadConfiguration(Class<?> cls, AbstractMod mod) {
		String filename = cls.getAnnotation(ConfigFile.class).filename();
		BinnieConfiguration config = new BinnieConfiguration(filename, mod);
		config.load();
		for (Field field : cls.getFields()) {
			if (field.isAnnotationPresent(ConfigProperty.class)) {
				ConfigProperty propertyAnnot = field.getAnnotation(ConfigProperty.class);
				for (Annotation annotation : field.getAnnotations()) {
					if (annotation.annotationType().isAnnotationPresent(ConfigProperty.Type.class)) {
						Class<?> propertyClass = annotation.annotationType().getAnnotation(ConfigProperty.Type.class).propertyClass();
						try {
							Constructor<?> constructor = propertyClass.getConstructor(Field.class, BinnieConfiguration.class, ConfigProperty.class, annotation.annotationType());
							Annotation cast = annotation.annotationType().cast(annotation);
							PropertyBase<?, ?> property = (PropertyBase<?, ?>) constructor.newInstance(field, config, propertyAnnot, cast);
						} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
							Log.error("Failed to load configuration for property {}", propertyClass, e);
						}
					}
				}
			}
		}
		config.save();
		this.configurations.put(cls, config);
	}
}