<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

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

  <form class="dept-edit-form" method="post">

    <table class="dept-edit-table">

      <tr>
        <td><label for="id" class="form-control">ID</label></td>
        <td><input type="text" id="id" name="<portlet:namespace/>id" class="form-control"
                   readonly value="${dept.id}" /></td>
      </tr>

      <tr>
        <td><label for="name" class="form-control">Name</label></td>
        <td><input type="text" id="name" name="<portlet:namespace/>name" class="form-control" value="${dept.name}" /></td>
        <td><span class="error">${errorsBean.name}</span></td>
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

  </form>

</div>

</body>

</html>

