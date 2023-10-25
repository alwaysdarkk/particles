package com.github.alwaysdarkk.particles;

import com.github.alwaysdarkk.particles.command.ParticleStopCommand;
import com.github.alwaysdarkk.particles.command.ParticlesCommand;
import com.github.alwaysdarkk.particles.hook.VaultHook;
import com.github.alwaysdarkk.particles.particle.cache.ParticleCache;
import com.github.alwaysdarkk.particles.particle.parser.ParticleParser;
import com.github.alwaysdarkk.particles.particle.view.ParticlesView;
import com.github.alwaysdarkk.particles.provider.RepositoryProvider;
import com.github.alwaysdarkk.particles.user.cache.ParticleUserCache;
import com.github.alwaysdarkk.particles.user.controller.ParticleUserController;
import com.github.alwaysdarkk.particles.user.listener.PlayerConnectionListener;
import com.github.alwaysdarkk.particles.user.repository.ParticleUserRepository;
import com.github.alwaysdarkk.particles.user.runnable.UserDisplayParticleRunnable;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticlesPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        final VaultHook vaultHook = new VaultHook();
        final ParticleCache particleCache = new ParticleCache();
        ParticleParser.of(getConfig(), particleCache).parse();

        final SQLConnector sqlConnector = RepositoryProvider.of(this).setup(null);
        final SQLExecutor sqlExecutor = new SQLExecutor(sqlConnector);

        final ParticleUserRepository userRepository = new ParticleUserRepository(sqlExecutor);
        userRepository.createTable();

        final ParticleUserCache userCache = new ParticleUserCache();
        final ParticleUserController userController = new ParticleUserController(userRepository, userCache);

        final ViewFrame viewFrame = ViewFrame.of(
                        this, new ParticlesView(particleCache, userCache, userRepository, vaultHook))
                .register();

        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(userController, userCache), this);

        this.getCommand("particles").setExecutor(new ParticlesCommand(viewFrame));
        this.getCommand("particlestop").setExecutor(new ParticleStopCommand(userCache, userRepository, particleCache));

        final UserDisplayParticleRunnable displayParticleRunnable =
                new UserDisplayParticleRunnable(userCache, particleCache);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, displayParticleRunnable, 20L, 20L);
    }
}