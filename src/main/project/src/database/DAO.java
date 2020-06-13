package src.database;

public interface DAO<Entity, Key> {
    int create(Entity entity);
    Entity read(String login);
    void update(Entity entity);
    void delete(int id);
}
