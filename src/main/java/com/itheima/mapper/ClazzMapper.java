package com.itheima.mapper;


import com.itheima.pojo.Clazz;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ClazzMapper {
    /**
     * 动态条件查询
     */
    List<Clazz> list(String name, LocalDate begin, LocalDate end);

    /**
     * 添加班级
     * @param clazz
     */
    @Insert("insert into clazz VALUES (null,#{name},#{room},#{beginDate},#{endDate},#{masterId}, #{subject},#{createTime},#{updateTime})")
    void save(Clazz clazz);

    /**
     * 删除班级
     * @param id
     */
    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    /**
     * 根据ID查询班级信息
     * @param id
     * @return
     */
    @Select("select * from clazz where id = #{id}")
    Clazz findById(Integer id);

    /**
     *  修改班级信息
     * @param clazz
     */
    void update(Clazz clazz);

    /**
     * 查询所有班级信息
     * @return
     */
    @Select("select * from clazz")
    List<Clazz> findAll();

}
