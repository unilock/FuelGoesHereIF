package rocks.katiekatiekatie.fuelgoeshereif.mixin;

import ironfurnaces.container.furnaces.BlockIronFurnaceContainerBase;
import ironfurnaces.items.augments.ItemAugmentBlasting;
import ironfurnaces.items.augments.ItemAugmentSmoking;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rocks.katiekatiekatie.fuelgoeshereif.FuelGoesHereIF;

@Mixin(value = BlockIronFurnaceContainerBase.class, remap = false)
public abstract class BlockIronFurnaceContainerBaseMixin extends ScreenHandler {
    protected BlockIronFurnaceContainerBaseMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow
    public BlockIronFurnaceTileBase te;

    @Shadow
    public abstract int getTier();

    public boolean isThisFuel(ItemStack itemstack, int slot) {
        ItemStack itemstackFuelSlot = this.slots.get(slot).getStack();
        return (
                   BlockIronFurnaceTileBase.isItemFuel(itemstack)
                || itemstack.isIn(FuelGoesHereIF.FORCED_FUELS)
               )
            && (
                   itemstackFuelSlot.isEmpty()
                || (
                       itemstackFuelSlot.getItem().equals(itemstack.getItem())
                    && itemstackFuelSlot.getCount() < itemstackFuelSlot.getMaxCount()
               )
            );
    }

    public ItemStack transferSlot(PlayerEntity player, int slotIndex) {
        ItemStack itemstackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasStack()) {
            ItemStack itemstack = slot.getStack();
            itemstackCopy = itemstack.copy();
            if (te.isGenerator()) {
                if (slotIndex != 6 && slotIndex != 3 && slotIndex != 4 && slotIndex != 5) {
                    if (isThisFuel(itemstack, BlockIronFurnaceTileBase.GENERATOR_FUEL)) {
                        if (!this.insertItem(itemstack, 6, 7, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (te.getStack(3).getItem() instanceof ItemAugmentSmoking) {
                        if (BlockIronFurnaceTileBase.getSmokingBurn(itemstack) > 0) {
                            if (!this.insertItem(itemstack, 6, 7, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    } else if (te.getStack(3).getItem() instanceof ItemAugmentBlasting) {
                        if (te.hasGeneratorBlastingRecipe(itemstack)) {
                            if (!this.insertItem(itemstack, 6, 7, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }

                    if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 0)) {
                        if (!this.insertItem(itemstack, 3, 4, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 1)) {
                        if (!this.insertItem(itemstack, 4, 5, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 2)) {
                        if (!this.insertItem(itemstack, 5, 6, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex >= 19 && slotIndex < 45) {
                        if (!this.insertItem(itemstack, 45, 54, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex >= 45 && slotIndex < 54 && !this.insertItem(itemstack, 19, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemstack, 19, 54, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (te.isFactory()) {
                if (slotIndex >= 12 && slotIndex <= 18) {
                    if (!this.insertItem(itemstack, 19, 54, true)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onQuickTransfer(itemstack, itemstackCopy);
                } else if (slotIndex >= 19) {
                    if (this.te.hasRecipe(itemstack)) {
                        if (getTier() == 2) {
                            if (!this.insertItem(itemstack, 7, 13, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (getTier() == 1) {
                            if (!this.insertItem(itemstack, 8, 12, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else {
                            if (!this.insertItem(itemstack, 9, 11, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 0)) {
                        if (!this.insertItem(itemstack, 3, 4, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 1)) {
                        if (!this.insertItem(itemstack, 4, 5, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 2)) {
                        if (!this.insertItem(itemstack, 5, 6, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex >= 19 && slotIndex < 45) {
                        if (!this.insertItem(itemstack, 45, 54, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex >= 45 && slotIndex < 54 && !this.insertItem(itemstack, 19, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemstack, 19, 54, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (te.isFurnace()) {
                if (slotIndex == 2) {
                    if (!this.insertItem(itemstack, 19, 54, true)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onQuickTransfer(itemstack, itemstackCopy);
                } else if (slotIndex != 1 && slotIndex != 0 && slotIndex != 3 && slotIndex != 4 && slotIndex != 5) {
                    if (isThisFuel(itemstack, BlockIronFurnaceTileBase.FUEL)) {
                        if (!this.insertItem(itemstack, 1, 2, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (this.te.hasRecipe(itemstack)) {
                        if (!this.insertItem(itemstack, 0, 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 0)) {
                        if (!this.insertItem(itemstack, 3, 4, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 1)) {
                        if (!this.insertItem(itemstack, 4, 5, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (BlockIronFurnaceTileBase.isItemAugment(itemstack, 2)) {
                        if (!this.insertItem(itemstack, 5, 6, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex >= 19 && slotIndex < 45) {
                        if (!this.insertItem(itemstack, 45, 54, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex >= 45 && slotIndex < 54 && !this.insertItem(itemstack, 19, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemstack, 19, 54, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (itemstack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemstack.getCount() == itemstackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemstack);
        }

        return itemstackCopy;
    }
}
