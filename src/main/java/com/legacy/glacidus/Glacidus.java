package com.legacy.glacidus;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.legacy.glacidus.entities.EntitiesGlacidus;
import com.legacy.glacidus.items.ItemsGlacidus;
import com.legacy.glacidus.loottables.GlacidusLootTables;
import com.legacy.glacidus.player.PlayerRegistry;
import com.legacy.glacidus.registry.RegistryEventHandler;
import com.legacy.glacidus.tiles.TileEntityRegistry;
import com.legacy.glacidus.util.ModInfo;
import com.legacy.glacidus.util.ModUtils;
import com.legacy.glacidus.world.WorldGlacidus;

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

		ModUtils.registerEvent(new RegistryEventHandler());

		proxy.preInitialization();
	}

	@EventHandler
	public void initializationEvent(FMLInitializationEvent event)
	{
		GlacidusLootTables.initialization();
		EntitiesGlacidus.initialization();
		PlayerRegistry.initialization();
		WorldGlacidus.initialization();
		TileEntityRegistry.initialization();

		ModUtils.registerEvent(new GlacidusEventHandler());
		
		FurnaceRecipes.instance().addSmelting(ItemsGlacidus.raw_porcali_meat, new ItemStack(ItemsGlacidus.grilled_porcali_meat), 0.35F);
		FurnaceRecipes.instance().addSmelting(ItemsGlacidus.grapes, new ItemStack(ItemsGlacidus.raisins), 0.35F);
		
		proxy.initialization();
	}

}