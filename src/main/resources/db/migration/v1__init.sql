CREATE TABLE departments (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           salary NUMERIC(10,2) NOT NULL,
                           departments_id INT NOT NULL,
                           manager BOOLEAN NOT NULL DEFAULT FALSE,
                           FOREIGN KEY (departments_id) REFERENCES departments(id) ON DELETE CASCADE
);

