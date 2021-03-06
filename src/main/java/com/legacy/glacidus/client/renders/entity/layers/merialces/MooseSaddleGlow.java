package com.legacy.glacidus.client.renders.entity.layers.merialces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import com.legacy.glacidus.client.models.entity.ModelMoose;
import com.legacy.glacidus.client.renders.entity.MerialcesRenderer;
import com.legacy.glacidus.entities.passive.EntityMerialces;

public class MooseSaddleGlow implements LayerRenderer<EntityMerialces>
{

	private static final ResourceLocation SADDLE_GLOW = new ResourceLocation("glacidus", "textures/entity/merialces/saddle/moose_saddle_glow.png");

	private final MerialcesRenderer renderer;
	private final ModelMoose model = new ModelMoose(0.51F);

	public MooseSaddleGlow(MerialcesRenderer moose)
	{
		super();
		this.renderer = moose;
	}

	@Override
	public void doRenderLayer(EntityMerialces entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (entity.getSaddled())
        {
			RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();	
			renderManager.renderEngine.bindTexture(SADDLE_GLOW);

		    GlStateManager.enableBlend();
		    GlStateManager.disableAlpha();
		    GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		    GlStateManager.depthMask(true);
		    int i = 61680;
		    int j = i % 65536;
		    int k = i / 65536;
		    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		    Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
		    this.model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
	        this.model.setModelAttributes(this.renderer.getMainModel());
	        this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
		    i = entity.getBrightnessForRender();
		    j = i % 65536;
		    k = i / 65536;
		    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		    GlStateManager.disableBlend();
		    GlStateManager.enableAlpha();
	    }
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return false;
	}

}