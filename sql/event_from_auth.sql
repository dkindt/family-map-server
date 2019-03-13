SELECT *
FROM events
WHERE eventID = ?
AND descendant = (
  SELECT username
  FROM auth
  WHERE token = ?
);