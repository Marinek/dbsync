-- init.sql
use schema my_user;

CREATE TABLE example_table (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR2(50),
    age NUMBER,
    city VARCHAR2(50)
);

INSERT INTO example_table (name, age, city) VALUES ('Alice', 25, 'New York');
INSERT INTO example_table (name, age, city) VALUES ('Bob', 30, 'San Francisco');
INSERT INTO example_table (name, age, city) VALUES ('Charlie', 35, 'Chicago');
INSERT INTO example_table (name, age, city) VALUES ('Diana', 28, 'Seattle');
INSERT INTO example_table (name, age, city) VALUES ('Eve', 22, 'Boston');
INSERT INTO example_table (name, age, city) VALUES ('Frank', 40, 'Denver');
INSERT INTO example_table (name, age, city) VALUES ('Grace', 32, 'Austin');
INSERT INTO example_table (name, age, city) VALUES ('Hank', 29, 'Miami');
INSERT INTO example_table (name, age, city) VALUES ('Ivy', 26, 'Dallas');
INSERT INTO example_table (name, age, city) VALUES ('Jack', 27, 'Los Angeles');

CREATE OR REPLACE VIEW example_view AS
SELECT * FROM example_table;

COMMIT;