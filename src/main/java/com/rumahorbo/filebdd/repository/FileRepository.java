package com.rumahorbo.filebdd.repository;

import com.rumahorbo.filebdd.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
}
