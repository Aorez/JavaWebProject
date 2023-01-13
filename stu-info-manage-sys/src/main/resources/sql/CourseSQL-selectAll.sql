select tb_course.course_id, tb_course.course_name, tb_course.grade, tt.teacher_id, tt.name
from tb_course left join tb_teacher tt on tb_course.course_id = tt.course_id
where true