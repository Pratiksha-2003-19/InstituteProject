package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Course;
import com.InstituteManagement.Repository.CourseRepository;
import com.InstituteManagement.dto.CreateCourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course createCourse(CreateCourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setDurationInHours(request.getDurationInHours());
        course.setFee(request.getFee());
        course.setLevel(request.getLevel());
        course.setStatus("ACTIVE");
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
