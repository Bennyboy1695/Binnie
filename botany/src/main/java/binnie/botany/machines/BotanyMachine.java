package binnie.botany.machines;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

import binnie.botany.machines.designer.PackageDesigner;
import binnie.botany.machines.designer.Tileworker;
import binnie.botany.modules.features.BotanyMachines;
import binnie.core.Constants;
import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.util.ModuleManager;

public enum BotanyMachine implements IMachineType {
	Tileworker(() -> {
		if (ModuleManager.isModuleEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.CERAMIC)) {
			return new PackageDesigner(new Tileworker());
		}
		return null;
	});

	private final Supplier<MachinePackage> supplier;

	BotanyMachine(Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return this.supplier;
	}

	public ItemStack get(int i) {
		return BotanyMachines.MACHINE.stack(i, this.ordinal());
	}

}