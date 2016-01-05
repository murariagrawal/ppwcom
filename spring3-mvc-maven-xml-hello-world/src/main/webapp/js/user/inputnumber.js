$(document).ready(function() {// plugin bootstrap minus and plus
// http://jsfiddle.net/laelitenetwork/puJ6G/
	$(".quantity-counter").on("click", function() {

		var $button = $(this);
		
	
		var availableValue = $(this).parent().find(".quantity-counter-input").attr('data-availablevalue');
		var maxValue = $(this).parent().find(".quantity-counter-input").attr('data-maxvalue');
		hideMaxErrorMessage();
		var oldValue = $(this).parent().find(".quantity-counter-input").text();
		oldValue = oldValue*1;
		availableValue = availableValue*1;
		if ($(this).children().hasClass("glyphicon-plus")) {
			if(oldValue < availableValue) {
				var newVal = parseFloat(oldValue) + 1;
			} else {
				if(maxValue !==0) {
					var fieldName = $(this).attr('data-itemName');
					var errorMessage =  "You have addded maximum quantity of " + fieldName + " currently available in your area."
					showMaxErrorMessage(errorMessage);
				}
			}
		} else {
			
			// Don't allow decrementing below zero
			if (oldValue > 0) {
				var newVal = parseFloat(oldValue) - 1;
			} else {
				newVal = 0;
			}
		}
		var availableVal = availableValue - newVal;
		
		$(this).parent().find(".quantity-counter-input").text(newVal)
		$($(this).parent()).trigger( "change" );

	});
	$(".quantity-counter-combo").on("click", function() {

		var $button = $(this);
		var fieldName = $(this).attr('data-field');
		var availableValue = $(this).parent().find(".quantity-counter-input").attr('data-availablevalue');
		var maxValue = $(this).parent().find(".quantity-counter-input").attr('data-maxvalue');
		hideMaxErrorMessage();
		var oldValue = $(this).parent().find(".quantity-counter-input").text();
		oldValue = oldValue*1;
		availableValue = availableValue*1;
		if ($(this).children().hasClass("glyphicon-plus")) {
			if(availableValue !== 0) {
				var newVal = parseFloat(oldValue) + 1;
			} else {
				if(maxValue !==0) {
					var fieldName = $(this).attr('data-itemName');
					var errorMessage =  "You have addded maximum quantity of " + fieldName + " currently available in your area."
					showMaxErrorMessage(errorMessage);
				}
			}
		} else {
			// Don't allow decrementing below zero
			if (oldValue > 0) {
				var newVal = parseFloat(oldValue) - 1;
			} else {
				newVal = 0;
			}
		}
		var availableVal = availableValue - newVal;
		
		$(this).parent().find(".quantity-counter-input").text(newVal)
		$($(this).parent()).trigger( "change" );

	});
	
});
function hideMaxErrorMessage() {
	$("#errorQuantityDiv").empty();
	$("#errorQuantityDiv").addClass("hide");
    }
    function showMaxErrorMessage(errorMessage) {
	$("#errorQuantityDiv").empty();
	$("#errorQuantityDiv").removeClass("hide");
	$("#errorQuantityDiv").append("<Strong>" + errorMessage + "");
    }