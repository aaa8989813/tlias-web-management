package com.itheima.mapper;


import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {
    // ----------------原始分页查询实现----------------
    /**
     * 查询总记录数
     * @return
     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();

    /**
     * 分页查询
     */
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);

    /**
     * 分页查询
     */
    //@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time")

    /**
     * 条件查询员工信息
     * @param empQueryParam
     * @return
     */
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     * @param emp
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "VALUES (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime});")
    void insert(Emp emp);


    /**
     * 删除员工
     * @param ids
     */
    void delete(List<Integer> ids);


    /**
     * 根据ID查询员工信息以及工作经历信息
     * @param id
     * @return
     */
    Emp getInfo(Integer id);

    /**
     * 根据ID更新员工基本信息
     * @param emp
     */
    void updateById(Emp emp);


    /**
     * 统计员工职位人数
     * @return
     */
    List<Map<String,Object>> countEmpJobData();

    /**
     * 统计员工性别人数
     * @return
     */
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 查询全部员工信息
     * @return
     */
    @Select("select id, username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time from emp")
    List<Emp> findAll();

    /**
     * 根据用户名和密码查询员工信息
     * @param emp
     * @return
     */
    @Select("select id,username,name from emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
