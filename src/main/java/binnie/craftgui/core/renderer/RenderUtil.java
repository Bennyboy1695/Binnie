package binnie.craftgui.core.renderer;

import binnie.core.BinnieCore;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class RenderUtil {

	private RenderUtil() {

	}

	public static void drawItem(final IPoint pos, final ItemStack itemStack) {
		drawItem(pos, itemStack, false);
	}

	public static void drawItem(final IPoint pos, final ItemStack itemStack, final boolean rotating) {
		Preconditions.checkNotNull(itemStack);
		Minecraft minecraft = Minecraft.getMinecraft();
		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
		FontRenderer font = getFontRenderer(minecraft, itemStack);

		if (rotating) {
			GlStateManager.pushMatrix();
			final float phase = Minecraft.getSystemTime() / 20;
			GlStateManager.translate(8, 8, 0);
			GlStateManager.rotate(phase, 0, -0.866f, 0.5f);
			GlStateManager.translate(-8, -8, -67.1f);
		}

		minecraft.getRenderItem().renderItemAndEffectIntoGUI(null, itemStack, pos.x(), pos.y());
		minecraft.getRenderItem().renderItemOverlayIntoGUI(font, itemStack, pos.x(), pos.y(), null);

		if (rotating) {
			GlStateManager.popMatrix();
		}

		GlStateManager.disableBlend();
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
	}

	private static FontRenderer getFontRenderer(Minecraft minecraft, ItemStack ingredient) {
		FontRenderer fontRenderer = ingredient.getItem().getFontRenderer(ingredient);
		if (fontRenderer == null) {
			fontRenderer = minecraft.fontRendererObj;
		}
		return fontRenderer;
	}

	public static void drawTexture(double xCoord, double yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, double zLevel) {
		double uMin = textureSprite.getMinU();
		double uMax = textureSprite.getMaxU();
		double vMin = textureSprite.getMinV();
		double vMax = textureSprite.getMaxV();
		uMax = uMax - maskRight / 16.0 * (uMax - uMin);
		vMax = vMax - maskTop / 16.0 * (vMax - vMin);

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
		vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexBuffer.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex();
		vertexBuffer.pos(xCoord + 16 - maskRight, yCoord + 16, zLevel).tex(uMax, vMax).endVertex();
		vertexBuffer.pos(xCoord + 16 - maskRight, yCoord + maskTop, zLevel).tex(uMax, vMin).endVertex();
		vertexBuffer.pos(xCoord, yCoord + maskTop, zLevel).tex(uMin, vMin).endVertex();
		tessellator.draw();
	}

	public static void setColour(final int hexColour) {
		int a = (hexColour & 0xFF000000) >> 24;
		final int r = (hexColour & 0xFF0000) >> 16;
		final int g = (hexColour & 0xFF00) >> 8;
		final int b = hexColour & 0xFF;
		if (a < 0) {
			a += 256;
		}
		if (a > 0 && a != 255) {
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
		} else {
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f);
		}
	}

	public static int getTextWidth(final String text) {
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		return fontRendererObj.getStringWidth(text);
	}

	public static int getTextHeight() {
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		return (fontRendererObj == null) ? 0 : fontRendererObj.FONT_HEIGHT;
	}

	public static void drawText(final IPoint pos, final String text, final int colour) {
		drawText(new IArea(pos, new IPoint(500, 500)), TextJustification.TopLeft, text, colour);
	}

	public static void drawText(final IArea area, final TextJustification justification, final String text, final int colour) {
		final IPoint pos = area.pos();
		if (area.size().x() <= 0.0f) {
			return;
		}
		FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
		final List<String> wrappedStrings = fontRendererObj.listFormattedStringToWidth(text, area.size().x());
		final float totalHeight = wrappedStrings.size() * getTextHeight();
		float posY = area.pos().y();
		if (area.size().y() > totalHeight) {
			posY += (area.size().y() - totalHeight) * justification.getYOffset();
		}
		for (final String string : wrappedStrings) {
			final float stringWidth = getTextWidth(string);
			float posX = area.size().x() - stringWidth;
			posX *= justification.getXOffset();
			GlStateManager.disableDepth();
			fontRendererObj.drawString(string, (int) (pos.x() + posX), (int) posY, colour);
			posY += getTextHeight();
		}
		GlStateManager.color(1.0f, 1.0f, 1.0f);
	}

	public static void drawSolidRect(float left, float top, float right, float bottom, final int color) {
		GuiUtils.drawGradientRect(0, (int) left, (int) top, (int) right, (int) bottom, color, color);
	}

	public static void drawSolidRect(final IArea area, final int colour) {
		drawSolidRect(area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), 0xFF000000 | colour);
	}

	public static void drawSolidRectWithAlpha(final IArea area, final int color) {
		drawSolidRect(area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), color);
	}

	public static void drawGradientRect(final IArea area, final int startColor, final int endColor) {
		GuiUtils.drawGradientRect(0, area.pos().x(), area.pos().y(), area.pos().x() + area.size().x(), area.pos().y() + area.size().y(), startColor, endColor);
	}

	public static void drawSprite(final IPoint pos, final TextureAtlasSprite icon) {
		if (icon != null) {
			BinnieCore.getBinnieProxy().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			drawTexture(pos.x(), pos.y(), icon, 0, 0, 0);
		}
	}

	public static void drawFluid(IPoint pos, @Nullable FluidStack fluid) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		Minecraft minecraft = Minecraft.getMinecraft();
		if (fluid != null) {
			Fluid fluid1 = fluid.getFluid();
			if (fluid1 != null) {
				TextureAtlasSprite fluidStillSprite = getStillFluidSprite(minecraft, fluid1);

				int fluidColor = fluid1.getColor(fluid);

				minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				setColour(fluidColor);
				drawTexture(pos.x(), pos.y(), fluidStillSprite, 0, 0, 100);
			}
		}

		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
	}

	private static TextureAtlasSprite getStillFluidSprite(Minecraft minecraft, Fluid fluid) {
		TextureMap textureMapBlocks = minecraft.getTextureMapBlocks();
		ResourceLocation fluidStill = fluid.getStill();
		TextureAtlasSprite fluidStillSprite = null;
		if (fluidStill != null) {
			fluidStillSprite = textureMapBlocks.getTextureExtry(fluidStill.toString());
		}
		if (fluidStillSprite == null) {
			fluidStillSprite = textureMapBlocks.getMissingSprite();
		}
		return fluidStillSprite;
	}
}
