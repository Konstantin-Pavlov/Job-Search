<#import "../layout.ftlh" as layout>
<@layout.layout>
    <h1>Edit Resume</h1>
    <p>Salary: ${resumeDto.salary}</p>
    <p>ID: ${resumeDto.id}</p>
    <form action="/resumes/edit/${resumeDto.id}" method="post">

        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${resumeDto.name}" required>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3">
            <label for="category_id" class="form-label">Category</label>
            <select class="form-select" id="category_id" name="categoryId" required>
                <#list categories as category>
                    <option value="${category.id}" <#if resumeDto.categoryId?? && resumeDto.categoryId == category.id>selected</#if>>${category.name}</option>
                </#list>
            </select>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("categoryId")>
                <span class="error-message">${bindingResult.getFieldError("categoryId").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3">
            <label for="salary" class="form-label">Salary</label>
            <input type="number" class="form-control" id="salary" name="salary" value="${resumeDto.salary?c}" required>
<#--            <p>Debug Salary: ${resumeDto.salary}</p> <!-- Debugging output &ndash;&gt;-->
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("salary")>
                <span class="error-message">${bindingResult.getFieldError("salary").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3 form-check form-switch">
            <input type="checkbox" class="form-check-input" id="is_active" name="isActive" <#if resumeDto.isActive?? && resumeDto.isActive == true || !resumeDto.isActive??>checked</#if>>
            <label class="form-check-label" for="is_active">Is Active</label>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("isActive")>
                <span class="error-message">${bindingResult.getFieldError("isActive").defaultMessage}</span>
            </#if>
        </div>

        <div class="button-group">
            <button type="submit" class="btn btn-primary">Update Resume</button>
            <a href="/auth/profile" class="btn btn-secondary">Back to Profile</a>
            <a href="/" class="btn btn-secondary">Home</a>
        </div>
    </form>
</@layout.layout>