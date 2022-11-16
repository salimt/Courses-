-- Python for Everybody Database Handout

-- https://www.pg4e.com/lectures/03-Techniques.sql

sudo -u postgres psql postgres
\l       -- list databases
-- Should already be done: CREATE USER pg4e WITH PASSWORD 'secret';
CREATE DATABASE discuss WITH OWNER 'pg4e' ENCODING 'UTF8';
\q       -- quit

psql discuss pg4e

\dt      -- List relations (tables)

CREATE TABLE account (
  id SERIAL,
  email VARCHAR(128) UNIQUE,
  created_at DATE NOT NULL DEFAULT NOW(),
  updated_at DATE NOT NULL DEFAULT NOW(),
  PRIMARY KEY(id)
);

CREATE TABLE post (
  id SERIAL,
  title VARCHAR(128) UNIQUE NOT NULL, -- Will extend with ALTER
  content VARCHAR(1024),
  account_id INTEGER REFERENCES account(id) ON DELETE CASCADE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY(id)
);

-- Allow multiple comments
CREATE TABLE comment (
  id SERIAL,
  content TEXT NOT NULL,
  account_id INTEGER REFERENCES account(id) ON DELETE CASCADE,
  post_id INTEGER REFERENCES post(id) ON DELETE CASCADE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY(id)
);

CREATE TABLE fav (
  id SERIAL,
  oops TEXT,  -- Will remove later with ALTER
  post_id INTEGER REFERENCES post(id) ON DELETE CASCADE,
  account_id INTEGER REFERENCES account(id) ON DELETE CASCADE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  UNIQUE(post_id, account_id),
  PRIMARY KEY(id)
);

\d+ fav

-- ALTER TABLE

ALTER TABLE post ALTER COLUMN content TYPE TEXT;

ALTER TABLE fav DROP COLUMN oops;

ALTER TABLE fav ADD COLUMN howmuch INTEGER;

-- Read SQL Commands fom a script

-- Download https://www.pg4e.com/lectures/03-Techniques-Load.sql

\i 03-Techniques-Load.sql

-- Dates

SELECT NOW();

SELECT NOW() AT TIME ZONE 'utc';
SELECT NOW() AT TIME ZONE 'HST';

SELECT * FROM pg_timezone_names;
SELECT * FROM pg_timezone_names WHERE name LIKE '%Hawaii%';

SELECT NOW()::DATE;

SELECT NOW()::TIME;

SELECT NOW() - INTERVAL '2 days';
SELECT CAST (NOW() - INTERVAL '2 days' AS DATE);
SELECT (NOW() - INTERVAL '2 days')::DATE;

SELECT DATE_TRUNC('day', NOW());

-- From string to timestamp
SELECT '2012-01-01 04:23:55'::TIMESTAMP;
SELECT CAST('2012-01-01 04:23:55' AS TIMESTAMP);

-- How long since...

SELECT NOW() - '2012-01-01'::TIMESTAMP;
SELECT DATE_PART('days', NOW() - '2012-01-01'::TIMESTAMP);

SELECT DATE_PART('years',NOW());
SELECT DATE_PART('years',NOW()) - DATE_PART('years', '2012-01-01'::DATE);

-- Inefficient - full table scan
SELECT id, content, created_at FROM comment 
  WHERE created_at::DATE = NOW()::DATE;

-- A range query evaluated inside the database
SELECT id, content, created_at FROM comment 
    WHERE created_at >= DATE_TRUNC('day',NOW()) 
    AND created_at < DATE_TRUNC('day',NOW() + INTERVAL '1 day');

-- DISTINCT AND DISTINCT ON

DROP TABLE IF EXISTS racing;

CREATE TABLE racing (
   make VARCHAR,
   model VARCHAR,
   year INTEGER,
   price INTEGER
);

INSERT INTO racing (make, model, year, price)
VALUES
('Nissan', 'Stanza', 1990, 2000),
('Dodge', 'Neon', 1995, 800),
('Dodge', 'Neon', 1998, 2500),
('Dodge', 'Neon', 1999, 3000),
('Ford', 'Mustang', 2001, 1000),
('Ford', 'Mustang', 2005, 2000),
('Subaru', 'Impreza', 1997, 1000),
('Mazda', 'Miata', 2001, 5000),
('Mazda', 'Miata', 2001, 3000),
('Mazda', 'Miata', 2001, 2500),
('Mazda', 'Miata', 2002, 5500),
('Opel', 'GT', 1972, 1500),
('Opel', 'GT', 1969, 7500),
('Opel', 'Cadet', 1973, 500)
;

SELECT DISTINCT make FROM racing;

SELECT DISTINCT model FROM racing;

-- Can have duplicates in the make column
SELECT DISTINCT ON (model) make,model,year FROM racing;

-- Must include the DISTINCT column in ORDER BY
SELECT DISTINCT ON (model) make,model,year FROM racing ORDER BY model, year;

SELECT DISTINCT ON (model) make,model,year FROM racing ORDER BY model, year DESC;

SELECT DISTINCT ON (model) make,model,year FROM racing ORDER BY model, year DESC LIMIT 2;

-- GROUP BY

SELECT * FROM pg_timezone_names LIMIT 20;

SELECT COUNT(*) FROM pg_timezone_names;

SELECT DISTINCT is_dst FROM pg_timezone_names;

SELECT COUNT(is_dst), is_dst FROM pg_timezone_names GROUP BY is_dst;

SELECT COUNT(abbrev), abbrev FROM pg_timezone_names GROUP BY abbrev;

-- WHERE is before GROUP BY, HAVING is after GROUP BY

SELECT COUNT(abbrev) AS ct, abbrev FROM  pg_timezone_names WHERE is_dst= 't' GROUP BY abbrev HAVING COUNT(abbrev) > 10;

SELECT COUNT(abbrev) AS ct, abbrev FROM  pg_timezone_names GROUP BY abbrev HAVING COUNT(abbrev) > 10;

SELECT COUNT(abbrev) AS ct, abbrev FROM  pg_timezone_names GROUP BY abbrev HAVING COUNT(abbrev) > 10 ORDER BY COUNT(abbrev) DESC;


-- Subquery

SELECT * FROM account 
WHERE email='ed@umich.edu';

SELECT content FROM comment WHERE account_id = 1;

SELECT content FROM comment 
WHERE account_id = (SELECT id FROM account WHERE email='ed@umich.edu');

-- If you did not have the HAVING clause for GROUP_BY
SELECT ct, abbrev FROM (
      SELECT COUNT(abbrev) AS ct, abbrev FROM  pg_timezone_names WHERE is_dst = 'f' GROUP BY abbrev
) AS zap WHERE ct > 10;

SELECT ct, abbrev FROM (
      SELECT COUNT(abbrev) AS ct, abbrev FROM  pg_timezone_names WHERE is_dst = 'f' GROUP BY abbrev
) AS zap WHERE ct > 10 ORDER BY ct DESC;


-- Concurrency

-- Do this twice
INSERT INTO fav (post_id, account_id, howmuch)
  VALUES (1,1,1)
RETURNING *;

UPDATE fav SET howmuch=howmuch+1
  WHERE post_id = 1 AND account_id = 1
RETURNING *;

INSERT INTO fav (post_id, account_id, howmuch)
  VALUES (1,1,1)
  ON CONFLICT (post_id, account_id) 
  DO UPDATE SET howmuch = fav.howmuch + 1;

INSERT INTO fav (post_id, account_id, howmuch)
  VALUES (1,1,1)
  ON CONFLICT (post_id, account_id) 
  DO UPDATE SET howmuch = fav.howmuch + 1
RETURNING *;

-- TRANSACTIONS (try in two windows)

BEGIN;
SELECT howmuch FROM fav WHERE account_id=1 AND post_id=1 FOR UPDATE OF fav;
-- Time passes... 
UPDATE fav SET howmuch=999 WHERE account_id=1 AND post_id=1;
SELECT howmuch FROM fav WHERE account_id=1 AND post_id=1;
ROLLBACK;
SELECT howmuch FROM fav WHERE account_id=1 AND post_id=1;

BEGIN;
SELECT howmuch FROM fav WHERE account_id=1 AND post_id=1 FOR UPDATE OF fav;
-- Time passes... 
UPDATE fav SET howmuch=999 WHERE account_id=1 AND post_id=1;
SELECT howmuch FROM fav WHERE account_id=1 AND post_id=1;
COMMIT;
SELECT howmuch FROM fav WHERE account_id=1 AND post_id=1;


-- Stored Procedures

UPDATE fav SET howmuch=howmuch+1
  WHERE post_id = 1 AND account_id = 1
RETURNING *;

-- https://x-team.com/blog/automatic-timestamps-with-postgresql/
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON post
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON fav
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON comment
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

--- Load a CSV file and automatically normalize into one-to-many

-- Download 
-- wget https://www.pg4e.com/lectures/03-Techniques.csv

-- x,y
-- Zap,A
-- Zip,A
-- One,B
-- Two,B

DROP TABLE IF EXISTS xy_raw;
DROP TABLE IF EXISTS y;
DROP TABLE IF EXISTS xy;

CREATE TABLE xy_raw(x TEXT, y TEXT, y_id INTEGER);
CREATE TABLE y (id SERIAL, PRIMARY KEY(id), y TEXT);
CREATE TABLE xy(id SERIAL, PRIMARY KEY(id), x TEXT, y_id INTEGER, UNIQUE(x,y_id));

\d xy_raw
\d+ y

\copy xy_raw(x,y) FROM '03-Techniques.csv' WITH DELIMITER ',' CSV;

SELECT DISTINCT y from xy_raw;

INSERT INTO y (y) SELECT DISTINCT y FROM xy_raw;

UPDATE xy_raw SET y_id = (SELECT y.id FROM y WHERE y.y = xy_raw.y);

SELECT * FROM xy_raw;

INSERT INTO xy (x, y_id) SELECT x, y_id FROM xy_raw;

SELECT * FROM xy JOIN y ON xy.y_id = y.id;

ALTER TABLE xy_raw DROP COLUMN y;

DROP TABLE xy_raw;

