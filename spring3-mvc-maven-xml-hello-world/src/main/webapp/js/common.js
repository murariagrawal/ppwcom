$(document).ready(function () {

	function hideOverlay() {
		$('#spinnerModal').modal('hide');
		// $('container').removeClass("overlay");
	}
	function showOverlay() {
		$('#spinnerModal').modal();
		// $('container').addClass("overlay");
	}
	function hideErrorMessage() {
		   $("#errorDiv").empty();
			  $("#errorDiv").addClass("hide");
	}
	function showErrorMessage(errorMessage) {
		   	$("#errorDiv").empty();
			  $("#errorDiv").removeClass("hide");
			  $("#errorDiv").append("<Strong>"+errorMessage+"");
	}
	function createIndividualToppingRow(topping) {
		var toppingName= topping.toppingName;
		var toppingQuantity= topping.quantity;
		var toppingPrice= topping.price;
		
		var toppingId= topping.toppingId;
		var toppingNameDiv = '<div class="row"><div class="col-sm-10"><h4 class="nomargin"><label>'+toppingName+'</label></h4></div></div>';
				
		var toppingTotalPrice = toppingPrice*toppingQuantity;
		var individualToppingRow='<tr> <td>'+toppingNameDiv+'</td><td>'+
								toppingPrice+'</td><td>'+
								toppingQuantity+'</td><td class="text-center subtotal" >'+
								toppingTotalPrice+'</td></tr>';
		return individualToppingRow;
		
	}
	function createDeliveryDetailsInfo(data) {
		
		var addressInfo = "<div class='row'><div class='col-lg-12'>Delivery Address :</div></div><div class='row'><div class='col-lg-12'><label>"+
							data.addressLine+"</label></div></div><div class='row'><div class='col-lg-12'>"+
							data.landmarkReturn+"<div class='row'><div class='col-lg-12'>"+
							data.zipcodeReturn+"</div></div></div></div>";    	
		var slotInfo = "<div class='row'><div class='col-lg-12'>Delivery Slot Selected :<br><label>"+data.deliverySlot+"</label></div></div>";
		var deliveryDetailsInfo = addressInfo+slotInfo;
		return deliveryDetailsInfo;
	}
});
