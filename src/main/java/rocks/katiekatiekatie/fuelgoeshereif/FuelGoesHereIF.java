package rocks.katiekatiekatie.fuelgoeshereif;

import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.common.Mod;

@Mod("fuelgoeshereif")
public class FuelGoesHereIF {
    public static final TagKey<Item> FORCED_FUELS = ItemTags.create(new Identifier("fuelgoeshere", "forced_fuels"));

    public FuelGoesHereIF() {
    }
}
