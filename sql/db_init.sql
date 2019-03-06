DROP TABLE IF EXISTS auth;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS events;

CREATE TABLE auth (
  token VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  PRIMARY KEY (token),
  FOREIGN KEY (username)
    REFERENCES users(username)
    ON DELETE NO ACTION
);

CREATE TABLE persons (
  uuid VARCHAR(255) NOT NULL,
  descendant VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  father VARCHAR(255) DEFAULT NULL,
  mother VARCHAR(255) DEFAULT NULL,
  spouse VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (uuid),
  FOREIGN KEY (descendant)
    REFERENCES users(username)
    ON DELETE RESTRICT,
  FOREIGN KEY (father)
    REFERENCES persons(uuid)
    ON DELETE NO ACTION,
  FOREIGN KEY (mother)
    REFERENCES persons(uuid)
    ON DELETE NO ACTION,
  FOREIGN KEY (spouse)
    REFERENCES persons(uuid)
    ON DELETE NO ACTION
);

CREATE TABLE users (
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  person_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (username),
  FOREIGN KEY (person_id)
    REFERENCES persons(uuid)
    ON DELETE CASCADE
);

CREATE TABLE events (
  id VARCHAR(255) NOT NULL,
  descendant VARCHAR(255) NOT NULL,
  person VARCHAR(255) NOT NULL,
  latitude FLOAT NOT NULL,
  longitude FLOAT NOT NULL,
  country VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  year INTEGER NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (descendant)
    REFERENCES users(username)
    ON DELETE NO ACTION,
  FOREIGN KEY (person)
    REFERENCES persons(uuid)
    ON DELETE NO ACTION
);

-- SEED TABLES WITH SOME DATA
INSERT INTO users
VALUES (
  'dkindt',
  'password',
  'dkindt@byu.edu',
  'dan',
  'kindt',
  'm',
  'test-person-id-dkindt'
);

INSERT INTO persons
VALUES (
  'test-person-id-dkindt',
  'dkindt',
  'dan',
  'kindt',
  'm',
  'dkindt-father-id',
  'dkindt-mother-id',
  'dkindt-spouse-id'
);
