package dao.generic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import dao.Persistent;
import exceptions.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;

public abstract class GenericDAO<T extends Persistent, E extends Serializable> implements IGenericDAO<T, E> {

    private Class<T> persistentClass;
    
    @PersistenceContext(unitName = "crud_client")
    protected EntityManager entityManager;

    public GenericDAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    private Object getId(T entity) throws DAOException {
        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    return field.get(entity);
                }
            }
            return null;
        } catch (Exception e) {
            throw new DAOException("ERRO AO ENCONTRAR ID", e);
        }
    }

    @Override
    public Boolean create(T entity) throws DAOException {
        try {
            entityManager.persist(entity);
            return true;
        } catch (Exception e) {
            throw new DAOException("ERRO AO ADICIONAR OBJETO", e);
        }
    }

    @Override
    public T read(E value) throws DAOException {
        try {
            T entity = entityManager.find(persistentClass, value);
            return entity;
        } catch (Exception e) {
            throw new DAOException("ERRO AO CONSULTAR OBJETO", e);
        }
    }

    @Override
    public Boolean update(T entity) throws DAOException {
        try {
            entityManager.merge(entity);
            return true;
        } catch (Exception e) {
            throw new DAOException("ERRO AO ALTERAR OBJETO", e);
        }
    }

    @Override
    public Boolean delete(T entity) throws DAOException {
        try {
            T entityManaged = entityManager.find(persistentClass, getId(entity));
            entityManager.remove(entityManaged);
            return true;
        } catch (Exception e) {
            throw new DAOException("ERRO AO DELETAR OBJETO", e);
        }
    }

    @Override
    public Collection<T> all() throws DAOException {
        String sql = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj";
        try {
            return entityManager.createQuery(sql, persistentClass).getResultList();
        } catch (Exception e) {
            throw new DAOException("ERRO AO BUSCAR TODOS OS OBJETOS", e);
        }
    }
}
