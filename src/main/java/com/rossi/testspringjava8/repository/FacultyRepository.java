package com.rossi.testspringjava8.repository;

import com.rossi.testspringjava8.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, String> {
    Faculty findByFacultyCode(String facultyCode);

    List<Faculty> findAll();
}
