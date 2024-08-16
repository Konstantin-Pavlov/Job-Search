<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-2">
    <div class="container-fluid d-flex justify-content-between align-items-center">
        <a class="navbar-brand" href="/">Job Search Platform</a>
        <div class="d-flex flex-grow-1 align-items-center">
            <div class="d-flex flex-grow-1 justify-content-center">
                <#if isAuthenticated>
                    <span class="navbar-text">
                        Hi, ${username}! Welcome back.
                    </span>
                <#else>
                    <!-- Empty placeholder to center buttons when not authenticated -->
                <span class="navbar-text">you are not logged in</span>
                </#if>
            </div>
            <div class="d-flex">
<#--                <form class="d-flex me-3">-->
<#--                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">-->
<#--                    <button class="btn btn-outline-success" type="submit">Search</button>-->
<#--                </form>-->
                <#if isAuthenticated>
                    <a href="/auth/profile" class="btn btn-outline-info ms-2">Profile</a>
                    <a href="/logout" class="btn btn-outline-info ms-2">Logout</a>
                <#else>
                    <a href="/auth/login" class="btn btn-outline-info ms-2">Login</a>
                    <a href="/auth/register" class="btn btn-outline-info ms-2">Register</a>
                </#if>
            </div>
        </div>
    </div>
</nav>
