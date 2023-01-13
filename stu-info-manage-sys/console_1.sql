

insert into tb_teacher (teacher_id, name, sex, age, course_id)
values
('20200001', '李四', '女', 30, 1);

insert into tb_class (grade, class_id, classroom_id, headmaster_id)
values
('高一', '401', '401', '20200001');

insert into tb_student (sno, name, sex, age, grade, class_id)
values
('20220001', '张三', '男', 17, '高一', '401');

insert into tb_score (sno, course_id, teacher_id, score)
values
('20220001', 1, '20200001', 90);

select tc1.grade, tc1.class_id, tc1.classroom_id, tc1.headmaster_id, tt.name,
       (select count(sno)
        from tb_class tc2 join tb_student ts on tc2.class_id = ts.class_id
        where tc1.class_id = tc2.class_id
        group by tc2.class_id
       ) student_num
from tb_class tc1 join tb_teacher tt on tt.teacher_id = tc1.headmaster_id;

select tb_course.course_id, tb_course.course_name, tb_course.grade, tt.teacher_id, tt.name
from tb_course left join tb_teacher tt on tb_course.course_id = tt.course_id;

select tb_score.sno, ts.name student_name, tb_score.course_id, course_name, tb_score.teacher_id, tt.name teacher_name, score
from tb_score join tb_student ts on ts.sno = tb_score.sno
              join tb_course tc on tc.course_id = tb_score.course_id
              join tb_teacher tt on tb_score.teacher_id = tt.teacher_id;

select count(*) from tb_score;