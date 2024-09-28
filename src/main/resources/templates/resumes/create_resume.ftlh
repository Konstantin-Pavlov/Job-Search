<#import "../layout.ftlh" as layout>
<@layout.layout
title=title
home=home
create=create
profile=profile
logout=logout
login=login
register=register
loggedInMessage=loggedInMessage
roleMessage=roleMessage
notLoggedInMessage=notLoggedInMessage
availableActions=availableActions
>

    <div class="container mt-4">
        <h2>${resumeCreateTitle}</h2>
        <#if errorMessage??>
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </#if>
        <#if successMessage??>
            <div class="toast align-items-center text-bg-success border-0" role="alert" aria-live="assertive"
                 aria-atomic="true" id="successToast">
                <div class="d-flex">
                    <div class="toast-body">
                        ${successMessage}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"
                            aria-label="Close"></button>
                </div>
            </div>
        </#if>

        <!-- Display the list of errors in a styled alert box with a title -->
        <#if bindingResult?has_content>
            <div class="alert alert-danger" role="alert">
                <h4 class="alert-heading">${resumeDetails}</h4>
                <hr>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>${resumeTitle}</th>
                        <th>${resumeCount}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list bindingResult.fieldErrors as fieldError>
                        <tr>
                            <td><strong>${fieldError.field}</strong></td>
                            <td>${fieldError.defaultMessage}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </#if>

        <form action="/resumes/create" method="post" enctype="multipart/form-data">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <div class="mb-3">
                <label for="name" class="form-label">${resumeName}</label>
                <input type="text" class="form-control" id="name" name="name" value="${resumeDto.name!'Software Engineer'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                    <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
                </#if>
            </div>

            <div class="mb-3">
                <label for="category_id" class="form-label">${resumeCategory}</label>
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
                <label for="salary" class="form-label">${resumeSalary}</label>
                <input type="number" class="form-control" id="salary" name="salary" value="${resumeDto.salary?if_exists?string('42000')}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("salary")>
                    <span class="error-message">${bindingResult.getFieldError("salary").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3 form-check form-switch">
                <input type="checkbox" class="form-check-input" id="is_active" name="isActive" <#if resumeDto.isActive?? && resumeDto.isActive == true || !resumeDto.isActive??>checked</#if>>
                <label class="form-check-label" for="is_active">${resumeIsActive}</label>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("isActive")>
                    <span class="error-message">${bindingResult.getFieldError("isActive").defaultMessage}</span>
                </#if>
            </div>

            <div class="button-group">
                <button type="submit" class="btn btn-primary">${resumeCreateButton}</button>
                <a href="/profile" class="btn btn-secondary">${resumeButtonBackToProfile}</a>
                <a href="/" class="btn btn-secondary">${resumeButtonHome}</a>
            </div>

        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const successToast = document.getElementById('successToast');
            if (successToast) {
                const toast = new bootstrap.Toast(successToast);
                toast.show();
                setTimeout(() => {
                    window.location.href = '/profile'; // Redirect to profile page
                }, 3000); // Redirect after 3 seconds
            }
        });
    </script>
</@layout.layout>