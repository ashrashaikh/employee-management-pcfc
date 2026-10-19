<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Employee Details</h2>

<div class="mb-4">
    <c:choose>
        <c:when test="${not empty employee.profilePhotoPath}">
            <img src="${pageContext.request.contextPath}${employee.profilePhotoPath}"
                 alt="Profile photo of ${employee.firstName} ${employee.lastName}"
                 width="150"
                 height="150"
                 class="rounded-circle shadow"
                 style="object-fit: cover; border: 3px solid #dee2e6;" />
        </c:when>
        <c:otherwise>
            <div class="rounded-circle bg-light border d-flex
                        align-items-center justify-content-center"
                 style="width: 150px; height: 150px;">
                <span class="text-muted">No photo</span>
            </div>
        </c:otherwise>
    </c:choose>
</div>

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