package net.minecraft.util;

import java.lang.reflect.Field;
import java.util.Map;

public class LanguageHelper {
	public static StringTranslate getStringTranslate() {
		return StringTranslate.getInstance();
	}
	
	public static Map getLanguageMap() {
		StringTranslate stringTranslate = LanguageHelper.getStringTranslate();
		Field fieldLanguage;
		try {
			fieldLanguage = StringTranslate.class.getDeclaredField("languageList");
			fieldLanguage.setAccessible(true);
			return (Map)fieldLanguage.get(stringTranslate);
		} catch (NoSuchFieldException e) {
			try {
				//reobf之后的languageList
				fieldLanguage = StringTranslate.class.getDeclaredField("field_74816_c");
				fieldLanguage.setAccessible(true);
				return (Map)fieldLanguage.get(stringTranslate);
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
