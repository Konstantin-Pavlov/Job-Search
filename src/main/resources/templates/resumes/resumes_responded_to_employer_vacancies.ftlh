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
        <#if resumesRespondedToEmployerVacancies?size == 0>
            <div class="alert alert-info text-center" role="alert">
                <h2>${resumesRespondedNoResponses}</h2>
            </div>
        <#else>
            <div class="alert alert-info text-center" role="alert">
                <h2>${resumesRespondedTitlePart1} ${resumesRespondedToEmployerVacancies?size} ${resumesRespondedTitlePart2}</h2>
            </div>
            <div class="row d-flex justify-content-between">
                <#list resumesRespondedToEmployerVacancies as resume>
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${resume.name}</h5>
                                <p class="card-text mb-2"><strong>${resumesRespondedCardSalary}</strong> ${resume.getFormattedCreatedDate()}</p>
                                <a href="/resumes/${resume.id}" class="btn btn-primary mt-auto">${resumesRespondedCardMoreInfo}</a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>

        <div class="button-group">
            <a href="/profile" class="btn btn-secondary">${backToProfile}</a>
            <a href="/" class="btn btn-secondary">${home}</a>
        </div>
    </div>
</@layout.layout>