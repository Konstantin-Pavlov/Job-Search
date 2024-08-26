<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container mt-4">
        <#if vacanciesUserResponded?size == 0>
            <div class="alert alert-info text-center" role="alert">
                <h2>You haven't responded to any vacancies yet</h2>
            </div>
        <#else>
            <div class="alert alert-info text-center" role="alert">
                <h2>You have responded to ${vacanciesUserResponded?size} vacancies</h2>
            </div>
            <div class="row d-flex justify-content-between">
                <#list vacanciesUserResponded as vacancy>
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card h-100 shadow-sm">
                            <img src="/static/images/avatars/default_user_avatar.png" class="card-img-top" alt="No image available">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${vacancy.name}</h5>
                                <p class="card-text text-muted mb-2">Description: ${vacancy.description}</p>
                                <p class="card-text mb-2"><strong>Salary:</strong> ${vacancy.salary}</p>
                                <p class="card-text mb-2"><strong>Work experience:</strong> from ${vacancy.expFrom} to ${vacancy.expTo} years</p>
                                <p class="card-text mb-2"><strong>Active:</strong> ${vacancy.isActive?string('yes', 'no')}</p>
                                <a href="/vacancies/${vacancy.id}" class="btn btn-primary mt-auto">More Info</a>
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
            <a href="/profile" class="btn btn-secondary">Back to Profile</a>
            <a href="/" class="btn btn-secondary">Home</a>
        </div>
    </div>
</@main.layout>