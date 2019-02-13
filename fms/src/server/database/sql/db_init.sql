DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS auth;

CREATE TABLE auth (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  token VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL
);

CREATE TABLE persons (
  uuid VARCHAR(255) NOT NULL PRIMARY KEY,
  descendant VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  father VARCHAR(255),
  mother VARCHAR(255),
  spouse VARCHAR(255),
  FOREIGN KEY (descendant) REFERENCES users(username),
  FOREIGN KEY (father) REFERENCES persons(uuid),
  FOREIGN KEY (mother) REFERENCES persons(uuid),
  FOREIGN KEY (spouse) REFERENCES persons(uuid)
);

CREATE TABLE users (
  username VARCHAR(255) NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL,
  firstName VARCHAR(100) NOT NULL,
  lastName VARCHAR(100) NOT NULL,
  gender VARCHAR(1),
  person_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (person_id) REFERENCES persons(uuid)
);

CREATE TABLE events (
  id VARCHAR(255) NOT NULL PRIMARY KEY,
  descendant VARCHAR(255) NOT NULL,
  person VARCHAR(255) NOT NULL,

  type VARCHAR(255),
  year INTEGER,
  FOREIGN KEY (descendant) REFERENCES users(username)
);


