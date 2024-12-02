package net.wbmjunior.serverutilitycommands.util;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

public interface IEntityDataSaver {
    NbtCompound getPersistentData();
}
