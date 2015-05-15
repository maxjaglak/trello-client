package org.max.trello.dao;


import com.orm.SugarRecord;

import java.util.List;

public abstract class BaseDao<T extends SugarRecord<T>> {

    protected Class<T> clazz;

    public BaseDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(List<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    public void save(T entity) {
        entity.save();
    }

    public List<T> getAll() {
        return T.listAll(clazz);
    }

    public T getById(long id) {
        return T.findById(clazz, id);
    }

    public void deleteAll() {
        T.deleteAll(clazz);
    }

    public void delete(T entity) {
        T.deleteAll(clazz, "id = ?", String.valueOf(entity.getId()));
    }
}
