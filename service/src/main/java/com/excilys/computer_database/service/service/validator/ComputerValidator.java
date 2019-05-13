package com.excilys.computer_database.service.service.validator;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.Computer;
import com.excilys.computer_database.persistence.CompanyDAO;
import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.service.service.exception.ConcernedField;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Component
public class ComputerValidator {
	private Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
	
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private CompanyDAO companyDAO;
	
	public void checkId(ComputerDTO computer) throws FailComputerException {
		String id = computer.getId();
		
		Optional<Integer> optId = Util.parseInt(id);
		if (optId.isEmpty()) {
			logger.warn("checkId - Invalid id");
			throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
		}
		
		Optional<Computer> toChange = computerDAO.getById(optId.get());
		if (toChange.isEmpty()) {
			logger.warn("checkId - This computer does not exist");
			throw new FailComputerException(ConcernedField.ID, FailComputerException.ID_ERROR);
		}
	}
	
	public void checkName(ComputerDTO computer) throws FailComputerException {
		if (computer.getName() == null || "".equals(computer.getName().trim())) {
			logger.warn("checkName - Empty name");
			throw new FailComputerException(ConcernedField.NAME, FailComputerException.NULL_NAME);
		}
	}
	
	public void checkDates(ComputerDTO computer) throws FailComputerException {
		String introduced = computer.getIntroduced();
		String discontinued = computer.getDiscontinued();
		
		Timestamp retIntroduced = null;
		Timestamp retDiscontinued = null;
		
		if (introduced == null || "".equals(introduced)) {
			if (discontinued != null && !"".equals(discontinued)) {
				logger.warn("checkDates - Discontinued without introduced");
				throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_WITHOUT_INTRO);
			}
		} else {
			Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
			
			if (optIntroduced.isEmpty()) {
				logger.warn("checkDates - Introduced: Wrong format");
				throw new FailComputerException(ConcernedField.INTRODUCED, FailComputerException.WRONG_FORMAT);
			}
			retIntroduced = optIntroduced.get();
			
			if (discontinued != null && !"".equals(discontinued)) {
				Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
				
				if (optDiscontinued.isEmpty()) {
					logger.warn("checkDates - Discontinued: Wrong format");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.WRONG_FORMAT);
				}
				retDiscontinued = optDiscontinued.get();
				
				if (retIntroduced.after(retDiscontinued)) {
					logger.warn("checkDates - Discontinued less then introduced");
					throw new FailComputerException(ConcernedField.DISCONTINUED, FailComputerException.DISC_LESS_THAN_INTRO);
				}
			}
		}
	}
	
	public void checkCompany(ComputerDTO computer) throws FailComputerException {
		String companyId = computer.getCompanyId();
		
		if (companyId != null) {
			Optional<Integer> optCompanyId = Util.parseInt(companyId);
			if (optCompanyId.isEmpty()) {
				logger.warn("checkCompany - Invalid company id");
				throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.INVALID_COMPANY_ID);
			}
			
			int intCompanyId = optCompanyId.get();
			
			if (intCompanyId > 0 && companyDAO.getById(intCompanyId).isEmpty()) {
				logger.warn("checkCompany - nonexistent company");
				throw new FailComputerException(ConcernedField.COMPANY, FailComputerException.NONEXISTENT_COMPANY);
			}
		} else {
			computer.setCompanyId("0");
		}
	}
}
