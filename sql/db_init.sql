CREATE TABLE IF NOT EXISTS auth (
  token VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  PRIMARY KEY (token),
  FOREIGN KEY (username)
    REFERENCES users(username)
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS persons (
                                     personID VARCHAR(255) NOT NULL,
  descendant VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  father VARCHAR(255) DEFAULT NULL,
  mother VARCHAR(255) DEFAULT NULL,
  spouse VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (personID),
  FOREIGN KEY (descendant)
    REFERENCES users(username)
    ON DELETE RESTRICT,
  FOREIGN KEY (father)
    REFERENCES persons(personID)
    ON DELETE NO ACTION,
  FOREIGN KEY (mother)
    REFERENCES persons(personID)
    ON DELETE NO ACTION,
  FOREIGN KEY (spouse)
    REFERENCES persons(personID)
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  personID VARCHAR(255) NOT NULL,
  PRIMARY KEY (username),
  FOREIGN KEY (personID)
    REFERENCES persons(personID)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS events (
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