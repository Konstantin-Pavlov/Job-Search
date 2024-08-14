<#import "../layout.ftlh" as layout>
<@layout.layout>

    <form action="/create" method="post">
        <div class="input-group mb-3">
            <span class="input-group-text" id="title">Title</span>
            <input type="text" class="form-control" aria-label="Title" aria-describedby="title" name="name">
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="year">Year</span>
            <input type="number" class="form-control" aria-label="Year" aria-describedby="year" name="year">
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="description">Description</span>
            <textarea class="form-control" aria-label="Description" name="description"></textarea>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="director">Director</span>
            <select class="form-select" aria-label="Director" aria-describedby="director" name="directorId">
                <option selected>Open this select menu</option>
                <#list directors as item>
                    <option value="${item.id}">${item.fullName}</option>
                </#list>
            </select>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="director">Cast</span>
            <select class="form-select" aria-label="Cast" aria-describedby="cast" name="castId">
                <option selected>Open this select menu</option>
                <#list casts as item>
                    <option value="${item.id}">${item.fullName}</option>
                </#list>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

</@layout.layout>