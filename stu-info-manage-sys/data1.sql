drop table if exists tb_score;
drop table if exists tb_student;
drop table if exists tb_class;
drop table if exists tb_teacher;
drop table if exists tb_course;
drop table if exists tb_user;

-- 用户表
create table tb_user
(
    -- id主键
    id       int primary key auto_increment,
    -- 用户名
    username varchar(20) unique ,
    -- 密码
    password varchar(20)
# mysql这个版本check没用
#     check ( username <> '' and password <> '' and username is not null and password is not null )
);

-- 课程表
create table tb_course
(
    -- 课程编号
    course_id   int primary key auto_increment,
    -- 课程名称
    course_name varchar(20) unique,
    -- 年级
    grade       enum('初一','初二','初三','高一','高二','高三')
);

-- 教师表
create table tb_teacher
(
    -- 教师编号
    teacher_id varchar(8) primary key,
    -- 姓名
    name       varchar(10),
    -- 性别
    sex        varchar(1),
    -- 年龄
    age        int,
    -- 课程编号
    course_id  int,
    foreign key (course_id) references tb_course (course_id)
#         on delete restrict
#         on update restrict
);

-- 班级表
create table tb_class
(
    -- 年级
    grade         enum('初一','初二','初三','高一','高二','高三'),
    -- 班级号
    class_id      varchar(3) primary key,
    -- 教室号
    classroom_id  varchar(3) unique,
    -- 班主任编号
    headmaster_id varchar(8),
    foreign key (headmaster_id) references tb_teacher (teacher_id)
);

-- 学生表
create table tb_student
(
    -- 学号
    sno    varchar(8) primary key,
    -- 姓名
    name   varchar(10),
    -- 性别
    sex    enum('男','女'),
    -- 年龄
    age    int,
    -- 年级
    grade  enum('初一','初二','初三','高一','高二','高三'),
    -- 班级
    class_id varchar(3),
    foreign key (class_id) references tb_class (class_id)
);

-- 成绩表
create table tb_score
(
    -- 学号
    sno     varchar(8),
    -- 课程编号
    course_id  int,
    -- 教师编号
    teacher_id  varchar(8),
    -- 成绩
    score   float,
    primary key (sno, course_id),
    foreign key (sno) references tb_student(sno),
    foreign key (course_id) references tb_course(course_id),
    foreign key (teacher_id) references tb_teacher(teacher_id)
);

insert into tb_user (username, password)
values
('张', 'zhang');

insert into tb_course (course_id, course_name, grade)
values
(null,'初一语文','初一'),
(null,'初一数学','初一'),
(null,'初一英语','初一'),
(null,'初一政治','初一'),
(null,'初一历史','初一'),
(null,'初一地理','初一'),
(null,'初一生物','初一'),

(null,'初二语文','初二'),
(null,'初二数学','初二'),
(null,'初二英语','初二'),
(null,'初二政治','初二'),
(null,'初二历史','初二'),
(null,'初二地理','初二'),
(null,'初二生物','初二'),
(null,'初二物理','初二'),

(null,'初三语文','初三'),
(null,'初三数学','初三'),
(null,'初三英语','初三'),
(null,'初三政治','初三'),
(null,'初三历史','初三'),
(null,'初三物理','初三'),
(null,'初三化学','初三'),

(null,'高一语文','高一'),
(null,'高一数学','高一'),
(null,'高一英语','高一'),
(null,'高一政治','高一'),
(null,'高一地理','高一'),
(null,'高一历史','高一'),
(null,'高一物理','高一'),
(null,'高一化学','高一'),
(null,'高一生物','高一'),

(null,'高二语文','高二'),
(null,'高二数学','高二'),
(null,'高二英语','高二'),
(null,'高二政治','高二'),
(null,'高二地理','高二'),
(null,'高二历史','高二'),
(null,'高二物理','高二'),
(null,'高二化学','高二'),
(null,'高二生物','高二'),

(null,'高三语文','高三'),
(null,'高三数学','高三'),
(null,'高三英语','高三'),
(null,'高三政治','高三'),
(null,'高三地理','高三'),
(null,'高三历史','高三'),
(null,'高三物理','高三'),
(null,'高三化学','高三'),
(null,'高三生物','高三');