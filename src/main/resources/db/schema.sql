DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS classrooms CASCADE;
DROP TABLE IF EXISTS lessons CASCADE;
CREATE TABLE IF NOT EXISTS groups(id SERIAL NOT NULL PRIMARY KEY, department_id INT, course_id INT, group_number INT);
CREATE TABLE IF NOT EXISTS departments(id SERIAL NOT NULL PRIMARY KEY, name varchar(20));
CREATE TABLE IF NOT EXISTS courses(id SERIAL NOT NULL PRIMARY KEY, establish_year INT);
CREATE TABLE IF NOT EXISTS students(id SERIAL NOT NULL PRIMARY KEY, group_id INT, first_name varchar(20), last_name varchar(20), age INT);
CREATE TABLE IF NOT EXISTS teachers(id SERIAL NOT NULL PRIMARY KEY, department_id INT, first_name varchar(20), last_name varchar(20), age INT);
CREATE TABLE IF NOT EXISTS classrooms(id SERIAL NOT NULL PRIMARY KEY, class_number varchar(20));
CREATE TABLE IF NOT EXISTS lessons(id SERIAL NOT NULL PRIMARY KEY, name varchar(20), teacher_id INT, group_id INT, date_time TIMESTAMP, class_room_id INT);
