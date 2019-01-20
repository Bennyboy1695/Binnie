package binnie.core.machines.inventory;

import java.util.ArrayList;
import java.util.List;

import binnie.core.AbstractMod;
import binnie.core.Binnie;
import binnie.core.resource.BinnieSprite;

public class ValidatorSprite {

	private final List<BinnieSprite> spritesInput;
	private final List<BinnieSprite> spritesOutput;

	public ValidatorSprite(AbstractMod mod, String pathInput, String pathOutput) {
		this(mod.getModId(), pathInput, pathOutput);
	}

	public ValidatorSprite(String modId, String pathInput, String pathOutput) {
		this.spritesInput = new ArrayList<>();
		this.spritesOutput = new ArrayList<>();
		this.spritesInput.add(Binnie.RESOURCE.getItemSprite(modId, pathInput));
		this.spritesOutput.add(Binnie.RESOURCE.getItemSprite(modId, pathOutput));
	}

	public BinnieSprite getSprite(boolean input) {
		return input ? this.spritesInput.get(0) : this.spritesOutput.get(0);
	}
}