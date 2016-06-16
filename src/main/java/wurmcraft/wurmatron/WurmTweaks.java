package wurmcraft.wurmatron;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import wurmcraft.wurmatron.common.blocks.WurmTweaksBlocks;
import wurmcraft.wurmatron.common.config.ConfigHandler;
import wurmcraft.wurmatron.common.events.*;
import wurmcraft.wurmatron.common.events.bloodmagic.KnifeHandler;
import wurmcraft.wurmatron.common.fluid.WurmTweaksFluid;
import wurmcraft.wurmatron.common.handler.LootHandler;
import wurmcraft.wurmatron.common.items.WTItems;
import wurmcraft.wurmatron.common.network.PacketHandler;
import wurmcraft.wurmatron.common.proxy.CommonProxy;
import wurmcraft.wurmatron.common.recipes.RecipeChecker;
import wurmcraft.wurmatron.common.recipes.Recipes;
import wurmcraft.wurmatron.common.reference.Global;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPENDENCIES, guiFactory = Global.GUIFACTORY)
public class WurmTweaks {

		@Mod.Instance (Global.MODID)
		public static WurmTweaks instance;

		@SidedProxy (serverSide = Global.COMMONPROXY, clientSide = Global.CLIENTPROXY)
		public static CommonProxy proxy;

		@Mod.EventHandler
		public void preInit (FMLPreInitializationEvent e) {
				ConfigHandler.init(e);
		}

		@Mod.EventHandler
		public void init (FMLInitializationEvent e) {
				WTItems.registerItems();
				WurmTweaksBlocks.registerBlocks();
				WurmTweaksFluid.registerFluids();
				PacketHandler.registerPackets();
				FMLCommonHandler.instance().bus().register(new JoinGameEvent());
				MinecraftForge.EVENT_BUS.register(new EntityInteract());
				MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
				MinecraftForge.EVENT_BUS.register(new HurtEvent());
				MinecraftForge.EVENT_BUS.register(new PickupEvent());
				if (RecipeChecker.modExists("AWWayofTime"))
						MinecraftForge.EVENT_BUS.register(new KnifeHandler());
				LootHandler.init();
		}

		@Mod.EventHandler
		public void postInit (FMLPostInitializationEvent e) {
				Recipes.init();
		}

		@Mod.EventHandler
		public void serverStarting (FMLServerStartingEvent e) {
				Recipes.checkSettings();
				LootHandler.init();
				// TODO e.registerServerCommand(new WTCommand());
		}

}
