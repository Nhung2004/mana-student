package studentLife.demo.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studentLife.demo.domain.file.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,String> {
}
