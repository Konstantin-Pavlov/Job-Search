<#import "../layout.ftlh" as main>
<@main.layout isAuthenticated=isAuthenticated username=username>
    <body>
    <div class="container">
        <h1 class="card-title">${vacancy.name}</h1>

        <p class="card-text mb-2">id: ${vacancy.id}</p>
        <p class="card-text mb-2">Description: ${vacancy.description}</p>
        <p class="card-text mb-2">category: ${vacancy.categoryId}</p>
        <p class="card-text mb-2">salary: ${vacancy.salary}</p>
        <p class="card-text mb-2"><strong>Work experience:
            </strong> from ${vacancy.expFrom} to ${vacancy.expTo} years</p>
        <p class="card-text mb-2"><strong>Active: </strong> ${vacancy.isActive?string('yes', 'no')}</p>
        <p class="card-text mb-2">author id: ${vacancy.authorId}</p>
        <p class="card-text mb-2">created date: ${vacancy.createdDate}</p>
        <p class="card-text mb-2">update time: ${vacancy.updateTime}</p>
    </div>
    <div class="mt-3">
        <a href="/" class="btn btn-primary">Home</a>
    </div>
    </body>
</@main.layout>
