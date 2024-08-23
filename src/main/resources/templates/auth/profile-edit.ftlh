<#import "../layout.ftlh" as main>
<@main.layout>

    <div class="container mt-4">
        <h2>Edit Profile</h2>
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

        <form action="/profile/edit" method="post" enctype="multipart/form-data">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${user.name}" required>
            </div>
            <div class="mb-3">
                <label for="age" class="form-label">Age</label>
                <input type="number" class="form-control" id="age" name="age" value="${user.age}" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}"
                       required>
            </div>

            <div class="mb-3">
                <label for="accountType" class="form-label">Account Type</label>
                <select class="form-control" id="accountType" name="accountType" required>
                    <option value="applicant" <#if user.accountType == "applicant">selected</#if>>Applicant</option>
                    <option value="employer" <#if user.accountType == "employer">selected</#if>>Employer</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="avatar" class="form-label">Avatar (optional)</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>
            <button type="submit" class="btn btn-primary">Save Changes</button>
        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const successToast = document.getElementById('successToast');
            if (successToast) {
                const toast = new bootstrap.Toast(successToast);
                toast.show();
                setTimeout(() => {
                    window.location.href = '/auth/profile'; // Redirect to profile page
                }, 3000); // Redirect after 3 seconds
            }
        });
    </script>

</@main.layout>