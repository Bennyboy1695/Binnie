package binnie.genetics.item;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.genetics.IGene;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneArrayItem;
import binnie.genetics.genetics.IGeneItem;
import binnie.genetics.modules.features.GeneticItems;

public class ItemSerumArray extends ItemGene implements IItemSerum {
	public ItemSerumArray() {
		super("serum_array");
		this.setMaxDamage(16);
	}

	public static ItemStack create(IGene gene) {
		ItemStack item = GeneticItems.SERUM_ARRAY.stack();
		item.setItemDamage(item.getMaxDamage());
		GeneArrayItem seq = new GeneArrayItem(gene);
		seq.writeToItem(item);
		return item;
	}

	@Override
	public int getCharges(ItemStack stack) {
		return stack.getMaxDamage() - stack.getItemDamage();
	}

	@Override
	public int getMaxCharges(ItemStack stack) {
		return stack.getMaxDamage();
	}

	@Override
	public IGene[] getGenes(ItemStack stack) {
		GeneArrayItem geneItem = this.getGeneItem(stack);
		if (geneItem != null) {
			return geneItem.getGenes().toArray(new IGene[0]);
		}
		return new IGene[0];
	}

	@Override
	@Nullable
	public ISpeciesRoot getSpeciesRoot(ItemStack stack) {
		GeneArrayItem geneItem = this.getGeneItem(stack);
		if (geneItem != null) {
			return geneItem.getSpeciesRoot();
		}
		return null;
	}

	@Override
	@Nullable
	public IGene getGene(ItemStack stack, int chromosome) {
		GeneArrayItem geneItem = this.getGeneItem(stack);
		if (geneItem != null) {
			return geneItem.getGene(chromosome);
		} else {
			return null;
		}
	}

	@Override
	public GeneArrayItem getGeneItem(ItemStack itemStack) {
		return new GeneArrayItem(itemStack);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
				for (IIndividual template : root.getIndividualTemplates()) {
					if (template.getGenome().getPrimary().isSecret()) {
						continue;
					}
					IGeneItem geneItem = new GeneArrayItem();
					for (IChromosomeType type : root.getKaryotype()) {
						IChromosome chromosome = template.getGenome().getChromosomes()[type.ordinal()];
						if (chromosome != null) {
							IAllele active = chromosome.getActiveAllele();
							geneItem.addGene(new Gene(active, type, root));
						}
					}
					ItemStack array = new ItemStack(this);
					geneItem.writeToItem(array);
					items.add(array);
				}
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		GeneArrayItem geneItem = getGeneItem(itemstack);
		if (geneItem != null) {
			ISpeciesRoot speciesRoot = geneItem.getSpeciesRoot();
			if (speciesRoot != null) {
				IBreedingSystem system = Binnie.GENETICS.getSystem(speciesRoot);
				return system.getDescriptor() + ' ' + I18N.localise("genetics.item.gene.serum.array");
			}
		}
		return I18N.localise("genetics.item.gene.serum.array.corrupted");
	}

	@Override
	public ItemStack addGene(ItemStack stack, IGene gene) {
		IGeneItem geneItem = this.getGeneItem(stack);
		Preconditions.checkNotNull(geneItem, "Cannot add gene to itemStack that is not a valid serum array.");
		geneItem.addGene(gene);
		geneItem.writeToItem(stack);
		return stack;
	}
}