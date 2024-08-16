<#import "../layout.ftlh" as layout>
<@layout.layout isAuthenticated=isAuthenticated username=username>
    <!-- Link to the external CSS file -->
    <link rel="stylesheet" type="text/css" href="/static/css/profile.css">

    <div class="profile-container">
        <h1 class="profile-header">Личный кабинет Соискателя</h1>

        <div class="profile-section">
            <div class="profile-info">
                <#--                не работает почемуто, видит файлы только если они в /static/-->
                <#--                <img src="/avatars/${userDto.avatar}" alt="User Avatar" class="profile-avatar">-->
                <img src="/static/images/avatars/${userDto.avatar}" alt="User Avatar" class="profile-avatar"
                     onerror="this.onerror=null;this.src='/static/images/avatars/ava.png';">
                <div>
                    <p class="profile-name">${userDto.name}</p>
                    <p class="profile-age">Возраст: ${userDto.age}</p>
                    <button class="edit-profile-btn">Редактировать</button>
                </div>
            </div>

            <div class="profile-actions">
                <div class="create-resume">
                    <button class="create-resume-btn">Создать<br>резюме</button>
                </div>
                <div class="responses">
                    <button class="create-resume-btn">отклики: 42</button>
                </div>
            </div>
        </div>

        <div class="resume-section">
            <h2>Резюме:</h2>
            <div class="resume-list">
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
            </div>
        </div>
        <div class="home-btn-container">
            <a href="/" class="home-btn">Home</a>
        </div>
    </div>
</@layout.layout>