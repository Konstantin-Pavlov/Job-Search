<#import "../layout.ftlh" as layout>
<@layout.layout>
    <div class="container mt-4">
        <#if resumesRespondedToEmployerVacancies?size == 0>
            <div class="alert alert-info text-center" role="alert">
                <h2>You haven't added any vacancies yet</h2>
            </div>
        <#else>
            <div class="alert alert-info text-center" role="alert">
                <h2>You have ${resumesRespondedToEmployerVacancies?size} resume(s) responded to your vacancies</h2>
            </div>
            <div class="row d-flex justify-content-between">
                <#list resumesRespondedToEmployerVacancies as resume>
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card h-100 shadow-sm">
                            <img src="/static/images/default_image.jpg" class="card-img-top" alt="No image available">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${resume.name}</h5>
                                <p class="card-text mb-2"><strong>Salary:</strong> ${resume.getFormattedCreatedDate()}</p>
<#--                                <p class="card-text mb-2"><strong>this resumes responded to vacancy:</strong> ${resumes.getFormattedCreatedDate()}</p>-->
                                <a href="/resumes/${resume.id}" class="btn btn-primary mt-auto">More Info</a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>

        <div class="button-group">
            <a href="/profile" class="btn btn-secondary">Back to Profile</a>
            <a href="/" class="btn btn-secondary">Home</a>
        </div>
    </div>
</@layout.layout>