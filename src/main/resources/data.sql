TRUNCATE student_book.students CASCADE;
INSERT INTO student_book.students (id, first_name, last_name, email)
VALUES 
(1,'Jan', 'Kowalski', 'jan.kowalski@twojmail.com'),
(2,'Patryk', 'Nowak', 'patryk.nowak@skrzyneczka.pl'),
(3,'Barbara', 'Kwiatek', 'kwiatuszek@twojmail.com');

TRUNCATE student_book.subjects CASCADE;
INSERT INTO student_book.subjects (id,subject_name)
VALUES 
(1,'Matematyka'),
(2,'Język Polski'),
(3,'Język Angielski'),
(4,'Historia'),
(5,'Biologia');

TRUNCATE student_book.grades CASCADE;
INSERT INTO student_book.grades (id, student_id, subject_id, grade)
VALUES
(1,1,1,5),
(2,1,2,3),
(3,1,5,4),
(4,2,3,2),
(5,2,4,2),
(6,2,1,3),
(7,3,1,1),
(8,3,2,5),
(9,2,4,3);