package hu.futureofmedia.task.contactsapi.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class TenContactDataFetcher implements DataFetcher<List<Contact>> {

    private final ContactRepository contactRepository;
    @Override
    public List<Contact> get(DataFetchingEnvironment dataFetchingEnvironment) {
        int pageNo = dataFetchingEnvironment.getArgument("pageNo");
        Pageable pageSortByName = PageRequest.of(pageNo-1 ,10, Sort.by("firstName"));
        return contactRepository.findAllByStatus(Status.ACTIVE, pageSortByName).toList();
    }
}
