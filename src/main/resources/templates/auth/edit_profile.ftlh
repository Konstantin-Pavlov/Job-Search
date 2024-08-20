<#import "../layout.ftlh" as layout>
<@layout.layout>
    <h1>Edit Profile</h1>
    <form action="/auth/profile/update-profile" method="post">
        <input type="hidden" name="id" value="${userDto.id}">
        <div>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${userDto.name}">
        </div>
        <div>
            <label for="age">Age:</label>
            <input type="number" id="age" name="age" value="${userDto.age}">
        </div>
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${userDto.email}">
        </div>
        <div>
            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" value="${userDto.phoneNumber}">
        </div>
        <div>
            <label for="accountType">Account Type:</label>
            <select id="accountType" name="accountType">
                <option value="employer" <#if userDto.accountType == "employer">selected</#if>>Employer</option>
                <option value="applicant" <#if userDto.accountType == "applicant">selected</#if>>Applicant</option>
            </select>
        </div>
        <button type="submit">Update Profile</button>
    </form>
</@layout.layout>