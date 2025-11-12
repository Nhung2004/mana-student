package studentLife.demo.domain.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import studentLife.demo.domain.AbstractEntity;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name ="files")
@EntityListeners(AuditingEntityListener.class)
public class FileEntity extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="file_name", nullable = false)
    private String fileName; // Tên gốc file

    @Column(name="file_path", nullable = false)
    private String filePath; // Đường dẫn lưu trên server

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name="target_id", nullable = false)
    private String targetId;

}
