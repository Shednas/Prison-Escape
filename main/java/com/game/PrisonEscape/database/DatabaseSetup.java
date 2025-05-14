package com.game.PrisonEscape.database;


import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void createTables() {
        String createPlayersTable = "CREATE TABLE IF NOT EXISTS Players ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "age INTEGER NOT NULL"
                + ");";

        String createLeaderboardTable = "CREATE TABLE IF NOT EXISTS Leaderboard ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "player_id INTEGER NOT NULL,"
                + "timer REAL NOT NULL,"
                + "FOREIGN KEY(player_id) REFERENCES Players(id)"
                + ");";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            // Execute the SQL statements
            stmt.execute(createPlayersTable);
            stmt.execute(createLeaderboardTable);
            System.out.println("Tables created successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}