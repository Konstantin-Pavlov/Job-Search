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
    <#assign name = entityName!"Default Name">
    <#assign updated = hasUpdated!false>
    <p>updated: <#if hasUpdated??>yes<#else>no</#if></p>

    <!-- Include the modal template and pass variables -->
    <@modalTemplates.modal modalTitle=title modalName=name />

    <div class="container mt-4">
        <h2>${profileEdit}</h2>
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
                <h4 class="alert-heading">${profileEditCorrectErrors}</h4>
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

        <form action="/profile/edit" method="post" enctype="multipart/form-data">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <div class="mb-3">
                <label for="name" class="form-label">${profileEditName}</label>
                <input type="text" class="form-control" id="name" name="name" value="${user.name}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                    <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="age" class="form-label">${profileEditAge}</label>
                <input type="number" class="form-control" id="age" name="age" value="${user.age}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("age")>
                    <span class="error-message">${bindingResult.getFieldError("age").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">${profileEditEmail}</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("email")>
                    <span class="error-message">${bindingResult.getFieldError("email").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label">${profileEditPhoneNumber}</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                       value="${user.phoneNumber}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("phoneNumber")>
                    <span class="error-message">${bindingResult.getFieldError("phoneNumber").defaultMessage}</span>
                </#if>
            </div>

            <div class="mb-3">
                <label for="accountType" class="form-label">${profileEditAccountType}</label>
                <select class="form-control" id="accountType" name="accountType" required>
                    <option value="applicant" <#if user.accountType == "applicant">selected</#if>>Applicant</option>
                    <option value="employer" <#if user.accountType == "employer">selected</#if>>Employer</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="avatar" class="form-label">${profileEditAvatar}</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>

            <div class="button-group">
                <button type="submit" class="btn btn-primary">${profileSaveChanges}</button>
                <a href="/profile" class="btn btn-secondary">${profileButton}</a>
                <a href="/" class="btn btn-secondary">${homeButton}</a>
            </div>

        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            <#--const isOkay = ${updated?c};-->
            if (${updated?c}) {
                // Set the title and body text of the modal dynamically
                const successModalElement = document.getElementById('successModal');

                // Set the modal title and body content
                successModalElement.querySelector('.modal-title').textContent = `${title}`;
                successModalElement.querySelector('.modal-body').textContent = `${user.name!"no name"} (${user.email!"no email"}) successfully updated their profile`;

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