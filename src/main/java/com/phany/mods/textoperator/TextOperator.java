package com.phany.mods.textoperator;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import com.phany.mods.textoperator.utils.FileIO;
import com.phany.mods.textoperator.utils.KeyHandler;
import com.phany.mods.textoperator.utils.MainMenu;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

@Mod(modid = TextOperator.MODID, version = TextOperator.VERSION)
public class TextOperator{
    public static final String MODID = "TextOperator";
    public static final String VERSION = "1.2.0";
    
    private FileIO fileIO= FileIO.getInstance();
    private boolean needLoad = true;
    
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	FMLCommonHandler.instance().bus().register(this);
    	FMLCommonHandler.instance().bus().register(new KeyHandler());
    }
    
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event){
    	if (needLoad && Minecraft.getMinecraft().ingameGUI != null) {
        	fileIO.getInstance().loadLanguageFiles();
        	needLoad = false;
		}
    }
}
