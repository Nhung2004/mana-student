package studentLife.demo.service.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import studentLife.demo.service.AbstractDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO extends AbstractDTO<String> {
    private String id;
    private String fileName;
    private String filePath;
    private String fileType;
    private String targetId;
}
