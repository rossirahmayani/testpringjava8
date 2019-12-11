package com.rossi.testspringjava8.repository;

import com.rossi.testspringjava8.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    Department findByDepartmentCode(String deptCode);

    List<Department> findByFaculty_FacultyCode(String facultyCode);
}
