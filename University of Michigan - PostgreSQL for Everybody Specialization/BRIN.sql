

CREATE TABLE brinfun(
    id SERIAL,
    content TEXT,
    numb INTEGER
);


INSERT INTO brinfun (content, numb) SELECT repeat('Blah Blah', 500), generate_series(1000,9999);

SELECT pg_relation_size('brinfun'), pg_indexes_size('brinfun');

EXPLAIN ANALYZE SELECT numb FROM brinfun WHERE numb = 2000;

CREATE INDEX brinfun_brin ON brinfun USING BRIN (numb);

SELECT pg_relation_size('brinfun'), pg_indexes_size('brinfun');

EXPLAIN ANALYZE SELECT numb FROM brinfun WHERE numb = 2000;

