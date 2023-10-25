package com.github.alwaysdarkk.particles.user.repository;

import com.github.alwaysdarkk.particles.user.data.ParticleUser;
import com.github.alwaysdarkk.particles.user.repository.adapter.ParticleUserAdapter;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.alwaysdarkk.particles.ParticlesConstants.*;

@RequiredArgsConstructor
public class ParticleUserRepository {

    private final SQLExecutor sqlExecutor;

    public void createTable() {
        this.sqlExecutor.updateQuery(CREATE_TABLE_QUERY);
    }

    public void insertOne(@NotNull ParticleUser user) {
        CompletableFuture.runAsync(() -> this.sqlExecutor.updateQuery(INSERT_ONE_QUERY, simpleStatement -> {
            simpleStatement.set(1, user.getName());
            simpleStatement.set(2, user.getParticle());
            simpleStatement.set(3, GSON.toJson(user.getParticles()));
        }));
    }

    public void updateOne(@NotNull ParticleUser user) {
        CompletableFuture.runAsync(() -> this.sqlExecutor.updateQuery(UPDATE_ONE_QUERY, simpleStatement -> {
            simpleStatement.set(1, user.getParticle());
            simpleStatement.set(2, GSON.toJson(user.getParticles()));
            simpleStatement.set(3, user.getName());
        }));
    }

    public ParticleUser selectOne(@NotNull String username) {
        return this.sqlExecutor.resultOneQuery(
                SELECT_ONE_QUERY, simpleStatement -> simpleStatement.set(1, username), ParticleUserAdapter.class);
    }
}