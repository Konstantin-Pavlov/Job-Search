<#import "../layout.ftlh" as main>
<@main.layout isAuthenticated=isAuthenticated username=username>
    <div class="container mt-4">
        <div class="row d-flex justify-content-between">
            <#list vacancies as vacancy>
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
        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary">Home</a>
            </div>
        </div>
    </div>
</@main.layout>