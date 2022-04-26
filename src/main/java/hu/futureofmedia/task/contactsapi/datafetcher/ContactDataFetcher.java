package hu.futureofmedia.task.contactsapi.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.exceptions.RecordNotFoundException;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ContactDataFetcher implements DataFetcher<Contact> {

private final ContactRepository contactRepository;

    @Override
    public Contact get(DataFetchingEnvironment environment) throws Exception {
        int l2 =  environment.getArgument("id");
        return contactRepository.findById(Long.valueOf(l2)).orElseThrow(RecordNotFoundException::new);
    }
}
