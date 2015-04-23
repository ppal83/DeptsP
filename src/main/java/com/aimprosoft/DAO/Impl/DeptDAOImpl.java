package com.aimprosoft.DAO.Impl;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.model.Dept;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("deptDAO")
public class DeptDAOImpl implements DeptDAO {

    private static final Logger logger = LoggerFactory.getLogger(DeptDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addDept(Dept dept) {
        Session session = sessionFactory.getCurrentSession();
        session.save(dept);
        logger.info("Dept was saved successfully. Dept details: " + dept);
    }

    @Override
    public void deleteDept(Dept dept) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dept);
        logger.info("Dept was deleted successfully. Dept details: " + dept);
    }

    @Override
    public void updateDept(Dept dept) {
        Session session = sessionFactory.getCurrentSession();
        session.update(dept);
        logger.info("Dept was updated successfully. Dept details: " + dept);
    }

    @Override
    public Dept getDeptById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Dept dept = (Dept) session.get(Dept.class, id);
        logger.info("Dept #" + id + " was loaded successfully. "
                + "Dept details: " + dept);

        return dept;
    }

    @Override
    public void deleteDeptById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Dept dept = (Dept) session.get(Dept.class, id);
        if(dept != null){
            session.delete(dept);
        }
        logger.info("Dept #" + id + " was deleted successfully. "
                + "Dept details: " + dept);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dept> getAllDepts() {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Dept.class);
        cr.addOrder(Order.asc("id"));
        List<Dept> deptsList = cr.list();
        logger.info("Depts list was loaded successfully. Depts list: " + deptsList);

        return deptsList;
    }

    @Override
    public Dept findByName(String name) {
        Dept dept = null;
        dept = (Dept) sessionFactory.getCurrentSession()
                .createQuery("from Dept where name=?")
                .setParameter(0, name).uniqueResult();

        return dept;
    }

}