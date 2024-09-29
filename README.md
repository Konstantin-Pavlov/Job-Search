# Spring MVC Project

This is a Spring MVC project for a job search application. Currently, it includes functionality for login and registration, as well as the display of job vacancies and resumes. The profile display changes depending on the type of user (employer or applicant).
* If the user is an applicant, the profile shows their resumes. If the user is an employer, the profile displays job vacancies.
* Users can also update or edit their resumes or vacancies, as well as modify their profiles.

This application is currently under development.

## Technologies Used

- **Spring Framework**: Core framework for building the application.
  - Spring MVC: For handling web requests and responses.
  - Spring Boot: For simplifying the setup and development process.
  - Spring Security: For securing the application.
- **Freemarker**: For templating and dynamic content generation.
- **Hibernate**: For ORM (Object-Relational Mapping) and database interactions.
- **H2 Database**: In-memory database for development and testing.
- **Bootstrap**: For responsive and modern UI design.
- **Lombok**: For reducing boilerplate code in Java classes.
- **Maven**: For project build and dependency management.
- **Localization**: Support for multiple languages - English (en), Russian (ru) and Kyrgyz (kg).

## Localization

The application supports multiple languages to cater to a diverse user base. Currently, the following languages are supported:
- **English (en)**
- **Russian (ru)**
- **Kyrgyz (kg)**

### Adding New Translations

To add new translations, follow these steps:
1. Create a new properties file for the desired language in the `resources` directory. For example, `resource_fr.properties` for French.
2. Add the translated key-value pairs to the new properties file.
3. Ensure that the keys in the new properties file match those in the default properties file.

Example of adding translations:

**Default properties file (`resource.properties`):**
```properties
profile.edit.popup.title=Profile updated successfully
profile.edit.popup.message=successfully updated their profile