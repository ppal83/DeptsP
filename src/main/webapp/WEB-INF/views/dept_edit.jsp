<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>

<!DOCTYPE html>

<html>

<head>

  <title>Edit dept page</title>

</head>

<body>

<portlet:defineObjects />

<portlet:actionURL var="deptEditLink" windowState='maximized'>
  <portlet:param name="action" value="/deptedit.html" />
</portlet:actionURL>

<div class="container">

  <h2>Edit dept</h2>

  <springForm:form cssClass="dept-add-form" method="post" commandName="dept">

    <table class="dept-add-table">

      <tr>
        <td><springForm:label path="id">ID</springForm:label></td>
        <td><springForm:input id="id" path="id" readonly="true" disabled="true"/></td>
        <td><springForm:errors path="id" cssClass="error" /></td>
        <td><springForm:hidden path="id" /></td>
      </tr>

      <tr>
        <td><springForm:label path="name">Name</springForm:label></td>
        <td><springForm:input id="name" path="name" /></td>
        <td><springForm:errors path="name" cssClass="error" /></td>
      </tr>

      <tr>
        <td colspan="2">
          <button formaction="${deptEditLink}"
                  class="btn btn-success cust">Edit dept</button>
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

