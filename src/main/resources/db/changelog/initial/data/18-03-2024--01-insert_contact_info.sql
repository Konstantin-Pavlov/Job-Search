INSERT INTO contact_info (typeId, resumeId, `value`)
VALUES
    (1, 2, 'example@example.com'),
    (2, 1, '123-456-7890'),
    (3, 3, '1234 Main St');

-- In SQL, `value` is a reserved keyword,
-- so it cannot be used as a column name without escaping it.
-- You can use backticks (`) in MySQL or double quotes (") in other databases to escape the column name.