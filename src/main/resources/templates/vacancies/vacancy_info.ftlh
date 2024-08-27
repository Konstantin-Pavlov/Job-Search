<#import "../layout.ftlh" as main>
<@main.layout>
    <body>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${vacancy.name}</h1>

                <p class="card-text"><strong>Описание:</strong> ${vacancy.description}</p>
                <p class="card-text"><strong>Категория:</strong> ${category.name}</p>
                <p class="card-text"><strong>Зарплата:</strong> ${vacancy.salary}</p>
                <p class="card-text">
                    <strong>Опыт работы:</strong> От ${vacancy.expFrom} до ${vacancy.expTo} лет</p>
                <p class="card-text"><strong>Активно:</strong> ${vacancy.isActive?string('Да', 'Нет')}</p>
                <p class="card-text">
                    <strong>Компания:</strong>
                    <a href="/company/${user.email}">${user.email}</a>
                </p>
                <p class="card-text"><strong>Дата создания:</strong> ${vacancy.getFormattedCreatedDate()}</p>
                <p class="card-text"><strong>Время обновления:</strong> ${vacancy.getFormattedUpdatedDate()}</p>
            </div>
        </div>
        <div class="row">
            <div class="col text-center">
                <a href="/" class="btn btn-primary">Домой</a>
            </div>
        </div>
    </div>
    </body>
</@main.layout>