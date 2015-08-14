package com.rooftrellen.pomoplan.db;

/**
 * The TableInterface in an interface for different database tables.
 *
 * @param <T> the class type.
 * @author Team FirstRow
 * @version 1.0.0
 */
public interface TableInterface<T> {

    /**
     * Inserts object into table.
     *
     * @param object the object.
     * @param backend the flag of backend.
     * @since 1.0.0
     */
    void insert(T object, boolean backend);

    /**
     * Updates object from table.
     *
     * @param object the object.
     * @since 1.0.0
     */
    void update(T object);

    /**
     * Deletes object from table.
     *
     * @param object the object.
     * @since 1.0.0
     */
    void delete(T object);

}
