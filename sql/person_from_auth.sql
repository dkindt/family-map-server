SELECT *
FROM persons
WHERE personID = ?
AND descendant = (
  SELECT username
  FROM auth
  WHERE token = ?
);