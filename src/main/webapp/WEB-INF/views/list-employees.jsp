<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h2>Employee List</h2>

<table class="table table-striped table-bordered">
    <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Departments</th>
            <th>Salary</th>
            <th>Manager</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.firstName}</td>
                <td>${emp.lastName}</td>
                <td>${emp.departments}</td>
                <td>${emp.salary}</td>
                <td>
                    <c:choose>
                        <c:when test="${emp.manager != null}">
                          ${emp.manager.firstName} ${emp.manager.lastName}
                        </c:when>
                        <c:otherwise>
                          N/A
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/employees/view/${emp.id}" class="btn btn-sm btn-info">View</a>
                    <a href="${pageContext.request.contextPath}/employees/edit/${emp.id}" class="btn btn-sm btn-primary">Edit</a>
                    <a href="${pageContext.request.contextPath}/employees/delete/${emp.id}" class="btn btn-sm btn-danger"
                       onclick="return confirm('Are you sure you want to delete this employee?');">Delete
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>