package com.aimprosoft.DAO;

import com.aimprosoft.model.Dept;

import java.util.List;

public interface DeptDAO {

    int addDept(Dept dept);
    int deleteDept(Dept dept);
    void updateDept(Dept dept);
    int deleteDeptById(int id);
    Dept getDeptById(int id);
    List<Dept> getAllDepts();

}
