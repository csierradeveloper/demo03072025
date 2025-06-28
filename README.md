# demo03072025
Oppgave backend

## Language

For time and ease of development, this demo project was written in English.

Hvis det er vanlig å skrive kode på norsk med bedriften deres, kan jeg skrive på norsk i fremtiden. Forhåpentligvis er det greit å vise frem mine tekniske ferdigheter her på engelsk.

## Structure and Purpose

The application is divided into a Controller (AppointmentController) which contains the high-level business logic of this endpoint, intermediary services which serve as interfaces with external systems, and then classes representing (and mocking the functionality of) either external REST clients or a database managed by this service. Some unit tests were added for demonstration purposes, but not all behavior is currently tested.

Based on the assignment description, I would approach this problem of appointment-booking with this being an AppointmentService RESTful microservice, with ownership over the domain of Appointments. Therefore responsibility for the persistence and fetching of information about Appointments would reside with this microservice. If we wish to make this a pure integration layer then we would just replace AppointmentRepository (and its mocked database interactions) with another REST client, AppointmentServiceRestClient, and leave the data persistence to a dedicated microservice.

An Appointment is defined as a relationship between a User (client, someone who wants to purchase insurance), a specific Property (an object which the User wants to insure, such as a car), a date and time on which the appointment is expected to take place, an Employee who will meet with the User (serving as an insurance agent), and an AppointmentState (indicating if the appointment has been freshly created, if the user has been notified of it, if the user has confirmed it, or if the appointment is completed). The specified business logic only provided for User and Appointment creation, but in a real case we'd want to validate the rest of the request data as well, and handle the case of pre-existing users or an incomplete Appointment (instead of creating duplicates).

Business logic is, upon receipt of a request to create an appointment:

1. Validate that the Property, Employee, and Office indicated in the request with their ID values exist in external systems. Also validate that the appointmentTime is in the future; it would be nonsensical to create fresh appointments in the past, and indicates a mistake in data entry. Request will fail here if any of these properties are invalid.
2. Search for an already-existing User matching the provided information. If one already exists in the system, use them (and their userId) going forward. If none exists, create one and use their userId.
3. Search for an already-existing Appointment in the system matching the provided information. If one already exists, and the user has already been notified of the Appointment (Appointment state in anything other than CREATED), exit early indicating that the request is invalid (as the Appointment is already created and User already notified).
4. Submit a notification to the User about the Appointment through the NotificationService. This is assumed to take a recipient email address (thought was given to implying a more generalized solution allowing faxing/paper mail/etc, but I decided to keep this simple), a message template, and metadata used to populate that template. In this case, it is assumed that what would be relevant for populating an Appointment notification message would be information about the Appointment, so that is attached to the NotificationRequest.
5. If Notification delivery fails, return a 500 indicating a problem with the service. We might want other behavior and I considered implying that in the code, but decided to keep this section simple to not go beyond the scope of the assignment.
6. Return a 200 response including the unique appointment ID, and the appointmentState.

## Testing

This is a standard gradle Spring Boot application. For local testing I have been running it within Intellij Idea, but it can also be run through the command line with `$ ./gradlew bootRun`. Application was written using Java 17.

For testing the endpoint I used Postman, but curl or other forms of sending messages to a local endpoint `POST localhost:8080/appointment/create` can also be used. Only the appointmentId and appointmentState are expected in the response body, the rest can be observed from logs.

### Sample payload:

```json
{
    "user": {
        "name": "other",
        "emailAddress": "abc@def.com"
    },
    "propertyId": "1",
    "officeId": "1",
    "agentId": "1",
    "appointmentTime": "2026-05-20T13:12:18.825Z"
}
```

### Modifying payload for different effects

- `propertyId`, `officeId`, or `agentId` with value of "1" will succeed, values other than "1" will fail validation (to show what that looks like). `appointmentTime` set in the past will fail validation.
- `user.name` will "find" an existing user if its value is `CREATED` or `NOTIFIED`, otherwise will "create" a new user with the information provided.
- `user.name` values of `CREATED` and `NOTIFIED` will also lead to "finding" an existing Appointment with the matching AppointmentState. This is to show that pre-existing Appointments in state `CREATED` will continue through the flow (to allow completion of interrupted requests), and those in other states (such as NOTIFIED) will fail as an invalid request.
- No means is provided for simulating failures in NotificationService, though that can be added if desired.

## Future Work

An attempt was made to keep the scope of this work within control, but there is much more to do to fully sketch in a RESTful microservice fulfilling the function described in the assignment. Future work would focus on the following:

- Sketch in an integration with a request/token validation service using REST interceptors. We want to make sure that Appointments can only be made by trusted systems/users.
- Sketch in integration testing in a separate module, allowing for requests to the app's endpoint and testing endpoints to confirm the results of calls made. Would want this to confirm happy-path behavior matches expectations.
- Sketch in integration with deployment pipelines and business environments. This is more open-ended depending on what is used within your company; in my experience this is usually Jenkins pipelines, service deployed to Docker containers managed by Kubernetes, integration with a cloud services provider such as AWS, infrastructure defined by an infrastructure-as-code solution such as Terraform, etc.

## Parting Thoughts

I tried to balance being reasonably thorough with also not providing too much code to read, but please let me know if there was anything considered crucial that I did not think to include or at least indicate would be relevant.

Looking forward to discussing this with you!

Mvh, Carlos Sierra