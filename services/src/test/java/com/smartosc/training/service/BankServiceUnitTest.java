package com.smartosc.training.service;

import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.entity.Bank;
import com.smartosc.training.exception.FieldDuplicateException;
import com.smartosc.training.repositories.BankRepository;
import com.smartosc.training.utils.ConvertUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * fres-parent
 *
 * @author thanhdat
 * @created_at 24/04/2020 - 10:50 AM
 * @since 24/04/2020
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BankServiceUnitTest {

    @InjectMocks
    private BankService bankService;

    @Mock
    private BankRepository bankRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createBank_Success() throws FieldDuplicateException {
        BankDTO requestData = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());
        BankDTO responseData = null;

        when(bankRepository.save(any(Bank.class))).thenAnswer((Answer<Bank>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Bank bank = (Bank) arguments[0];
                bank.setBankId(1);
                return bank;
            }
            return null;
        });

        responseData = bankService.createBank(requestData);
        requestData.setBankId(1);
        Assertions.assertEquals(requestData, responseData);
    }

    @Test
    public void getAllBank_Success() throws FieldDuplicateException {
        Page<BankDTO> responseData = null;

        when(bankRepository.searchByNameAndDes(anyString(), any(Pageable.class))).thenAnswer((Answer<Page<Bank>>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0) {

                List<Bank> bank = new ArrayList<>();
                Bank bank1 = new Bank(1, "code", "shortName", "legalName", "1545", 1, "createdBy", LocalDateTime.now(), "modifiedBy", LocalDateTime.now());
                Bank bank2 = new Bank(2, "code", "shortName", "legalName", "1545", 1, "createdBy", LocalDateTime.now(), "modifiedBy", LocalDateTime.now());
                Bank bank3 = new Bank(3, "code", "shortName", "legalName", "1545", 1, "createdBy", LocalDateTime.now(), "modifiedBy", LocalDateTime.now());
                Bank bank4 = new Bank(4, "code", "shortName", "legalName", "1545", 1, "createdBy", LocalDateTime.now(), "modifiedBy", LocalDateTime.now());

                bank.add(bank1);
                bank.add(bank2);
                bank.add(bank3);
                bank.add(bank4);
                return new PageImpl<>(bank);
            }
            return null;
        });

        responseData = bankService.getAllBanks(" ", 0, 5, "bankId");
        Assertions.assertEquals(4, responseData.getContent().size());
    }

    @Test
    public void createBank_duplicateCode_throwFieldDuplicateException() {
        BankDTO requestData = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());
        String inputCode = "DEMO01";

        when(bankRepository.findByCode(inputCode)).thenAnswer((Answer<Optional<Bank>>) invocationOnMock -> {
            BankDTO result = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                    "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());
            result.setBankId(1);
            return Optional.of(ConvertUtils.convertBankDTOToBank(result));
        });

        Assertions.assertThrows(FieldDuplicateException.class, () -> {
            bankService.createBank(requestData);
        });
    }

    @Test
    public void createBank_duplicateLegalName_throwFieldDuplicateException() {
        BankDTO requestData = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());
        String inputLegalName = "Demo Bank 01";

        when(bankRepository.findByLegalName(inputLegalName)).thenAnswer((Answer<Optional<Bank>>) invocationOnMock -> {
            BankDTO result = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                    "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());
            result.setBankId(1);
            return Optional.of(ConvertUtils.convertBankDTOToBank(result));
        });

        Assertions.assertThrows(FieldDuplicateException.class, () -> {
            bankService.createBank(requestData);
        });
    }

    @Test
    public void createBank_duplicatePrefixCard_throwFieldDuplicateException() {
        BankDTO requestData = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());
        String inputPrefixCard = "00101";

        when(bankRepository.findByPrefixCard(inputPrefixCard)).thenAnswer((Answer<Optional<Bank>>) invocationOnMock -> {
            BankDTO result = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                    "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());
            result.setBankId(1);
            return Optional.of(ConvertUtils.convertBankDTOToBank(result));
        });

        Assertions.assertThrows(FieldDuplicateException.class, () -> {
            bankService.createBank(requestData);
        });
    }

}