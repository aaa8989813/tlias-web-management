package com.itheima.service;


import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface EmpService {
//    /**
//     * 分页查询
//     * @param page 页码
//     * @param pageSize 每页记录数
//     */
//    PageResult<Emp> page(Integer page,Integer pageSize,String name,Integer gender, LocalDate begin, LocalDate end);

    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     * @param emp
     */
    void save(Emp emp);

    /**
     * 删除员工
     *
     * @param ids
     */
    void delete(List<Integer> ids);


    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Emp getInfo(Integer id);


    /**
     * 修改员工信息
     * @param emp
     */
    void update(Emp emp);

    /**
     * 查询所有员工信息
     * @return
     */
    List<Emp> list();

    /**
     * 登录员工
     * @param emp
     * @return
     */
    LoginInfo login(Emp emp);
}
