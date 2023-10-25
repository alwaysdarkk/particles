package com.github.alwaysdarkk.particles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParticlesConstants {
    public static final String
            CREATE_TABLE_QUERY =
                    "CREATE TABLE IF NOT EXISTS particles_users(name VARCHAR(64) NOT NULL PRIMARY KEY, particle VARCHAR(64) DEFAULT NULL, particles LONGTEXT NOT NULL)",
            INSERT_ONE_QUERY = "INSERT INTO particles_users VALUES(?,?,?)",
            UPDATE_ONE_QUERY = "UPDATE particles_users SET particle = ?, particles = ? WHERE name = ?",
            SELECT_ONE_QUERY = "SELECT * FROM particles_users WHERE name = ?";

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}