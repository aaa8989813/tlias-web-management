-- MySQL 8.x. Run only in a new, empty local database.
-- Schema reconstructed from this repository's entities and mapper SQL.
-- No production records, passwords, phone numbers, or identity data are included.
SET NAMES utf8mb4;

CREATE TABLE dept (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    create_time DATETIME,
    update_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE emp (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) DEFAULT NULL,
    name VARCHAR(50) NOT NULL,
    gender TINYINT UNSIGNED,
    phone VARCHAR(20),
    job TINYINT UNSIGNED,
    salary INT UNSIGNED,
    image VARCHAR(512),
    entry_date DATE,
    dept_id INT UNSIGNED,
    create_time DATETIME,
    update_time DATETIME,
    INDEX idx_emp_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE emp_expr (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    emp_id INT UNSIGNED NOT NULL,
    `begin` DATE,
    `end` DATE,
    company VARCHAR(100),
    job VARCHAR(100),
    INDEX idx_emp_expr_emp_id (emp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Column order matches ClazzMapper.save(), which currently uses INSERT ... VALUES.
CREATE TABLE clazz (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    room VARCHAR(50),
    begin_date DATE,
    end_date DATE,
    master_id INT UNSIGNED,
    subject TINYINT UNSIGNED,
    create_time DATETIME,
    update_time DATETIME,
    INDEX idx_clazz_master_id (master_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE student (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    no VARCHAR(30) NOT NULL UNIQUE,
    gender TINYINT UNSIGNED,
    phone VARCHAR(20),
    id_card VARCHAR(30),
    is_college TINYINT UNSIGNED,
    address VARCHAR(255),
    degree TINYINT UNSIGNED,
    graduation_date DATE,
    clazz_id INT UNSIGNED,
    violation_count SMALLINT NOT NULL DEFAULT 0,
    violation_score SMALLINT NOT NULL DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME,
    INDEX idx_student_clazz_id (clazz_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE emp_log (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    operate_time DATETIME,
    info TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operate_log (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    operate_emp_id INT UNSIGNED,
    operate_time DATETIME,
    class_name VARCHAR(255),
    method_name VARCHAR(255),
    method_params TEXT,
    return_value TEXT,
    cost_time BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
