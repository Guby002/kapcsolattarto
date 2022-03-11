package hu.futureofmedia.task.contactsapi.mapper;

import hu.futureofmedia.task.contactsapi.DTO.ContractDTO;
import hu.futureofmedia.task.contactsapi.entities.Contract;
import hu.futureofmedia.task.contactsapi.mapping.ContractMapper;
import hu.futureofmedia.task.contactsapi.mapping.ContractMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertAll;

public class ContractMapperTest {
    private static ContractMapper mapper;

    private static PodamFactory podamFactory;
    @BeforeAll
    public static void setUp() {
        podamFactory = new PodamFactoryImpl();

        // to avoid infinite loops creating random data
        DefaultClassInfoStrategy classInfoStrategy = DefaultClassInfoStrategy.getInstance();
        classInfoStrategy.addExcludedField(Contract.class, "phoneNumber");
        classInfoStrategy.addExcludedField(Contract.class, "comment");
        classInfoStrategy.addExcludedField(Contract.class, "stat");

        podamFactory.setClassStrategy(classInfoStrategy);

        mapper = new ContractMapperImpl();
    }
    @Test
    void testContractMappertoContractDTO(){

        Contract contract = podamFactory.manufacturePojo(Contract.class);

        ContractDTO contractDTO = mapper.toContractDto(contract);

        assertAll(
                () -> {
                    Assertions.assertEquals(contract.getFirstName(), contractDTO.getFirstName());
                    Assertions.assertEquals(contract.getSecondName(), contractDTO.getSecondName());
                    //if thats 2 true every copy will be okey
                }
        );
    }
    @Test
    void testContractMappertoContract(){

        ContractDTO contractDTO = podamFactory.manufacturePojo(ContractDTO.class);

        Contract contract = mapper.toContract(contractDTO);

        assertAll(
                () -> {
                    Assertions.assertEquals(contract.getFirstName(), contractDTO.getFirstName());
                    Assertions.assertEquals(contract.getSecondName(), contractDTO.getSecondName());
                    //if thats 2 true every copy will be okey

                }
        );
    }
}
