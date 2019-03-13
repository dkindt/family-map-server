SELECT * FROM persons
WHERE personID = (
  SELECT users.personID FROM users
  WHERE username=?
);