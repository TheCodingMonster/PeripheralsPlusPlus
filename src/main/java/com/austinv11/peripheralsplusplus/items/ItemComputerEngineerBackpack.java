package com.austinv11.peripheralsplusplus.items;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.austinv11.collectiveframework.minecraft.reference.ModIds;
import com.austinv11.peripheralsplusplus.blocks.BlockAnalyzer;
import com.austinv11.peripheralsplusplus.blocks.BlockPPP;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import forestry.api.storage.IBackpackDefinition;

@Optional.InterfaceList(value = { @Optional.Interface(modid = ModIds.Forestry, iface = "forestry.api.storage.IBackpackDefinition", striprefs = true) })
public class ItemComputerEngineerBackpack extends ItemPPP implements
		IBackpackDefinition {
	

	public ItemComputerEngineerBackpack() {
	}

	@Override
	public String getKey() {
		return "computer";
	}

	@Override
	public String getName(ItemStack backpack) {
		return StatCollector
				.translateToLocal("peripheralsplusplus.forestry.computer");
	}

	@Override
	public int getPrimaryColour() {
		return 0xC0C0C0;
	}

	@Override
	public int getSecondaryColour() {
		return 0xFFFFFF;
	}

	@Override
	public void addValidItem(ItemStack validItem) {
	}

	@Override
	public void addValidItems(List<ItemStack> validItems) {
	}

	@Override
	public boolean isValidItem(EntityPlayer player, ItemStack itemstack) {
		Item item = itemstack.getItem();
		Block block = Block.getBlockFromItem(item);
		return item instanceof ItemPPP | item instanceof ItemSmartHelmet
				| block instanceof BlockPPP | block instanceof BlockAnalyzer
				| block instanceof IPeripheralProvider
				| block instanceof IPeripheral;
	}

	@Override
	public String getName() {
		return StatCollector
				.translateToLocal("peripheralsplusplus.forestry.computer");
	}

	@Override
	public boolean isValidItem(ItemStack itemstack) {
		return isValidItem(null, itemstack);
	}

}
