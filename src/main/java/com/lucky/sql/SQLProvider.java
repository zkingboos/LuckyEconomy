package com.lucky.sql;

import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public class SQLProvider extends SQLConnection {

    public SQLProvider(Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    @SneakyThrows
    public <K> Optional<K> query(String sql, SQLConsumer<ResultSet, K> consumer, Object... objects){
        PreparedStatement ps = getCon().prepareStatement(sql);
        syncObjects(ps, objects);
        ResultSet set = ps.executeQuery();
        K result = set != null && set.next() ? consumer.apply(set) : null;
        set.close(); ps.close();
        return Optional.ofNullable(result);
    }

    @SneakyThrows
    public <K> Optional<Stream<K>> map(String sql, SQLConsumer<ResultSet, K> consumer, Object... objects) {
        PreparedStatement ps = getCon().prepareStatement(sql);
        syncObjects(ps, objects);
        ResultSet set = ps.executeQuery();

        List<K> objectsResult = new ArrayList<>();

        while (set.next()) { objectsResult.add(consumer.apply(set)); }
        set.close(); ps.close();
        return Optional.ofNullable(objectsResult.stream());
    }

    @SneakyThrows
    public int update(String sql, Object... objects){
        PreparedStatement ps = getCon().prepareStatement(sql);
        syncObjects(ps, objects);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    private void syncObjects(PreparedStatement ps, Object... objects) throws SQLException {
        Iterator<Object> iterator = Arrays.stream(objects).iterator();
        for (int i = 1; iterator.hasNext(); i++) {
            ps.setObject(i, iterator.next());
        }
    }
}
