function clearDeliveryForm() {
    $("#addressFields input[type=text]").val("");
    $("#addressFieldsExisting").empty();
    $('.error-field').removeClass('error-field');
    $('#selectDeliverySlot').find('option').remove().end().append('<option value="">Select Delivery Slot</option>')
	    .val('');
    $(".areaMenu").empty();
    $(".subAreaMenu").empty();
    $('#addressDivElements').removeClass("hide").addClass("show");
    hideErrorMessage();
}
function getDeliverySlots() {
    ajax.postForm("fetchDeliverySlots?F=J", $("#deliveryForm")).done(
	    function(data) {
		$("#selectDeliverySlot").prop("disabled", false);
		if (data.errormessage && data.errormessage !== "") {
		    showErrorMessage(data.errormessage);
		} else if (data.deliverySlots) {
		    hideErrorMessage();
		    $('#selectDeliverySlot').find('option').remove().end().append(
			    '<option value="">Select Delivery Slot</option>').val('');
		    $.each(data.deliverySlots, function(i, deliverySlot) {
			if (deliverySlot.slotQuantity > 0) {
			    var slotText = "Slot Time :" + deliverySlot.startTime + "-" + deliverySlot.endTime
				    + "    Available Slots :" + deliverySlot.slotQuantity;
			    var slotValue = deliverySlot.deliverySlotId;
			    var option = new Option(slotText, slotValue);
			    // / jquerify the DOM object 'o'
			    // so we can use the html method
			    $(option).html(slotText);
			    $("#selectDeliverySlot").append(option);
			}
		    });
		}
	    }).fail(function(data) {
	alert("failed");
    });
}
function bindAddressModelEvents() {
    $("[data-th='individualAddress']").on('click', function(e) {
	clearDeliveryForm();
	$("#addressFieldsExisting").removeClass("hide").addClass("show");
	$('#addressFields').removeClass("show").addClass("hide");
	$("#addressFieldsExisting").html($(this).html());
	/*
	 * $("#firstNameAddr").val($(this).find("#firstName").html());
	 * $("#lastNameAddr").val($(this).find("#lastName").html());
	 * $("#addr1").val($(this).find("#addressLine1").html());
	 * $("#addr2").val($(this).find("#addressLine2").html());
	 * $("#landmarkAddr").val($(this).find("#landmark").html());
	 * $("#areaAddr").val($(this).find("#area").html());
	 */
	$("#deliveryAddressId").val($(this).find("#addressId").val());
	$("#searchId").val($(this).find("#areaId").val());
	$('#editAddress').removeClass("hide").addClass("show");
	$('#saveAddress').removeClass("show").addClass("hide");
	$('#myModal').modal('hide');
	// $('#addressFields').removeClass("hide").addClass("show");
	getDeliverySlots();
    });
    $(".editbutton").on('click', function(e) {
	editAddress($(this).parent().parent().parent().parent().find("[data-th='individualAddress']"));
    });
    $("#enterNewAddress").on('click', function(e) {
	$('#myModal').modal('hide');
	clearDeliveryForm();
	$("#addressFieldsExisting").removeClass("show").addClass("hide");
	$('#addressFields').removeClass("hide").addClass("show");
	$('#editAddress').removeClass("show").addClass("hide");
	$("#addAddress").removeClass("show").addClass("hide");
	$('#saveAddress').removeClass("hide").addClass("show");
    });
}
function createIndividualAddressRow(d) {
    var divIndividual = "<div class='customrow well well-sm' >";
    var divIndividual1 = "<div class='col-lg-8 col-xs-8' data-th='individualAddress' style='padding: 0px;'>";
    var ulIndividual = "<ul style='list-style:none;-webkit-padding-start: 15px;'>";
    var liIndividual = "";
    var hiddenAddressId = "<input type='hidden' id='addressId' value='" + d.addressId + "' />"
    var hiddenAreaId = "<input type='hidden' id='areaId' value='" + d.area.deliveryAreaId + "' />"
    if (d.firstName || d.lastName) {
	var firstName = "";
	var lastName = "";
	if (d.firstName && d.firstName !== null) {
	    firstName = d.firstName;
	}
	if (d.lastName && d.lastName !== null) {
	    lastName = d.lastName;
	}
	if (firstName != "" || lastName !== "") {
	    liIndividual = "<li id='addressName'>"
	    if (firstName != "") {
		liIndividual = liIndividual + "<span id='firstName'>" + firstName + "</span>";
		if (lastName !== "") {
		    liIndividual = liIndividual + " ";
		}
	    }
	    if (lastName !== "") {
		liIndividual = liIndividual + "<span id='lastName'>" + lastName + "</span>";
	    }
	    liIndividual = liIndividual + "</li>";
	    ulIndividual = ulIndividual + liIndividual;
	}
    }
    if (d.addressLine1) {
	liIndividual = "<li id='addressLine1'>" + d.addressLine1 + "</li>";
	ulIndividual = ulIndividual + liIndividual;
    }
    if (d.addressline2) {
	liIndividual = "<li id='addressLine2'>" + d.addressline2 + "</li>";
	ulIndividual = ulIndividual + liIndividual;
    }
    if (d.landmark) {
	liIndividual = "<li id='landmark'>" + d.landmark + "</li>";
	ulIndividual = ulIndividual + liIndividual;
    }
    liIndividual = "<li>";
    if (d.area) {
	if (d.area.subAreaName) {
	    liIndividual = liIndividual + "<span id='subArea'>";
	    liIndividual = liIndividual + d.area.subAreaName;
	    liIndividual = liIndividual + "</span>, ";
	}
	if (d.area.areaName) {
	    liIndividual = liIndividual + "<span id='area'>";
	    liIndividual = liIndividual + d.area.areaName;
	    liIndividual = liIndividual + "</span>, ";
	}
	if (d.area.zipcode) {
	    liIndividual = liIndividual + d.area.zipcode;
	}
	liIndividual = liIndividual + "</span>"
    }
    liIndividual = liIndividual + "</li>";
    ulIndividual = ulIndividual + liIndividual;
    ulIndividual = ulIndividual + "</ul></a>";
    if (d.addressLine1 || d.addressline1) {
	var liedit = "<li><div class='editbutton col-lg-4' style='text-align: right;'><a><span class='glyphicon glyphicon-edit'> Edit</span></a></div></li>";
	divIndividual1 = divIndividual1 + ulIndividual + hiddenAreaId + hiddenAddressId + "</div>";
	divIndividual = divIndividual + divIndividual1 + "<div class='row'><ul style='list-style:none;'>" + liedit
		+ "</ul></div></div>";
    }
    return divIndividual;
}
function getAddressDetails() {
    ajax.postForm("fetchDeliveryDetails?F=J", $("#deliveryForm")).done(
	    function(data) {
		var ulFinal = "<ul style='list-style:none;padding: 0px;'>";
		var liFinal = "";
		var addressList = data.addressList;
		var email = data.emailAddress;
		if (addressList) {
		    $.each(addressList, function(i, d) {
			var divIndividual = createIndividualAddressRow(d);
			liFinal = "<li>" + divIndividual + "</li>";
			ulFinal = ulFinal + liFinal;
		    });
		}
		ulFinal = ulFinal + "</ul>";
		$("#emailDiv").removeClass("hide").addClass("show");
		$("#emailAddress").val(email);
		fetchDeliveryArea();
		if (addressList && addressList !== null && addressList.length === 1) {
		    clearDeliveryForm();
		    var addressExistingDiv = $("#addressFieldsExisting");
		    addressExistingDiv.empty();
		    var addressIndividual = "<ul style='list-style:none;padding-left: 20px;'>"
			    + $(liFinal).find('[data-th="individualAddress"]').html() + "</ul>"
		    addressExistingDiv.append(addressIndividual);
		    $("#addressFieldsExisting").removeClass("hide").addClass("show");
		    $('#addressFields').removeClass("show").addClass("hide");
		    $("#deliveryAddressId").val($(addressIndividual).find("#addressId").val());
		    $('#editAddress').removeClass("hide").addClass("show");
		    $("#changeAddress").removeClass("show").addClass("hide");
		    $("#addAddress").removeClass("hide").addClass("show");
		    $('#saveAddress').removeClass("show").addClass("hide");
		    $("#searchId").val($("#addressFieldsExisting").find("#areaId").val());
		    getDeliverySlots();
		} else if (addressList && addressList !== null && addressList.length > 1) {
		    $('#myModal').modal();
		    var addressDiv = $("#addressDiv");
		    addressDiv.empty();
		    addressDiv.append(ulFinal);
		    $('#myModal').modal('handleUpdate');
		    $('#editAddress').removeClass("hide").addClass("show");
		    $("#changeAddress").removeClass("hide").addClass("show");
		    $("#addAddress").removeClass("show").addClass("hide");
		    $('#saveAddress').removeClass("show").addClass("hide");
		    bindAddressModelEvents();
		} else {
		    clearDeliveryForm();
		    $("#addressFieldsExisting").removeClass("show").addClass("hide");
		    $("#emailDiv").removeClass("hide").addClass("show");
		    $('#addressFields').removeClass("hide").addClass("show");
		    $('#editAddress').removeClass("show").addClass("hide");
		    $("#changeAddress").removeClass("show").addClass("hide");
		    $("#addAddress").removeClass("show").addClass("hide");
		    $('#saveAddress').removeClass("show").addClass("hide");
		}
		$("#addAddressButton").on('click', function(e) {
		    clearDeliveryForm();
		    $("#addressFieldsExisting").removeClass("show").addClass("hide");
		    $('#addressFields').removeClass("hide").addClass("show");
		    $('#editAddress').removeClass("show").addClass("hide");
		    $("#addAddress").removeClass("show").addClass("hide");
		    $('#saveAddress').removeClass("hide").addClass("show");
		    $("#deliveryAddressId").val('');
		});
		$("#changeAddresslink").on('click', function(e) {
		    $('#myModal').modal();
		});
		$("#editAddresslink").on('click', function(e) {
		    editAddress($("#addressFieldsExisting"));
		});
		$("#saveAddress").on('click', function(e) {
		    $('.has-error').removeClass('has-error');
		    hideErrorMessage();
		    if (validateDeliveryForm(true)) {
			$("#editAddressRequested").val("true");
			ajax.postForm("verifyDetails?F=J", $("#deliveryForm")).done(function(data) {
			    $("#editAddressRequested").val("false");
			    getAddressDetails();
			}).fail(function(data) {
			    showErrorMessage("Something went wrong. Please try again later.");
			    hideOverlay();
			});
		    }
		});
	    }).fail(function(data) {
	alert("failed");
    });
}
function editAddress(address) {
    $("#firstNameAddr").val($(address).find("#firstName").html());
    $("#lastNameAddr").val($(address).find("#lastName").html());
    $("#addr1").val($(address).find("#addressLine1").html());
    $("#addr2").val($(address).find("#addressLine2").html());
    $("#landmarkAddr").val($(address).find("#landmark").html());
    $("#areaAddr").val($(address).find("#area").html());
    $("#subAreaAddr").val($(address).find("#subArea").html());
    $("#deliveryAddressId").val($(address).find("#addressId").val());
    $("#searchId").val($(address).find("#areaId").val());
    $('#myModal').modal('hide');
    $('#addressFieldsExisting').removeClass("show").addClass("hide");
    $('#addressFields').removeClass("hide").addClass("show");
    $('#addressDivElements').removeClass("hide").addClass("show");
    $('#saveAddress').removeClass("hide").addClass("show");
    $('#editAddress').removeClass("show").addClass("hide");
    getDeliverySlots();
}
function validateDeliveryForm(isSaveAddress) {
    var phoneNumberVal = $("#phoneNumber").val(), firstNameVal1 = $("#firstNameAddr").val(), lastNameVal1 = $(
	    "#lastNameAddr").val(), addressVal1 = $("#addr1").val(), addressVal2 = $("#addr2").val(), areaAddrVal = $(
	    "#areaAddr").val(), subAreaAddrVal = $("#subAreaAddr").val(), emailAddressVal = $("#emailAddress").val(), landMarkVal = $(
	    "#landmarkAddr").val(), deliverySlotVal = $("#selectDeliverySlot").val(), existingAddress = $(
	    "#addressFieldsExisting").html(), noOfFieldsInError = 0, errorMessage = "";
    if (existingAddress === "") {
	if (phoneNumberVal === null || phoneNumberVal == "" || phoneNumberVal.length < 10) {
	    $("#phoneNumber").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "Phone Number is a required field.";
	}
	if (firstNameVal1 === null || firstNameVal1 == "") {
	    $("#firstNameAddr").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "First Name is a required field.";
	}
	if (lastNameVal1 === null || lastNameVal1 == "") {
	    $("#lastNameAddr").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "Last Name is a required field.";
	}
	if (addressVal1 === null || addressVal1 == "") {
	    $("#addr1").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "Address Line 1 is a required field.";
	}
	if (areaAddrVal === null || areaAddrVal == "") {
	    $("#areaAddr").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "Area is a required field.";
	}
	if (subAreaAddrVal === null || subAreaAddrVal == "") {
	    $("#subAreaAddr").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "SubArea is a required field.";
	}
	if (emailAddressVal === null || emailAddressVal == "") {
	    $("#emailAddress").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "Email Address is a required field.";
	}
    } else {
	if (!isSaveAddress) {
	    if ($("#deliveryAddressId").val() === "") {
		noOfFieldsInError = noOfFieldsInError + 1;
		errorMessage = "Something went wrong. Please try again later.";
	    }
	}
    }
    if (!isSaveAddress) {
	if (deliverySlotVal === null || deliverySlotVal == "") {
	    $("#selectDeliverySlot").parent().addClass('has-error');
	    noOfFieldsInError = noOfFieldsInError + 1;
	    errorMessage = "Please select a delivery slot to continue.";
	}
    }
    if (noOfFieldsInError > 1) {
	errorMessage = "There are two or more fields in error";
    }
    if (errorMessage !== "") {
	showErrorMessage(errorMessage);
	return false;
    } else {
	hideErrorMessage();
	return true;
    }
}
function bindDeliveryEvents() {
    $('#phoneNumber').on("keyup", function() {
	var length = $(this).val().length;
	if (length && length == 10) {
	    getAddressDetails();
	}
    });
}
