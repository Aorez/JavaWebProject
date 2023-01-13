package com.aorez.util;

import com.aorez.pojo.Cls;
import com.aorez.pojo.Score;
import com.aorez.pojo.Student;
import com.aorez.pojo.Teacher;
import com.aorez.sql.ClassSQL;
import com.aorez.sql.ScoreSQL;
import com.aorez.sql.StudentSql;
import com.aorez.sql.TeacherSQL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataCreator {
    private TeacherSQL teacherSQL = new TeacherSQL();
    private PersonGenerator personGenerator = new PersonGenerator();
    private ClassSQL classSQL = new ClassSQL();
    private StudentSql studentSql = new StudentSql();
    private ScoreSQL scoreSQL = new ScoreSQL();

//    public static void main(String[] args) {
//        DataCreator dataCreator = new DataCreator();
////        dataCreator.teacherInsert();
////        dataCreator.classInsert();
////        dataCreator.studentInsert();
////        耗时2分钟左右
////        dataCreator.scoreInsert();
//    }

    private void teacherInsert() {
        int teacherId = 20200001;
        //总共49个科目
        for (int courseId = 1; courseId <= 49; courseId++) {
            //每个科目8个老师
            for (int i = 1; i <= 8; i++) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(Integer.toString(teacherId++));
                String sex = personGenerator.getSex();
                teacher.setSex(sex);
                String familyName = personGenerator.getFamilyName();
                String name = personGenerator.getNameBySex(sex);
                teacher.setName(familyName+name);
                teacher.setAge(personGenerator.getTeacherAge());
                teacher.setCourseId(courseId);
                if(!teacherSQL.insert(teacher)) {
                    System.out.println(teacherId + " 添加失败");
                }
            }
        }
    }

    private void classInsert() {
        String[] grades = {"初一","初二","初三","高一","高二","高三"};
        //生成Map对象，比如，初一对应100，生成classId时则用100加一、加二……
        Map<String, Integer> classIdStarts = new HashMap<>();
        int classIdStart = 100;
        for (String grade: grades) {
            classIdStarts.put(grade, classIdStart);
            classIdStart += 100;
        }
        //生成班主任起始的编号
        Map<String, Integer> headmasterIdStarts = new HashMap<>();
        int index = 0;
        headmasterIdStarts.put(grades[index++], 20200001);
        headmasterIdStarts.put(grades[index++], 20200057);
        headmasterIdStarts.put(grades[index++], 20200121);
        headmasterIdStarts.put(grades[index++], 20200177);
        headmasterIdStarts.put(grades[index++], 20200249);
        headmasterIdStarts.put(grades[index++], 20200321);
        //遍历所有年级
        for (String grade: grades) {
            //每个年级32个班
            classIdStart = classIdStarts.get(grade);
            int headmasterIdStart = headmasterIdStarts.get(grade);
            for (int i = 1; i <= 32; i++) {
                String classId = Integer.toString(classIdStart + i);
                String classroomId = classId;
                String headmasterId = Integer.toString(headmasterIdStart++);
                Cls cls = new Cls();
                cls.setGrade(grade);
                cls.setClassId(classId);
                cls.setClassroomId(classroomId);
                cls.setHeadmasterId(headmasterId);
                if (!classSQL.insert(cls)) {
                    System.out.println(classId + " 添加失败");
                }
            }
        }
    }

    private void studentInsert() {
        int sno = 20220001;
        String[] grades = {"初一","初二","初三","高一","高二","高三"};
        //班级号
        Map<String, Integer> classIdStarts = new HashMap<>();
        int classIdStart = 100;
        for (String grade: grades) {
            classIdStarts.put(grade, classIdStart);
            classIdStart += 100;
        }
        //和创建班级差不多
        for (String grade: grades) {
            //每个年级32个班
            String ageType = "";
            switch (grade) {
                case "初一":
                case "初二":
                case "初三":ageType = "junior";break;
                case "高一":
                case "高二":
                case "高三":ageType = "senior";break;
            }
            classIdStart = classIdStarts.get(grade);
            for (int i = 1; i <= 32; i++) {
                String classId = Integer.toString(classIdStart + i);
                //每个班50个学生
                for (int j = 1; j <= 50; j++) {
                    String[] nameSexAge = null;
                    try {
                         nameSexAge = personGenerator.getNameSexAge(ageType);
                    } catch (Exception e) {
                        System.out.println(sno + " 添加失败");
                        continue;
                    }
                    Student student = new Student();
                    student.setSno(Integer.toString(sno++));
                    student.setName(nameSexAge[0]);
                    student.setSex(nameSexAge[1]);
                    student.setAge(Integer.parseInt(nameSexAge[2]));
                    student.setGrade(grade);
                    student.setClassId(classId);
                    if (!studentSql.insert(student)) {
                        System.out.println(sno + " 添加失败");
                    }
                }
            }
        }
    }

    private void scoreInsert() {
        String[] grades = {"", "初一","初二","初三","高一","高二","高三"};
        //每个年级对应课程编号最大的值
        int[] courseIdMaxs = {0,7,15,22,31,40,49};
        int[] courseIdStarts = {0,1,8,16,23,32,41};
        int sno = 20220001;
        //6个年级
        for (int i = 1; i <= 6; i++) {
            //每个年级有1600人
            for (int j = 1; j <= 1600; j++) {
                //每个学生都有很多科目
                for (int courseId = courseIdStarts[i]; courseId <= courseIdMaxs[i]; courseId++) {
                    Score score = new Score();
                    score.setSno(Integer.toString(sno));
                    score.setCourseId(courseId);
                    //处理teacherId
                    List<String> teacherIds = teacherSQL.selectTeacherIdByCourseId(courseId);
                    int teacherIdIndex = new Random().nextInt(teacherIds.size());
                    score.setTeacherId(teacherIds.get(teacherIdIndex));
                    //处理成绩
                    score.setScore(new Random().nextInt(101));
                    if (!scoreSQL.insert(score)) {
                        System.out.println(sno + " " + courseId + " 添加失败");
                    }
                }
                sno++;
            }
        }
    }
}
