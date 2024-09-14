<#import "../layout.ftlh" as main>
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
    <link rel="stylesheet" type="text/css" href="/static/css/company-info.css">
    <div class="container mt-4">
        <h2>Информация о компании</h2>
        <div class="company-info">
            <p><strong>Представитель компании: </strong> ${userDto.name}</p>
            <p><strong>Контактный телефон:</strong> ${userDto.phoneNumber}</p>
            <p><strong>Электронная почта:</strong> ${userDto.email}</p>
        </div>

        <h1>Активные вакансии</h1>
        <#if companyVacancies?size == 0>
            <p>Вакансий пока нет</p>
        <#else>
            <div class="vacancies-list">
                <#list companyVacancies as vacancy>
                    <div class="vacancy-card">
                        <h4>${vacancy.name}</h4>
                        <p><strong>Описание:</strong> ${vacancy.description}</p>
                        <p><strong>Зарплата:</strong> ${vacancy.salary}</p>
                        <p><strong>Опыт работы:</strong> От ${vacancy.expFrom} до ${vacancy.expTo} лет</p>
                        <p><strong>Дата публикации:</strong> ${vacancy.getFormattedCreatedDate()}</p>
                        <p><strong>Дата обновления:</strong> ${vacancy.getFormattedUpdatedDate()}</p>
                        <div class="vacancy-actions">
                            <a href="/vacancies/${vacancy.id}" class="btn btn-primary">Подробнее</a>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
    </div>

    <!-- Добавляем кнопки внизу страницы -->
    <div class="bottom-buttons">
        <a href="/" class="btn">Домой</a>
        <a href="/vacancies" class="btn">Полный список вакансий</a>
    </div>
</@main.layout>