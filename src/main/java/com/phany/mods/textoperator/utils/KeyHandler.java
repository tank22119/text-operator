package com.phany.mods.textoperator.utils;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyHandler {
    private MainMenu menu = null;
    private KeyBinding openMenu;
    
    public KeyHandler(){
    	openMenu = new KeyBinding("打开文本菜单", Keyboard.KEY_O, "文本操作");
    	ClientRegistry.registerKeyBinding(openMenu);
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
    	if (openMenu.isPressed()) {
			if (menu == null) {
				menu = new MainMenu(Minecraft.getMinecraft().currentScreen);
			}
			if (Minecraft.getMinecraft().currentScreen instanceof GuiScreen) {
				return;
			}
			Minecraft.getMinecraft().displayGuiScreen(menu);
		}
    }
}
