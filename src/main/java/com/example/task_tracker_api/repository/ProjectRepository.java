package com.example.task_tracker_api.repository;
import com.example.task_tracker_api.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByOwner_Id(Long ownerId, Pageable pageable);
}
