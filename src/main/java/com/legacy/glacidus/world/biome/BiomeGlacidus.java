package com.legacy.glacidus.world.biome;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.google.common.collect.Lists;
import com.legacy.glacidus.util.ModInfo;
import com.legacy.glacidus.world.features.WorldGenUndergroundTree;

public class BiomeGlacidus extends Biome
{

	public BiomeGlacidus()
	{
		super(new BiomeProperties("glacidus").setRainDisabled().setWaterColor(0xE0FFFF));

		this.setRegistryName(ModInfo.locate("glacidus"));
	}

	@Override
    public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
    {
        if (!this.modSpawnableLists.containsKey(creatureType)) this.modSpawnableLists.put(creatureType, Lists.<Biome.SpawnListEntry>newArrayList());

        return this.modSpawnableLists.get(creatureType);
    }
 
	@Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return new WorldGenUndergroundTree();
    }

	@Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return new WorldGenCoreTallGrass();
    }

	@Override
    public BiomeDecorator createBiomeDecorator()
    {
        return new BiomeGlacidusDecorator();
    }

    @Override
    public Class <? extends Biome > getBiomeClass()
    {
    	return BiomeGlacidus.class;
    }

}