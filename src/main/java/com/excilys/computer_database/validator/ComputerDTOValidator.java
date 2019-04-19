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
	
	Logger logger = LoggerFactory.getLogger(ComputerDTOValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
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
			&& !Util.dateToTimestamp(introduced).isPresent()) {
			
				errors.rejectValue("introduced", INTRO_WRONG_FORMAT);
		}
	}
	
	private void checkDiscontinued(ComputerDTO computer, Errors errors) {
		String introduced = computer.getIntroduced();
		String discontinued = computer.getDiscontinued();
		
		Optional<Timestamp> optIntroduced = Util.dateToTimestamp(introduced);
		Optional<Timestamp> optTsd = Util.dateToTimestamp(discontinued);
		
		if (introduced == null || introduced.trim().equals("")) {
			if (discontinued != null && !discontinued.trim().equals("")) {
				errors.rejectValue("discontinued", DISC_WITHOUT_INTRO);
			}
		} else {
			if (discontinued != null && !discontinued.trim().equals("")) {
				if (!optTsd.isPresent()) {
					errors.rejectValue("discontinued", DISC_WRONG_FORMAT);
				}
			}
			Timestamp tsi = optIntroduced.get();
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
	}
}
