<#import "../layout.ftlh" as layout>
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
    <div class="container mt-4">
        <#if vacanciesUserResponded?size == 0>
            <div class="alert alert-info text-center" role="alert">
                <h2>${vacancyRespondedNoVacancies}</h2>
            </div>
        <#else>
            <div class="alert alert-info text-center" role="alert">
                <h2>${vacancyUserRespondedTo} ${vacanciesUserResponded?size} ${vacanciesVacancies}</h2>
            </div>
            <div class="row d-flex justify-content-between">
                <#list vacanciesUserResponded as vacancy>
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card h-100 shadow-sm">
                            <img src="/static/images/avatars/default_user_avatar.png" class="card-img-top" alt="No image available">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${vacancy.name}</h5>
                                <p class="card-text"><strong>${vacancyDescription}:</strong> ${vacancy.description}</p>
                                <p class="card-text"><strong>${vacancySalary}:</strong> ${vacancy.salary}</p>
                                <p class="card-text">
                                    <strong>${vacancyExperience}:</strong>
                                    ${vacancyExperienceFrom} ${vacancy.expFrom} ${vacancyExperienceTo} ${vacancy.expTo} ${vacancyExperienceYears}                                <p class="card-text mb-2"><strong>Active:</strong> ${vacancy.isActive?string('yes', 'no')}</p>
                                <a href="/vacancies/${vacancy.id}" class="btn btn-primary mt-auto">${vacancyButtonInfo}</a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
        <!-- Centering the Home button -->
<#--        <div class="row">-->
<#--            <div class="col text-center"> <!-- This div centers the button &ndash;&gt;-->
<#--                <a href="/" class="btn btn-primary">Home</a>-->
<#--            </div>-->
<#--        </div>-->
        <div class="button-group">
            <a href="/profile" class="btn btn-secondary">${vacancyBackToProfile}</a>
            <a href="/" class="btn btn-secondary">${home}</a>
        </div>
    </div>
</@layout.layout>