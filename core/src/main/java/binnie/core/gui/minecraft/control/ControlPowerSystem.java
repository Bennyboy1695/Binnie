package binnie.core.gui.minecraft.control;

import net.minecraft.client.util.ITooltipFlag;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.machines.power.PowerSystem;

public class ControlPowerSystem extends Control implements ITooltip {
	private final PowerSystem system;

	public ControlPowerSystem(IWidget parent, int x, int y, PowerSystem system) {
		super(parent, x, y, 16, 16);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.system = system;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.POWER_BUTTON, this.getArea());
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		tooltip.setType(Tooltip.Type.POWER);
		tooltip.add("Power Supply");
		tooltip.add("Powered by " + this.system.getUnitName());
		tooltip.setMaxWidth(200);
	}
}