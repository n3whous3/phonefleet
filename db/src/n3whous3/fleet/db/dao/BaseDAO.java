package n3whous3.fleet.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import n3whous3.fleet.db.util.HibernateUtil;

public class BaseDAO
{
	protected interface SingleTableQueryCondition<TableObj> {
		public void body(CriteriaBuilder critBuilder, CriteriaQuery<TableObj> criteria, Root<TableObj> root);
	}
	
	protected static <Obj> void persistObject(Obj objectToPersist) throws Exception {
		EntityManager manager = null;
		try {
			manager = HibernateUtil.getEntityManagerFactory().createEntityManager();
			manager.getTransaction().begin();
			manager.persist(objectToPersist);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager != null) manager.getTransaction().rollback();
			throw e;
		} finally {
			if(manager != null) manager.close();
		}
	}
	
	protected static <Obj> void updateObject(Obj objectToUpdate) throws Exception {
		EntityManager manager = null;
		try {
			manager = HibernateUtil.getEntityManagerFactory().createEntityManager();
			manager.getTransaction().begin();
			manager.merge(objectToUpdate);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager != null) manager.getTransaction().rollback();
			throw e;
		} finally {
			if(manager != null) manager.close();
		}
	}
	
	protected static <Obj> void removeObject(Obj objectToRemove) throws Exception {
		EntityManager manager = null;
		try {
			manager = HibernateUtil.getEntityManagerFactory().createEntityManager();
			manager.getTransaction().begin();
			// TODO: maybe not the best idea, if we updated its data
			manager.remove(manager.contains(objectToRemove) ? objectToRemove : manager.merge(objectToRemove));
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager != null) manager.getTransaction().rollback();
			throw e;
		} finally {
			if(manager != null) manager.close();
		}
	}
	
	protected static <TableObj> List<TableObj> singleTableQuery(Class<TableObj> tableObjClass, SingleTableQueryCondition<TableObj> queryCondition) throws Exception {
		EntityManager manager = null;
		try {
			manager = HibernateUtil.getEntityManagerFactory().createEntityManager();
			CriteriaBuilder critBuilder = manager.getCriteriaBuilder();
			CriteriaQuery<TableObj> criteria = critBuilder.createQuery(tableObjClass);
			Root<TableObj> rootObj = criteria.from(tableObjClass);
			criteria.select(rootObj);
			queryCondition.body(critBuilder, criteria, rootObj);
			TypedQuery<TableObj> typedQuery = manager.createQuery(criteria);
			// TODO just a few, or iterated lazily, or i don't know
			return typedQuery.getResultList();
		} catch (Exception e) {
			// TODO log
			throw e;
		} finally {
			// TODO: are you sure, that this will be done if return was done above?
			if(manager != null) manager.close();
		}
	}
}
