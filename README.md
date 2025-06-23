# SafeSteps
SAFE HIKING APPLICATION
Provides secure, role-based access to hiking trails, difficulty estimation, comments, weather forecast, and user management.

Prerequisites
Java 17

Maven 3.8+

PostgreSQL 16+

IntelliJ IDEA (recommended)

Environment Configuration
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


Getting Started (Run Locally):

Clone the repository:
git clone https://github.com/PetruselGeorge/safesteps-be.git
cd safesteps-backend


Set Up: Database Initialization with External Tool (IntelliJ)
After cloning the backend repository: git clone https://github.com/your-username/safesteps-backend.git
