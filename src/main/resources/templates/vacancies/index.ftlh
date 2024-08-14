<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row d-flex justify-content-between">
        <#list vacancies as vacancie>
            <div class="card col-6 m-2" style="width: 18rem;">
<#--                <img src="static.images/default_avatar.jpg" class="card-img-top" alt="No image available">-->
                <div class="card-body">
                    <h5 class="card-title">${vacancie.name}</h5>
                    <p class="card-text">description: ${vacancie.description}</p>
                    <p class="card-text">salary: ${vacancie.salary}</p>
<#--                    <p class="card-text">${vacancie.expFrom}</p>-->
<#--                    <p class="card-text">${vacancie.expTo}</p>-->
                    <p class="card-text">active: ${vacancie.isActive?string('yes', 'no')}</p>
                    <a href="#" class="btn btn-primary">More Info</a>
                </div>
            </div>
        </#list>
    </div>
</@main.layout>