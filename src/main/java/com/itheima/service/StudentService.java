package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;

import java.util.List;

public interface StudentService {

    /**
     * 分页查询
     * @param name
     * @param degree
     * @param clazzId
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<Student> page(String name, Integer degree, Integer clazzId, Integer page, Integer pageSize);

    /**
     * 添加学生信息
     * @param student
     */
    void add(Student student);


    /**
     * 根据ID查询学生信息
     * @param id
     * @return
     */
    Student getById(Integer id);

    /**
     * 修改学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 批量删除学生信息
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 违纪处理
     * @param id
     * @param score
     */
    void violate(Integer id, Integer score);
}
