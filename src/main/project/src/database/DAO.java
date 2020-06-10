package src.database;

public interface DAO<Entity, Key> {
    void create(Entity entity);
    Entity read(String login);
    void update(Entity entity);
    void delete(Entity entity);
}
