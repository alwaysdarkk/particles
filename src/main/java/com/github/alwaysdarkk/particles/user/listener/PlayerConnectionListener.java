package com.github.alwaysdarkk.particles.user.listener;

import com.github.alwaysdarkk.particles.user.cache.ParticleUserCache;
import com.github.alwaysdarkk.particles.user.controller.ParticleUserController;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerConnectionListener implements Listener {

    private final ParticleUserController userController;
    private final ParticleUserCache userCache;

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        this.userController.loadUser(player);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        this.userCache.remove(player.getName());
    }
}