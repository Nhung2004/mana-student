package studentLife.demo.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO<T> {

    private String status = String.valueOf(HttpStatus.OK.value());
    private String message;
    private T data;
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private long totalElements;
}
