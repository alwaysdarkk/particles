package com.github.alwaysdarkk.particles.user.runnable;

import com.github.alwaysdarkk.particles.particle.cache.ParticleCache;
import com.github.alwaysdarkk.particles.particle.data.CustomParticle;
import com.github.alwaysdarkk.particles.user.cache.ParticleUserCache;
import com.github.alwaysdarkk.particles.user.data.ParticleUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class UserDisplayParticleRunnable implements Runnable {

    private final ParticleUserCache userCache;
    private final ParticleCache particleCache;

    @Override
    public void run() {
        if (this.userCache.isEmpty()) return;

        this.userCache.values().stream().filter(ParticleUser::hasActiveParticle).forEach(user -> {
            final Player player = Bukkit.getPlayer(user.getName());
            if (player == null) return;

            final CustomParticle particle = this.particleCache.get(user.getParticle());
            if (particle == null) return;

            particle.getParticleBuilder()
                    .location(player.getLocation().clone().subtract(0.0, 1.0, 0.0))
                    .spawn();
        });
    }
}