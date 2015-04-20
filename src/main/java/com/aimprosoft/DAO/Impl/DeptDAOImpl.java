package com.aimprosoft.DAO.Impl;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.connection.DBCPDataSourceFactory;
import com.aimprosoft.model.Dept;
import com.aimprosoft.util.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeptDAOImpl implements DeptDAO {

    private static final Logger logger = LoggerFactory.getLogger(DeptDAOImpl.class);

    private static final String ADD_QUERY = "INSERT INTO dept (name) VALUE (?)";
    private static final String DELETE_QUERY = "DELETE FROM dept WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE dept SET id = ?, name = ? WHERE id = ?";
    private static final String GET_QUERY = "SELECT * FROM dept WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM dept ORDER BY id";

    @Override
    public int addDept(Dept dept) {
        int num;
        int res = 0;
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                stat.setString(1, dept.getName());
                num = stat.executeUpdate();
                try ( ResultSet rs = stat.getGeneratedKeys() ) {
                    if (rs.next()) {
                        res = rs.getInt(1);
                    }
                }
                logger.info(num + " row succefully added");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return res;
    }

    @Override
    public int deleteDept(Dept dept) {
        int num = 0;
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try ( PreparedStatement stat = conn.prepareStatement(DELETE_QUERY) ) {
                stat.setInt(1, dept.getId());
                num = stat.executeUpdate();
                logger.info(num + " row(s) succefully deleted");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return num;
    }

    @Override
    public void updateDept(Dept dept) {
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(UPDATE_QUERY)) {
                stat.setInt(1, dept.getId());
                stat.setString(2, dept.getName());
                stat.setInt(3, dept.getId());
                int num = stat.executeUpdate();
                logger.info(num + " row succefully updated");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
    }

    @Override
    public int deleteDeptById(int id) {
        int num = 0;
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(DELETE_QUERY)) {
                stat.setInt(1, id);
                num = stat.executeUpdate();
                logger.info(num + " row(s) succefully deleted");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return num;
    }

    @Override
    public Dept getDeptById(int id) {
        Dept dept = null;
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(GET_QUERY)) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    rs.next();
                    String name = rs.getString(2);
                    dept = new Dept(name);
                    dept.setId(id);
                }
                logger.info(dept + " was fetched from db");
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return dept;
    }

    @Override
    public List<Dept> getAllDepts() {
        List<Dept> deptsList = new ArrayList<>();
        try (Connection conn = DBCPDataSourceFactory.getDataSource().getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement(GET_ALL_QUERY)) {
                try (ResultSet rs = stat.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString(2);
                        Dept dept = new Dept(name);
                        int id = rs.getInt(1);
                        dept.setId(id);
                        deptsList.add(dept);
                    }
                    logger.info(deptsList + " list was fetched from db");
                }
            }
        } catch (SQLException e) {
            JDBCUtil.printSQLException(e);
        }
        return deptsList;
    }
}