select tb_score.sno, ts.name student_name, tb_score.course_id, course_name, tb_score.teacher_id, tt.name teacher_name, score
from tb_score join tb_student ts on ts.sno = tb_score.sno
    join tb_course tc on tc.course_id = tb_score.course_id
    join tb_teacher tt on tb_score.teacher_id = tt.teacher_id
where true