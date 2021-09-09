package me.yuval270.rpacore.database;

import me.yuval270.rpacore.util.async.FutureUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public abstract class SingleAbstractTable<T> extends AbstractTable {
    protected final String fieldName;

    public SingleAbstractTable(String table, String primaryKey, String field) {
        super(table, primaryKey);
        this.fieldName = field;
    }

    public void insertData(String uniqueId, T value) {
        FutureUtil.executeAsync(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + table + " (" + primaryKey + ", " + fieldName + ") VALUES (?, ?)");
                statement.setString(1, uniqueId);
                statement.setObject(2, value);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateField(String uniqueId, String value) {
        FutureUtil.executeAsync(() -> {
            try {
                PreparedStatement statement = connection
                        .prepareStatement("UPDATE " + table + " SET " + fieldName + "=? WHERE " + primaryKey + "=?");
                statement.setObject(1, value);
                statement.setString(2, uniqueId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    public CompletableFuture<T> getField(String uniqueId) {
        return FutureUtil.performFuture(() -> {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM " + table + " WHERE " + primaryKey + "=?");
            statement.setString(1, uniqueId);
            ResultSet results = statement.executeQuery();
            results.next();
            return (T) results.getObject(fieldName);
        });
    }

/*    public CompletableFuture<Integer> getIntegerField(String uniqueId) {
        return FutureUtil.performFuture(() -> {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM " + table + " WHERE " + primaryKey + "=?");
            statement.setString(1, uniqueId);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt(fieldName);
        });
    }*/

}

