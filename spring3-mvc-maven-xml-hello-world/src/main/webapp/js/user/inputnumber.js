$(document).ready(function() {// plugin bootstrap minus and plus
// http://jsfiddle.net/laelitenetwork/puJ6G/
	$(".quantity-counter").on("click", function() {

		var $button = $(this);
		var fieldName = $(this).attr('data-field');
		var oldValue = $(this).parent().find(".quantity-counter-input").text();

		if ($(this).children().hasClass("glyphicon-plus")) {
			var newVal = parseFloat(oldValue) + 1;
		} else {
			// Don't allow decrementing below zero
			if (oldValue > 0) {
				var newVal = parseFloat(oldValue) - 1;
			} else {
				newVal = 0;
			}
		}
		$(this).parent().find(".quantity-counter-input").text(newVal)
		$($(this).parent()).trigger( "change" );

	});
	
});
