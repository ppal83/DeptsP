<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<!DOCTYPE html>

<html>

<head>

  <title>Add new employee page</title>

</head>

<body>

<div class="container">

  <portlet:defineObjects />

  <portlet:actionURL var="empAddLink" windowState='maximized'>
    <portlet:param name="action" value="/empadd.html" />
  </portlet:actionURL>

  <form class="emp-add-form" role="form" method="post">

    <h2>Add employee</h2>

    <table class="emp-add-table">

      <tr>
        <td><label for="name" class="form-control">Name</label></td>
        <td><input type="text" id="name" name="<portlet:namespace />name" class="form-control" value="${employee.name}"></td>
        <td><span class="error">${errorsBean.name}</span></td></td>
      </tr>

      <tr>
        <td><label for="Birthday" class="form-control">Birthday</label></td>
        <td><input type="text" id="Birthday" name="<portlet:namespace />Birthday" placeholder="yyyy-MM-dd"
                   value="${employee.birthDate}" /></td>
        <td><span class="error">${errorsBean.birthDate}</span></td>
      </tr>

      <tr>
        <td><label for="HireDate" class="form-control">HireDate</label></td>
        <td><input type="text" id="HireDate" name="<portlet:namespace />HireDate" class="form-control" placeholder="yyyy-MM-dd"
                   value="${employee.hireDate}" /></td>
        <td><span class="error">${errorsBean.hireDate}</span></td>
      </tr>

      <tr>
        <td><label for="address" class="form-control">Address</label></td>
        <td><input type="text" id="address" name="<portlet:namespace />address" class="form-control" value="${employee.address}"></td>
        <td><span class="error">${errorsBean.address}</span></td>
      </tr>

      <tr>
        <td><label for="email" class="form-control">Email</label></td>
        <td><input type="text" id="email" name="<portlet:namespace />email" class="form-control" value="${employee.email}" /></td>
        <td><span class="error">${errorsBean.email}</span></td>
      </tr>

      <tr>
        <td><label for="dept" class="form-control">Dept</label></td>
        <td><select id="dept" name="<portlet:namespace />deptId" class="form-control">
          <c:forEach items="${deptsList}" var="dept">
            <option value="${dept.id}">${dept.name}
            <c:if test="${dept.id eq id}"><option value="${dept.id}" selected>${dept.name}</c:if>
            </c:forEach>
        </select></td>
      </tr>

      <tr>
        <td><label for="salary" class="form-control">Salary</label></td>
        <td><input type="text" id="salary" name="<portlet:namespace />salary" class="form-control" value="${employee.salary}" /></td>
        <td><span class="error">${errorsBean.salary}</span></td>
      </tr>

      <tr>
        <td colspan="2">
          <button formaction="${empAddLink}"
                  class="btn btn-success cust">Add new employee</button>
        </td>
      </tr>

      <tr>
        <td>
          <button onclick="window.history.back()"
                  class="btn btn-primary btn-back">Go Back</button>
        </td>
      </tr>

    </table>

  </form>

</div>

</body>

</html>

