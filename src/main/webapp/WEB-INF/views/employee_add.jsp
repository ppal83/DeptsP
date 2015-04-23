<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>

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

  <h2>Add employee</h2>

  <springForm:form cssClass="emp-add-form" method="post" commandName="employee">

    <table class="emp-add-table">

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
          <c:forEach items="${deptsList}" var="dept">
          <option value="${dept.id}">${dept.name}
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
          <button formaction="${empAddLink}" class="btn btn-success cust">Add new employee</button>
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

