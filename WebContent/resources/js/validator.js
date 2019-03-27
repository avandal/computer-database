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
	} else {
		errElement
		.empty()
		.append(message)
		.show();
	}
	
	enableAdd();
}

var checkName = function() {
	let name = $("#computerName").val();
	
	let errName = $("#errorName");
	
	if (name != "") {
		errDiv(errName);
	} else {
		errDiv(errName, "The name cannot be null.")
	}
};

$(document).ready(function() {
	checkName();
	
	let regDateSlash = /^([0-9]{2}\/){2}[0-9]{4}$/;
	let regDateDash  = /^[0-9]{4}(\-[0-9]{2}){2}$/;
	
	let regRationalDateSlash = /^(0[1-9]|[1-2][0-9]|3[01])\/(0[1-9]|1[0-2])\/([0-9]{4})$/;
	let regRationalDateDash  = /^([0-9]{4})\-(0[1-9]|1[0-2])\-(0[1-9]|[1-2][0-9]|3[01])$/;
	
	let regRationalYearDateSlash = /^(0[1-9]|[1-2][0-9]|3[01])\/(0[1-9]|1[0-2])\/(19[7-9][0-9]|20[0-1][0-9])$/;
	let regRationalYearDateDash  = /^(19[7-9][0-9]|20[0-1][0-9])\-(0[1-9]|1[0-2])\-(0[1-9]|[1-2][0-9]|3[01])$/;

	$("#discontinued").attr("disabled", true);
	
	$("#computerName").on("change paste keyup cut", checkName);

	$("#introduced").on("change paste keyup cut", function() {
		let introduced = $("#introduced").val();
		let discontinued = $("#discontinued").val();
		
		let errIntro = $("#errorIntroduced");

		if (introduced != "") {
			if (introduced.match(regRationalYearDateSlash) || introduced.match(regRationalYearDateDash)) {
				errDiv(errIntro);
				$("#discontinued").attr("disabled", false);
			} else if (introduced.match(regRationalDateSlash) || introduced.match(regRationalDateDash)) {
				errDiv(errIntro, "The given year is out of reasonable range.");
			} else if (introduced.match(regDateSlash) || introduced.match(regDateDash)) {
				errDiv(errIntro, "The given date isn't a real date.");
			} else {
				errDiv(errIntro, "Wrong format.");
				$("#discontinued").attr("disabled", true);
			}
		} else {
			errDiv(errIntro);
			$("#discontinued").attr("disabled", true);
		}
	});
	
	$("#discontinued").on("change paste keyup cut", function() {
		let introduced = $("#introduced").val();
		let discontinued = $("#discontinued").val();
		
		let errDisc = $("#errorDiscontinued");
		
		if (discontinued != "") {
			if (introduced == "") {
				errDiv(errDisc, "If the introduced date is null, the discontinued date must be null too");
			}
		} else {
			errDiv(errDisc);
		}
	});
});
