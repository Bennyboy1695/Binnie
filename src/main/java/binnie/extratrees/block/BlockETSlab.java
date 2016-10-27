package binnie.extratrees.block;

import binnie.Binnie;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import forestry.api.core.Tabs;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockETSlab extends BlockWoodSlab implements IBlockMetadata {
	public BlockETSlab(final boolean par2) {
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setHardness(2.0f).setResistance(5.0f);
		setSoundType(SoundType.WOOD);
//		if (!this.field_150004_a) {
//			this.useNeighborBrightness = true;
//		}
		this.setLightOpacity(0);
		this.setRegistryName("slabs" + (par2 ? "Double" : "Single"));
	}

//	@Override
//	public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
//		final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
//		drops.addAll(BlockMetadata.getBlockDropped((IBlockMetadata) ExtraTrees.blockSlab, world, x, y, z, blockMeta));
//		if (this.field_150004_a) {
//			drops.addAll(BlockMetadata.getBlockDropped((IBlockMetadata) ExtraTrees.blockSlab, world, x, y, z, blockMeta));
//		}
//		return drops;
//	}
//
//	@Override
//	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
//		return BlockMetadata.breakBlock(this, player, world, x, y, z);
//	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMetadata();
	}

//	@Override
//	public boolean hasTileEntity(final int meta) {
//		return true;
//	}
//
//	@Override
//	public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
//		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
//		final TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
//		return tileentity != null && tileentity.receiveClientEvent(par5, par6);
//	}
//
//	@Override
//	public int getPlacedMeta(final ItemStack stack, final World world, final int x, final int y, final int z, final ForgeDirection clickedBlock) {
//		return TileEntityMetadata.getItemDamage(stack);
//	}

	//TODO
	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public int getPlacedMeta(ItemStack p0, World p1, BlockPos pos, EnumFacing p5) {
		return TileEntityMetadata.getItemDamage(p0);
	}

	@Override
	public int getDroppedMeta(final int blockMeta, final int tileMeta) {
		return tileMeta;
	}

	@Override
	public String getBlockName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		return Binnie.Language.localise(ExtraTrees.instance, "block.woodslab.name", PlankType.ExtraTreePlanks.values()[meta].getName());
	}

	@Override
	public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
	}

	@Override
	public void dropAsStack(World p0, BlockPos pos, ItemStack p4) {
		//		this.dropBlockAsItem(world, x, y, z, drop);
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
//		if (this.field_150004_a) {
//			return;
//		}
//		for (int i = 0; i < PlankType.ExtraTreePlanks.values().length; ++i) {
//			itemList.add(TileEntityMetadata.getItemStack(this, i));
//		}
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
//		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
//		if (tile != null) {
//			return this.getIcon(side, tile.getTileMetadata());
//		}
//		return super.getIcon(world, x, y, z, side);
//	}

//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return PlankType.ExtraTreePlanks.values()[meta].getIcon();
//	}
//
//	@Override
//	public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
//		super.breakBlock(par1World, par2, par3, par4, par5, par6);
//		par1World.removeTileEntity(par2, par3, par4);
//	}
//
//	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}
//
//	@Override
//	public boolean isWood(final IBlockAccess world, final int x, final int y, final int z) {
//		return true;
//	}
//
//	@Override
//	public int getFlammability(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
//		return 20;
//	}
//
//	@Override
//	public boolean isFlammable(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
//		return true;
//	}
//
//	@Override
//	public int getFireSpreadSpeed(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
//		return 5;
//	}
//
//	@Override
//	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
//		return BlockMetadata.getPickBlock(world, x, y, z);
//	}
}
