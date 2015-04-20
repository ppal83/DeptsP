package com.aimprosoft.validation.dept;


public class DeptErrorsBean {

    String name;

    private boolean valid = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "DeptErrorsBean {" +
                "name='" + name + '\'' +
                '}';
    }
}
