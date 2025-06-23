## SafeSteps
SAFE HIKING APPLICATION
Provides secure, role-based access to hiking trails, difficulty estimation, comments, weather forecast, and user management.

-------------------------------------------------------------------------------------------------------------------------------------

Prerequisites
Java 17

Maven 3.8+

PostgreSQL 16+

IntelliJ IDEA (recommended)

-----------------------------------------------------------------------------------------------------------------------------------

## Environment Configuration
Create a .env file (or use IntelliJ’s EnvFile plugin) with the following variables. I already added an example:

DB_HOST=localhost
DB_PORT=5432
DB_NAME=safesteps
DB_USERNAME=postgres
DB_PASSWORD=yourpassword

JWT_SECRET=your_jwt_secret_key
JWT_REFRESH_SECRET=your_jwt_refresh_secret_key
JWT_EXPIRATION_TIME=900000
JWT_REFRESH_EXPIRATION=1209600000

OLLAMA_API_URL=http://localhost:11434/api/generate
PORT=8080

-----------------------------------------------------------------------------------------------------------------------------------------

## Getting Started (Run Locally):

Clone the repository:
git clone https://github.com/PetruselGeorge/safesteps-be.git
cd safesteps-backend


Set Up: Database Initialization with External Tool (IntelliJ)
After cloning the backend repository: git clone https://github.com/PetruselGeorge/safesteps-be.git

\\

Step 1: Create External Tool
Open IntelliJ and go to:
File > Settings > Tools > External Tools

Click the "+" icon and fill in the fields as follows:

Field	Value
Name	DB Creation
Program	C:\Program Files\Git\bin\bash.exe (or your Git Bash path)
Arguments	startup-database.sh
Working Dir	[Project Root]/src/main/resources/bash.cfg/
Description	Creation of the database

Make sure these are also selected:
Synchronize files after execution

Open console for tool output

\\

Step 2: Run from Run/Debug Configurations
Go to Run > Edit Configurations

Click "+" and select Compound

Name it Start Project + DB

In the “Run configuration” list, add:

DB Creation (the tool you just created)

Spring Boot App (or your backend Application config)

Press OK and now you can run both.

-------------------------------------------------------------------------------------------------------------------------------------------------

## Architecture Overview

The backend follows a Layered Architecture, influenced by Clean Architecture principles:

Controller->Facade (BF)->Business Object (BO)->Data Access Layer (Repository + Entity);

..Controller: Handles HTTP requests, validates input, and delegates to the facade layer.

..Facade (BF): Mediates between controller and business logic, applying coordination and validation.

..Business Object (BO): Core business logic (e.g., login, trail handling).

..Data Access Layer: Spring Data JPA repositories and entity classes that map to PostgreSQL.

..DTO + Mapper: Convert between internal models and exposed API structures.

\\

This architecture enables:

..Separation of concerns and testability

..Scalability and easy extension

..Secure JWT-based authentication

..Modularity for clean code management

------------------------------------------------------------------------------------------------------------------------------------------------------
