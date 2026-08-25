<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Employee Details</h2>

<table class="table table-bordered w-50">
    <tr>
        <th>ID</th>
        <td>${employee.id}</td>
    </tr>
    <tr>
        <th>First Name</th>
        <td>${employee.firstName}</td>
    </tr>
    <tr>
        <th>Last Name</th>
        <td>${employee.lastName}</td>
    </tr>
    <tr>
        <th>Date of Birth</th>
        <td>${employee.dob}</td>
    </tr>
    <tr>
        <th>Departments</th>
        <td>${employee.departments}</td>
    </tr>
    <tr>
        <th>Salary</th>
        <td>${employee.salary}</td>
    </tr>
    <tr>
        <th>Manager</th>
        <td>
            <c:choose>
            <c:when test="${employee.manager != null}">
                ${employee.manager.firstName} ${employee.manager.lastName}
            </c:when>
            <c:otherwise>N/A</c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
<a href="${pageContext.request.contextPath}/employees/list" class="btn btn-secondary">Back</a>
<a href="${pageContext.request.contextPath}/employees/edit/${employee.id}" class="btn btn-primary">Edit</a>