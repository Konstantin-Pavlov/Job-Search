<#import "../layout.ftlh" as layout>
<@layout.layout>
    <h1>Edit Vacancy</h1>
    <p>Salary: ${vacancyDto.salary}</p>
    <p>ID: ${vacancyDto.id}</p>
    <form action="/vacancies/edit/${vacancyDto.id}" method="post">

        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${vacancyDto.name}" required>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">description</label>
            <input type="text" class="form-control" id="description" name="description"
                   value="${vacancyDto.description}" required>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("description")>
                <span class="error-message">${bindingResult.getFieldError("description").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3">
            <label for="category_id" class="form-label">Category</label>
            <select class="form-select" id="category_id" name="categoryId" required>
                <#list categories as category>
                    <option value="${category.id}"
                            <#if vacancyDto.categoryId?? && vacancyDto.categoryId == category.id>selected</#if>>${category.name}</option>
                </#list>
            </select>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("categoryId")>
                <span class="error-message">${bindingResult.getFieldError("categoryId").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3">
            <label for="salary" class="form-label">Salary</label>
            <input type="number" class="form-control" id="salary" name="salary" value="${vacancyDto.salary?c}" required>
            <#--            <p>Debug Salary: ${vacancyDto.salary}</p> <!-- Debugging output &ndash;&gt;-->
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("salary")>
                <span class="error-message">${bindingResult.getFieldError("salary").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3">
            <label for="expFrom" class="form-label">Experience From</label>
            <input type="number" class="form-control" id="expFrom" name="expFrom"
                   value="${vacancyDto.expFrom!0}" required>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("expFrom")>
                <span class="error-message">${bindingResult.getFieldError("expFrom").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3">
            <label for="expTo" class="form-label">Experience To</label>
            <input type="number" class="form-control" id="expTo" name="expTo"
                   value="${vacancyDto.expTo!1}" required>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("expTo")>
                <span class="error-message">${bindingResult.getFieldError("expTo").defaultMessage}</span>
            </#if>
        </div>

        <div class="mb-3 form-check form-switch">
            <input type="checkbox" class="form-check-input" id="is_active" name="isActive"
                   <#if vacancyDto.isActive?? && vacancyDto.isActive == true || !vacancyDto.isActive??>checked</#if>>
            <label class="form-check-label" for="is_active">Is Active</label>
            <#if bindingResult?has_content && bindingResult.hasFieldErrors("isActive")>
                <span class="error-message">${bindingResult.getFieldError("isActive").defaultMessage}</span>
            </#if>
        </div>

        <div class="button-group">
            <button type="submit" class="btn btn-primary">Update Vacancy</button>
            <a href="/profile" class="btn btn-secondary">Back to Profile</a>
            <a href="/" class="btn btn-secondary">Home</a>
        </div>
    </form>
</@layout.layout>