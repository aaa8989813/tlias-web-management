package com.itheima.mapper;


import com.itheima.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    /**
     * 分页查询
     * @param name
     * @param degree
     * @param clazzId
     * @return
     */
    List<Student> page(String name, Integer degree, Integer clazzId);

    /**
     * 添加学生信息
     * @param student
     */
    @Insert("insert into student(name, no, gender, phone,id_card, is_college, address, degree, graduation_date,clazz_id, create_time, update_time) VALUES " +
            "(#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege},#{address},#{degree},#{graduationDate},#{clazzId},#{createTime},#{updateTime})")
    void add(Student student);

    /**
     * 根据ID查询学生信息
     * @param id
     * @return
     */
    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    /**
     * 修改学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 批量删除学生
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 违纪扣分
     * @param id
     * @param score
     */
    @Update("update student set violation_score = #{score} where id = #{id}")
    void violate(Integer id, Integer score);

    /**
     * 统计各班人数
     * @return
     */
    List<Map<String, Object>> countStudentCountData();

    /**
     * 统计各学历人数
     * @return
     */
    List<Map<String, Object>> countStudentDegreeData();

    /**
     * 根据班级ID查询班级人数
     * @param id
     * @return
     */
    @Select("select count(*) from student where clazz_id = #{id}")
    Integer countByClazzId(Integer id);
}
