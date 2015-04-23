<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>

<!DOCTYPE html>

<html>

<head>

  <title>Edit employee page</title>

</head>

<body>

<portlet:defineObjects />

<portlet:actionURL var="empEditLink" windowState='maximized'>
  <portlet:param name="action" value="/empedit.html" />
</portlet:actionURL>

<div class="container">

  <h2>Edit employee</h2>

  <springForm:form cssClass="emp-edit-form" method="post" commandName="employee">

    <table class="emp-edit-table">

      <tr>
        <td><springForm:label path="id">ID</springForm:label></td>
        <td><springForm:input id="id" path="id" readonly="true" disabled="true"/></td>
        <td><springForm:errors path="id" cssClass="error" /></td>
        <td><springForm:hidden path="id" /></td>
      </tr>

      <tr>
        <td><springForm:label path="name">Name</springForm:label></td>
        <td><springForm:input path="name" /></td>
        <td><springForm:errors path="name" cssClass="error" /></td>
      </tr>

      <tr>
        <td><springForm:label path="birthDate">Birthday</springForm:label></td>
        <td><springForm:input path="birthDate" /></td>
        <td><springForm:errors path="birthDate" cssClass="error" /></td>
      </tr>

      <tr>
        <td><springForm:label path="hireDate">Hire date</springForm:label></td>
        <td><springForm:input path="hireDate" /></td>
        <td><springForm:errors path="hireDate" cssClass="error" /></td>
      </tr>

      <tr>
        <td><springForm:label path="address">Address</springForm:label></td>
        <td><springForm:input path="address" /></td>
        <td><springForm:errors path="address" cssClass="error" /></td>
      </tr>

      <tr>
        <td><springForm:label path="email">Email</springForm:label></td>
        <td><springForm:input path="email" /></td>
        <td><springForm:errors path="email" cssClass="error" /></td>
      </tr>

      <tr>
        <td><springForm:label path="dept.name">Department</springForm:label></td>
        <td><select name="deptId">
          <option value="${employee.dept.id}">${employee.dept.name}</option>
          <c:forEach items="${deptsList}" var="dept">
            <c:if test="${dept.id ne employee.dept.id}">
              <option value="${dept.id}">${dept.name}</option>
            </c:if>
          </c:forEach>
        </select> </td>
      </tr>

      <tr>
        <td><springForm:label path="salary">Salary</springForm:label></td>
        <td><springForm:input path="salary" /></td>
        <td><springForm:errors path="salary" cssClass="error" /></td>
      </tr>

      <tr>
        <td colspan="2">
          <button formaction="${empEditLink}" class="btn btn-success cust">Edit employee</button>
        </td>
      </tr>

      <tr>
        <td>
          <button onclick="window.history.back()" class="btn btn-primary">Go Back</button>
        </td>
      </tr>

    </table>

  </springForm:form>

</div>

</body>

</html>

