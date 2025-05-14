package com.game.PrisonEscape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PlayerDAO {

    public void addPlayer(String name, int age) {
        String sql = "INSERT INTO Players(name, age) VALUES(?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.executeUpdate();
            System.out.println("Player added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}