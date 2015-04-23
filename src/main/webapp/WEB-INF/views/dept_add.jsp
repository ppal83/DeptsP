<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>

<!DOCTYPE html>

<html>

<head>

  <title>Add new dept page</title>

</head>

<body>

<portlet:defineObjects />

<portlet:actionURL var="deptAddLink" windowState='maximized'>
  <portlet:param name="action" value="/deptadd.html" />
</portlet:actionURL>

<div class="container">

  <h2>Add dept</h2>

  <springForm:form cssClass="dept-add-form" method="post" commandName="dept">

    <table class="dept-add-table">

      <tr>
        <td><springForm:label path="name">Name</springForm:label></td>
        <td><springForm:input path="name" /></td>
        <td><springForm:errors path="name" cssClass="error" /></td>
      </tr>

      <tr>
        <td colspan="2">
          <button formaction="${deptAddLink}"
                  class="btn btn-success cust">Add new dept</button>
        </td>
      </tr>

      <tr>
        <td>
          <button onclick="window.history.back()"
                  class="btn btn-primary btn-back">Go Back</button>
        </td>
      </tr>

    </table>

  </springForm:form>


</div>

</body>

</html>

