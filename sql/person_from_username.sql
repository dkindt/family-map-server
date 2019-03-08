SELECT * FROM persons
WHERE uuid = (
  SELECT person_id FROM users
  WHERE username=?
);