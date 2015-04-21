<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>

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

  <form class="dept-add-form" method="post">

    <table class="dept-add-table">

      <tr>
        <td><label for="name" class="form-control">Name</label></td>
        <td><input type="text" id="name" name="<portlet:namespace/>name" class="form-control" value="${dept.name}" /></td>
        <td><span class="error">${errorsBean.name}</span></td>
        <td><input type="hidden" id="id" name="<portlet:namespace/>id" value="0" /></td>
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

  </form>

</div>

</body>

</html>

