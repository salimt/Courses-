select SUM(SALEPRICE) from PETSALE;
select SUM(SALEPRICE) AS SUM_OF_SALEPRICE from PETSALE;
select MAX(QUANTITY) from PETSALE;
select AVG(SALEPRICE) from PETSALE;
select AVG( SALEPRICE / QUANTITY ) from PETSALE where ANIMAL = 'Dog';
select ROUND(SALEPRICE) from PETSALE;
select LENGTH(ANIMAL) from PETSALE;
select UCASE(ANIMAL) from PETSALE;
select DISTINCT(UCASE(ANIMAL)) from PETSALE;
select * from PETSALE where LCASE(ANIMAL) = 'cat';
select DAY(SALEDATE) from PETSALE where ANIMAL = 'Cat';
select COUNT(*) from PETSALE where MONTH(SALEDATE)='05';
select (SALEDATE + 3 DAYS) from PETSALE;
select (CURRENT DATE - SALEDATE) from PETSALE;

