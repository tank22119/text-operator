package com.phany.mods.textoperator.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassInfo {
	public static List<String> getClassMethods(String className){
		List<String> result = new ArrayList<String>();
		try {
			Class<?> c = Class.forName(className);
			Method[] m = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				result.add(m[i].getReturnType().getName() + " " + m[i].getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<String> getClassFields(String className) {
		List<String> result = new ArrayList<String>();
		try {
			Class<?> c = Class.forName(className);
			Field[] f = c.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				result.add(f[i].getType().getName() + " " + f[i].getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}
