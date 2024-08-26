<#import "../layout.ftlh" as main>
<@main.layout>
<#--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">-->
    <link rel="stylesheet" type="text/css" href="/static/css/vacancies_with_responses.css">

    <div class="container mt-4">
        <h1 class="text-center mb-4">List of Vacancies with Responded Resumes</h1>

        <#if vacanciesWithResumesEntries?size == 0>
            <p class="text-center">No vacancies have responded resumes.</p>
        <#else>
            <#list vacanciesWithResumesEntries as entry>
                <div class="card mb-4 vacancy-card">
                    <div class="card-header">
                        <h2 class="card-title">${entry.key.name}</h2>
                    </div>
                    <div class="card-body">
                        <p class="card-text"><strong>Description:</strong> ${entry.key.description}</p>
                        <p class="card-text"><strong>Salary:</strong> ${entry.key.salary}</p>
                        <p class="card-text"><strong>Work experience:</strong> from ${entry.key.expFrom}
                            to ${entry.key.expTo} years</p>
                        <p class="card-text"><strong>Active:</strong> ${entry.key.isActive?string('yes', 'no')}</p>
                    </div>
                    <div class="card-footer">
                        <button class="spoiler-button" type="button" data-toggle="collapse"
                                data-target="#resumesCollapse${entry.key.id}" aria-expanded="false"
                                aria-controls="resumesCollapse${entry.key.id}">
                            Responded Resumes
                        </button>
                        <div id="resumesCollapse${entry.key.id}" class="collapse spoiler-content">
                            <#if (entry.value??) && (entry.value?size > 0)>
                                <div class="accordion" id="resumeAccordion${entry.key.id}">
                                    <#list entry.value as resume>
                                        <div class="card resume-card">
                                            <div class="card-header" id="heading${resume.id}">
                                                <h5 class="mb-0">
                                                    <button class="resume-button" type="button" data-toggle="collapse"
                                                            data-target="#collapse${resume.id}" aria-expanded="false"
                                                            aria-controls="collapse${resume.id}">
                                                        ${resume.name}
                                                    </button>
                                                </h5>
                                            </div>

                                            <div id="collapse${resume.id}" class="collapse resume-content"
                                                 aria-labelledby="heading${resume.id}"
                                                 data-parent="#resumeAccordion${entry.key.id}">
                                                <div class="card-body">
                                                    <p><strong>Category ID:</strong> ${resume.categoryId}</p>
                                                    <p><strong>Salary:</strong> ${resume.salary}</p>
                                                    <p><strong>Active:</strong> ${resume.isActive?string('yes', 'no')}
                                                    </p>
                                                    <p><strong>Created Date:</strong> ${resume.createdDate}</p>
                                                    <p><strong>Update Time:</strong> ${resume.updateTime}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            <#else>
                                <p>No resumes have responded to this vacancy.</p>
                            </#if>
                        </div>
                    </div>
                </div>
            </#list>
        </#if>

        <a href="/" class="home-btn">Home</a>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</@main.layout>