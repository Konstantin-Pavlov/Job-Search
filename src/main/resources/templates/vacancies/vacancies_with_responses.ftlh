<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container mt-4">
        <h1 class="text-center mb-4">List of Vacancies with Responded Resumes</h1>

        <#if vacanciesWithResumes?size == 0>
            <p class="text-center">No vacancies have responded resumes.</p>
        <#else>
            <#list vacanciesWithResumes?keys as vacancy>
                <div class="card mb-4">
                    <div class="card-header">
                        <h2 class="card-title">${vacancy.name}</h2>
                    </div>
                    <div class="card-body">
                        <p class="card-text"><strong>Description:</strong> ${vacancy.description}</p>
                        <p class="card-text"><strong>Salary:</strong> ${vacancy.salary}</p>
                        <p class="card-text"><strong>Work experience:</strong> from ${vacancy.expFrom} to ${vacancy.expTo} years</p>
                        <p class="card-text"><strong>Active:</strong> ${vacancy.isActive?string('yes', 'no')}</p>
                    </div>
                    <div class="card-footer">
                        <h3>Responded Resumes</h3>
                        <#if vacanciesWithResumes[vacancy]?size == 0>
                            <p>No resumes have responded to this vacancy.</p>
                        <#else>
                            <ul class="list-group">
                                <#list vacanciesWithResumes[vacancy] as resume>
                                    <li class="list-group-item">
                                        <p><strong>Name:</strong> ${resume.name}</p>
                                        <p><strong>Category ID:</strong> ${resume.categoryId}</p>
                                        <p><strong>Salary:</strong> ${resume.salary}</p>
                                        <p><strong>Active:</strong> ${resume.isActive?string('yes', 'no')}</p>
                                        <p><strong>Created Date:</strong> ${resume.createdDate}</p>
                                        <p><strong>Update Time:</strong> ${resume.updateTime}</p>
                                    </li>
                                </#list>
                            </ul>
                        </#if>
                    </div>
                </div>
            </#list>
        </#if>

        <div class="button-group">
            <a href="/profile" class="btn btn-secondary">Back to Profile</a>
            <a href="/" class="btn btn-secondary">Home</a>
        </div>
    </div>
</@main.layout>