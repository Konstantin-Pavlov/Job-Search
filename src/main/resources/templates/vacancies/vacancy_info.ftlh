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
    <body>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${vacancy.name}</h1>

                <p class="card-text"><strong>${vacancyDescription}:</strong> ${vacancy.description}</p>
                <p class="card-text"><strong>${vacancyCategory}:</strong> ${category.name}</p>
                <p class="card-text"><strong>${vacancySalary}:</strong> ${vacancy.salary}</p>
                <p class="card-text">
                    <strong>${vacancyExperience}:</strong>
                    ${vacancyExperienceFrom} ${vacancy.expFrom} ${vacancyExperienceTo} ${vacancy.expTo} ${vacancyExperienceYears}
                <p class="card-text mb-2"><strong>${vacancyIsActive}:</strong>
                    ${vacancy.isActive?string('${vacancyActiveYes}', '${vacancyActiveNo}')}</p>
                <p class="card-text">
                    <strong>${vacancyCompany}:</strong>
                    <a href="/company/${user.email}">${user.email}</a>
                </p>
                <p class="card-text"><strong>${vacancyCreatedDate}:</strong> ${vacancy.getFormattedCreatedDate()}</p>
                <p class="card-text"><strong>${vacancyUpdatedDate}:</strong> ${vacancy.getFormattedUpdatedDate()}</p>
            </div>
        </div>
        <div class="row">

            <#if userResumes?? && user.accountType == "applicant">
                <#if userRespondedToVacancy>
                    <div class="alert alert-warning text-center" role="alert">
                        <strong>${vacancyAlreadyRespondedToVacancy}</strong>
                    </div>
                <#else>
                    <div class="col text-center">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                data-bs-target="#applyModal">
                            ${vacancyButtonApply}
                        </button>
                    </div>
                </#if>
            </#if>

            <div class="col text-center">
                <a href="/" class="btn btn-primary">${home}</a>
                <a href="/vacancies" class="btn btn-primary">${vacancyButtonBack}</a>
            </div>
        </div>
    </div>

    <!-- Apply Modal -->
    <#if isAuthenticated>
        <div class="modal fade" id="applyModal" tabindex="-1" aria-labelledby="applyModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="/vacancies/apply/${vacancy.id}" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="applyModalLabel">${vacancyButtonApply} ${vacancy.name}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="resumeSelect" class="form-label">${vacancySelectResume}</label>
                                <select class="form-select" id="resumeSelect" name="resumeId" required>
                                    <#list userResumes as resume>
                                        <option value="${resume.id}">${resume.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${vacancyButtonClose}</button>
                            <button type="submit" class="btn btn-primary">${vacancyButtonApply}</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </#if>

    </body>
</@layout.layout>