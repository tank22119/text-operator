package com.phany.mods.textoperator.utils;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class MainMenu extends GuiScreen{
	private GuiScreen parentScreen;

	private GuiButton returnButton;
	private GuiButton saveButton;
	private GuiButton reloadButton;
	private GuiTextField textOuputFileName;
	
	public MainMenu(GuiScreen parent){
		parentScreen = parent;
	}

	@Override
	public void initGui() { // 此方法中添加控件,此方法在类每次被显示的时候被调用
		buttonList.clear(); // 添加控件前务必清除
		buttonList.add(saveButton = new GuiButton(1, width / 2 - 70,
				height - 40, 60, 20, "保存并返回"));
		buttonList.add(returnButton = new GuiButton(2, width / 2 + 10,
				height - 40, 60, 20, "取消并返回"));
		buttonList.add(reloadButton = new GuiButton(3, width / 2 - 60,
				height - 70, 120, 20, "重新载入汉化文本"));
		// 初始化textbox=========
		textOuputFileName = new GuiTextField(fontRendererObj, width / 2 - 110,
				height / 2 - 10, 200, 20);
		textOuputFileName.setFocused(false);
		textOuputFileName.setText("test"); // 设置默认文本
		Keyboard.enableRepeatEvents(true); // 注册键盘输入事件
		// 初始化结�?==========
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) { // 此方法中绘制
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		drawCenteredString(fontRendererObj, "设置输出文本的文件名", width / 2, 20,
				0x00ffffff);
		String pathString = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
		if (pathString.endsWith("."))
			pathString = pathString.substring(0, pathString.length() - 1);
		drawCenteredString(fontRendererObj, "保存" + pathString, width / 2, 40,
				0x00ffffff);
		textOuputFileName.drawTextBox();
		drawString(fontRendererObj, ".lang", width / 2 + 95, height / 2
				- fontRendererObj.FONT_HEIGHT / 2, 0x00ffffff);
	}

	@Override
	protected void actionPerformed(GuiButton parButton) { // 此方法响应按钮点击
		switch (parButton.id) {
		case 1:
			FileIO.getInstance().saveLanguageFile(textOuputFileName.getText());
			Minecraft.getMinecraft().displayGuiScreen(parentScreen);
			break;
		case 2:
			Minecraft.getMinecraft().displayGuiScreen(parentScreen);
			break;
		case 3:
			FileIO.getInstance().loadLanguageFiles();
			break;
		default:
			break;
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		switch (par1) {
		case '\r': // 按下回车时的操作
			this.actionPerformed(saveButton);
			break;
		case '\033': // 按下ESC时的操作
			this.actionPerformed(returnButton);
			break;

		default:
			textOuputFileName.textboxKeyTyped(par1, par2);// 此方法向文本框添加文本
			break;
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		textOuputFileName.mouseClicked(par1, par2, par3);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
