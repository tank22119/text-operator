package com.phany.mods.textoperator.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.LanguageHelper;
import net.minecraft.util.StringTranslate;

public final class FileIO {
	private static FileIO instance = null;
	
	private Map languageMap;
	private Properties properties;
	private Minecraft mc;
	
	private FileIO(){
	}
	
	public static FileIO getInstance() {
		if (instance == null) {
			instance = new FileIO();
		}
		instance.properties = new Properties();
		instance.mc = Minecraft.getMinecraft();
		instance.languageMap = LanguageHelper.getLanguageMap();
		instance.properties.putAll(instance.languageMap);
		return instance;
	}
	
	public void loadLanguageFiles() {// 读取汉化文件
		try {
			File cnfiles = new File(mc.mcDataDir, "cnlang");
			if (cnfiles.exists()) {
				File[] cnfilelist = cnfiles.listFiles();
				for (int i = 0; i < cnfilelist.length; i++) {
					File cnfile = cnfilelist[i];
					if (cnfile.getName().toLowerCase().contains(".lang")) {
						BufferedReader rder = new BufferedReader(
								new FileReader(cnfile));
						String tmpstr = null;
						while ((tmpstr = rder.readLine()) != null) {
							if (tmpstr.trim().isEmpty())
								continue;
							if (tmpstr.trim().charAt(0) == '#')
								continue;
							String[] arrstr = tmpstr.split("=");
							if ((languageMap.get(arrstr[0].trim()) != null)
									&& (!arrstr[1].trim().equals(null))) {
								languageMap.put(arrstr[0].trim(),
										arrstr[1].trim());
							}
						}
						mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("读取" + cnfile.getName() + "成功"));
						rder.close();
					}
				}
			} else {
				cnfiles.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveLanguageFile(String fileName) {// 输出语言文件
		try {
			File testfile = new File(mc.mcDataDir, fileName
					+ ".lang");

			FileWriter fwriter = new FileWriter(testfile, true);

			fwriter.write("##############################################\n"
					+ "# 感谢Markus \"Notch\" Persson开发的Minecraft  #\n"
					+ "# 感谢Forge团队                              #\n"
					+ "# 修改制作：Phany  特别鸣谢：推土君、风铃君  #\n"
					+ "##############################################\n\n");

			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(StringTranslate.class
							.getResourceAsStream("/assets/minecraft/lang/en_US.lang"), "UTF-8"));

			HashMap shieldString = new HashMap(1024);

			for (String s = bufferedreader.readLine(); s != null; s = bufferedreader
					.readLine()) {
				if (!s.startsWith("#")) {
					String[] as = s.split("=");

					if ((as != null) && (as.length == 2)) {
						shieldString.put(as[0], Integer.valueOf(1));
					}
				}
			}
			// 屏蔽自身按钮文本
			shieldString.put("textOperatorMainUI Key", Integer.valueOf(1));
			Enumeration enu = properties.propertyNames();

			ArrayList keyArrayList = Collections.list(enu);
			Collections.sort(keyArrayList);

			Iterator keyIterator = keyArrayList.iterator();
			while (keyIterator.hasNext()) {
				String key = (String) keyIterator.next();
				if (!shieldString.containsKey(key)) {
					fwriter.write(key + " = " + properties.getProperty(key)
							+ "\n");
				}
			}

			fwriter.close();
			mc.ingameGUI.getChatGUI().printChatMessage(
					new ChatComponentText("导出" + fileName + ".lang文件成功"));
		} catch (Exception e) {
			mc.ingameGUI.getChatGUI().printChatMessage(
					new ChatComponentText("导出" + fileName + ".lang文件失败"));
			mc.ingameGUI.getChatGUI().printChatMessage(
					new ChatComponentText("原因" + e.getMessage()));
		}
	}
}
