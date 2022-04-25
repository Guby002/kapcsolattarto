package hu.futureofmedia.task.contactsapi.service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import hu.futureofmedia.task.contactsapi.DTO.CompanyDTO;
import hu.futureofmedia.task.contactsapi.DTO.ContactDTO;
import hu.futureofmedia.task.contactsapi.datafetcher.ContactDataFetcher;
import hu.futureofmedia.task.contactsapi.datafetcher.TenContactDataFetcher;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static java.time.ZonedDateTime.now;

@Service
public class GraphQLService {

    @Autowired
    ContactRepository contactRepository;

    @Value("classpath:query.graphql")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private TenContactDataFetcher tenContactDataFetcher;
    @Autowired
    private ContactDataFetcher contactDataFetcher;

    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {

        //Load Books into the Book Repository
        loadDataIntoHSQL();

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoHSQL() {
        /*Company company = new Company(1L, "as");
        ZonedDateTime d2 = now();
        ZonedDateTime d1 = now();
        Stream.of(
                new Contact(5L, "1Nagyon", "Almos",
                        "ha@hah.hu", "11231120", company,
                        "ha", Status.ACTIVE, d1, d2),
                new Contact(6L, "2Nagyon", "Almos",
                        "ha@hah.hu", "11231120", company,
                        "ha", Status.ACTIVE, d1, d2),
                new Contact(7L, "3Nagyon", "Almos",
                        "ha@hah.hu", "11231120", company,
                        "ha", Status.ACTIVE, d1, d2)
        ).forEach(contact -> {
            contactRepository.save(contact);
        });*/
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allContact", tenContactDataFetcher)
                        .dataFetcher("contact", contactDataFetcher))
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
