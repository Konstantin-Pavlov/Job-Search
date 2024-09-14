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
        <div class="row d-flex justify-content-between">
            <#list resumes as resume>
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="card h-100 shadow-sm">
                        <img src="/static/images/default_image.jpg" class="card-img-top" alt="No image available">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${resume.name}</h5>
                            <p class="card-text mb-2"><strong>${resumeSalary}:</strong> ${resume.getFormattedCreatedDate()}</p>
                            <a href="/resumes/${resume.id}" class="btn btn-primary mt-auto">${resumeDetails}</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary">${home}</a>
            </div>
        </div>
    </div>
</@layout.layout>