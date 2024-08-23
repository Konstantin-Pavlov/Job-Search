<#import "../layout.ftlh" as layout>
<#--<@layout.layout isAuthenticated=isAuthenticated username=username>-->
<@layout.layout>
    <!-- Link to the external CSS file -->
    <link rel="stylesheet" type="text/css" href="/static/css/profile.css" xmlns="http://www.w3.org/1999/html">

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
                                <p class="vacancy-or-resume-date">Дата обновления: ${vacancy.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <button class="update-vacancy-or-resume-btn" onclick="location.href='/vacancies/update/${vacancy.id}'">Обновить</button>
                                    <button class="edit-vacancy-or-resume-btn" onclick="location.href='/vacancies/edit/${vacancy.id}'">Ред.</button>
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
                                <p class="vacancy-or-resume-date">Дата обновления: ${resume.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <button class="update-vacancy-or-resume-btn" onclick="location.href='/resumes/update/${resume.id}'">Обновить</button>
                                    <button class="edit-vacancy-or-resume-btn" onclick="location.href='/resumes/edit/${resume.id}'">Ред.</button>
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
        });
    </script>
</@layout.layout>
