package DAO;

import java.util.List;


/**
 * DAO interface with basic functions for data managment.
 *
 * @param <T> Type of object dao represents
 * @param <V> Primary Key Type
 * @Author baeldung - resource: https://www.baeldung.com/java-dao-patter
 * @Author Jostein Skadberg jsk026
 */
public interface Dao<T, V> {

    /**
     * Gets an item from the storage based on V id.
     *
     * @param id
     * @return T element with matching id
     */
    T get(V id);

    /**
     * @return All stored entities of a type( a table etc.)
     */
    List<T> getAll();

    /**
     * @param t Save entity of type T in storage.
     */
    void save(T t);

    /**
     * Update values of entity in storage.
     *
     * @param t   entity
     * @param val new values
     */
    void update(T t, String[] val);

    /**
     * Delete an entity from storage.
     *
     * @param t the object that represents the to be deleted entity.
     */
    void delete(T t);


}
