package com.smartosc.training.service;

import com.smartosc.training.dto.BankDTO;
import com.smartosc.training.entity.Bank;
import com.smartosc.training.entity.Status;
import com.smartosc.training.exception.FieldDuplicateException;
import com.smartosc.training.repositories.BankRepository;
import com.smartosc.training.utils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);

    private BankRepository bankRepository;


    @Autowired
    public BankService(final BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    /**
     * created by duongbv
     * get all Banks
     *
     * @param searchValue
     * @param pageNo
     * @param sizeNo
     * @param sortBy
     * @return page
     */
    public Page<BankDTO> getAllBanks(String searchValue, Integer pageNo, Integer sizeNo, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, sizeNo, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Bank> pageResult = bankRepository.searchByNameAndDes(searchValue, pageable);
        return pageResult.map(ConvertUtils::convertBankToBankDTO);
    }

    /**
     * Create new bank
     *
     * @author DatNT5
     * @param bankDTO
     * @return the bank has been created
     * @throws FieldDuplicateException if duplicate fields found
     */
    public BankDTO createBank(BankDTO bankDTO) throws FieldDuplicateException {
        LOGGER.info("Starting check duplicate");
        if (bankRepository.findByCode(bankDTO.getCode()).isPresent()) {
            throw new FieldDuplicateException("Bank code: " + bankDTO.getCode() + " already exist!");
        } else if (bankRepository.findByLegalName(bankDTO.getLegalName()).isPresent()) {
            throw new FieldDuplicateException("Bank legal name: " + bankDTO.getLegalName() + " already exist!");
        } else if (bankRepository.findByPrefixCard(bankDTO.getPrefixCard()).isPresent()) {
            throw new FieldDuplicateException("Bank prefix card " + bankDTO.getPrefixCard() + " already exist!");
        } else {
            LOGGER.info("No duplicate found!");
            Bank result = ConvertUtils.convertBankDTOToBank(bankDTO);
            return ConvertUtils.convertBankToBankDTO(bankRepository.save(result));
        }
    }

    /**
     * create by tuan on 17/04/2020
     *
     * @param bankDTO
     * @return
     */
    public BankDTO updateBank(BankDTO bankDTO, Integer bankId) {
        LOGGER.info("Starting check duplicate");
        Optional<Bank> responseData = null;
        if ((responseData = bankRepository.findByCode(bankDTO.getCode())).isPresent()) {
            if (!bankDTO.getBankId().equals(responseData.get().getBankId()))
                throw new FieldDuplicateException("Bank code: " + bankDTO.getCode() + " already exist!");
        }
        if ((responseData = bankRepository.findByLegalName(bankDTO.getLegalName())).isPresent()) {
            if (!bankDTO.getBankId().equals(responseData.get().getBankId()))
                throw new FieldDuplicateException("Bank legal name: " + bankDTO.getLegalName() + " already exist!");
        }
        if ((responseData = bankRepository.findByPrefixCard(bankDTO.getPrefixCard())).isPresent()) {
            if (!bankDTO.getBankId().equals(responseData.get().getBankId()))
                throw new FieldDuplicateException("Bank prefix card " + bankDTO.getPrefixCard() + " already exist!");
        }
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (bank.isPresent()) {
            LOGGER.info("No duplicate found!");
            Bank bankUpdate = bankRepository.save(ConvertUtils.convertBankDTOToBankUpdate(bankDTO, bank.get(), bankId));
            LOGGER.info("update successful!");
            return ConvertUtils.convertBankToBankDTO(bankUpdate);
        }
        LOGGER.info("update fail by not found bank!");
        return null;
    }

    public BankDTO findById(Integer id) {
        Optional<Bank> result = bankRepository.findById(id);
        return result.map(ConvertUtils::convertBankToBankDTO).orElse(null);
    }

    /**
     * @param id
     * @return void
     * @author Duong NV
     * @since 21/04/2020
     */

    public void deleteBank(int id) throws Exception {
        try {
            if (!bankRepository.findById(id).isPresent()) {
                throw new Exception("Bank is not exits");
            }
            bankRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Check bank is duplicate or not
     *
     * @author DatNT5
     * @param bankDTO
     * @return bank already exist
     */
    public Boolean isBankExist(BankDTO bankDTO) {
        Boolean result = false;
        List<Bank> banks = bankRepository.findByCodeOrLegalNameOrPrefixCard(
                bankDTO.getCode(), bankDTO.getLegalName(), bankDTO.getPrefixCard());
        for(Bank bank : banks) {
            if(bankDTO.getBankId() == null || !bankDTO.getBankId().equals(bank.getBankId())) {
                result = true;
            }
        }
        return result;
    }
}
