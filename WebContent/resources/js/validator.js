var regDateSlash = /^([0-9]{2}\/){2}[0-9]{4}$/;
var regDateDash  = /^[0-9]{4}(\-[0-9]{2}){2}$/;

var regRationalDateSlash = /^(0[1-9]|[1-2][0-9]|3[01])\/(0[1-9]|1[0-2])\/([0-9]{4})$/;
var regRationalDateDash  = /^([0-9]{4})\-(0[1-9]|1[0-2])\-(0[1-9]|[1-2][0-9]|3[01])$/;

var regRationalYearDateSlash = /^(0[1-9]|[1-2][0-9]|3[01])\/(0[1-9]|1[0-2])\/(19[7-9][0-9]|20[0-1][0-9])$/;
var regRationalYearDateDash  = /^(19[7-9][0-9]|20[0-1][0-9])\-(0[1-9]|1[0-2])\-(0[1-9]|[1-2][0-9]|3[01])$/;


var NULL_NAME = "The computer must have a name.";

var OUT_OF_RANGE = "The given year is out of reasonable range.";
var NOT_A_DATE = "The given date isn't a real date.";
var WRONG_FORMAT = "Wrong format.";

var DISC_WITHOUT_INTRO = "If the introduced date is null, the discontinued date must be null too.";
var DISC_LESS_THAN_INTRO = "The discontinued date must be larger than the introduced one.";

var TOO_MUCH_DAYS = "There is too much days in the given month.";
var NOT_A_LEAP_YEAR = "The given date isn't a leap year.";

function enableAdd() {
	let enable = $("#errorName").is(":hidden")
		&& $("#errorIntroduced").is(":hidden")
		&& $("#errorDiscontinued").is(":hidden");
	
	$("#add").attr("disabled", !enable);
}

function errDiv(errElement, message) {
	if (message === undefined) {
		errElement
		.empty()
		.hide();
		errElement.parent().removeClass("has-error").addClass("has-success");
	} else {
		errElement
		.empty()
		.append(message)
		.show();
		errElement.parent().removeClass("has-success").addClass("has-error");
	}
	
	enableAdd();
}

var checkName = function() {
	let name = $("#computerName").val();
	
	let errName = $("#errorName");
	
	if (name != "") {
		errDiv(errName);
	} else {
		errDiv(errName, NULL_NAME)
	}
};

var checkOneDate = function(date) {
	let day = date[0];
	let month = date[1];
	let year = date[2];
	
	if (day <= 28) {
		return null;
	}
	
	if (month != 2) {
		if (day <= 30) {
			return null;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return TOO_MUCH_DAYS;
		}
		return null;
	}
	
	if (day > 29) {
		return TOO_MUCH_DAYS;
	}
	
	if (year%4 == 0) {
		if (year%100 == 0) {
			if (year%400 == 0) {
				return null;
			}
			return NOT_A_LEAP_YEAR;
		}
		return null;
	}
	
	return NOT_A_LEAP_YEAR;
};

var dateToArray = function (date) {
	if (date.match(regRationalYearDateSlash)) {
		ret = date.split("/");
		
		day = parseInt(ret[0], 10);
		month = parseInt(ret[1], 10);
		year = parseInt(ret[2], 10);
		
		return [day, month, year];
	} else if (date.match(regRationalYearDateDash)) {
		ret = date.split("-");
		
		year = parseInt(ret[0], 10);
		month = parseInt(ret[1], 10);
		day = parseInt(ret[2], 10);
		
		return [day, month, year];
	}
	
	return [0, 0, 0];
};

var checkDates = function(intro, disc) {
	let dIntro = dateToArray(intro);
	let correctIntro = checkOneDate(dIntro);
	
	if (correctIntro != null) {
		return correctIntro;
	}
	
	let dayIntro = dIntro[0];
	let monthIntro = dIntro[1];
	let yearIntro = dIntro[2];
	
	let dDisc = dateToArray(disc);
	let correctDisc = checkOneDate(dDisc);
	
	if (correctDisc != null) {
		return correctDisc;
	}
	
	let dayDisc = dDisc[0];
	let monthDisc = dDisc[1];
	let yearDisc = dDisc[2];
	
	if (yearDisc > yearIntro) {
		return null;
	}
	
	if (yearDisc == yearIntro) {
		if (monthDisc > monthIntro) {
			return null;
		}
		
		if (monthDisc == monthIntro && dayDisc >= dayIntro) {
			return null;
		}
	}
	
	return DISC_LESS_THAN_INTRO;
};

var disableDiscontinued = function(disable) {
	if (disable) {
		$("#discontinued").val("").attr("disabled", true);
		errDiv($("#errorDiscontinued"));
	} else {
		$("#discontinued").attr("disabled", false);
	}
}

var checkIntroduced = function() {
	let introduced = $("#introduced").val();
	let discontinued = $("#discontinued").val();
	
	let errIntro = $("#errorIntroduced");

	if (introduced != "") {
		if (introduced.match(regRationalYearDateSlash) || introduced.match(regRationalYearDateDash)) {
			let correctIntro = (checkOneDate(dateToArray(introduced)));
			if (correctIntro != null) {
				errDiv(errIntro, correctIntro);
				disableDiscontinued(true);
			} else {
				errDiv(errIntro);
				disableDiscontinued(false);
			}
		} else if (introduced.match(regRationalDateSlash) || introduced.match(regRationalDateDash)) {
			errDiv(errIntro, OUT_OF_RANGE);
			disableDiscontinued(true);
		} else if (introduced.match(regDateSlash) || introduced.match(regDateDash)) {
			errDiv(errIntro, NOT_A_DATE);
			disableDiscontinued(true);
		} else {
			errDiv(errIntro, WRONG_FORMAT);
			disableDiscontinued(true);
		}
	} else {
		errDiv(errIntro);
		disableDiscontinued(true);
	}
};

var checkDiscontinued = function() {
	let introduced = $("#introduced").val();
	let discontinued = $("#discontinued").val();
	
	let errDisc = $("#errorDiscontinued");
	
	if (discontinued != "") {
		if (introduced == "") {
			errDiv(errDisc, DISC_WITHOUT_INTRO);
		} else if (discontinued.match(regRationalYearDateSlash) || discontinued.match(regRationalYearDateDash)) {
			let correctDates = checkDates(introduced, discontinued);
			if (correctDates != null) {
				errDiv(errDisc, correctDates);
			} else {
				errDiv(errDisc);
			}
		} else if (discontinued.match(regRationalDateSlash) || discontinued.match(regRationalDateDash)) {
			errDiv(errDisc, OUT_OF_RANGE);
		} else if (discontinued.match(regDateSlash) || discontinued.match(regDateDash)) {
			errDiv(errDisc, NOT_A_DATE);
		} else {
			errDiv(errDisc, WRONG_FORMAT);
		}
	} else {
		errDiv(errDisc);
	}
};

$(document).ready(function() {
	checkName();
	checkIntroduced();
	checkDiscontinued();

//	$("#discontinued").attr("disabled", !checkIntroduced());
	
	$("#computerName").on("load change paste keyup cut", checkName);
	$("#introduced").on("load change paste keyup cut", checkIntroduced);
	$("#discontinued").on("load change paste keyup cut", checkDiscontinued);
});
