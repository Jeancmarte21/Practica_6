package dao;

import hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Repositorio<T,K extends Long> implements IRepositorio<T, K>{

    private static final Logger logger = LoggerFactory.getLogger(Repositorio.class);

    private Class<T> tClass;

    public Repositorio(Class<T> tClass) {

        this.tClass = tClass;
    }

    @Override
    public void add(T t) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            session.save(t);

            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al salvar el objeto en la base de datos.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(T t) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            session.update(t);

            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al actualizar el objeto en la base de datos.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteById(T t) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            session.delete(t);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al borrar el objeto en la base de datos.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void merge(T t) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            session.merge(t);

            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al borrar el objeto en la base de datos.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public T findOne(K k) {
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("from " + tClass.getSimpleName() + " t where t.id = " + k);

            return (T) query.uniqueResult();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }


    @Override
    public List<T> getAll() {
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("from " + tClass.getSimpleName());

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }
}
