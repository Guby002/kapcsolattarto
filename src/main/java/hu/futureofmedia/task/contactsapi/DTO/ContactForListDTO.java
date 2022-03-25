package hu.futureofmedia.task.contactsapi.DTO;


import lombok.Data;

@Data
public class ContactForListDTO {
        private Long id;
        private String Name;
        private String companyName;
        private String email;
        private String phoneNumber;
}
