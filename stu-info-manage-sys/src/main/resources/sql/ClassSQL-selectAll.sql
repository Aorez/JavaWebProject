select tc1.grade, tc1.class_id, tc1.classroom_id, tc1.headmaster_id, tt.name,
       (select count(sno)
        from tb_class tc2 join tb_student ts on tc2.class_id = ts.class_id
        where tc1.class_id = tc2.class_id
        group by tc2.class_id
       ) student_num
from tb_class tc1 join tb_teacher tt on tt.teacher_id = tc1.headmaster_id
where true