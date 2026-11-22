package com.itheima.controller;


import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询
     */
    @GetMapping
    public Result page(String name, Integer degree, Integer clazzId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageResult<Student> pageResult = studentService.page(name, degree, clazzId, page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 添加学生信息
     */
    @PostMapping
    public Result add(@RequestBody Student student){
        log.info("添加学生信息：{}",student);
        studentService.add(student);
        return Result.success();
    }


    /**
     * 根据ID查询学生信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询员工信息:{}",id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    /**
     * 修改学生信息
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学生信息：{}",student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 批量删除学生
     * @return
     */
    @DeleteMapping("{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除学生：{}", ids);
        studentService.delete(ids);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{score}")
    public Result violate(@PathVariable Integer id,
                          @PathVariable Integer score){

        studentService.violate(id, score);
        return Result.success();
    }
}
