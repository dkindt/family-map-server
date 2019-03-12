SELECT *
FROM events
WHERE id = ?
AND descendant = (
  SELECT username
  FROM auth
  WHERE token = ?
);