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
        <div class="row">
            <#list vacancies.content as vacancy>
                <div class="col-6 col-md-4 mb-4 d-flex">
                    <div class="card h-100 shadow-sm w-100">
                        <#--                        <img src="/static/images/avatars/default_user_avatar.png" class="card-img-top" alt="No image available">-->
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${vacancy.name}</h5>
                            <p class="card-text text-muted mb-2">${vacancyDescription}: ${vacancy.description}</p>
                            <p class="card-text mb-2"><strong>${vacancySalary}:</strong> ${vacancy.salary}</p>
                            <p class="card-text">
                                <strong>${vacancyExperience}:</strong>
                                ${vacancyExperienceFrom} ${vacancy.expFrom} ${vacancyExperienceTo} ${vacancy.expTo} ${vacancyExperienceYears}
                            <p class="card-text mb-2"><strong>${vacancyIsActive}:</strong>
                                ${vacancy.isActive?string('${vacancyActiveYes}', '${vacancyActiveNo}')}</p>
                            <a href="/vacancies/${vacancy.id}" class="btn btn-primary mt-auto">${vacancyButtonInfo}</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

        <div class="pagination-container">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <#if currentPage?is_number && (currentPage > 0)>
                        <li class="page-item">
                            <a class="page-link" href="/vacancies?page=${currentPage - 1}&size=${pageSize}">${paginationButtonPrevious}</a>
                        </li>
                    </#if>
                    <#if totalPages?is_number>
                        <#list 0..totalPages - 1 as page>
                            <li class="page-item <#if page == currentPage>active</#if>">
                                <a class="page-link" href="/vacancies?page=${page}&size=${pageSize}">${page + 1}</a>
                            </li>
                        </#list>
                    </#if>
                    <#if currentPage?is_number && (currentPage < (totalPages - 1))>
                        <li class="page-item">
                            <a class="page-link" href="/vacancies?page=${currentPage + 1}&size=${pageSize}">${paginationButtonNext}</a>
                        </li>
                    </#if>
                </ul>
            </nav>
        </div>

        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary">${home}</a>
            </div>
        </div>
    </div>
</@layout.layout>