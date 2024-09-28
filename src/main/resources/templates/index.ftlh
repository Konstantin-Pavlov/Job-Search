<#import "layout.ftlh" as layout>
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

    <link rel="stylesheet" type="text/css" href="/static/css/index.css">
    <div class="main-content">
        <div class="welcome-container">
            <div class="welcome-header">
                <h1>${greeting}</h1>
            </div>
            <div class="welcome-image">
                <img src="/static/images/istockphoto-job-search.jpg" alt="Job Search">
            </div>
            <div class="welcome-links">
                <a href="/vacancies" class="btn btn-primary">${vacancies}</a>
                <a href="/resumes" class="btn btn-primary">${resumes}</a>
                <a href="/vacancies/vacancies-with-responses" class="btn btn-primary">${vacanciesAndResponses}</a>
            </div>
        </div>
    </div>
</@layout.layout>