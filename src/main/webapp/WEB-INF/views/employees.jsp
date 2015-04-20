<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>

<head>

  <title>List of employees page</title>

</head>

<body>

<h2>List of employees of ${dept.name} department</h2>

<portlet:defineObjects />

<portlet:actionURL var="empEditFormLink" windowState='maximized'>
  <portlet:param name="action" value="/empedit_form.html" />
</portlet:actionURL>

<portlet:actionURL var="empDelLink" windowState='maximized'>
  <portlet:param name="action" value="/empdel.html" />
</portlet:actionURL>

<portlet:actionURL var="empAddFormLink" windowState='maximized'>
  <portlet:param name="action" value="/empadd_form.html" />
</portlet:actionURL>

<div class="table-responsive">

  <table class="table table-bordered table-hover empls-table">

    <tr>
      <th>Employee ID</th>
      <th>Employee Name</th>
      <th>Birthday</th>
      <th>Hireday</th>
      <th>Address</th>
      <th>Email</th>
      <th>Department</th>
      <th>Salary</th>
      <th>Edit</th>
      <th>Delete</th>
    </tr>

    <c:forEach items="${empsList}" var="employee">

      <form class="emps-form" method="post">

        <tr>

          <td>${employee.id}</td>
          <td><c:out value="${employee.name}" /></td>
          <td><fmt:formatDate pattern="yyyy-MM-dd"
                              value="${employee.birthDate}" /></td>
          <td><fmt:formatDate pattern="yyyy-MM-dd"
                              value="${employee.hireDate}" /></td>
          <td><c:out value="${employee.address}" /></td>
          <td>${employee.email}</td>
          <td><c:out value="${dept.name}" /></td>
          <td>${employee.salary}</td>

          <!-- hidden input with param 'id'-->
          <input type="hidden" name="<portlet:namespace />id" value="${employee.id}" />

          <td>
            <button formaction="${empEditFormLink}"
                    class="btn btn-sm btn-primary">Edit</button>
          </td>

          <td>
            <button formaction="${empDelLink}"
                    class="btn btn-sm btn-danger">Delete</button>
          </td>

        </tr>

      </form>

    </c:forEach>

  </table>

  <form class="emps-addbtn-form" method="post">

    <button formaction="${empAddFormLink}"
            class="btn btn-success emp-add-btn">Add new employee</button>
  </form>

  <button onclick="window.history.back()"
          class="btn btn-primary btn-back">Go Back</button>

</div>

</body>

</html>