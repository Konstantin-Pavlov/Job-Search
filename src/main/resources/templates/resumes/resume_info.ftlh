<#import "../layout.ftlh" as main>
<@main.layout isAuthenticated=isAuthenticated username=username>
    <body>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${resume.name}</h1>
                <p class="card-text"><strong>vacancy ID:</strong> ${resume.id}</p>
                <p class="card-text"><strong>Description:</strong> ${resume.applicantId}</p>
                <p class="card-text"><strong>Category:</strong> ${resume.categoryId}</p>
                <p class="card-text"><strong>Salary:</strong> ${resume.salary}</p>
                <p class="card-text"><strong>Active:</strong> ${resume.isActive?string('Yes', 'No')}</p>
                <p class="card-text"><strong>Created Date:</strong> ${resume.getFormattedCreatedDate()}</p>
                <p class="card-text"><strong>Updated Time:</strong> ${resume.getFormattedUpdatedDate()}</p>
            </div>
        </div>
        <div class="mt-3">
            <a href="/" class="btn btn-primary">Home</a>
        </div>
    </div>
    </body>
</@main.layout>