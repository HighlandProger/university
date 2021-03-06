INSERT INTO courses (establish_year) VALUES (2021), (2022), (2023);
INSERT INTO departments (name) VALUES ('IT'), ('Management'), ('Economics');
INSERT INTO groups (department_id, course_id, group_number) VALUES (1, 1, 1), (2, 1, 2), (3, 1, 3), (1, 2, 1), (2, 2, 2), (3, 2, 3);
INSERT INTO students (group_id, first_name, last_name, age) VALUES (1, 'John', 'Connor', 23), (2, 'Jack', 'Richer', 25), (3, 'John', 'Travolta', 26);
INSERT INTO teachers (department_id, first_name, last_name, age) VALUES (1, 'Steve', 'Jobs', 45), (2, 'Stan', 'Lee', 51), (3, 'Ozzy', 'Osborne', 73);
INSERT INTO classrooms (class_number) VALUES ('1'), ('2'), ('3');
INSERT INTO lessons (name, teacher_id, group_id, date_time, class_room_id) VALUES
('Math', 1, 4, '2022-01-15 12:30:00', 1), ('History', 2, 4, '2022-01-16 11:30:00', 1), ('Chemists', 2, 3, '2022-01-17 12:00:00', 1),
('Geography', 1, 3, '2022-01-15 14:00:00', 2), ('Math', 2, 1, '2022-01-16 15:30:00', 2), ('Math', 3, 2, '2022-01-17 14:00:00', 2),
('History', 1, 2, '2022-01-15 16:30:00', 3), ('Chemists', 2, 5, '2022-01-16 17:00:00', 3), ('Geography', 1, 6, '2022-01-17 16:30:00', 2),
('Math', 3, 6, '2022-01-15 12:30:00', 1), ('History', 3, 5, '2022-01-16 11:30:00', 1), ('Chemists', 1, 3, '2022-01-17 12:00:00', 3),
('Geography', 3, 5, '2022-01-15 14:00:00', 2), ('Math', 2, 1, '2022-01-16 15:30:00', 2), ('Math', 3, 5, '2022-01-17 14:00:00', 2),
('History', 3, 6, '2022-01-15 16:30:00', 3), ('Chemists', 1, 6, '2022-01-16 17:00:00', 3), ('Geography', 3, 1, '2022-01-17 16:30:00', 1);
