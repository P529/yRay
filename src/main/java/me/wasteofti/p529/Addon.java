package me.wasteofti.p529;

import me.wasteofti.p529.modules.P529;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("P529");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor Addon Template");

        // Modules
        Modules.get().add(new P529());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "me.wasteofti.p529";
    }
}
