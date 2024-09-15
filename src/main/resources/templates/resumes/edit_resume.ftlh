<#import "../layout.ftlh" as layout>
<#import "../modal.ftlh" as modalTemplates>
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

    <#assign title = successMessage!"Default Title">
    <#assign name = resumeDto.name!"Default Name">
    <#assign updated = entityUpdated!false>

    <p>updated: <#if updated>yes<#else>no</#if></p>

    <!-- Include the modal template and pass variables -->
    <@modalTemplates.modal modalTitle=title modalName=name />

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
            <button type="submit" class="btn btn-primary">${resumeButtonEdit}</button>
            <a href="/profile" class="btn btn-secondary">${resumeButtonBackToProfile}</a>
            <a href="/" class="btn btn-secondary">${resumeButtonHome}</a>
        </div>
    </form>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            console.log("JavaScript is running on create_vacancy.ftlh");

            <#assign updated = entityUpdated!false>
            <#--            <#assign title = entityTitle!"">-->
            <#--            <#assign name = entityName!"">-->

            const vacancyUpdated = ${updated?c};
            if (vacancyUpdated) {
                // Set the title and body text of the modal dynamically
                <#--const title = "${title}";-->
                <#--const name = "${name}";-->
                const successModalElement = document.getElementById('successModal');

                // Set the modal title and body content
                successModalElement.querySelector('.modal-title').textContent = "${title}";
                successModalElement.querySelector('.modal-body').textContent = `Resume ${name} successfully edited`;

                // Initialize and show the modal
                const successModal = new bootstrap.Modal(successModalElement);
                successModal.show();

                // Auto-close the modal after 3 seconds (3000 milliseconds)
                setTimeout(() => {
                    successModal.hide();
                    // Redirect to the profile page after the modal is closed
                    window.location.href = "/profile";
                }, 3000);
            }

        });
    </script>

</@layout.layout>