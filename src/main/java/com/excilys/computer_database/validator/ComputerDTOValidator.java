package com.excilys.computer_database.validator;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.computer_database.dto.ComputerDTO;
import com.excilys.computer_database.util.Util;

public class ComputerDTOValidator implements Validator {
	private static final String EMPTY_NAME = "error.validator.name.empty";
	private static final String INTRO_WRONG_FORMAT = "error.validator.intro.format";
	private static final String DISC_WRONG_FORMAT = "error.validator.disc.format";
	private static final String DISC_WITHOUT_INTRO = "error.validator.disc.without_intro";
	private static final String DISC_LESS_THAN_INTRO = "error.validator.disc.less_than_intro";
	private static final String INVALID_COMPANY = "error.validator.comp.invalid";
	
	Logger logger = LoggerFactory.getLogger(ComputerDTOValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
			logger.error("Target is null");
			return;
		}
		
		verif(target, errors);
	}
	
	private void checkName(ComputerDTO computer, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", EMPTY_NAME);
	}
	
	private void checkIntroduced(ComputerDTO computer, Errors errors) {
		String introduced = computer.getIntroduced();
		
		if (introduced != null && !introduced.trim().equals("")
			&& Util.dateToTimestamp(introduced).isEmpty()) {
			
				logger.error("Introduced wrong format");
				errors.rejectValue("introduced", INTRO_WRONG_FORMAT);
		}
	}
	
	private void checkDiscontinued(ComputerDTO computer, Errors errors) {
		String introduced = computer.getIntroduced();
		String discontinued = computer.getDiscontinued();
		
		Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
		Optional<Timestamp> optDiscontinued = Util.dateToTimestamp(discontinued);
		
		if (introduced == null || introduced.trim().equals("")) {
			if (discontinued != null && !discontinued.trim().equals("")) {
				logger.error("Discontinued without introducted");
				errors.rejectValue("discontinued", DISC_WITHOUT_INTRO);
			}
		} else {
			if (discontinued == null || discontinued.trim().equals("")) {
				return;
			}
			
			if (optDiscontinued.isEmpty()) {
				logger.error("Discontinued wrong format");
				errors.rejectValue("discontinued", DISC_WRONG_FORMAT);
			} else {
				if (optIntroduced.isEmpty()) {
					return;
				}
				
				Timestamp intro = optIntroduced.get();
				Timestamp disc = optDiscontinued.get();
				
				if (disc.before(intro)) {
					logger.error("Discontinued less than introduced");
					errors.rejectValue("discontinued", DISC_LESS_THAN_INTRO);
				}
			}
		}
	}
	
	private void checkCompany(ComputerDTO computer, Errors errors) {
		Optional<Integer> optCompany = Util.parseInt(computer.getCompanyId());
		
		if (optCompany.isEmpty()) {
			logger.error("Invalid company id");
			errors.rejectValue("companyId", INVALID_COMPANY);
		}
	}
	
	private void verif(Object obj, Errors errors) {
		if (!(obj instanceof ComputerDTO)) {
			logger.error("verif - Error type");
			return;
		}
		
		ComputerDTO computer = (ComputerDTO) obj;
		
		checkName(computer, errors);
		checkIntroduced(computer, errors);
		checkDiscontinued(computer, errors);
		checkCompany(computer, errors);
	}
}
