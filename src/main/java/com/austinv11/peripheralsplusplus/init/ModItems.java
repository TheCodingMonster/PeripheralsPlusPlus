package com.austinv11.peripheralsplusplus.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.austinv11.collectiveframework.minecraft.reference.ModIds;
import com.austinv11.peripheralsplusplus.PeripheralsPlusPlus;
import com.austinv11.peripheralsplusplus.items.ItemComputerEngineerBackpack;
import com.austinv11.peripheralsplusplus.items.ItemFeederUpgrade;
import com.austinv11.peripheralsplusplus.items.ItemNanoSwarm;
import com.austinv11.peripheralsplusplus.items.ItemPPP;
import com.austinv11.peripheralsplusplus.items.ItemPositionalUnit;
import com.austinv11.peripheralsplusplus.items.ItemRocket;
import com.austinv11.peripheralsplusplus.items.ItemSatellite;
import com.austinv11.peripheralsplusplus.items.ItemSmartHelmet;
import com.austinv11.peripheralsplusplus.items.ItemSocket;
import com.austinv11.peripheralsplusplus.items.ItemTank;
import com.austinv11.peripheralsplusplus.reference.Reference;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	public static final ItemPPP feederUpgrade = new ItemFeederUpgrade();
	public static final ItemPPP satellite = new ItemSatellite();
	public static final ItemPPP rocket = new ItemRocket();
	public static final ItemPPP tank = new ItemTank();
	public static final Item smartHelmet = new ItemSmartHelmet();
	public static final ItemPPP socket = new ItemSocket();
	public static final ItemPPP positionalUnit = new ItemPositionalUnit();
	public static final ItemPPP nanoSwarm = new ItemNanoSwarm();
	public static Item backpack1;
	public static Item backpack2;

	public static void preInit() {
		GameRegistry.registerItem(feederUpgrade, "feederUpgrade");
		GameRegistry.registerItem(satellite, "satellite");
		GameRegistry.registerItem(rocket, "rocket");
		GameRegistry.registerItem(tank, "tank");
		GameRegistry.registerItem(smartHelmet, "smartHelmet");
		GameRegistry.registerItem(socket, "socket");
		GameRegistry.registerItem(positionalUnit, "positionalUnit");
		GameRegistry.registerItem(nanoSwarm, "nanoSwarm");
	}

	public static void init() {
		if (Loader.isModLoaded(ModIds.Forestry)) {
			ItemComputerEngineerBackpack backpack = new ItemComputerEngineerBackpack();
			backpack1 = BackpackManager.backpackInterface.addBackpack(
					backpack, EnumBackpackType.T1);
			backpack2 = BackpackManager.backpackInterface.addBackpack(
					backpack, EnumBackpackType.T2);

			GameRegistry.registerItem(backpack1, "computerEngineerBackpack1");
			GameRegistry.registerItem(backpack2, "computerEngineerBackpack2");
			
			GameRegistry.registerCustomItemStack("computerEngineerBackpack1", new ItemStack(backpack1));
			GameRegistry.registerCustomItemStack("computerEngineerBackpack2", new ItemStack(backpack2));

			Item disk = GameRegistry.findItem(ModIds.ComputerCraft, "disk");

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
					ModItems.backpack1), "SWS", "DCD", "SWS", 'S',
					Items.string, 'W', Blocks.wool, 'D', disk, 'C',
					Blocks.chest));
			ItemStack silkStack = new ItemStack(GameRegistry.findItem(
					ModIds.Forestry, "craftingMaterial"), 1, 3);
			RecipeManagers.carpenterManager.addRecipe(30, new FluidStack(
					FluidRegistry.WATER, 1000), null, new ItemStack(
					ModItems.backpack2), "SDS", "SBS", "SSS", 'S', silkStack,
					'D', "gemDiamond", 'B', new ItemStack(ModItems.backpack1));
		}
		for (int i = 0; i < PeripheralsPlusPlus.SATELLITE_UPGRADE_REGISTRY
				.size(); i++)
			GameRegistry.registerItem(
					PeripheralsPlusPlus.SATELLITE_UPGRADE_REGISTRY.get(i),
					PeripheralsPlusPlus.SATELLITE_UPGRADE_REGISTRY.get(i)
							.getUpgrade().getUnlocalisedName());
	}
}
