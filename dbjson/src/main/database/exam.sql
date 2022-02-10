DROP TABLE IF EXISTS exam_bilet;
CREATE TABLE exam_bilet (
    id SERIAL NOT NULL PRIMARY KEY,

    -- один способ добавить UNIQUE
    student_id INT NOT NULL,

    question_id INT
);

-- второй способ добавить UNIQUE
ALTER TABLE exam_bilet
    ADD CONSTRAINT uk_question_id UNIQUE (question_id, student_id);

SELECT * from exam_bilet;

INSERT INTO exam_bilet (student_id, question_id) VALUES (1, 1);
INSERT INTO exam_bilet (student_id, question_id) VALUES (2,11);

-- student_id должно быть УНИКАЛЕН
INSERT INTO exam_bilet (student_id, question_id) VALUES (1,7);

-- question_id должно быть УНИКАЛЕН
INSERT INTO exam_bilet (student_id, question_id) VALUES (4,1);

-- student_id и question_id  должно быть УНИКАЛЬНЫ
INSERT INTO exam_bilet (student_id, question_id) VALUES (1, 1);
