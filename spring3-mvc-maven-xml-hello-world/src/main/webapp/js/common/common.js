function hideOverlay() {
    $('#spinnerModal').modal('hide');
    // $('container').removeClass("overlay");
}
function showOverlay() {
    $('#spinnerModal').modal();
    // $('container').addClass("overlay");
}
function hideAreaErrorMessage() {
	$("#areaErrorDiv").empty();
	$("#areaErrorDiv").addClass("hide");
}
function showAreaErrorMessage(errorMessage) {
	$("#areaErrorDiv").empty();
	$("#areaErrorDiv").removeClass("hide");
	$("#areaErrorDiv").append("<Strong>" + errorMessage + "");
}
function hideErrorMessage() {
    $("#errorDiv").empty();
    $("#errorDiv").addClass("hide");
}
function showErrorMessage(errorMessage) {
    $("#errorDiv").empty();
    $("#errorDiv").removeClass("hide");
    $("#errorDiv").append("<Strong>" + errorMessage + "");
}
function goToNextStep(nextStep) {
    hideErrorMessage();
    $('.has-error').removeClass('has-error');
    $("#pageContentCarousel").carousel('next');
    showHideButtons(nextStep);
};
function goToPreviousStep(previousStep) {
    hideErrorMessage();
    $('.has-error').removeClass('has-error');
    $("#pageContentCarousel").carousel('prev');
    showHideButtons(previousStep);
};
function showHideButtons(nextStep) {
    if (nextStep === "order") {
	$(".continueToDeliverySummary").removeClass("hide").addClass("show");
	$(".continueToVerifySummary").removeClass("show").addClass("hide");
	$(".continueToConfirmSummary").removeClass("show").addClass("hide");
	$(".placeNewOrderSummary").removeClass("show").addClass("hide");
	$("#couponCode").removeClass("show").addClass("hide");
    } else if (nextStep === "address") {
	$(".continueToDeliverySummary").removeClass("show").addClass("hide");
	$(".continueToVerifySummary").removeClass("hide").addClass("show");
	$(".continueToConfirmSummary").removeClass("show").addClass("hide");
	$(".placeNewOrderSummary").removeClass("show").addClass("hide");
	$("#couponCode").removeClass("show").addClass("hide");
    } else if (nextStep === "verify") {
	$(".continueToDeliverySummary").removeClass("show").addClass("hide");
	$(".continueToVerifySummary").removeClass("show").addClass("hide");
	$(".continueToConfirmSummary").removeClass("hide").addClass("show");
	$(".placeNewOrderSummary").removeClass("show").addClass("hide");
	$("#couponCode").removeClass("hide").addClass("show");
    } else if (nextStep === "confirm") {
	$(".continueToDeliverySummary").removeClass("show").addClass("hide");
	$(".continueToVerifySummary").removeClass("show").addClass("hide");
	$(".continueToConfirmSummary").removeClass("show").addClass("hide");
	$(".placeNewOrderSummary").removeClass("hide").addClass("show");
	$("#couponCode").removeClass("show").addClass("hide");
    }
}
