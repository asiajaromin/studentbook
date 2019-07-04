INSERT INTO students (first_name, last_name, email)
VALUES 
('Jan', 'Kowalski', 'jan.kowalski@twojmail.com'),
('Patryk', 'Nowak', 'patryk.nowak@skrzyneczka.pl'),
('Barbara', 'Kwiatek', 'kwiatuszek@twojmail.com');

INSERT INTO subjects (subject_name)
VALUES 
('Matematyka'),
('Język Polski'),
('Język Angielski'),
('Historia'),
('Biologia');

INSERT INTO grades (student_id, subject_id, grade)
VALUES
(1,1,5),
(1,2,3),
(1,5,4),
(2,3,2),
(2,4,2),
(2,1,3),
(3,1,1),
(3,2,5),
(2,4,3);

