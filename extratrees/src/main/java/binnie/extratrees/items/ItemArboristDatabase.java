package binnie.extratrees.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;

import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.IBinnieGuiItem;
import binnie.core.util.I18N;
import binnie.extratrees.gui.ExtraTreesGUID;

public class ItemArboristDatabase extends Item implements IItemModelRegister, IBinnieGuiItem {

	public ItemArboristDatabase() {
		setCreativeTab(Tabs.tabArboriculture);
		setTranslationKey("databaseTree");
		setRegistryName("databaseTree");
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "arborist_database");
		manager.registerItemModel(item, 1, "master_arborist_database");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (isMaster(stack)) {
			tooltip.add(TextFormatting.DARK_PURPLE + I18N.localise("extratrees.item.database.arborist.master.tooltip"));
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this, 1, 1));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemStack = player.getHeldItem(hand);
		openGui(itemStack, world, player);
		return new ActionResult<>(EnumActionResult.PASS, itemStack);
	}

	@Override
	public IBinnieGUID getGuiID(ItemStack itemStack) {
		return isMaster(itemStack) ? ExtraTreesGUID.DATABASE_MASTER : ExtraTreesGUID.DATABASE;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return I18N.localise("extratrees.item.database.arborist." + (isMaster(itemStack) ? "master.name" : "name"));
	}

	protected boolean isMaster(ItemStack itemStack) {
		return itemStack.getMetadata() > 0;
	}
}