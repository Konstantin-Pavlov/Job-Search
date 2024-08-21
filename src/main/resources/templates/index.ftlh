<#import "layout.ftlh" as layout>
<#--<@layout.layout isAuthenticated=isAuthenticated username=username>-->
<@layout.layout>
<link rel="stylesheet" type="text/css" href="/static/css/index.css">
    <div class="main-content">
        <div class="welcome-container">
            <div class="welcome-header">
                <h1>Welcome to Job Search</h1>
            </div>
            <div class="welcome-image">
                <img src="/static/images/istockphoto-job-search.jpg" alt="Job Search">
            </div>
            <div class="welcome-links">
                <a href="/vacancies" class="btn btn-primary">View Vacancies</a>
                <a href="/resumes" class="btn btn-primary">View Resumes</a>
            </div>
        </div>
    </div>
</@layout.layout>