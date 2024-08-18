<#import "../layout.ftlh" as layout>
<@layout.layout isAuthenticated=isAuthenticated username=username>
    <!-- Link to the external CSS file -->
    <link rel="stylesheet" type="text/css" href="/static/css/profile.css">

    <div class="profile-container">
        <h1 class="profile-header" id="profile-header"></h1>

        <div class="profile-section">
            <div class="profile-info">
                <img id="profile-avatar"
                     src="/avatars/${userDto.avatar}"
                     alt="User Avatar"
                     class="profile-avatar"
                     onerror="this.onerror=null;this.src='/avatars/<#if userDto.accountType == 'employer'>default_company_avatar.png<#else>ava.png</#if>';">

                <div id="profile-details">
                    <#if userDto.accountType == "employer">
                        <p class="profile-name" id="profile-name">company name</p><br>
                        <button class="edit-profile-btn">Редактировать</button>
                    <#else>
                        <p class="profile-name" id="profile-name">${userDto.name}</p>
                        <p class="profile-age" id="profile-age">Возраст: ${userDto.age}</p>
                        <button class="edit-profile-btn">Редактировать</button>
                    </#if>
                </div>
            </div>

            <div class="profile-actions">
                <div class="create-resume">
                    <button class="create-resume-btn" id="create-resume-btn">
                        <#if userDto.accountType == "employer">
                            Создать<br>вакансию
                        <#else>
                            Создать<br>резюме
                        </#if>
                    </button>
                </div>
                <div class="responses">
                    <button class="create-resume-btn">отклики: 42</button>
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
                            <div class="resume-card">
                                <p class="resume-title">${vacancy.name}</p>
                                <p class="resume-date">Дата обновления: ${vacancy.getFormattedUpdatedDate()}</p>
                                <div class="resume-actions">
                                    <button class="update-resume-btn">Обновить</button>
                                    <button class="edit-resume-btn">Ред.</button>
                                </div>
                            </div>
                        </#list>
                    </#if>
                <#else>
                    <#if userResumes?size == 0>
                        <p class="no-resumes">No resume added yet</p>
                    <#else>
                        <#list userResumes as resume>
                            <div class="resume-card">
                                <p class="resume-title">${resume.name}</p>
                                <p class="resume-date">Дата обновления: ${resume.getFormattedUpdatedDate()}</p>
                                <div class="resume-actions">
                                    <button class="update-resume-btn">Обновить</button>
                                    <button class="edit-resume-btn">Ред.</button>
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
