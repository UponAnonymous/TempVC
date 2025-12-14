package dev.starless.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import dev.starless.mongo.schema.MigrationSchema;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public final class MongoStorage implements dev.starless.mongo.api.MongoStorage {

    private final StorageBuilder builder;
    private dev.starless.mongo.api.MongoStorage delegate;

    public MongoStorage(String url) {
        this.builder = StorageBuilder.create(url);
    }

    public MongoStorage(java.util.logging.Logger logger, String url) {
        this.builder = StorageBuilder.create(url).logger(logger);
    }

    public MongoStorage(org.slf4j.Logger logger, String url) {
        this.builder = StorageBuilder.create(url).logger(logger);
    }

    public MongoStorage typeAdapter(Type type, Object typeAdapter) {
        builder.typeAdapter(type, typeAdapter);
        return this;
    }

    public MongoStorage migrationSchema(MigrationSchema schema) {
        builder.migrationSchema(schema);
        return this;
    }

    public MongoStorage mongoLoggerLevel(Level level) {
        builder.mongoLoggerLevel(level);
        return this;
    }

    @Override
    public void init() {
        delegate().init();
    }

    @Override
    public void close() {
        if (delegate != null) {
            delegate.close();
        }
    }

    @Override
    public void overrideCollectionName(Class<?> type, String name) {
        delegate().overrideCollectionName(type, name);
    }

    @Override
    public <T> List<T> find(Class<? extends T> type, dev.starless.mongo.api.IterableProcessor processor, Bson filter) {
        return delegate().find(type, processor, filter);
    }

    @Override
    public <T> Optional<T> findFirst(Class<? extends T> type, dev.starless.mongo.api.IterableProcessor processor, Bson filter) {
        return delegate().findFirst(type, processor, filter);
    }

    @Override
    public boolean store(Object obj, boolean update) {
        return delegate().store(obj, update);
    }

    @Override
    public int remove(Object obj) {
        return delegate().remove(obj);
    }

    @Override
    public void processRequest(Class<?> type, dev.starless.mongo.RequestConsumer consumer) {
        delegate().processRequest(type, consumer);
    }

    @Override
    public MongoCollection<Document> getObjectCollection(Class<?> type) {
        return delegate().getObjectCollection(type);
    }

    @Override
    public MongoClient getClient() {
        return delegate().getClient();
    }

    private dev.starless.mongo.api.MongoStorage delegate() {
        if (delegate == null) {
            delegate = builder.build();
        }

        return delegate;
    }
}
