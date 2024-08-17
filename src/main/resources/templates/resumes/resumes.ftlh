<#import "../layout.ftlh" as main>
<@main.layout isAuthenticated=isAuthenticated username=username>
    <div class="container mt-4">
        <div class="row d-flex justify-content-between">
            <#list resumes as resume>
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="card h-100 shadow-sm">
                        <img src="/static/images/default_image.jpg" class="card-img-top" alt="No image available">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${resume.name}</h5>
                            <p class="card-text mb-2"><strong>Salary:</strong> ${resume.getFormattedCreatedDate()}</p>
                            <a href="/resumes/${resume.id}" class="btn btn-primary mt-auto">More Info</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</@main.layout>