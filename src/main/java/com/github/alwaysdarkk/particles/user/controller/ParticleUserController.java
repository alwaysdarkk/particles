package com.github.alwaysdarkk.particles.user.controller;

import com.github.alwaysdarkk.particles.user.cache.ParticleUserCache;
import com.github.alwaysdarkk.particles.user.data.ParticleUser;
import com.github.alwaysdarkk.particles.user.repository.ParticleUserRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ParticleUserController {

    private final ParticleUserRepository userRepository;
    private final ParticleUserCache userCache;

    public void loadUser(@NotNull Player player) {
        ParticleUser user = this.userRepository.selectOne(player.getName());
        if (user == null) {
            user = ParticleUser.builder().name(player.getName()).build();
            this.userRepository.insertOne(user);
        }

        this.userCache.put(user.getName(), user);
    }
}