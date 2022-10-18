DROP DATABASE IF EXISTS `student-manager`;
CREATE DATABASE `student-manager`;
USE `student-manager`;

CREATE TABLE teachers
(
    `id`         varchar(100)  NOT NULL,
    `email`      nvarchar(200) NOT NULL,
    `password`   varchar(100)  NOT NULL,
    `name`       nvarchar(200),
    `sex`        varchar(100),
    `avatar_url` varchar(200) DEFAULT 'images/default.png',

    createdAt    datetime     DEFAULT CURRENT_TIMESTAMP,
    updatedAt    datetime     DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_teacher_id` (`id`)
);

CREATE TABLE students
(
    `id`          varchar(100) NOT NULL,
    `password`    varchar(100) NOT NULL,
    `email`       nvarchar(200),
    `school_date` DATE,
    `major`       nvarchar(200),
    `name`        nvarchar(200),
    `sex`         varchar(100),
    `class_id`    int,
    `avatar_url`  varchar(200) DEFAULT 'images/default.png',

    createdAt     datetime     DEFAULT CURRENT_TIMESTAMP,
    updatedAt     datetime     DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_teacher_id` (`id`)
);

CREATE TABLE classes
(
    `id`         int           NOT NULL AUTO_INCREMENT,
    `class_name` nvarchar(200) NOT NULL,
    createdAt    datetime DEFAULT CURRENT_TIMESTAMP,
    updatedAt    datetime DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`)
);

CREATE TABLE courses
(
    `id`          int           NOT NULL AUTO_INCREMENT,
    `name`        nvarchar(200) NOT NULL,
    `description` nvarchar(200),

    createdAt     datetime DEFAULT CURRENT_TIMESTAMP,
    updatedAt     datetime DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`)
);

CREATE TABLE course_student
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `student_id` varchar(100) NOT NULL,
    `course_id`  int NOT NULL,
    `score`      int DEFAULT 0,

    createdAt    datetime DEFAULT CURRENT_TIMESTAMP,
    updatedAt    datetime DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`student_id`) REFERENCES students (`id`),
    FOREIGN KEY (`course_id`) REFERENCES courses (`id`)
)