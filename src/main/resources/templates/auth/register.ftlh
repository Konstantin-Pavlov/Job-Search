<#import "../layout.ftlh" as main>
<@main.layout>
    <p>ok: <#if okay??>yes<#else>no</#if></p>
    <div class="container mt-4">
        <h2>Register</h2>
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

        <form action="/auth/register" method="post" enctype="multipart/form-data">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${userDto.name!'Sid W'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                    <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="age" class="form-label">Age</label>
                <input type="number" class="form-control" id="age" name="age" value="${userDto.age!'42'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("age")>
<#--                    <div class="text-danger">${bindingResult.getFieldError("age").defaultMessage}</div>-->
                    <span class="error-message">${bindingResult.getFieldError("age").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email"
                       value="${userDto.email!'sid.w1@example.com'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("email")>
                <#--                    <div class="text-danger">${bindingResult.getFieldError("email").defaultMessage}</div>-->
                    <span class="error-message">${bindingResult.getFieldError("email").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password"
                       value="${userDto.password!'123'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("password")>
                    <span class="error-message">${bindingResult.getFieldError("password").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                       value="${userDto.phoneNumber!'+9961234783'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("phoneNumber")>
                    <span class="error-message">${bindingResult.getFieldError("phoneNumber").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="accountType" class="form-label">Account Type</label>
                <select class="form-control" id="accountType" name="accountType" required>
                    <option value="applicant"
                            <#if userDto.accountType?? && userDto.accountType == "applicant">selected</#if>>Applicant
                    </option>
                    <option value="employer"
                            <#if userDto.accountType?? && userDto.accountType == "employer">selected</#if>>Employer
                    </option>
                </select>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("accountType")>
                    <span class="error-message">${bindingResult.getFieldError("accountType").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="avatar" class="form-label">Avatar (optional)</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>
            <button type="submit" class="btn btn-primary">Register</button>
        </form>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="successModalLabel">Success</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Test message
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            <#assign ok = okay!false>


            const isOkay = ${ok?c};
            if (isOkay) {
                // Set the title and body text of the modal dynamically
                const successModalElement = document.getElementById('successModal');

                // Set the modal title and body content
                successModalElement.querySelector('.modal-title').textContent = `success!`;
                successModalElement.querySelector('.modal-body').textContent = `${userDto.name!"no name"} (${userDto.email!"no email"}) successfully registered`;

                // Initialize and show the modal
                const successModal = new bootstrap.Modal(successModalElement);
                successModal.show();

                // Auto-close the modal after 3 seconds (3000 milliseconds)
                setTimeout(() => {
                    successModal.hide();
                    // Redirect to the profile page after the modal is closed
                    window.location.href = "/auth/profile";
                }, 3000);
            }
        });
    </script>
</@main.layout>