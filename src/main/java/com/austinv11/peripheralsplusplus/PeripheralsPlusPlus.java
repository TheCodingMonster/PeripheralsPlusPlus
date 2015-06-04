package com.austinv11.peripheralsplusplus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.BlockDispenser;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

import com.austinv11.collectiveframework.minecraft.config.ConfigException;
import com.austinv11.collectiveframework.minecraft.config.ConfigRegistry;
import com.austinv11.collectiveframework.minecraft.logging.Logger;
import com.austinv11.collectiveframework.minecraft.reference.ModIds;
import com.austinv11.collectiveframework.minecraft.utils.CurseVersionChecker;
import com.austinv11.collectiveframework.multithreading.SimpleRunnable;
import com.austinv11.peripheralsplusplus.api.satellites.upgrades.ISatelliteUpgrade;
import com.austinv11.peripheralsplusplus.blocks.BlockAnalyzerBee;
import com.austinv11.peripheralsplusplus.blocks.BlockAnalyzerButterfly;
import com.austinv11.peripheralsplusplus.blocks.BlockAnalyzerTree;
import com.austinv11.peripheralsplusplus.blocks.BlockAntenna;
import com.austinv11.peripheralsplusplus.blocks.BlockChatBox;
import com.austinv11.peripheralsplusplus.blocks.BlockEnvironmentScanner;
import com.austinv11.peripheralsplusplus.blocks.BlockMEBridge;
import com.austinv11.peripheralsplusplus.blocks.BlockOreDictionary;
import com.austinv11.peripheralsplusplus.blocks.BlockPeripheralContainer;
import com.austinv11.peripheralsplusplus.blocks.BlockPlayerSensor;
import com.austinv11.peripheralsplusplus.blocks.BlockSpeaker;
import com.austinv11.peripheralsplusplus.blocks.BlockTeleporter;
import com.austinv11.peripheralsplusplus.blocks.BlockTeleporterT2;
import com.austinv11.peripheralsplusplus.client.gui.GuiHandler;
import com.austinv11.peripheralsplusplus.commands.CommandUpdate;
import com.austinv11.peripheralsplusplus.creativetab.CreativeTabPPP;
import com.austinv11.peripheralsplusplus.entities.EntityNanoBotSwarm;
import com.austinv11.peripheralsplusplus.entities.EntityRidableTurtle;
import com.austinv11.peripheralsplusplus.entities.EntityRocket;
import com.austinv11.peripheralsplusplus.hooks.ComputerCraftNotFoundException;
import com.austinv11.peripheralsplusplus.init.ModBlocks;
import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.init.Recipes;
import com.austinv11.peripheralsplusplus.items.ItemNanoSwarm;
import com.austinv11.peripheralsplusplus.items.SatelliteUpgradeBase;
import com.austinv11.peripheralsplusplus.mount.DynamicMount;
import com.austinv11.peripheralsplusplus.network.AudioPacket;
import com.austinv11.peripheralsplusplus.network.AudioResponsePacket;
import com.austinv11.peripheralsplusplus.network.ChatPacket;
import com.austinv11.peripheralsplusplus.network.CommandPacket;
import com.austinv11.peripheralsplusplus.network.GuiPacket;
import com.austinv11.peripheralsplusplus.network.InputEventPacket;
import com.austinv11.peripheralsplusplus.network.ParticlePacket;
import com.austinv11.peripheralsplusplus.network.RidableTurtlePacket;
import com.austinv11.peripheralsplusplus.network.RobotEventPacket;
import com.austinv11.peripheralsplusplus.network.RocketCountdownPacket;
import com.austinv11.peripheralsplusplus.network.RocketLaunchPacket;
import com.austinv11.peripheralsplusplus.network.ScaleRequestPacket;
import com.austinv11.peripheralsplusplus.network.ScaleRequestResponsePacket;
import com.austinv11.peripheralsplusplus.network.TextFieldInputEventPacket;
import com.austinv11.peripheralsplusplus.proxy.CommonProxy;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.turtles.TurtleBarrel;
import com.austinv11.peripheralsplusplus.turtles.TurtleBluePower;
import com.austinv11.peripheralsplusplus.turtles.TurtleChatBox;
import com.austinv11.peripheralsplusplus.turtles.TurtleCompass;
import com.austinv11.peripheralsplusplus.turtles.TurtleDispenser;
import com.austinv11.peripheralsplusplus.turtles.TurtleDropCollector;
import com.austinv11.peripheralsplusplus.turtles.TurtleEnvironmentScanner;
import com.austinv11.peripheralsplusplus.turtles.TurtleFeeder;
import com.austinv11.peripheralsplusplus.turtles.TurtleGarden;
import com.austinv11.peripheralsplusplus.turtles.TurtleNoteBlock;
import com.austinv11.peripheralsplusplus.turtles.TurtleOreDictionary;
import com.austinv11.peripheralsplusplus.turtles.TurtlePlayerSensor;
import com.austinv11.peripheralsplusplus.turtles.TurtleProjRed;
import com.austinv11.peripheralsplusplus.turtles.TurtleRidable;
import com.austinv11.peripheralsplusplus.turtles.TurtleShear;
import com.austinv11.peripheralsplusplus.turtles.TurtleSignReader;
import com.austinv11.peripheralsplusplus.turtles.TurtleSpeaker;
import com.austinv11.peripheralsplusplus.turtles.TurtleTank;
import com.austinv11.peripheralsplusplus.turtles.TurtleXP;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.turtle.ITurtleUpgrade;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION/*
																					 * ,
																					 * guiFactory
																					 * =
																					 * Reference
																					 * .
																					 * GUI_FACTORY_CLASS
																					 */, dependencies = "after:CollectiveFramework")
public class PeripheralsPlusPlus {

	public static int VILLAGER_ID = 1337; // :P

	public static final List<SatelliteUpgradeBase> SATELLITE_UPGRADE_REGISTRY = new ArrayList<SatelliteUpgradeBase>();
	public static final List<Integer> SATELLITE_UPGRADE_ID_REGISTRY = new ArrayList<Integer>();

	/**
	 * Object containing all registered upgrades, the key is the upgrade id
	 */
	public static final HashMap<Integer, ISatelliteUpgrade> UPGRADE_REGISTRY = new HashMap<Integer, ISatelliteUpgrade>();

	public static SimpleNetworkWrapper NETWORK;

	@Mod.Instance(Reference.MOD_ID)
	public static PeripheralsPlusPlus instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static Logger LOGGER = new Logger(Reference.MOD_NAME);

	public static String BASE_PPP_DIR = "./mods/PPP/";

	public static volatile CurseVersionChecker VERSION_CHECKER;
	public static volatile IChatComponent versionMessage;
	public static volatile boolean didMessage = false;
	public static int tries = 1;
	public static volatile File currentFile;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		try {
			ConfigRegistry.registerConfig(new Config());
		} catch (ConfigException e) {
			LOGGER.fatal("Fatal problem with the Peripherals++ config has been caught, if this continues, please delete the config file");
			e.printStackTrace();
		}
		currentFile = event.getSourceFile();
		VERSION_CHECKER = new CurseVersionChecker("226687-peripherals",
				Reference.MOD_NAME + "-" + Reference.VERSION + ".jar");
		doVersionCheck();
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("ppp");
		NETWORK.registerMessage(AudioPacket.AudioPacketHandler.class,
				AudioPacket.class, 0, Side.CLIENT);
		NETWORK.registerMessage(
				AudioResponsePacket.AudioResponsePacketHandler.class,
				AudioResponsePacket.class, 1, Side.SERVER);
		NETWORK.registerMessage(
				RocketCountdownPacket.RocketCountdownPacketHandler.class,
				RocketCountdownPacket.class, 2, Side.CLIENT);
		NETWORK.registerMessage(
				RocketLaunchPacket.RocketLaunchPacketHandler.class,
				RocketLaunchPacket.class, 3, Side.SERVER);
		NETWORK.registerMessage(ChatPacket.ChatPacketHandler.class,
				ChatPacket.class, 4, Side.CLIENT);
		NETWORK.registerMessage(
				ScaleRequestPacket.ScaleRequestPacketHandler.class,
				ScaleRequestPacket.class, 5, Side.CLIENT);
		NETWORK.registerMessage(
				ScaleRequestResponsePacket.ScaleRequestResponsePacketHandler.class,
				ScaleRequestResponsePacket.class, 6, Side.SERVER);
		NETWORK.registerMessage(CommandPacket.CommandPacketHandler.class,
				CommandPacket.class, 7, Side.CLIENT);
		NETWORK.registerMessage(ParticlePacket.ParticlePacketHandler.class,
				ParticlePacket.class, 8, Side.CLIENT);
		NETWORK.registerMessage(InputEventPacket.InputEventPacketHandler.class,
				InputEventPacket.class, 9, Side.SERVER);
		NETWORK.registerMessage(GuiPacket.GuiPacketHandler.class,
				GuiPacket.class, 10, Side.CLIENT);
		NETWORK.registerMessage(
				TextFieldInputEventPacket.TextFieldInputEventPacketHandler.class,
				TextFieldInputEventPacket.class, 11, Side.SERVER);
		NETWORK.registerMessage(
				RidableTurtlePacket.RidableTurtlePacketHandler.class,
				RidableTurtlePacket.class, 12, Side.SERVER);
		NETWORK.registerMessage(RobotEventPacket.RobotEventPacketHandler.class,
				RobotEventPacket.class, 13, Side.CLIENT);
		proxy.iconManagerInit();
		proxy.prepareGuis();
		proxy.registerEvents();
		ModItems.preInit();
		ModBlocks.init();
		LOGGER.info("Preparing the mount...");
		DynamicMount.prepareMount();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Config.setWhitelist(Config.dimensionWhitelist);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		LOGGER.info("Registering peripherals...");
		proxy.registerTileEntities();
		ComputerCraftAPI.registerPeripheralProvider(new BlockChatBox());
		ComputerCraftAPI.registerPeripheralProvider(new BlockPlayerSensor());
		ComputerCraftAPI.registerPeripheralProvider(new BlockOreDictionary());
		if (Loader.isModLoaded(ModIds.Forestry)) {
			LOGGER.info("Forestry is loaded! Registering analyzer peripherals and backpack...");
			ComputerCraftAPI.registerPeripheralProvider(new BlockAnalyzerBee());
			ComputerCraftAPI
					.registerPeripheralProvider(new BlockAnalyzerTree());
			ComputerCraftAPI
					.registerPeripheralProvider(new BlockAnalyzerButterfly());

		} else
			LOGGER.info("Forestry not found, skipping analyzer peripherals");
		ComputerCraftAPI.registerPeripheralProvider(new BlockTeleporter());
		ComputerCraftAPI.registerPeripheralProvider(new BlockTeleporterT2());
		ComputerCraftAPI
				.registerPeripheralProvider(new BlockEnvironmentScanner());
		ComputerCraftAPI.registerPeripheralProvider(new BlockSpeaker());
		ComputerCraftAPI.registerPeripheralProvider(new BlockAntenna());
		ComputerCraftAPI
				.registerPeripheralProvider(new BlockPeripheralContainer());
		if (Loader.isModLoaded(ModIds.AppliedEnergistics2)) {
			LOGGER.info("Applied Energistics is loaded! Registering the ME Bridge...");
			ComputerCraftAPI.registerPeripheralProvider(new BlockMEBridge());
		} else
			LOGGER.info("Applied Energistics not found, skipping the ME Bridge");
		LOGGER.info("Registering turtle upgrades...");
		registerUpgrade(new TurtleChatBox());
		registerUpgrade(new TurtlePlayerSensor());
		registerUpgrade(new TurtleCompass());
		registerUpgrade(new TurtleXP());
		if (Loader.isModLoaded(ModIds.Factorization)
				|| Loader.isModLoaded(ModIds.JABBA)) {
			LOGGER.info("A mod that adds barrels is loaded! Registering the barrel turtle upgrade...");
			registerUpgrade(new TurtleBarrel());
		} else
			LOGGER.info("No barrel-adding mods found, skipping the barrel turtle upgrade");
		registerUpgrade(new TurtleOreDictionary());
		registerUpgrade(new TurtleEnvironmentScanner());
		registerUpgrade(new TurtleFeeder());
		registerUpgrade(new TurtleShear());
		registerUpgrade(new TurtleSignReader());
		registerUpgrade(new TurtleGarden());
		if (Loader.isModLoaded(ModIds.ProjectRed_Exploration)
				|| Loader.isModLoaded(ModIds.BluePower)) {
			LOGGER.info("At least one RedPower-like mod is loaded! Registering RedPower-like turtle upgrades...");
			registerRedPowerLikeUpgrades();
		} else
			LOGGER.info("No RedPower-like mods found, skipping RedPower-like turtle upgrades");
		registerUpgrade(new TurtleSpeaker());
		registerUpgrade(new TurtleTank());
		registerUpgrade(new TurtleNoteBlock());
		registerUpgrade(new TurtleRidable());
		registerUpgrade(new TurtleDispenser());
		LOGGER.info("All peripherals and turtle upgrades registered!");
		// LOGGER.info("Registering satellite upgrades...");
		// PeripheralsPlusPlusAPI.registerSatelliteUpgrade(new GPSUpgrade());
		// LOGGER.info("All satellite upgrades registered!");
		proxy.registerRenderers();
		// EntityRegistry.registerGlobalEntityID(EntityRocket.class, "Rocket",
		// EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityRocket.class, "Rocket", 0,
				instance, 64, 20, true);
		if (Config.enableVillagers)
			proxy.setupVillagers();
		// EntityRegistry.registerGlobalEntityID(EntityRidableTurtle.class,
		// "Ridable Turtle", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityRidableTurtle.class,
				"Ridable Turtle", 1, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityNanoBotSwarm.class,
				"NanoBotSwarm", 2, instance, 64, 20, true);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
			throws ComputerCraftNotFoundException {
		ModItems.init();// Inits satellite upgrades and forestry backpacks
		Recipes.init();
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.nanoSwarm,
				new ItemNanoSwarm.BehaviorNanoSwarm());
	}

	@Mod.EventHandler
	public void onServerStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandUpdate());
	}

	private void doVersionCheck() {
		if (Config.doVersionUpdateChecks) {
			new SimpleRunnable() {

				@Override
				public void run() {
					PeripheralsPlusPlus.LOGGER
							.info("Starting version check...");
					if (VERSION_CHECKER.isUpdateAvailable()) {
						PeripheralsPlusPlus.LOGGER
								.warn("Update for Peripherals++ found!");
						versionMessage = IChatComponent.Serializer
								.func_150699_a(StatCollector
										.translateToLocal("peripheralsplusplus.chat.notification"));
					} else
						PeripheralsPlusPlus.LOGGER.info("No update found");
					this.disable(true);
				}

				@Override
				public String getName() {
					return "Peripherals++ Version Checker";
				}
			}.start();
		}
	}

	public static void registerUpgrade(ITurtleUpgrade u) {
		ComputerCraftAPI.registerTurtleUpgrade(u);
		CreativeTabPPP.upgrades.add(u);
		if (u instanceof TurtleDropCollector)
			MinecraftForge.EVENT_BUS.register(((TurtleDropCollector) u)
					.newInstanceOfListener());
	}

	private void registerRedPowerLikeUpgrades() {

		// Better solution but don't keeps old ids

		// int i = 0;
		// for (final ToolType type : ToolType.values()) {
		// if (type == ToolType.UNKNOWN) continue;
		//
		// for (final ToolMaterial material : ToolMaterial.values()) {
		// if (material == ToolMaterial.UNKNOWN) continue;
		//
		// final int id = i++;
		// registerUpgrade(new TurtleProjRed() {
		//
		// @Override
		// public ToolType getToolType() {
		// return type;
		// }
		//
		// @Override
		// public ToolMaterial getToolMaterial() {
		// return material;
		// }
		//
		// @Override
		// public int getID() {
		// return id;
		// }
		// });
		// }
		// }

		// Not as good as the first but better than yours and it keeps the same
		// ids as before
		if (Loader.isModLoaded(ModIds.ProjectRed_Exploration)) {
			int i = 0;
			for (final TurtleProjRed.ToolMaterial material : new TurtleProjRed.ToolMaterial[] {
					TurtleProjRed.ToolMaterial.PERIDOT,
					TurtleProjRed.ToolMaterial.RUBY,
					TurtleProjRed.ToolMaterial.SAPPHIRE }) {
				for (final TurtleProjRed.ToolType type : new TurtleProjRed.ToolType[] {
						TurtleProjRed.ToolType.AXE, TurtleProjRed.ToolType.HOE,
						TurtleProjRed.ToolType.PICKAXE,
						TurtleProjRed.ToolType.SHOVEL,
						TurtleProjRed.ToolType.SWORD }) {

					final int id = i++;
					registerUpgrade(new TurtleProjRed() {

						@Override
						public ToolType getToolType() {
							return type;
						}

						@Override
						public ToolMaterial getToolMaterial() {
							return material;
						}

						@Override
						public int getID() {
							return id;
						}
					});
				}
			}
		}

		if (Loader.isModLoaded(ModIds.BluePower)) {
			int j = 0;
			for (final TurtleBluePower.ToolMaterial material : new TurtleBluePower.ToolMaterial[] {
					TurtleBluePower.ToolMaterial.AMETHYST,
					TurtleBluePower.ToolMaterial.RUBY,
					TurtleBluePower.ToolMaterial.SAPPHIRE }) {
				for (final TurtleBluePower.ToolType type : new TurtleBluePower.ToolType[] {
						TurtleBluePower.ToolType.AXE,
						TurtleBluePower.ToolType.HOE,
						TurtleBluePower.ToolType.PICKAXE,
						TurtleBluePower.ToolType.SHOVEL,
						TurtleBluePower.ToolType.SWORD }) {

					final int id = j++;
					registerUpgrade(new TurtleBluePower() {

						@Override
						public ToolType getToolType() {
							return type;
						}

						@Override
						public ToolMaterial getToolMaterial() {
							return material;
						}

						@Override
						public int getID() {
							return id;
						}
					});
				}
			}
		}
	}
}
