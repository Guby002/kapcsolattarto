package hu.futureofmedia.task.contactsapi.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteContactDataFetcher implements DataFetcher<String> {

    private final ContactService contactService;
    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        int id = environment.getArgument("id");
        contactService.delete(Long.valueOf(id));
        return "hello";
    }
}
