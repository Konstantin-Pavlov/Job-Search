<#import "spring.ftl" as spring>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-2">
    <div class="container-fluid">
        <!-- Navbar content -->
        <#if isAuthenticated>
            <a href="/auth/profile" class="btn btn-outline-info m-2">Profile</a>
            <a href="/logout" class="btn btn-outline-info m-2">Logout</a>
        <#else>
            <a href="/auth/login" class="btn btn-outline-info m-2">Login</a>
            <a href="/auth/register" class="btn btn-outline-info m-2">Register</a>
        </#if>
    </div>
</nav>