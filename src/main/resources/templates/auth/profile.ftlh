<#import   "../layout.ftlh" as layout>
<@layout.layout>
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2 class="mb-0">User Profile</h2>
            </div>
            <div class="card-body">
                <#if userDto??>
                    <div class="mb-3">
                        <p><strong>Username:</strong> ${userDto.name}</p>
                        <p><strong>age:</strong> ${userDto.age}</p>
                        <p><strong>email:</strong> ${userDto.email}</p>
                        <p><strong>phone:</strong> ${userDto.phoneNumber}</p>
                        <p><strong>avatar:</strong> ${userDto.avatar}</p>
                        <p><strong>type:</strong> ${userDto.accountType}</p>
                        <p><strong>enabled:</strong> ${userDto.enabled?string('yes', 'no')}</p>
                        <!-- Add more user details as needed -->
                    </div>
                <#else>
                    <p class="text-danger">User details not available.</p>
                </#if>
                <div class="mt-3">
                    <a href="/" class="btn btn-primary">Home</a>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>


***

//  how it supposed to be
<#--<div class="profile-container">-->
<#--    <h1 class="profile-header">Личный кабинет Соискателя</h1>-->

<#--    <div class="profile-section">-->
<#--        <div class="profile-info">-->
<#--            <img src="${user.avatarUrl}" alt="User Avatar" class="profile-avatar">-->
<#--            <p class="profile-name">${user.fullName}</p>-->
<#--            <p class="profile-age">Возраст: ${user.age}</p>-->
<#--            <button class="edit-profile-btn">Редактировать</button>-->
<#--        </div>-->

<#--        <div class="profile-actions">-->
<#--            <div class="create-resume">-->
<#--                <button class="create-resume-btn">Создать резюме</button>-->
<#--            </div>-->
<#--            <div class="responses">-->
<#--                <span class="responses-count">${user.responsesCount}</span>-->
<#--                <p>Отклики</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->

<#--    <div class="resume-section">-->
<#--        <h2>Резюме:</h2>-->
<#--        <div class="resume-list">-->
<#--            <#list user.resumes as resume>-->
<#--                <div class="resume-card">-->
<#--                    <p class="resume-title">${resume.title}</p>-->
<#--                    <p class="resume-date">Дата обновления: ${resume.updateDate?string("dd/MM/yyyy")}</p>-->
<#--                    <div class="resume-actions">-->
<#--                        <button class="update-resume-btn">Обновить</button>-->
<#--                        <button class="edit-resume-btn">Ред.</button>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </#list>-->
<#--        </div>-->
<#--    </div>-->
<#--</div>-->
