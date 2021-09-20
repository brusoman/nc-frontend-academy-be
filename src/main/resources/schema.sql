CREATE TABLE IF NOT EXISTS users  (
        id BIGINT PRIMARY KEY ,
        login VARCHAR(255) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        name VARCHAR(100) NOT NULL,
        surname VARCHAR(100) NOT NULL,
        admin BOOLEAN NOT NULL
);

COMMENT ON TABLE users IS 'Table contains user data';
COMMENT ON COLUMN users.id IS 'Personal user database ID';
COMMENT ON COLUMN users.admin IS 'Boolean const for verification admin preferences';
COMMENT ON COLUMN users.email IS 'User email';
COMMENT ON COLUMN users.login IS 'User login';
COMMENT ON COLUMN users.password IS 'Password hash is hash of hashes';
COMMENT ON COLUMN users.surname IS 'Users last name';
COMMENT ON COLUMN users.name IS 'Users name';


CREATE SEQUENCE IF NOT EXISTS user_id_sequence START WITH 1 MINVALUE 1 INCREMENT BY 1;
COMMENT ON SEQUENCE user_id_sequence IS 'Sequence for ID of table users';


CREATE TABLE IF NOT EXISTS tasks  (
    id BIGINT PRIMARY KEY,
    number INTEGER NOT NULL,
    section VARCHAR(255) NOT NULL,
    deadline TIMESTAMP NOT NULL,
    description VARCHAR(2000) UNIQUE NOT NULL,
    file_path VARCHAR(100) UNIQUE,
    attempts_max INTEGER,
    task_name VARCHAR(100) UNIQUE
);

COMMENT ON TABLE tasks IS 'Table contains tasks & path on sample .png for front';
COMMENT ON COLUMN tasks.id IS 'Task id';
COMMENT ON COLUMN tasks.number IS 'Task number';
COMMENT ON COLUMN tasks.section IS 'Task section';
COMMENT ON COLUMN tasks.description IS 'Unique task description';
COMMENT ON COLUMN tasks.file_path IS 'Unique path to sample picture';
COMMENT ON COLUMN tasks.task_name IS 'Task varchar name';

CREATE SEQUENCE IF NOT EXISTS task_id_sequence START WITH 1 MINVALUE 1 INCREMENT BY 1;
COMMENT ON SEQUENCE task_id_sequence IS 'Sequence for ID of table tasks';

CREATE TABLE IF NOT EXISTS users_tasks  (
    user_id BIGINT REFERENCES users(id),
    task_id BIGINT REFERENCES tasks(id),
    progress NUMERIC NOT NULL,
    path_result VARCHAR(255) NOT NULL,
    path_upload VARCHAR(255) NOT NULL,
    attempt_number INTEGER NOT NULL,
    time TIMESTAMP NOT NULL,
    CONSTRAINT users_tasks_pk PRIMARY KEY (user_id, task_id, attempt_number)
);

