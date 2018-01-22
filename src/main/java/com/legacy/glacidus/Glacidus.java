package com.legacy.glacidus;

import com.legacy.glacidus.util.ModInfo;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class Glacidus 
{

	@Instance(ModInfo.MOD_ID)
	public static Glacidus instance;

	@SidedProxy(modId = ModInfo.MOD_ID, clientSide = "com.legacy.glacidus.client.ClientProxy", serverSide = "com.legacy.glacidus.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInitializationEvent(FMLPreInitializationEvent event)
	{
		ModConfig.initialization(event.getSuggestedConfigurationFile());

		proxy.preInitialization();
	}

	@EventHandler
	public void initializationEvent(FMLInitializationEvent event)
	{
		proxy.initialization();
	}

}