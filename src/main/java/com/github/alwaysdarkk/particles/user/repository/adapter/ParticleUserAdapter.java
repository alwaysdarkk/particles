package com.github.alwaysdarkk.particles.user.repository.adapter;

import com.github.alwaysdarkk.particles.user.data.ParticleUser;
import com.google.common.reflect.TypeToken;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;

import java.lang.reflect.Type;
import java.util.Set;

import static com.github.alwaysdarkk.particles.ParticlesConstants.GSON;

public class ParticleUserAdapter implements SQLResultAdapter<ParticleUser> {
    private final Type particlesType = new TypeToken<Set<String>>() {}.getType();

    @Override
    public ParticleUser adaptResult(SimpleResultSet resultSet) {
        final String name = resultSet.get("name"), particle = resultSet.get("particle");
        final Set<String> particles = GSON.fromJson((String) resultSet.get("particles"), particlesType);
        return ParticleUser.builder()
                .name(name)
                .particle(particle)
                .particles(particles)
                .build();
    }
}