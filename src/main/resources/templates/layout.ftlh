<#import "spring.ftl" as spring>
<#macro layout isAuthenticated=false, username="">
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
        <style>
            /* Custom Styles */

            .navbar-text {
                font-weight: normal; /* Adjust to normal if bold is too heavy */
                font-size: 0.9em; /* Make the font size smaller */
                color: #f8f9fa; /* Light color to contrast with dark background */
                margin-right: 1rem;
                white-space: nowrap;
            }
            .btn {
                margin-left: 0.5rem; /* Space between buttons */
            }
            .d-flex {
                display: flex;
            }
            .flex-grow-1 {
                flex-grow: 1;
            }
            .ms-auto {
                margin-left: auto;
            }
            .me-3 {
                margin-right: 1rem;
            }
            .navbar-brand {
                margin-right: auto;
            }

            .card {
                transition: transform 0.2s, box-shadow 0.2s;
            }

            .card:hover {
                transform: scale(1.05);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            .card-title {
                font-size: 1.25rem;
                font-weight: bold;
            }

            .card-text {
                font-size: 1rem;
            }

            .card-text strong {
                color: #333;
            }

            .error-message {
                color: yellow;
            }

            .btn-primary {
                background-color: #28a745; /* Green */
                border-color: #28a745; /* Green */
            }

            .btn-primary:hover {
                background-color: #218838; /* Darker green */
                border-color: #1e7e34; /* Even darker green */
            }

            .btn-outline-info {
                color: #28a745; /* Green */
                border-color: #28a745; /* Green */
            }

            .btn-outline-info:hover {
                color: #fff;
                background-color: #28a745; /* Green */
                border-color: #28a745; /* Green */
            }

            .container {
                background-color: #2a72b8; /* White background for the container */
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .navbar-text {
                font-weight: bold;
                font-size: 1.1em;
                color: #f8f9fa; /* Light color to contrast with dark background */
                margin-right: 1rem;
            }

            .navbar-text span {
                color: #17a2b8; /* Info color */
            }
        </style>
        <title>Document</title>
    </head>
    <body>

    <#--    <#assign isAuthenticatedValue = isAuthenticated?c>-->
    <#include "navigation/navigation.ftlh">

    <div class="container">

        <#nested>

    </div>

    </body>
    </html>
</#macro>