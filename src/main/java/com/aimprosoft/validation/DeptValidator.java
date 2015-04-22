package com.aimprosoft.validation;

import com.aimprosoft.model.Dept;
import com.aimprosoft.services.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DeptValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(DeptValidator.class);

    @Autowired
    private DeptService deptService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Dept.class;
    }

    @Override
    public void validate(Object target, Errors errors) {

        Dept formDept = (Dept) target;
        Dept dbDeptByName = deptService.findByName(formDept.getName());
        Dept dbDeptById = deptService.getDeptById(formDept.getId());

        if (formDept.getId() == 0 && dbDeptByName != null) {
            errors.rejectValue("name", "existingDeptName1", new Object[]{"'name'"},
                    "Dept with this name already exists");
        }
        if ( formDept.getId() != 0 && dbDeptByName != null
                && !dbDeptByName.getName().equals(dbDeptById.getName()) ) {
            errors.rejectValue("name", "existingDeptName2", new Object[]{"'name'"},
                    "Cannot change to existing dept name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "deptName.required");
    }
}