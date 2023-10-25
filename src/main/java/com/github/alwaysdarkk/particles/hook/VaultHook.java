package com.github.alwaysdarkk.particles.hook;

import lombok.experimental.Delegate;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class VaultHook {

    @Delegate
    private final Economy economy;

    public VaultHook() {
        final ServicesManager servicesManager = Bukkit.getServicesManager();
        final RegisteredServiceProvider<Economy> registration = servicesManager.getRegistration(Economy.class);

        if (registration == null) {
            throw new NullPointerException("Vault plugin not found");
        }

        this.economy = registration.getProvider();
    }
}