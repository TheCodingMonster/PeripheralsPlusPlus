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
	
	LinkedList<ItemStack> validItemsExtra = new LinkedList<ItemStack>();

	public ItemComputerEngineerBackpack() {
		Block computerBase = GameRegistry.findBlock(ModIds.ComputerCraft, "CC-Computer");
		Block commandComputer = GameRegistry.findBlock(ModIds.ComputerCraft, "command_computer");
		Block peripheralBase = GameRegistry.findBlock(ModIds.ComputerCraft, "CC-Peripheral");
		Block turtleBase = GameRegistry.findBlock(ModIds.ComputerCraft, "CC-turtle");
		Item diskColored = GameRegistry.findItem(ModIds.ComputerCraft, "diskExpanded");
		Item disk = GameRegistry.findItem(ModIds.ComputerCraft, "disk");
		Item printedPage = GameRegistry.findItem(ModIds.ComputerCraft, "printout");
		Item pocket = GameRegistry.findItem(ModIds.ComputerCraft, "pocketComputer");
		validItemsExtra.add(new ItemStack(computerBase));
		validItemsExtra.add(new ItemStack(commandComputer));
		validItemsExtra.add(new ItemStack(peripheralBase));
		validItemsExtra.add(new ItemStack(turtleBase));
		validItemsExtra.add(new ItemStack(disk));
		validItemsExtra.add(new ItemStack(diskColored));
		validItemsExtra.add(new ItemStack(printedPage));
		validItemsExtra.add(new ItemStack(pocket));


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
		for (ItemStack stack : validItemsExtra) {
			if (stack.getItem().equals(itemstack.getItem())) return true;
		}
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
