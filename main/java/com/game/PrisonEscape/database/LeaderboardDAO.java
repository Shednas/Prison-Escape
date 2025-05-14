package com.game.PrisonEscape.database;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LeaderboardDAO {

    public void addLeaderboardEntry(int playerId, double timer) {
        String sql = "INSERT INTO Leaderboard(player_id, timer) VALUES(?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.setDouble(2, timer);
            pstmt.executeUpdate();
            System.out.println("Leaderboard entry added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}