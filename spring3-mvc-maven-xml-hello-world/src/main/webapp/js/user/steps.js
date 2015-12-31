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
	    $("#areaModal").modal('hide');
	    showOverlay();
	    updateItemStocks();
	    hideOverlay();
	});
    }
    function updateItemStocks() {
	ajax.postForm("updateItemStock?F=J", $("#areaForm")).done(function(data) {
	    if(data.availableStock) {
		 $.each(data.availableStock, function(i, stock) {
		     if(stock.stuffing) {
		    	 id= "stuffing~"+stock.id;
		    	 $("span[id='"+id+"'").attr("data-maxvalue",stock.quantity);
		     } else {
		    	 id= "item~"+stock.id;
		    	 $("span[id='"+id+"'").attr("data-maxvalue",stock.quantity);
		     }
		     
		 });
		
	    }
    	    $("#orderId").val(data.orderId);
    	    hideOverlay(); 
    	}).fail(function(data) {
    	    alert("failed");
    	    hideOverlay();
    	});
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