package com.aimprosoft.DAO;

import com.aimprosoft.model.Dept;

import java.util.List;

public interface DeptDAO {

    void addDept(Dept dept);
    void deleteDept(Dept dept);
    void updateDept(Dept dept);
    Dept getDeptById(int id);
    Dept findByName(String name);
    void deleteDeptById(int id);
    List<Dept> getAllDepts();

}
