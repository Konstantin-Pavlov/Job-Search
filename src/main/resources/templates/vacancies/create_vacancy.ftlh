<#import "../layout.ftlh" as main>
<#import "../modal.ftlh" as modalTemplates>
<@main.layout>

    <#assign title = successMessage!"Default Title">
    <#assign name = vacancyDto.name!"Default Name">
    <#assign updated = entityUpdated!false>

    <p>updated: <#if updated>yes<#else>no</#if></p>

    <!-- Include the modal template and pass variables -->
    <@modalTemplates.modal modalTitle=title modalName=name />

    <div class="container mt-4">
        <h2>Create Vacancy</h2>
        <#if errorMessage??>
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </#if>
<#--        <#if successMessage??>-->
<#--            <div class="toast align-items-center text-bg-success border-0" role="alert" aria-live="assertive"-->
<#--                 aria-atomic="true" id="successToast">-->
<#--                <div class="d-flex">-->
<#--                    <div class="toast-body">-->
<#--                        ${successMessage}-->
<#--                    </div>-->
<#--                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"-->
<#--                            aria-label="Close"></button>-->
<#--                </div>-->
<#--            </div>-->
<#--        </#if>-->

        <!-- Display the list of errors in a styled alert box with a title -->
        <#if bindingResult?has_content>
            <div class="alert alert-danger" role="alert">
                <h4 class="alert-heading">Please correct the following errors:</h4>
                <hr>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>Field Name</th>
                        <th>Error Description</th>
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

        <form action="/vacancies/create" method="post" enctype="multipart/form-data">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name"
                       value="${vacancyDto.name!'Software Engineer'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                    <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
                </#if>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <input type="text" class="form-control" id="description" name="description"
                       value="${vacancyDto.name!'Exciting opportunity for a skilled software engineer'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                    <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
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
                <input type="number" class="form-control" id="salary" name="salary" value="${vacancyDto.salary!'42000'}" required>
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
                <input type="checkbox" class="form-check-input custom-switch-green" id="is_active" name="isActive"
                       <#if vacancyDto.isActive?? && vacancyDto.isActive == true || !vacancyDto.isActive??>checked</#if>>
                <label class="form-check-label" for="is_active">Is Active</label>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("isActive")>
                    <span class="error-message">${bindingResult.getFieldError("isActive").defaultMessage}</span>
                </#if>
            </div>
            <button type="submit" class="btn btn-primary">Create Vacancy</button>
        </form>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            console.log("JavaScript is running on create_vacancy.ftlh");

            <#assign updated = entityUpdated!false>
<#--            <#assign title = entityTitle!"">-->
<#--            <#assign name = entityName!"">-->

            const vacancyUpdated = ${updated?c};
            if (vacancyUpdated) {
                // Set the title and body text of the modal dynamically
                const title = "${title}";
                const name = "${name}";
                const successModalElement = document.getElementById('successModal');

                // Set the modal title and body content
                successModalElement.querySelector('.modal-title').textContent = `${title}`;
                successModalElement.querySelector('.modal-body').textContent = `Vacancy ${name} successfully created`;

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
</@main.layout>