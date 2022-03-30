package hu.futureofmedia.task.contactsapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CompanyDTO {
    @NotNull (message = "validation.required.company.id")
    private Long id;
    private String name;
}
