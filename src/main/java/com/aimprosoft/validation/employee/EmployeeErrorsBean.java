package com.aimprosoft.validation.employee;

public class EmployeeErrorsBean {

    private String name;

    private String birthDate;

    private String hireDate;

    private String address;

    private String email;

    private String salary;

    private boolean valid = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "EmployeeErrorsBean {" +
                "name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", salary='" + salary + '\'' +
                ", valid=" + valid +
                '}';
    }
}
