package hu.futureofmedia.task.contactsapi.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import hu.futureofmedia.task.contactsapi.service.CompanyService;
import hu.futureofmedia.task.contactsapi.service.GraphQLService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateContactDataFetcher implements DataFetcher<String> {
    Logger logger =  LoggerFactory.getLogger(GraphQLService.class);
    private final CompanyService companyService;
    private final ContactRepository contactRepository;

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        Contact contact = new Contact();
        contact.setStatus(Status.ACTIVE);
        contact.setEmail(environment.getArgument("firstEmail"));
        int l = environment.getArgument("companyId");
        contact.setCompany(companyService.getById(Long.valueOf(Long.valueOf(l))));
        contact.setComment("ha");
        contact.setFirstName(environment.getArgument("firstName"));
        contact.setSecondName(environment.getArgument("secondName"));
        logger.info("Contact created");
        return contactRepository.save(contact).getId().toString();
    }
}
