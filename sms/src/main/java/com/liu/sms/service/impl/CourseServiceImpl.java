package com.liu.sms.service.impl;

import com.liu.sms.mapper.CollegeMapper;
import com.liu.sms.mapper.CourseMapper;
import com.liu.sms.mapper.CourseMapperCustom;
import com.liu.sms.mapper.SelectedcourseMapper;
import com.liu.sms.po.*;
import com.liu.sms.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseMapperCustom courseMapperCustom;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private SelectedcourseMapper selectedcourseMapper;

    @Override
    public void updateById(Integer id, CourseCustom courseCustom) throws Exception {
        courseMapper.updateByPrimaryKey(courseCustom);
    }

    @Override
    public Boolean removeById(Integer id) throws Exception {
        //自定义查询条件
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseidEqualTo(id);
        List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);

        if (list.size() == 0) {
            courseMapper.deleteByPrimaryKey(id);
            return true;
        }

        return false;
    }

    @Override
    public List<CourseCustom> findByPaging(Integer toPageNo) throws Exception {
        PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(toPageNo);

        List<CourseCustom> list = courseMapperCustom.findByPaging(pagingVO);
        return list;
    }

    @Override
    public Boolean save(CourseCustom couseCustom) throws Exception {
        Course course = courseMapper.selectByPrimaryKey(couseCustom.getCourseid());
        if (course == null) {
            courseMapper.insert(couseCustom);
            return true;
        }
        return false;
    }

    @Override
    public int getCountCourse() throws Exception {
        //自定义查询对象
        CourseExample courseExample = new CourseExample();
        //通过criteria构造查询条件
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andCoursenameIsNotNull();

        return courseMapper.countByExample(courseExample);
    }

    @Override
    public CourseCustom findById(Integer id) throws Exception {
        Course course = courseMapper.selectByPrimaryKey(id);
        CourseCustom courseCustom = null;
        if (course != null) {
            courseCustom = new CourseCustom();
            BeanUtils.copyProperties(courseCustom, course);
        }

        return courseCustom;
    }

    @Override
    public List<CourseCustom> findByName(String name) throws Exception {
        CourseExample courseExample = new CourseExample();
        //自定义查询条件
        CourseExample.Criteria criteria = courseExample.createCriteria();

        criteria.andCoursenameLike("%" + name + "%");

        List<Course> list = courseMapper.selectByExample(courseExample);

        List<CourseCustom> courseCustomList = null;

        if (list != null) {
            courseCustomList = new ArrayList<CourseCustom>();
            for (Course c : list) {
                CourseCustom courseCustom = new CourseCustom();
                //类拷贝
                org.springframework.beans.BeanUtils.copyProperties(c, courseCustom);
                //获取课程名
                College college = collegeMapper.selectByPrimaryKey(c.getCollegeid());
                courseCustom.setCollegeName(college.getCollegename());

                courseCustomList.add(courseCustom);
            }
        }

        return courseCustomList;
    }

    @Override
    public List<CourseCustom> findByTeacherID(Integer id) throws Exception {
        CourseExample courseExample = new CourseExample();
        //自定义查询条件
        CourseExample.Criteria criteria = courseExample.createCriteria();
        //根据教师id查课程
        criteria.andTeacheridEqualTo(id);

        List<Course> list = courseMapper.selectByExample(courseExample);
        List<CourseCustom> courseCustomList = null;

        if (list.size() > 0) {
            courseCustomList = new ArrayList<CourseCustom>();
            for (Course c : list) {
                CourseCustom courseCustom = new CourseCustom();
                //类拷贝
                BeanUtils.copyProperties(courseCustom, c);
                //获取课程名
                College college = collegeMapper.selectByPrimaryKey(c.getCollegeid());
                courseCustom.setCollegeName(college.getCollegename());

                courseCustomList.add(courseCustom);
            }
        }

        return courseCustomList;
    }

    @Override
    public List<CourseCustom> findByCourseWithTeacher(CourseCustom courseCustom) throws Exception {
        return courseMapperCustom.findByCourseWithTeacher(courseCustom);
    }
}