<#import "../layout.ftlh" as layout>
<@layout.layout>
    <h1>Edit Resume</h1>
    <form action="/profile/updateResume" method="post">
        <input type="hidden" name="resumeId" value="${resume.id}">
        <input type="hidden" name="userId" value="${userDto.id}">
        <div>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${resume.name}">
        </div>
        <div>
            <label for="categoryId">Category ID:</label>
            <input type="text" id="categoryId" name="categoryId" value="${resume.categoryId}">
        </div>
        <div>
            <label for="salary">Salary:</label>
            <input type="text" id="salary" name="salary" value="${resume.salary}">
        </div>
        <div>
            <label for="isActive">Is Active:</label>
            <input type="checkbox" id="isActive" name="isActive" <#if resume.isActive>checked</#if>>
        </div>
        <button type="submit">Update Resume</button>
    </form>
</@layout.layout>