# Task Manager Backend

## How to Run (Backend)
This is a Spring Boot application (Java 17+). To start the backend server:

1. **Build and run with Maven**:
   ```bash
   mvn spring-boot:run
   ```

2. **Or build a jar and run**:
   ```bash
   mvn clean package
   java -jar target/taskManager-backend-*.jar
   ```

The API will be available at [http://localhost:8080](http://localhost:8080).

### Endpoints
- **GET** `/tasks` — List all tasks
- **POST** `/tasks` — Create a task (body: `{"title":"My Task"}`)
- **PUT** `/tasks/{id}/complete` — Mark a task as complete

### Run Tests
To run the tests:

```bash
mvn test
```

---

## How to Run (Frontend)

This frontend is a minimal React app, located in `frontend/`.

1. Install dependencies:
   ```bash
   cd frontend
   npm install
   ```

2. Start the frontend server:
   ```bash
   npm start
   ```
   (Requires Node.js and npm. Runs on [http://localhost:3000](http://localhost:3000))

**Note:** The backend must be running at http://localhost:8080 for the frontend to work.

---

## GenAI Tooling Usage Reflection

This project was accelerated using AI tools (ChatGPT). Tasks supported by GenAI included backend code scaffolding (Spring Boot boilerplate), generating controller/service/tests, and React component generation for frontend integration.

**Example where AI output needed correction:**  
When implementing the POST /tasks endpoint, the AI initially suggested returning HTTP 200 OK with a null body if the task title was missing or empty. However, REST best practices require returning a 400 Bad Request in this situation. This issue was documented, and the code was corrected to respond with the proper error status, ensuring correct API semantics.

Manual organization was also needed to move files into proper packages and fix imports after initial AI-generated code. GenAI enabled rapid iteration, but careful review and adjustment was required to ensure the solution met project requirements.
