SELECT *
FROM persons
WHERE uuid = ?
AND descendant = (
  SELECT username
  FROM auth
  WHERE token = ?
);