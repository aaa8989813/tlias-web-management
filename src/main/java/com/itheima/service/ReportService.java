package com.itheima.service;

import com.itheima.pojo.ClazzOption;
import com.itheima.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {

    /**
     * 统计职位人数
     * @return
     */
    JobOption getEmpJobData();


    /**
     * 统计性别人数
     * @return
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计班级人数
     * @return
     */
    ClazzOption getClazzData();

    /**
     * 统计学生学历人数
     * @return
     */
    List<Map<String, Object>> getStudentDegreeData();
}
