<#import "spring.ftl" as spring>
<#macro layout>

    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
        <!-- Link to the external CSS file -->
        <link rel="stylesheet" type="text/css" href="/static/css/layout.css">
        <!-- Link to the favicon -->
        <link rel="icon" type="image/png" href="/static/images/favicon.png">

        <title>Job Search Application</title>
    </head>

    <body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-2 fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Job Search</a>
<#--            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"-->
<#--                    data-bs-target="#navbarSupportedContent"-->
<#--                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">-->
<#--                <span class="navbar-toggler-icon"></span>-->
<#--            </button>-->
<#--            <div class="collapse navbar-collapse" id="navbarSupportedContent">-->
<#--                <ul class="navbar-nav me-auto mb-2 mb-lg-0">-->
<#--                    <li class="nav-item">-->
<#--                        <a class="nav-link active" aria-current="page" href="/">Home</a>-->
<#--                    </li>-->
<#--                    <li class="nav-item">-->
<#--                        <a class="nav-link" href="/create">Create</a>-->
<#--                    </li>-->
<#--                    <li class="nav-item dropdown">-->
<#--                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"-->
<#--                           data-bs-toggle="dropdown" aria-expanded="false">-->
<#--                            Dropdown-->
<#--                        </a>-->
<#--                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">-->
<#--                            <li><a class="dropdown-item" href="#">Action</a></li>-->
<#--                            <li><a class="dropdown-item" href="#">Another action</a></li>-->
<#--                            <li>-->
<#--                                <hr class="dropdown-divider">-->
<#--                            </li>-->
<#--                            <li><a class="dropdown-item" href="#">Something else here</a></li>-->
<#--                        </ul>-->
<#--                    </li>-->
<#--                    <li class="nav-item">-->
<#--                        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>-->
<#--                    </li>-->
<#--                </ul>-->
<#--            </div>-->

            <#assign known =  SPRING_SECURITY_CONTEXT??>
            <#if known>
                <#assign
                user = SPRING_SECURITY_CONTEXT.authentication.principal
                name = user.getUsername()
                >
            <div class="navbar-text">
<#--                <a href="/profile/${name}" class="text-light m-2 m-sm-0"> link to the profile by email-->
                    <span class="navbar-text">you are logged in as ${name}!</span>
                    <#list user.authorities as auth>
                        <span class=navbar-text">your role is ${auth}</span>
                        <#if auth?has_next>
                            /
                        </#if>
                    </#list>
<#--                </a>-->
            </div>
                <a href="/profile" class="btn btn-outline-info ms-2">Profile</a>
                <a href="/logout" class="btn btn-outline-info m-2" id="logout">Logout</a>

            <#else>
                <span class="navbar-text">you are not logged in</span>
                <a href="/auth/login"
                   class="btn btn-outline-info m-2">Login</a>
                <a href="/auth/register"
                   class="btn btn-outline-info m-2">Register</a>
            </#if>
        </div>
    </nav>

    <div class="container  mt-5 pt-5">

        <#nested spring>

    </div>


    </body>
    </html>
</#macro>