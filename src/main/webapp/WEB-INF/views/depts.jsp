<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>

<head>

  <title>List of departments page</title>

</head>

<body>

<portlet:defineObjects />

<portlet:actionURL var="deptEditFormLink" windowState='maximized'>
  <portlet:param name="action" value="/deptedit_form.html" />
</portlet:actionURL>

<portlet:actionURL var="deptDelLink" windowState='maximized'>
  <portlet:param name="action" value="/deptdel.html" />
</portlet:actionURL>

<portlet:actionURL var="deptAddFormLink" windowState='maximized'>
  <portlet:param name="action" value="/deptadd_form.html" />
</portlet:actionURL>

<portlet:actionURL var="empListLink" windowState='maximized'>
  <portlet:param name="action" value="/emplist.html" />
</portlet:actionURL>

<div class="container">

  <h2>List of departments</h2>

  <table class="table table-bordered table-hover depts-table">

    <tr>
      <th>Dept ID</th>
      <th>Dept name</th>
      <th>Edit</th>
      <th>Delete</th>
      <th>View</th>
    </tr>

    <c:forEach items="${deptsList}" var="dept">

      <form class="depts-form" method="post">

        <tr>

          <td>${dept.id}</td>
          <td><c:out value="${dept.name}" /></td>
          <td>
            <button formaction="${deptEditFormLink}"
                    class="btn btn-sm btn-primary">Edit</button>
          </td>

          <!-- hidden input with param 'deptId'-->
          <input type="hidden" name="<portlet:namespace/>deptId" value="${dept.id}" />

          <td>
            <button formaction="${deptDelLink}"
                    class="btn btn-sm btn-danger">Delete</button>
          </td>

          <td>
            <button formaction="${empListLink}"
                    class="btn btn-sm btn-primary">View</button>
          </td>

        </tr>

      </form>

    </c:forEach>

  </table>

  <form class="emps-addbtn-form" method="post">
    <button formaction="${deptAddFormLink}"
            class="btn btn-success dept-add-btn">Add new dept</button>
  </form>

  <button onclick="window.history.back()"
          class="btn btn-primary btn-back">Go back</button>

</div>

</body>

</html>