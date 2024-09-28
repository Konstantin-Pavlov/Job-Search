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
    <!-- Link to the external CSS file -->
    <link rel="stylesheet" type="text/css" href="/static/css/profile.css" xmlns="http://www.w3.org/1999/html">

    <#assign title = entityTitle!"${profileTitle}">
    <#assign name = entityName!"${profileName}">
    <#assign updated = entityUpdated!false>

    <p>${profileUpdated}: <#if updated>${profileYes}<#else>${profileNo}</#if></p>

    <!-- Include the modal template and pass variables -->
    <@modalTemplates.modal modalTitle=title modalName=name />

    <div class="profile-container">
        <h1 class="profile-header" id="profile-header"></h1>

        <div class="profile-section">
            <div class="profile-info">
                <img id="profile-avatar"
                     src="/avatars/${userDto.avatar}"
                     alt="${profileAvatar}"
                     class="profile-avatar"
                     onerror="this.onerror=null; this.src='/avatars/<#if userDto.accountType == 'employer'>default_company_avatar.png<#else>default_user_avatar.png</#if>';
                             this.onerror=function(){ this.src='/static/images/avatars/<#if userDto.accountType == 'employer'>default_company_avatar.png<#else>default_user_avatar.png</#if>'; };">

                <div id="profile-details">
                    <#if userDto.accountType == "employer">
                        <p class="profile-name" id="profile-name">${profileCompanyName}</p><br>
                        <a href="/profile/edit" class="btn btn-primary edit-profile-btn">${profileEdit}</a>
                    <#else>
                        <p class="profile-name" id="profile-name">${userDto.name}</p>
                        <p class="profile-age" id="profile-age">${profileAge}: ${userDto.age}</p>
                        <a href="/profile/edit" class="btn btn-primary edit-profile-btn">${profileEdit}</a>
                    </#if>
                </div>
            </div>

            <div class="profile-actions">
                <div class="create-resume">
                    <#if userDto.accountType == "employer">
                        <form action="/vacancies/create" method="get">
                            <button class="create-btn" id="create-btn">
                                ${profileCreateVacancy}
                            </button>
                        </form>
                    <#else>
                        <form action="/resumes/create" method="get">
                            <button type="submit" class="create-btn" id="create-btn">
                                ${profileCreateResume}
                            </button>
                        </form>
                    </#if>
                </div>

                <#if userDto.accountType == "employer">
                    <form action="/resumes/resumes-responded-to-employer-vacancies" method="get">
                        <button type="submit" class="create-btn" id="create-btn">
                            ${profileResponsesToVacancies}: ${resumesRespondedToEmployerVacancies?size}
                        </button>
                    </form>
                <#else>
                    <form action="/vacancies/vacancies-user-responded" method="get">
                        <button type="submit" class="create-btn" id="create-btn">
                            ${profileVacancies}: ${vacanciesUserResponded?size}
                        </button>
                    </form>
                </#if>
            </div>
        </div>

        <div class="resume-section">
            <h2 id="resume-section-title">
                <#if userDto.accountType == "employer">
                    ${profileVacancies}:
                <#else>
                    ${profileResumes}:
                </#if>
            </h2>
            <div class="resume-list" id="resume-list">
                <#if userDto.accountType == "employer">
                    <#if userVacancies?size == 0>
                        <p class="no-resumes">${profileNoVacancies}</p>
                    <#else>
                        <#list userVacancies as vacancy>
                            <div class="vacancy-or-resume-card">
                                <p class="vacancy-or-resume-title">${vacancy.name}</p>
                                <p class="vacancy-or-resume-title">ID: ${vacancy.id}</p>
                                <p class="vacancy-or-resume-date">${profileUpdatedDate}: ${vacancy.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <form action="/vacancies/update/${vacancy.id}" method="post" style="display:inline;">
                                        <button type="submit" class="update-vacancy-or-resume-btn">${profileUpdate}</button>
                                    </form>
                                    <button class="edit-vacancy-or-resume-btn" onclick="location.href='/vacancies/edit/${vacancy.id}'">${profileEdit}</button>
                                </div>
                            </div>
                        </#list>
                    </#if>
                <#else>
                    <#if userResumes?size == 0>
                        <p class="no-resumes">${profileNoResumes}</p>
                    <#else>
                        <#list userResumes as resume>
                            <div class="vacancy-or-resume-card">
                                <p class="vacancy-or-resume-title">${resume.name}</p>
                                <p class="vacancy-or-resume-title">${resume.id}</p>
                                <p class="vacancy-or-resume-date">${profileUpdatedDate}: ${resume.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <form action="/resumes/update/${resume.id}" method="post" style="display:inline;">
                                        <button type="submit" class="update-vacancy-or-resume-btn">${profileUpdate}</button>
                                    </form>
                                    <button class="edit-vacancy-or-resume-btn" onclick="location.href='/resumes/edit/${resume.id}'">${profileEdit}</button>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </#if>
            </div>
        </div>
        <div class="home-btn-container">
            <a href="/" class="home-btn">${profileHome}</a>
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
                profileHeader.textContent = "${profileEmployerDashboard}";
            } else {
                profileHeader.textContent = "${profileApplicantDashboard}";
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
                successModalElement.querySelector('.modal-title').textContent = `${title} ${profileSuccessfullyUpdated}`;
                successModalElement.querySelector('.modal-body').textContent = `${title} ${name} ${profileSuccessfullyUpdated}`;

                // Initialize and show the modal
                const successModal = new bootstrap.Modal(successModalElement);
                successModal.show();

                // Auto-close the modal after 3 seconds (3000 milliseconds)
                setTimeout(() => {
                    successModal.hide();
                }, 3000);
            }

        });
    </script>
</@layout.layout>