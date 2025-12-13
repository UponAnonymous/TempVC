package dev.starless.mongo.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Compatibility wrapper kept for projects importing Query from the root api package.
 */
public class Query {

    private final Map<String, String> values;

    public Query() {
        values = new HashMap<>();
    }

    public String get(String key) {
        return values.getOrDefault(key, null);
    }

    public Map<String, String> getValues() {
        return values;
    }
}
