package binnie.extratrees.genetics.gui.analyst;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAlleleBoolean;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.core.util.UniqueItemStackSet;
import binnie.genetics.api.analyst.AnalystConstants;
import binnie.genetics.api.analyst.IAnalystManager;

@SideOnly(Side.CLIENT)
public class AnalystPageWood extends Control implements ITitledWidget {
	public AnalystPageWood(IWidget parent, IArea area, ITree ind, IAnalystManager analystManager) {
		super(parent, area);
		setColor(6697728);
		ITreeGenome genome = ind.getGenome();
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		if (((IAlleleBoolean) ind.getGenome().getActiveAllele(EnumTreeChromosome.FIREPROOF)).getValue()) {
			new ControlIconDisplay(this, (getWidth() - 16) / 2, y, analystManager.getIcons().getIconNoFire()).addTooltip(I18N.localise(AnalystConstants.WOOD_KEY + ".fireproof"));
		} else {
			new ControlIconDisplay(this, (getWidth() - 16) / 2, y, analystManager.getIcons().getIconFire()).addTooltip(I18N.localise(AnalystConstants.WOOD_KEY + ".flammable"));
		}
		y += 30;
		Collection<ItemStack> products = new UniqueItemStackSet();

		IAlleleTreeSpecies treeSpecies = genome.getPrimary();
		ItemStack stackWood = treeSpecies.getWoodProvider().getWoodStack();
		if (!stackWood.isEmpty()) {
			products.add(stackWood);
		}
		//Why ?
		//products.addAll(ind.getGenome().getFruitProvider().getProducts().keySet());
		if (products.size() > 0) {
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.WOOD_KEY + ".logs")).setColor(getColor());
			y += 10;
			int w = products.size() * 18 - 2;
			int i = 0;
			for (ItemStack stack : products) {
				ControlItemDisplay d = new ControlItemDisplay(this, (getWidth() - w) / 2 + 18 * i, y);
				d.setTooltip();
				d.setItemStack(stack);
				i++;
			}
			y += 26;
		}
		Collection<ItemStack> allProducts = new UniqueItemStackSet();
		allProducts.addAll(products);
		Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
		refinedProducts.addAll(analystManager.getAllProductsAndFluids(allProducts));
		if (refinedProducts.size() > 0) {
			y = analystManager.drawRefined(this, I18N.localise(AnalystConstants.WOOD_KEY + ".refined"), y, refinedProducts);
			y += 8;
		}
		if (products.size() == 0) {
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.WOOD_KEY + ".noFruits")).setColor(getColor());
			y += 28;
		}
		setSize(new Point(getWidth(), y + 8));
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.WOOD_KEY + ".title");
	}
}
