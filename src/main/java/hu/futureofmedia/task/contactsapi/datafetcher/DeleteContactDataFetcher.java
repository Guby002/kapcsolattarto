package hu.futureofmedia.task.contactsapi.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.service.GraphQLService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteContactDataFetcher implements DataFetcher<String> {
    Logger logger =  LoggerFactory.getLogger(GraphQLService.class);
    private final ContactService contactService;
    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        int id = environment.getArgument("id");
        contactService.delete(Long.valueOf(id));
        logger.info("Contact deleted");
        return "deleted";
    }
}
