$(document).ready(function() {// plugin bootstrap minus and plus
// http://jsfiddle.net/laelitenetwork/puJ6G/
	$(".quantity-counter").on("click", function() {

		var $button = $(this);
		var fieldName = $(this).attr('data-field');
		var maxValue = $(this).parent().find(".quantity-counter-input").attr('data-availablevalue');
		
		var oldValue = $(this).parent().find(".quantity-counter-input").text();
		oldValue = oldValue*1;
		maxValue = maxValue*1;
		if ($(this).children().hasClass("glyphicon-plus")) {
			if(oldValue < maxValue) {
				var newVal = parseFloat(oldValue) + 1;
			}
		} else {
			// Don't allow decrementing below zero
			if (oldValue > 0) {
				var newVal = parseFloat(oldValue) - 1;
			} else {
				newVal = 0;
			}
		}
		var availableVal = maxValue - newVal;
		
		$(this).parent().find(".quantity-counter-input").text(newVal)
		$($(this).parent()).trigger( "change" );

	});
	
});
