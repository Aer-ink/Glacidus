package com.legacy.glacidus.client.renders;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.legacy.glacidus.util.ModInfo;

public class ItemRenders
{

	@SubscribeEvent
	public void onModelRegisterEvent(ModelRegistryEvent event)
	{
		
	}

	private static void register(Item item, String model)
	{
		register(item, 0, model);
	}

	private static void register(Item item, int meta, String model)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.MOD_ID + ":" + model, "inventory"));
	}

	private static void registerVariants(Item item, ResourceLocation... model)
	{
		ModelLoader.registerItemVariants(item, model);
	}

}