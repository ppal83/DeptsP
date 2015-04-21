<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<html>

<head>

  <title>Index page</title>

  <link rel="stylesheet" href="<c:url value="/css/main.css" />" media="screen"/>

</head>

<body>

<portlet:defineObjects />

<h2>Lorem ipsum</h2>

<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
  ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
  laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
  voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
  cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>

<portlet:actionURL var="deptlist" windowState='maximized'>
  <portlet:param name="action" value="/deptlist.html" />
</portlet:actionURL>

<portlet:actionURL var="emplist" windowState='maximized'>
  <portlet:param name="action" value="/emplist.html" />
</portlet:actionURL>

<form class="empls-form" method="POST">
  <table>
    <tr>
      <td>
        <select name="<portlet:namespace/>deptId" class="form-control">
          <c:forEach items="${deptsList}" var="dept">
          <option value="${dept.id}">${dept.name}
            </c:forEach>
        </select></td>
      <td><button formaction="${emplist}" class="btn btn-primary">View</button></td>
    </tr>
    <tr>
      <td colspan="2">
        <button formaction="${deptlist}" class="btn btn-primary cust">Customize
          dept list</button>
      </td>
    </tr>
  </table>
</form>

<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
  ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
  laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
  voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
  cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>

</body>

</html>
