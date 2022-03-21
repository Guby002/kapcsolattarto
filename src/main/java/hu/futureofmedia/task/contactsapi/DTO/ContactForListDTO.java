package hu.futureofmedia.task.contactsapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.futureofmedia.task.contactsapi.entities.Company;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ContactForListDTO {
        @JsonIgnore
        private Long id;

        @JsonIgnore
        private Company company;
        @JsonIgnore
        private String firstName;
        @JsonIgnore
        private String secondName;
///---------------KI
        @JsonProperty(value = "Name",index = 1)
        public String getName() {
                return(firstName + " " + secondName);
        }
        @JsonProperty(value = "Company_name",index = 2)
        public String getCompanyName() {
                return(company.getName());
        }
        private String eMail;
        private String phoneNumber;
//-------------Eddig
        @JsonIgnore
        private Boolean stat;
}
