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
                <h1 class="card-title">${resume.name}</h1>
                <p class="card-text"><strong>Resume ID:</strong> ${resume.id}</p>
                <p class="card-text"><strong>${resumeAuthor}:</strong> ${userDto.name}</p>
                <p class="card-text"><strong>${resumeCategory}:</strong> ${categoryDto.name}</p>
                <p class="card-text"><strong>${resumeSalary}:</strong> ${resume.salary}</p>
                <p class="card-text"><strong>${resumeIsActive}:</strong> ${resume.isActive?string('${resumeActiveYes}', '${resumeActiveNo}')}</p>
                <p class="card-text"><strong>${resumeCreatedDate}:</strong> ${resume.getFormattedCreatedDate()}</p>
                <p class="card-text"><strong>${resumeUpdatedDate}:</strong> ${resume.getFormattedUpdatedDate()}</p>
            </div>
        </div>
        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary">${home}</a>
                <a href="/resumes" class="btn btn-primary">back to resumes</a>
            </div>
        </div>
    </div>
    </body>
</@layout.layout>