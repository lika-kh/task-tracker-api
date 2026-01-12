# Task Tracker API

Task Tracker API is a simple backend application that allows you to:
- Create projects
- Create tasks
- Assign tasks to users
- Update task status (TODO, IN_PROGRESS, DONE)
- Use JWT authentication



## Technologies Used

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- Maven


## Project Structure (Brief)
src/main/java/com/example/task_tracker_api
├── controller 
├── service 
│ └── impl
├── repository 
├── entity 
├── dto 
├── security 
└── TaskTrackerApiApplication.java


## How to Run the Application

### 1. Prerequisites
- Java 17 or higher
- Maven

### 2. Clone the Repository
```bash
git clone https://github.com/lika-kh/task-tracker-api.git

## Postman Collection
A Postman collection is provided to test the API.

Location:
postman/Task Tracker API.postman_collection.json

How to use:
1. Open Postman
2. Click Import
3. Select the JSON file from the postman folder
4. Run the requests in order (Auth → Projects → Tasks)

