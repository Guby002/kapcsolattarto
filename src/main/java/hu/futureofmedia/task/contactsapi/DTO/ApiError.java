package hu.futureofmedia.task.contactsapi.DTO;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;
}