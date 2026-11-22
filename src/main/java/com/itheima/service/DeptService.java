package com.itheima.service;


import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {

    /**
     * 查询所有部门信息
     *
     * @return
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 添加部门
     * @param dept
     */
    void add(Dept dept);

    /**
     * 跟据id查询部门信息
     * @param id
     * @return
     */
    Dept getById(Integer id);

    /**
     * 修改部门信息
     */
    void update(Dept dept);
}
