package me.yuval270.rpacore.database;

import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.util.async.FutureUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractTable {
    protected final Connection connection = RpaCore.getMain().getMySql().getConnection();
    protected final String table;
    protected final String primaryKey;

    public AbstractTable(final String table, String primaryKey) {
        this.table = table;
        this.primaryKey = primaryKey;
    }

    public CompletableFuture<Boolean> dataExists(String uuid) {
        return FutureUtil.performFuture(() -> {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM " + table + " WHERE " + primaryKey + "=?");
            statement.setString(1, uuid);
            ResultSet results = statement.executeQuery();
            return results.next();
        });
    }

}
