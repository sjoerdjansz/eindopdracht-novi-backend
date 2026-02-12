package nl.sweatdaddy.fileUpload.repository;

import nl.sweatdaddy.fileUpload.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileUploadRepository extends JpaRepository<File, Long> {
 Optional<File> findByFileName(String fileName);
}
