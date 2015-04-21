package com.aimprosoft.services.Impl;

import com.aimprosoft.DAO.DeptDAO;
import com.aimprosoft.model.Dept;
import com.aimprosoft.services.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	
	@Autowired(required=true)
	@Qualifier(value="deptDAO")
	private DeptDAO deptDAO;
	
	@Override
	@Transactional
	public void addDept(Dept dept) {
		deptDAO.addDept(dept);
	}

	@Override
	@Transactional
	public void deleteDept(Dept dept) {
		deptDAO.deleteDept(dept);
	}

	@Override
	@Transactional
	public void updateDept(Dept dept) {
		deptDAO.updateDept(dept);
	}

	@Override
	@Transactional
	public Dept getDeptById(int id) {
		return deptDAO.getDeptById(id);
	}

	@Override
	@Transactional
	public void deleteDeptById(int id) {
		deptDAO.deleteDeptById(id);
	}

	@Override
	@Transactional
	public List<Dept> getAllDepts() {
		return deptDAO.getAllDepts();
	}

	@Override
	@Transactional
	public Dept findByName(String name) {
		return deptDAO.findByName(name);
	}

}
