package therealeststu.dtbop;

import com.dtteam.dynamictrees.api.registry.RegistryHandler;
import com.dtteam.dynamictrees.treepack.Resources;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import com.dtteam.dynamictrees.DynamicTreesNeoForge;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(DynamicTreesBOP.MOD_ID)
public class DynamicTreesBOP {
    public static final String MOD_ID = "dtbop";

    public DynamicTreesBOP(IEventBus eventBus, ModContainer container) {
        DynamicTreesNeoForge.MOD_EVENT_BUS = eventBus;
        eventBus.addListener(this::onCommonSetup);
        eventBus.addListener(this::gatherData);

        //if (ModList.get().isLoaded("dynamictreesplus")){
        //    modEventBus.register(DTBOPPlusRegistries.class);
        //}

        RegistryHandler.setup(MOD_ID);

        DynamicTreesNeoForge.MOD_EVENT_BUS = null;
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        DTBOPRegistries.setup();
    }

    private void gatherData(final GatherDataEvent event) {
        Resources.MANAGER.gatherData();
        //GatherDataHelper.gatherAllData(MOD_ID, event,
        //        SoilProperties.REGISTRY,
        //        Family.REGISTRY,
        //        Species.REGISTRY,
        //        LeavesProperties.REGISTRY
                //, CapProperties.REGISTRY
        //);
    }

    public static ResourceLocation location (String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

}
