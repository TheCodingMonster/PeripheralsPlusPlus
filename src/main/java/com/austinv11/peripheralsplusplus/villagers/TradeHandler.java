package com.austinv11.peripheralsplusplus.villagers;

import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.utils.NBTHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class TradeHandler implements VillagerRegistry.IVillageTradeHandler {
	
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		ItemStack error = new ItemStack(Items.diamond);
		ItemStack emerald = new ItemStack(Items.emerald);
		error.setStackDisplayName(Reference.Colors.RED+"THIS IS A BUG, REPORT TO THE P++ AUTHOR ASAP");
		MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Blocks.dirt), error);
		do {
			int trade = 0;MathHelper.getRandomIntegerInRange(random, 0, 7);
			//Logger.info(trade);
			switch (trade) {
				case 0://Empty floppy disk + 3 emeralds = dungeon disk FIXME
					int type = MathHelper.getRandomIntegerInRange(random, 0, 9);
					//Logger.info(type);
					ItemStack floppy = getFloppyFromInt(type);
					emerald.stackSize = 3;
					recipe = new MerchantRecipe(new ItemStack(GameRegistry.findItem("ComputerCraft", "diskExpanded")), emerald, floppy);
					break;
				case 1://Normal comp + emerald = advanced comp
					recipe = new MerchantRecipe(new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Computer")), emerald, new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Computer"), 1, 16384));
					break;
				case 2://Normal monitor + emerald = advanced monitor
					recipe = new MerchantRecipe(new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Peripheral"), 1, 2), emerald, new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Peripheral"), 1, 4));
					break;
				case 3://Normal turtle + emerald = advanced turtle
					recipe = new MerchantRecipe(new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Turtle")), emerald, new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-TurtleAdvanced")));
					break;
				case 4://Normal portable comp + emerald = advanced portable comp
					recipe = new MerchantRecipe(new ItemStack(GameRegistry.findItem("ComputerCraft", "pocketComputer")), emerald, new ItemStack(GameRegistry.findItem("ComputerCraft", "pocketComputer"), 1, 1));
					break;
				case 5://Normal comp + 2 emeralds = normal turtle
					emerald.stackSize = 2;
					recipe = new MerchantRecipe(new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Computer")), emerald, new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Turtle")));
					break;
				case 6://Advanced comp + 2 emeralds = advanced turtle
					emerald.stackSize = 2;
					recipe = new MerchantRecipe(new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Computer"), 1, 16384), emerald, new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-TurtleAdvanced")));
					break;
				case 7://64 emeralds = portable computer (an IPhone basically)
					emerald.stackSize = 64;
					ItemStack iPhone = new ItemStack(GameRegistry.findItem("ComputerCraft", "pocketComputer"), 1, 1);
					iPhone.setStackDisplayName(StatCollector.translateToLocal("item.peripheralsplusplus:iphone.name"));
					recipe = new MerchantRecipe(emerald, iPhone);
					break;
				case 8://Paper + emerald = printout w/ lore
					//TODO
					break;
			}
		} while (recipeList.contains(recipe));
		recipeList.add(recipe);
	}

	public ItemStack getFloppyFromInt(int t) {
		ItemStack stack = new ItemStack(GameRegistry.findItem("ComputerCraft", "treasureDisk"));
		switch (t) {
			case 0:
				break;
			case 1:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "fredthead/protector");
				NBTHelper.setString(stack, "title", "\"protector\" by fredthead");
				break;
			case 2:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "GopherAtl/battleship");
				NBTHelper.setString(stack, "title", "\"battleship\" by GopherAtl");
				break;
			case 3:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "GravityScore/LuaIDE");
				NBTHelper.setString(stack, "title", "\"LuaIDE\" by GravityScore");
				break;
			case 4:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "JTK/maze3d");
				NBTHelper.setString(stack, "title", "\"maze3d\" by JTK");
				break;
			case 5:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "Lyqyd/nsh");
				NBTHelper.setString(stack, "title", "\"nsh\" by Lyqyd");
				break;
			case 6:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "nitrogenfingers/goldrunner");
				NBTHelper.setString(stack, "title", "\"goldrunner\" by nitrogenfingers");
				break;
			case 7:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "nitrogenfingers/npaintpro");
				NBTHelper.setString(stack, "title", "\"npaintpro\" by nitrogenfingers");
				break;
			case 8:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "vilsol/gameoflife");
				NBTHelper.setString(stack, "title", "\"gameoflife\" by vilsol");
				break;
			case 9:
				NBTHelper.setInteger(stack, "colour", 3368652);
				NBTHelper.setString(stack, "subPath", "TheOriginalBIT/tictactoe");
				NBTHelper.setString(stack, "title", "\"tictactoe\" by TheOriginalBIT");
				break;
		}
		stack.setItemDamage(0);
		return stack;
	}
}
