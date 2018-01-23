package com.legacy.glacidus.world;

import java.util.List;
import java.util.Random;

import com.legacy.glacidus.blocks.BlocksGlacidus;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class ChunkGeneratorGlacidus implements IChunkGenerator
{

    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    protected static final IBlockState LAVA = Blocks.LAVA.getDefaultState();
    protected static final IBlockState GRAVEL = Blocks.GRAVEL.getDefaultState();

    private double[] gravelNoise = new double[256];
    private double[] depthBuffer = new double[256];
    private double[] buffer;

    private NoiseGeneratorOctaves lperlinNoise1;
    private NoiseGeneratorOctaves lperlinNoise2;
    private NoiseGeneratorOctaves perlinNoise1;

    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves scaleNoise;
    public NoiseGeneratorOctaves depthNoise;

    double[] pnr;
    double[] ar;
    double[] br;
    double[] noiseData4;
    double[] dr;

	private World world;

	private Random random;

    public ChunkGeneratorGlacidus(World worldIn, long seed)
    {
    	this.world = worldIn;
    	this.random = new Random(seed);
        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.random, 16);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.random, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.random, 8);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.random, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.random, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.random, 16);
        worldIn.setSeaLevel(63);
    }

    public void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer)
    {
        int j = this.world.getSeaLevel() / 2 + 1;
        this.buffer = this.getHeights(this.buffer, p_185936_1_ * 4, 0, p_185936_2_ * 4, 5, 17, 5);

        for (int j1 = 0; j1 < 4; ++j1)
        {
            for (int k1 = 0; k1 < 4; ++k1)
            {
                for (int l1 = 0; l1 < 16; ++l1)
                {
                    double d1 = this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 0];
                    double d2 = this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 0];
                    double d3 = this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 0];
                    double d4 = this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 0];
                    double d5 = (this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 1] - d1) * 0.125D;
                    double d6 = (this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 1] - d2) * 0.125D;
                    double d7 = (this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 1] - d3) * 0.125D;
                    double d8 = (this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 1] - d4) * 0.125D;

                    for (int i2 = 0; i2 < 8; ++i2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for (int j2 = 0; j2 < 4; ++j2)
                        {
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                IBlockState iblockstate = null;

                                if (l1 * 8 + i2 < j)
                                {
                                    iblockstate = BlocksGlacidus.antinatric_stone.getDefaultState();
                                }

                                if (d15 > 0.0D)
                                {
                                    iblockstate = BlocksGlacidus.antinatric_stone.getDefaultState();
                                }

                                int l2 = j2 + j1 * 4;
                                int i3 = i2 + l1 * 8;
                                int j3 = k2 + k1 * 4;

                                if (i3 < 70)
                                {
                                    iblockstate = BlocksGlacidus.antinatric_stone.getDefaultState();
                                }

                                primer.setBlockState(l2, i3, j3, iblockstate);

                                if (i3 <= 50 && i3 >= 20)
                                {
                                	if (iblockstate == LAVA)
                                	{
                                		iblockstate = BlocksGlacidus.antinatric_stone.getDefaultState();
                                	}

                                	primer.setBlockState(l2, i3 + 105, j3, iblockstate);
                                }

                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void buildSurfaces(int p_185937_1_, int p_185937_2_, ChunkPrimer primer)
    {
        if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, p_185937_1_, p_185937_2_, primer, this.world)) return;
        int i = this.world.getSeaLevel() + 1;
        double d0 = 0.03125D;

        this.depthBuffer = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.depthBuffer, p_185937_1_ * 16, p_185937_2_ * 16, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);

        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                boolean flag1 = this.gravelNoise[j + k * 16] + this.random.nextDouble() * 0.2D > 0.0D;
                int l = (int)(this.depthBuffer[j + k * 16] / 3.0D + 3.0D + this.random.nextDouble() * 0.25D);
                int i1 = -1;
                int data = (int)(3.0D + this.random.nextDouble() * 0.25D);

                IBlockState iblockstate = BlocksGlacidus.lumicia_grass.getDefaultState();
                IBlockState iblockstate1 = BlocksGlacidus.lumicia_dirt.getDefaultState();

                for (int j1 = 127; j1 >= 0; --j1)
                {
                    if (j1 < 127 - this.random.nextInt(5) && j1 > this.random.nextInt(5))
                    {
                        IBlockState iblockstate2 = primer.getBlockState(k, j1, j);

                        if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR)
                        {
                            if (iblockstate2.getBlock() == BlocksGlacidus.antinatric_stone)
                            {
        						if (i1 == -1)
        						{
        							if (data <= 0)
        							{
        								iblockstate = Blocks.AIR.getDefaultState();
        								iblockstate1 = BlocksGlacidus.antinatric_stone.getDefaultState();
        							}

        							i1 = data;

        							if (j1 >= 0)
        							{
        								primer.setBlockState(k, j1, j, iblockstate);
        							}
        							else
        							{
        								primer.setBlockState(k, j1, j, iblockstate1);
        							}
        						}
        						else if (i1 > 0)
        						{
        							--i1;
        							primer.setBlockState(k, j1, j, iblockstate1);
        						}
        					}
                        }
                        else
                        {
                            i1 = -1;
                        }
                    }
                    else if (j1 == 0)
                    {
                        primer.setBlockState(k, j1, j, BEDROCK);
                    }
                }
            }
        }
    }

	@Override
	public Chunk generateChunk(int x, int z)
	{
        this.random.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();

        this.prepareHeights(x, z, chunkprimer);
        this.buildSurfaces(x, z, chunkprimer);

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);

        chunk.generateSkylightMap();

        return chunk;
	}

    private double[] getHeights(double[] p_185938_1_, int p_185938_2_, int p_185938_3_, int p_185938_4_, int p_185938_5_, int p_185938_6_, int p_185938_7_)
    {
        if (p_185938_1_ == null)
        {
            p_185938_1_ = new double[p_185938_5_ * p_185938_6_ * p_185938_7_];
        }

        net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, p_185938_1_, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getNoisefield();

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.scaleNoise.generateNoiseOctaves(this.noiseData4, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, 1, p_185938_7_, 1.0D, 0.0D, 1.0D);
        this.dr = this.depthNoise.generateNoiseOctaves(this.dr, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, 1, p_185938_7_, 100.0D, 0.0D, 100.0D);
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, 684.412D, 2053.236D, 684.412D);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, 684.412D, 2053.236D, 684.412D);
        int i = 0;
        double[] adouble = new double[p_185938_6_];

        for (int j = 0; j < p_185938_6_; ++j)
        {
            adouble[j] = Math.cos((double)j * Math.PI * 6.0D / (double)p_185938_6_) * 2.0D;
            double d2 = (double)j;

            if (j > p_185938_6_ / 2)
            {
                d2 = (double)(p_185938_6_ - 1 - j);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int l = 0; l < p_185938_5_; ++l)
        {
            for (int i1 = 0; i1 < p_185938_7_; ++i1)
            {
                double d3 = 0.0D;

                for (int k = 0; k < p_185938_6_; ++k)
                {
                    double d4 = adouble[k];
                    double d5 = this.ar[i] / 512.0D;
                    double d6 = this.br[i] / 512.0D;
                    double d7 = (this.pnr[i] / 10.0D + 1.0D) / 2.0D;
                    double d8;

                    if (d7 < 0.0D)
                    {
                        d8 = d5;
                    }
                    else if (d7 > 1.0D)
                    {
                        d8 = d6;
                    }
                    else
                    {
                        d8 = d5 + (d6 - d5) * d7;
                    }

                    d8 = d8 - d4;

                    if (k > p_185938_6_ - 4)
                    {
                        double d9 = (double)((float)(k - (p_185938_6_ - 4)) / 3.0F);
                        d8 = d8 * (1.0D - d9) + -10.0D * d9;
                    }

                    if ((double)k < 0.0D)
                    {
                        double d10 = (0.0D - (double)k) / 4.0D;
                        d10 = MathHelper.clamp(d10, 0.0D, 1.0D);
                        d8 = d8 * (1.0D - d10) + -10.0D * d10;
                    }

                    p_185938_1_[i] = d8;
                    ++i;
                }
            }
        }

        return p_185938_1_;
    }

	@Override
	public void populate(int x, int z)
	{
        BlockFalling.fallInstantly = true;

        int i = x * 16;
        int j = z * 16;

        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));

        this.random.setSeed(this.world.getSeed());
        long k = this.random.nextLong() / 2L * 2L + 1L;
        long l = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, x, z, false);

        biome.decorate(this.world, this.random, new BlockPos(i, 0, j));

        if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.random, x, z, false, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
        {
            WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.random);
        }

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.random, x, z, false);

        BlockFalling.fallInstantly = false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) 
	{
		Biome biome = this.world.getBiome(pos);

		if (biome != null)
		{
			return biome.getSpawnableList(creatureType);
		}

		return null;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) 
	{
		return false;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
	{
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z)
	{

	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) 
	{
		return false;
	}

}