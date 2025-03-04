#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ENV_FILE="$SCRIPT_DIR/../../../../.env"

if [ -f "$ENV_FILE" ]; then
    echo "Sourcing .env from $ENV_FILE"
    set -o allexport
    # shellcheck disable=SC1090
    source "$ENV_FILE"
    set +o allexport
else
    echo ".env file not found at $ENV_FILE"
fi

export PGPASSWORD="$DB_PASSWORD"

export PATH="/c/Progra~1/PostgreSQL/16/bin:$PATH"

if ! command -v psql >/dev/null; then
    echo "psql command not found in PATH: $PATH"
    exit 1
fi

RESULT=$(psql -U "$DB_USERNAME" -h "$DB_HOST" -p "$DB_PORT" -tAc "SELECT 1 FROM pg_database WHERE datname='$DB_NAME'")
if [ "$RESULT" == "1" ]; then
    echo "Database '$DB_NAME' already exists"
else
    echo "Database '$DB_NAME' doesn't exist. Creating..."
    createdb -U "$DB_USERNAME" -h "$DB_HOST" -p "$DB_PORT" "$DB_NAME"
    # shellcheck disable=SC2181
    if [ $? -eq 0 ]; then
        echo "Database '$DB_NAME' successfully created."
    else
        echo "Error creating database '$DB_NAME'."
        exit 1
    fi
fi
