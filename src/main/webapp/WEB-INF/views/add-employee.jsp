<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>${employee.id == null ? 'Add Employee' : 'Edit Employee'}</h2>

<form:form method="post"
    action="${pageContext.request.contextPath}/employees/save"
    modelAttribute="employee"
    enctype="multipart/form-data">
    <!-- Hidden field for ID (required for edit) -->
    <form:hidden path="id"/>
    <div class="mb-3">
        <label for="profilePhotoFile" class="form-label">Profile Photo</label>
        <c:if test="${not empty employee.profilePhotoPath}">
            <div class="mb-2">
                <img src="${pageContext.request.contextPath}${employee.profilePhotoPath}"
                     alt="Current employee photo"
                     width="120"
                     height="120"
                     style="object-fit: cover; border-radius: 8px;"/>
                <p class="text-muted">Current photo</p>
            </div>
        </c:if>
        <input type="file" id="profilePhotoFile" name="photoFile"
           accept=".jpg,.jpeg,.png,.gif,.webp" class="form-control" />
    </div>
    <div class="mb-3">
        <form:label path="firstName">First Name *</form:label>
        <form:input path="firstName" cssClass="form-control" required="required"/>
    </div>
    <div class="mb-3">
        <form:label path="lastName">Last Name *</form:label>
        <form:input path="lastName" cssClass="form-control" required="required"/>
    </div>
    <div class="mb-3">
        <form:label path="dob">Date of Birth *</form:label>
        <form:input path="dob" type="date" cssClass="form-control" required="required"/>
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger">
                ${errorMsg}
            </div>
        </c:if>
        <form:errors path="dob" cssClass="text-danger" />
    </div>
    <div class="mb-3">
        <form:label path="departments">Departments *</form:label>
        <form:select path="departments" cssClass="form-select" required="required">
            <form:options items="${departments}"/>
        </form:select>
    </div>
    <div class="mb-3">
        <form:label path="salary">Salary *</form:label>
        <form:input path="salary" type="number" step="0.01" cssClass="form-control" required="required"/>
    </div>
    <div class="mb-3">
        <form:label path="manager">Manager (Optional)</form:label>
        <form:select path="manager.id" cssClass="form-select">
            <form:option value="" label="-- Select Manager --"/>
            <form:options items="${managers}" itemValue="id" itemLabel="firstName"/>
        </form:select>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
    <a href="${pageContext.request.contextPath}/employees/list" class="btn btn-secondary">Cancel</a>
</form:form>