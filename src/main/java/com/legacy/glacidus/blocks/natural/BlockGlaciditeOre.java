package com.legacy.glacidus.blocks.natural;

import java.util.Random;

import com.legacy.glacidus.items.ItemsGlacidus;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockGlaciditeOre extends Block implements IOre
{

	public BlockGlaciditeOre()
	{
		super(Material.ROCK);

		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.STONE);
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemsGlacidus.glacidite_fragment;
    }

}