package me.yuval270.rpacore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.Getter;
import me.yuval270.rpacore.RpaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MySql {
    @Getter
    private Connection connection;
    @Getter
    private String characterResetTable;

    public MySql() {
        FileConfiguration config = RpaCore.getMain().getConfig();
        String host = config.getString("host");
        String database = config.getString("database");
        String username = config.getString("username");
        String password = config.getString("password");
        int port = config.getInt("port");
        characterResetTable = "character_reset_table";
        try {
            if (connection != null && !connection.isClosed())
                return;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
                    password);
            initCharacterResetTable();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MySql has been connected");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initCharacterResetTable() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("CREATE TABLE IF NOT EXISTS `character_reset` (uuid char(36), stage char(36)" + ")");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}