define(["jquery", "common/mwf-core-ajax","bootstrap.min", "bootstrap-formhelpers", "user/inputnumber", "user/deliveryaddress", "user/deliveryarea", "common/common"], function($) {


$(document).ready(function () {
    if($("#selectedAreaId").val() ==="") {
		fetchDeliveryArea();
		$("#areaModal").modal();
		bindAreaModalEvents();
    } else {
	
    }
    function bindAreaModalEvents() {
		$("#enterArea").on("click" , function() {
		   
		    showOverlay();
		    updateItemStocks();
		    hideOverlay();
		});
    }
    $("#partyOrdernav").on("click", function() {
    	$("#errorDiv").removeClass("show").addClass("hide");    	
    	hideMaxErrorMessage();
    });
    $("#comboOrdernav").on("click", function() {
    	if($("#errorDiv").html() !== "") {
    		$("#errorDiv").removeClass("hide").addClass("show");
    	}
    	hideMaxErrorMessage();
    });
    $("#individualOrdernav").on("click", function() {
    	if($("#errorDiv").html() !== "") {
    		$("#errorDiv").removeClass("hide").addClass("show");
    	}
    	hideMaxErrorMessage();
    });
    function validateAreaForm() {
		 var phoneNumberVal = $("#phoneNumberInit").val(), 
		 selectedAreaIdVal = $("#selectedAreaId").val(),
		 areaAddrVal = $("#areaAddr").val(),
		 subAreaAddrVal = $("#subAreaAddr").val(),
		 errorMessage="",
		 noOfFieldsInError = 0;
		 if (phoneNumberVal === null || phoneNumberVal == "" || phoneNumberVal.length < 10) {
		     $("#phoneNumberInit").parent().addClass('has-error');
		     noOfFieldsInError = noOfFieldsInError + 1;
		     errorMessage = "Phone Number is a required field.";
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
		if (selectedAreaIdVal === null || selectedAreaIdVal == "") {
		    $("#areaAddr").parent().addClass('has-error');
		    $("#subAreaAddr").parent().addClass('has-error');
		    noOfFieldsInError = noOfFieldsInError + 1;
		    errorMessage = "Please select a appropriate area and subarea to continue";
		}
		
		if (errorMessage !== "") {
		    showAreaErrorMessage(errorMessage);
		    return false;
		} else {
		    hideAreaErrorMessage();
		    return true;
		}
    }
    function updateItemStocks() {
	if(validateAreaForm()) {
	    $("#areaModal").modal('hide');
	    ajax.postForm("updateItemStock?F=J", $("#areaForm")).done(function(data) {
	    	hideErrorMessage();
        	    if(data.availableStock) {
        	    	var unavailbleStock ="";
        	    	
	        		 $.each(data.availableStock, function(i, stock) {
	        			 var itemName = "";
	        		     if(stock.stuffing) { 
	        		    	 
	        		    	 id= "stuffing~"+stock.id;
	        		    	 itemName = $(('[data-stuffingId="'+stock.id+'"]')).attr("data-itemName");
	        		    	 $("span[id='"+id+"'").attr("data-maxvalue",stock.quantity);
	        		    	 $("span[id='"+id+"'").attr("data-availablevalue",stock.quantity);
	        		     } else {			
	        		    	 id= "item~"+stock.id;
	        		    	 itemName = $(('[data-itemId="'+stock.id+'"]')).attr("data-itemName");
	        		    	 $("span[id='"+id+"'").attr("data-maxvalue",stock.quantity);
	        		    	 $("span[id='"+id+"'").attr("data-availablevalue",stock.quantity);
	        		     }
	        		     var stockQuantity = stock.quantity*1;
	        		     if(stockQuantity ===0) {
	        		    	 if(unavailbleStock !==  "") {
	        		    		 unavailbleStock = unavailbleStock+ ", " + itemName
	        		    	 } else {
	        		    		 unavailbleStock = unavailbleStock + itemName;
	        		    	 }
	        		     }
	        		     
	        		 });
	        		if(unavailbleStock !== "") {
	        			var noOfUnavaiableItems = unavailbleStock.split(",").length;
	        			var errorMessage = "";
	        			if(noOfUnavaiableItems ===1) {
	        				errorMessage = unavailbleStock+" is currenty not available in your area."
	        			}  else {
	        				errorMessage = unavailbleStock+" are currenty not available in your area."
	        			}
	        			 
	        			showErrorMessage(errorMessage)
	        		}
        	    }
        	    if(data.comboItems) {
        		 $.each(data.comboItems, function(i, comboItem) {		     			
        		    	 id= "item~"+comboItem.comboItemId;
        		    	 $("span[id='"+id+"'").attr("data-itemids",comboItem.itemIds);
        		    	$("span[id='"+id+"'").attr("data-itemquantity",comboItem.quantity);
        		     
        		     
        		 });
        		
        	    }
            	    $("#orderId").val(data.orderId);
            	    
        	}).fail(function(data) {
        		showErrorMessage("Something Went Wrong. Please Try Again Later")
        	    alert("failed");
        	    hideOverlay();
        	});
	}
    }
    function createDeliveryDetailsInfo(data) {
        var addressInfo = "<div class='row'><div class='col-lg-12'>Delivery Address :</div></div><div class='row'><div class='col-lg-12'><label>"
    	    + data.addressLine
    	    + "</label></div></div><div class='row'><div class='col-lg-12'>"
    	    + data.landmarkReturn
    	    + "<div class='row'><div class='col-lg-12'>" + data.zipcodeReturn + "</div></div></div></div>";
        var slotInfo = "<div class='row'><div class='col-lg-12'>Delivery Slot Selected :<br><label>" + data.deliverySlot
    	    + "</label></div></div>";
        var deliveryDetailsInfo = addressInfo + slotInfo;
        return deliveryDetailsInfo;
    }
    // Initialize tooltips
   
    $('.nav-tabs > li a[title]').tooltip();
    // Wizard
    $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
        var $target = $(e.target);
        if ($target.parent().hasClass('disabled')) {
    	return false;
        }
    });
    function resetPartyOrder() {
        $("select[name*='partyQauntity'] option[selected='selected']").removeAttr('selected');
        // mark the first option as selected
        $("select[name*='partyQauntity'] option:first").attr('selected', 'selected');
        var itemId = $("select[name*='partyQauntity']").attr("data-itemId");
        var itemPriceId = "itemPrice" + itemId;
        $("#" + itemPriceId).html('0.00');
        $("#partyQuantitySummary").empty();
    }
    $("select[name*='partyQauntity']").change(
    	function() {
    	    var str = "";
    	    var individualItemAvailable = false;
    	    var itemId = $(this).attr("data-itemId");
    	    var subtotalId = "subTotalItem" + itemId;
    	    var itemOrderId = "partyItem" + itemId;
    	    var itemPrice = $("select[name*='partyQauntity'] option:selected").attr('data-price');
    	    $("#itemPrice" + itemId).html(itemPrice);
    	    var itemQuantity = $("select[name*='partyQauntity'] option:selected").val();
    	    $("#quantity" + itemId).val(itemQuantity);
    	    if (itemQuantity > 0) {
    		itemAdded = true;
    		var subtotal = itemPrice;
    		var itemName = $(this).attr("data-itemName");
    		var itemOrderSummaryDiv = '<div class="customrow" id="' + itemOrderId + '">'
    			+ '<div class="col-md-4"><span>' + itemName + '</span></div>'
    			+ '<div class="col-md-4" style="text-align: center;"><span class="quantity">' + itemQuantity
    			+ '</span></div>' + '<div class="col-md-4"><span class="subtotal">' + subtotal
    			+ '</span></div></div>';
    		$("#partyQuantitySummary").empty();
    		$("#itemQuantitySummaryNotAdded").removeClass("show").addClass("hide");
    		$("#partyQuantitySummary").append(itemOrderSummaryDiv);
    	    } else {
    	    }
    	    $('[data-th="Quantity"]').each(function(i, quantity) {
    		var itemId = $(this).attr("data-itemId");
    		var quantityId = "quantity" + itemId;
    		var itemQuantity = $("#" + quantityId).val(0);
    		if (itemQuantity > 0) {
    		    individualItemAvailable = true;
    		}
    	    });
    	    $('[data-th="QuantityStuffing"]').each(function(i, quantity) {
    		var stuffingId = $(this).attr("data-stuffingId");
    		var stuffingQuantityId = "stuffingquantity" + stuffingId;
    		var stuffingQuantity = $("#" + stuffingQuantityId).val(0);
    	    });
    	    if (individualItemAvailable) {
    		alert("Your existing items of panipuri and combo packs will be removed from summary.");
    	    }
    	    createItemSummary();
    	    createStuffingSummary();
    	    calculateTotal();
    	});
    function calculateAvailableQuantity(thisval) {
	
	
	$("#individualOrder").find(".quantity-counter-input").each(function() {
	    var itemIdSelected =  $(this).attr("data-itemId");
	    var itemMaxQuantity=$(this).attr("data-maxvalue")*1;
	    var itemQuantityConsumedByCombo = 0;
	    $("#comboOrder").find(".quantity-counter-input").each(function() {
		var requiredAttr = $(this).attr("data-itemids");
		 var comboattrArray = requiredAttr.split(",");
		 var itemQuantArray = $(this).attr("data-itemquantity").split(",");
		var comboQuantitySelected =  $(this).text()*1;
		 $.each(comboattrArray, function(i, itemid) {
			individualItemQuantity = itemQuantArray[i]*1;
			if(itemid === itemIdSelected) {
			    if(individualItemQuantity >0) {
				itemQuantityConsumedByCombo = itemQuantityConsumedByCombo + (comboQuantitySelected * individualItemQuantity);
			    }			  
			}
		 });
		
	    });
	    var itemAvailableQuantity = itemMaxQuantity - itemQuantityConsumedByCombo;
	    $(this).attr("data-availablevalue", itemAvailableQuantity);
	});
	 $("#comboOrder").find(".quantity-counter-input").each(function() {
	     	var comboQuantityBasedOnItem = 0,
		requiredAttr = $(this).attr("data-itemids"),
		comboattrArray = requiredAttr.split(","),
		itemQuantArray = $(this).attr("data-itemquantity").split(","),
		comboAvailableQuantity = $(this).attr("data-availablevalue")*1
		 $.each(comboattrArray, function(i, itemid) {
			individualItemQuantity = itemQuantArray[i]*1;
			 id= "item~"+itemid;
		    	 var itemAvailableQuantity= $("span[id='"+id+"'").attr("data-availablevalue")*1,
		    	 itemQuantityAdded = $("span[id='"+id+"'").text()*1;
		    	 itemAvailableQuantity = itemAvailableQuantity - itemQuantityAdded;		    	
		    	 if(itemAvailableQuantity >0 && individualItemQuantity >0) {
				comboQuantityBasedOnItem = itemAvailableQuantity/individualItemQuantity;
			    } else if(itemAvailableQuantity ===0 && individualItemQuantity >0) {
				comboQuantityBasedOnItem = 0;
			    } else if(individualItemQuantity ===0) {
				comboQuantityBasedOnItem = comboAvailableQuantity;
			    }
		    	 comboQuantityBasedOnItem = parseInt(comboQuantityBasedOnItem);
		    	 if(i==0 || comboQuantityBasedOnItem< comboAvailableQuantity) {
		    	     comboAvailableQuantity = comboQuantityBasedOnItem;
		    	 }
		 });
	     	
	     	$(this).attr("data-availablevalue",comboAvailableQuantity);
	    });
    }
    function createItemSummary() {
        $("#itemQuantitySummary").empty();
        var itemAdded = false;
        $('[data-th="Quantity"]')
    	    .each(
    		    function() {
    			var itemId = $(this).attr("data-itemId");
    			var itemOrderId = "itemOrderSummary" + itemId;
    			var itemQuantity = $(this).find(".quantity-counter-input").text();
    			var itemPrice = $("#itemPrice" + itemId).html();
    			$(this).find("input").val(itemQuantity);
    			if (itemQuantity > 0) {
    			    itemAdded = true;
    			    var subtotal = itemQuantity * itemPrice;
    			    var itemName = $(this).attr("data-itemName");
    			    var itemOrderSummaryDiv = '<div class="customrow" id="'
    				    + itemOrderId
    				    + '">'
    				    + '<div class="col-md-6 col-xs-6 col-sm-6"><span>'
    				    + itemName
    				    + '</span></div>'
    				    + '<div class="col-md-2 col-xs-2 col-sm-2" style="text-align: center;"><span class="quantity">'
    				    + itemQuantity + '</span></div>'
    				    + '<div class="col-md-4 col-xs-4 col-sm-4"><span class="subtotal">'
    				    + subtotal.toFixed(2) + '</span></div></div>';
    			    $("#itemQuantitySummary").append(itemOrderSummaryDiv);
    			} else {
    			}
    		    });
        if (itemAdded) {
    	$("#itemQuantitySummaryNotAdded").removeClass("show").addClass("hide");
        }
    }
    function createStuffingSummary() {
        $("#stuffiingQuantitySummary").empty();
        $('[data-th="QuantityStuffing"]')
    	    .each(
    		    function() {
    			var stuffingId = $(this).attr("data-stuffingId");
    			var stuffingOrderId = "stuffingOrderSummary" + stuffingId;
    			var subtotalId = "subTotalStuffing" + stuffingId;
    			var stuffingPrice = $("#stuffingPrice" + stuffingId).html();
    			var stuffingQuantity = $(this).find(".quantity-counter-input").text()
    			$(this).find("input").val(stuffingQuantity);
    			if (stuffingQuantity > 0) {
    			    var subtotal = stuffingQuantity * stuffingPrice;
    			    var stuffingName = $(this).attr("data-itemName");
    			    var stuffingOrderSummaryDiv = '<div class="customrow" id="'
    				    + stuffingOrderId
    				    + '">'
    				    + '<div class="col-md-6 col-xs-6 col-sm-6"><span>'
    				    + stuffingName
    				    + '</span></div>'
    				    + '<div class="col-md-2 col-xs-2 col-sm-2" style="text-align: center;"><span class="quantity">'
    				    + stuffingQuantity + '</span></div>'
    				    + '<div class="col-md-4 col-xs-4 col-sm-4"><span class="subtotal">'
    				    + subtotal.toFixed(2) + '</span></div></div>';
    			    $("#stuffiingQuantitySummary").append(stuffingOrderSummaryDiv);
    			} else {
    			}
    		    });
    }
    $('[data-th="Quantity"]').on('change', function(e) {
        resetPartyOrder();
        createItemSummary();
        calculateTotal();
        calculateAvailableQuantity(this);
    });
    $('[data-th="QuantityStuffing"]').on('change', function(e) {
        resetPartyOrder();
        createStuffingSummary();
        calculateTotal();
    });
    function calculateTotal() {
        var totalPrice = 0.00;
        $('.subtotal').each(function() {
    	if ($(this).html() && !isNaN($(this).html())) {
    	    totalPrice += parseFloat($(this).html());
    	}
        });
        $("#totalAmount1").html(totalPrice.toFixed(2));
        $("#subTotal").html(totalPrice.toFixed(2));
        $("#totalAmount").html(totalPrice.toFixed(2));
    }
    $(".continueToDelivery").on("click", function(e) {
    	hideMaxErrorMessage();
        showOverlay();
        var noIfItemsSelected = 0;
        $('[data-th="Quantity"]').each(function() {
    	var itemId = $(this).attr("data-itemId");
    	var quantityId = "quantity" + itemId;
    	var itemQuantity = $(this).find(".quantity-counter-input").text();
    	if (itemQuantity > 0) {
    	    noIfItemsSelected = noIfItemsSelected + 1;
    	}
        });
        $("select[name*='partyQauntity']").each(function() {
    	var itemId = $(this).attr("data-itemId");
    	var quantityId = "quantity" + itemId;
    	if ($("#" + quantityId).val() !== '') {
    	    noIfItemsSelected = noIfItemsSelected + 1;
    	}
        });
        if (noIfItemsSelected > 0) {
    	var deliveryAddressId = $("#deliveryAddressId").val();
    	ajax.postForm("deliveryDetails?F=J", $("#homeForm")).done(function(data) {
    	    $("#orderId").val(data.orderId);
    	    if (deliveryAddressId && deliveryAddressId !== "") {
    		goToNextStep("address");
    		hideOverlay();
    		getDeliverySlots();
    	    } else {
    		ajax.loadFragment("html/deliverydetails.html").done(function(out) {
    		    $("#deliveryDetailsDiv").empty();
    		    $("#deliveryDetailsDiv").append(out);
    		    $("input[id=deliveryOrderId]").val(data.orderId);
    		    bindDeliveryEvents();
    		    $('#phoneNumberDisplay').val(data.contactNumber);
    		    $('#phoneNumber').val(data.contactNumber);
    		    $("#deliveryAreaID").attr("value",$("#selectedAreaId").val());	
    		    $("#areaAddrDisplay").attr("placeholder",$("#selectedArea").val());
    			$("#subAreaAddrDisplay").attr("placeholder",$("#selectedSubArea").val());
    			 $("#areaAddr").val($("#selectedArea").val());
     			$("#subAreaAddr").val($("#selectedSubArea").val());
    		    getAddressDetails();
    		    getDeliverySlots();
    		    hideErrorMessage();
    		    goToNextStep("address");
    		    hideOverlay();
    		}).fail(function(data) {
    		    alert("failed");
    		    hideOverlay();
    		});
    	    }
    	}).fail(function(data) {
    	    alert("failed");
    	    hideOverlay();
    	});
        } else {
    	showErrorMessage('Please add atleast one Box of Pani puri to continue');
    	hideOverlay();
        }
    });
        
    $(".editOrder").on("click", function(e) {
        goToPreviousStep("order");
    });
    $(".backToAddress").on("click", function(e) {
        goToPreviousStep("address");
    });
    $(".continueToVerify").on("click", function(e) {
        $('.has-error').removeClass('has-error');
        hideErrorMessage();
        if (validateDeliveryForm(false)) {
    	showOverlay();
    	ajax.postForm("verifyDetails?F=J", $("#deliveryForm")).done(function(data) {
    	    ajax.loadFragment("html/orderverification.html").done(function(out) {
    		if (data.errormessage && errormessage !== "") {
    		    showErrorMessage(errormessage);
    		    hideOverlay();
    		} else {
    		    hideErrorMessage();
    		    $("#verifyDetailsDiv").empty();
    		    $("#verifyDetailsDiv").append(out);
    		    $("#orderIdPayment").val(data.orderId);
    		    $("#contactNumber").val(data.contactNo);
    		    bindVerifyEvents(data);
    		    goToNextStep("verify");
    		    hideOverlay();
    		}
    	    }).fail(function(data) {
    		showErrorMessage("Something went wrong. Please try again later.");
    		hideOverlay();
    	    });
    	}).fail(function(data) {
    	    showErrorMessage("Something went wrong. Please try again later.");
    	    hideOverlay();
    	});
        }
    });
    $(".confirmOrderButton").on("click", function(e) {
    	showOverlay();
    	ajax.postForm("validateOTP?F=J", $("#oneTimePasswordForm")).done(function(data) {
    	    if (data.errormessage && data.errormessage !== "") {
    		showErrorMessage(data.errormessage);
    		hideOverlay();
    	    } else {
    		hideErrorMessage();
    		ajax.loadFragment("html/orderconfirmation.html").done(function(out) {
    		    $("#confirmDetailsDiv").empty();
    		    $("#confirmDetailsDiv").append(out);
    		    var deliveryDetailsInfo = createDeliveryDetailsInfo(data);
    		    $("#confirmDeliveryDetails").append(deliveryDetailsInfo);
    		    // $("#orderIdConfirmPayment").val(data.orderId);
    		    goToNextStep("confirm");
    		    $(".placeNewOrder").on("click", function() {
    			location.reload(true);
    		    });
    		    hideOverlay();
    		}).fail(function(data) {
    		    showErrorMessage("Something went wrong. Please try again later.");
    		    hideOverlay();
    		});
    	    }
    	}).fail(function(data) {
    	    showErrorMessage("Something went wrong. Please try again later.");
    	    hideOverlay();
    	});
        });
    function bindVerifyEvents(data) {
        $("#cashoption, #otheroption").change(function() {
    	if ($("#cashoption").is(":checked")) {
    	    $("#cashOnDeliveryDiv").show();
    	    $("#otherPaymentOptionDiv").hide();
    	}
    	if ($("#otheroption").is(":checked")) {
    	    $("#cashOnDeliveryDiv").hide();
    	    $("#otherPaymentOptionDiv").show();
    	}
        });
        $("#getCodeButton").on("click", function(e) {
    	ajax.postForm("sendOTP?F=J", $("#oneTimePasswordForm")).done(function(data) {
    	    if (data.success === true) {
    		$("#submitCodeDiv").removeClass("hide");
    		$("#getCodeDiv").hide();
    		$("#otpConfirmationMessage").empty();
    		$("#otpConfirmationMessage").html(data.message);
    		$("#otpCode").val(data.otp)
    	    } else {
    	    }
    	}).fail(function(data) {
    	    alert("failed");
    	});
        });
        $("#resendCodeButton").on("click", function(e) {
    	ajax.postForm("sendOTP?F=J", $("#oneTimePasswordForm")).done(function(data) {
    	    if (data.success === true) {
    	    } else {
    	    }
    	}).fail(function(data) {
    	    alert("failed");
    	});
        });
       
    }
});
function nextTab(elem) {
    $(elem).next().find('a[data-toggle="tab"]').click();
}
function prevTab(elem) {
    $(elem).prev().find('a[data-toggle="tab"]').click();
}
});