<#import   "../layout.ftlh" as layout>
<@layout.layout>
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2 class="mb-0">User Profile</h2>
            </div>
            <div class="card-body">
                <#if userDto??>
                    <div class="mb-3">
                        <p><strong>Username:</strong> ${userDto.name}</p>
                        <p><strong>age:</strong> ${userDto.age}</p>
                        <p><strong>email:</strong> ${userDto.email}</p>
                        <p><strong>phone:</strong> ${userDto.phoneNumber}</p>
                        <p><strong>avatar:</strong> ${userDto.avatar}</p>
                        <p><strong>type:</strong> ${userDto.accountType}</p>
                        <p><strong>enabled:</strong> ${userDto.enabled?string('yes', 'no')}</p>
                        <!-- Add more user details as needed -->
                    </div>
                <#else>
                    <p class="text-danger">User details not available.</p>
                </#if>
                <div class="mt-3">
                    <a href="/" class="btn btn-primary">Home</a>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>