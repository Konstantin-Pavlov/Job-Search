<#import "../layout.ftlh" as layout>
<#--<@layout.layout isAuthenticated=isAuthenticated username=username>-->
<@layout.layout>
    <!-- Link to the external CSS file -->
    <link rel="stylesheet" type="text/css" href="/static/css/profile.css" xmlns="http://www.w3.org/1999/html">

    <p>updated: <#if entityUpdated??>yes<#else>no</#if></p>

    <div class="profile-container">
        <h1 class="profile-header" id="profile-header"></h1>

        <div class="profile-section">
            <div class="profile-info">
                <#--                <img id="profile-avatar"-->
                <#--                     src="/avatars/${userDto.avatar}"-->
                <#--                     alt="User Avatar"-->
                <#--                     class="profile-avatar"-->
                <#--                     onerror="this.onerror=null;this.src='/avatars/<#if userDto.accountType == 'employer'>default_company_avatar.png<#else>ava.png</#if>';">-->

                <img id="profile-avatar"
                     src="/avatars/${userDto.avatar}"
                     alt="User Avatar"
                     class="profile-avatar"
                     onerror="this.onerror=null; this.src='/avatars/<#if userDto.accountType == 'employer'>default_company_avatar.png<#else>default_user_avatar.png</#if>';
                             this.onerror=function(){ this.src='/static/images/avatars/<#if userDto.accountType == 'employer'>default_company_avatar.png<#else>default_user_avatar.png</#if>'; };">

                <div id="profile-details">
                    <#if userDto.accountType == "employer">
                        <p class="profile-name" id="profile-name">company name</p><br>
                        <a href="/profile/edit" class="btn btn-primary edit-profile-btn">Редактировать</a>
                    <#else>
                        <p class="profile-name" id="profile-name">${userDto.name}</p>
                        <p class="profile-age" id="profile-age">Возраст: ${userDto.age}</p>
                        <a href="/profile/edit" class="btn btn-primary edit-profile-btn">Редактировать</a>
                    </#if>
                </div>
            </div>

            <div class="profile-actions">
                <div class="create-resume">
                    <#if userDto.accountType == "employer">
                        <form action="/vacancies/create" method="get">
                            <button class="create-btn" id="create-btn">
                                Создать<br>вакансию
                            </button>
                        </form>
                    <#else>
                        <form action="/resumes/create" method="get">
                            <button type="submit" class="create-btn" id="create-btn">
                                Создать<br>резюме
                            </button>
                        </form>
                    </#if>
                </div>
                <div class="responses">
                    <button class="create-btn">отклики: 42</button>
                </div>
            </div>
        </div>

        <div class="resume-section">
            <h2 id="resume-section-title">
                <#if userDto.accountType == "employer">
                    Вакансии:
                <#else>
                    Резюме:
                </#if>
            </h2>
            <div class="resume-list" id="resume-list">
                <#if userDto.accountType == "employer">
                    <#if userVacancies?size == 0>
                        <p class="no-resumes">No vacancies added yet</p>
                    <#else>
                        <#list userVacancies as vacancy>
                            <div class="vacancy-or-resume-card">
                                <p class="vacancy-or-resume-title">${vacancy.name}</p>
                                <p class="vacancy-or-resume-date">Дата
                                    обновления: ${vacancy.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <button class="update-vacancy-or-resume-btn"
                                            onclick="location.href='/vacancies/update/${vacancy.id}'">Обновить
                                    </button>
                                    <button class="edit-vacancy-or-resume-btn"
                                            onclick="location.href='/vacancies/edit/${vacancy.id}'">Ред.
                                    </button>
                                </div>
                            </div>
                        </#list>
                    </#if>
                <#else>
                    <#if userResumes?size == 0>
                        <p class="no-resumes">No resume added yet</p>
                    <#else>
                        <#list userResumes as resume>
                            <div class="vacancy-or-resume-card">
                                <p class="vacancy-or-resume-title">${resume.name}</p>
                                <p class="vacancy-or-resume-title">${resume.id}</p>
                                <p class="vacancy-or-resume-date">Дата
                                    обновления: ${resume.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <form action="/resumes/update/${resume.id}" method="post" style="display:inline;">
                                        <button type="submit" class="update-vacancy-or-resume-btn">Обновить</button>
                                    </form>
                                    <button class="edit-vacancy-or-resume-btn"
                                            onclick="location.href='/resumes/edit/${resume.id}'">Ред.
                                    </button>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </#if>
            </div>
        </div>
        <div class="home-btn-container">
            <a href="/" class="home-btn">Home</a>
        </div>
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
        document.addEventListener("DOMContentLoaded", function () {
            console.log("JavaScript is running");

            const accountType = "${userDto.accountType}";
            console.log("Account Type:", accountType);

            // Set the header based on account type
            const profileHeader = document.getElementById("profile-header");
            if (accountType === "employer") {
                profileHeader.textContent = "Личный кабинет работодателя";
            } else {
                profileHeader.textContent = "Личный кабинет Соискателя";
            }


            <#assign updated = entityUpdated!false>
            <#assign title = entityTitle!"">
            <#assign name = entityName!"">

            const vacancyUpdated = ${updated?c};
            if (vacancyUpdated) {
                // Set the title and body text of the modal dynamically
                const title = "${title}";
                const name = "${name}";
                const successModalElement = document.getElementById('successModal');

                // Set the modal title and body content
                successModalElement.querySelector('.modal-title').textContent = `${title} updated`;
                successModalElement.querySelector('.modal-body').textContent = `${title} ${name} successfully updated`;

                // Initialize and show the modal
                const successModal = new bootstrap.Modal(successModalElement);
                successModal.show();

                // Auto-close the modal after 3 seconds (3000 milliseconds)
                setTimeout(() => {
                    successModal.hide();
                }, 3000);
            }



            // in js console we see "Toast should be showing now." message
            // but toast message is not shown
            // const successToast = document.getElementById('successToast');
            // if (successToast) {
            //     console.log("Success toast element found.");
            //     const toast = new bootstrap.Toast(successToast, {
            //         autohide: false  // Ensure it stays visible
            //     });
            //     toast.show();
            //     console.log("Toast should be showing now.");
            // } else {
            //     console.log("Success toast element NOT found.");
            // }
            //
            // // Delay the redirect to allow the toast to be visible
            // setTimeout(function() {
            //     window.location.href = "/auth/profile";
            // }, 3000);
        });


    </script>
</@layout.layout>
