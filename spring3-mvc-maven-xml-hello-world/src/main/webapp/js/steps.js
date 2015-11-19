$(document).ready(function () {
    //Initialize tooltips
    $('.nav-tabs > li a[title]').tooltip();
    
    //Wizard
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {

        var $target = $(e.target);
    
        if ($target.parent().hasClass('disabled')) {
            return false;
        }
    });

   function goToNextStep() {

        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);

    };
    $('[data-th="Quantity"]').on('change',function (e) {
    	
    	var itemId = $(this).attr("data-itemId");
    	var subtotalId = "subTotalItem"+ itemId;
    	var itemPriceId = "itemPrice" +itemId;
    	var quantityId = "quantity" +itemId;    	
    	var itemPrice = $("#"+itemPriceId).html();    	
    	var itemQuantity = $("#"+quantityId).val();
    	var subtotal = itemQuantity *itemPrice;
    	
       $("#"+subtotalId).html(subtotal.toFixed(2));
       calculateTotal();

    });
	$('[data-th="QuantityStuffing"]').on('change',function (e) {
	    	
	    	var stuffingId = $(this).attr("data-stuffingId");
	    	var subtotalId = "subTotalStuffing"+ stuffingId;
	    	var itemPriceId = "stuffingPrice" +stuffingId;
	    	var quantityId = "stuffingquantity" +stuffingId;    	
	    	var itemPrice = $("#"+itemPriceId).html();    	
	    	var itemQuantity = $("#"+quantityId).val();
	    	var subtotal = itemQuantity *itemPrice;
	    	
	       $("#"+subtotalId).html(subtotal.toFixed(2));
	       calculateTotal();
	
	    });
    function calculateTotal() {
    	var totalPrice = 0.00;
    	 $('[data-th="Subtotal"]').each(function() {
             if ($(this).html() && !isNaN($(this).html())) {
            	 totalPrice += parseFloat($(this).html());
             }
         });    	 
    	 $("#totalAmount1").html(totalPrice.toFixed(2));
    	 $("#totalAmount2").html(totalPrice.toFixed(2));
    }
    
    $("#continueToDelivery").on("click", function(e) {
    	var noIfItemsSelected = 0;
    	 $('[data-th="Quantity"]').each(function() {
    		 var itemId = $(this).attr("data-itemId");
    		 var quantityId = "quantity" +itemId;  
    		 var itemQuantity = $("#"+quantityId).val();
    		 if(itemQuantity>0) {
    			 noIfItemsSelected = noIfItemsSelected+1;
    		 }
    	 });
    	 if(noIfItemsSelected>0) {
    		 ajax.postForm("deliveryDetails?F=J", $("#homeForm")).done(function(data) {			
    				ajax.loadFragment("html/deliverydetails.html").done(function(out) {				
    					$("#deliveryDetailsDiv").empty();
    					$("#deliveryDetailsDiv").append(out);
    					$("input[id=deliveryOrderId]").val(data.orderId);
    					bindDeliveryEvents();
    					hideErrorMessage();
    					goToNextStep();
    					disableAllOtherWizard();
    				}).fail(function(data) {	        	
    		        	alert("failed");
    		        });						
    	        }).fail(function(data) {        	
    	        	alert("failed");
    	        });  		 
    		 
    	 } else {
    		 showErrorMessage('Please add atleast one Box of Pani puri to continue');    		
    	 }
		
    
    });
    function createIndividualItemRow(item) {
    	var itemName= item.itemName;
		var itemQuantity= item.itemQuantity;
		var itemPrice= item.itemPrice;
		
		var itemId= item.itemId;
		var itemNameDiv = '<div class="row"><div class="col-sm-10"><h4 class="nomargin"><label>'+itemName+'</label></h4>'+
							'<p><a class="accordion-toggle" data-toggle="collapse"	href="#itemDetails'+itemId+'">'+
							'<span	class="label label-info">View Details</span></a></p></div></div>';
		var itemDetailsLabel = "";
		$.each(item.itemDetails, function(j, itemdetail ) {
			itemDetailsLabel = itemDetailsLabel+"<label>"+itemdetail+"</label><br>";
		});
		var itemDetailsDiv='<div id="itemDetails'+itemId+'"	class="accordion-body collapse"><div class="accordion-inner">'+
							'<table class="table table-hover table-condensed">'+	
							'<tr><td>'+itemDetailsLabel+
							'</td></tr></table></div></div>';
		var itemTotalPrice = itemPrice*itemQuantity;
		var individualItemRow='<tr> <td>'+itemNameDiv+itemDetailsDiv+'</td><td>'+
								itemPrice+'</td><td>'+
								itemQuantity+'</td><td class="text-center">'+
								itemTotalPrice+'</td></tr>';
		return individualItemRow;
		
    }
   function createDeliveryDetailsInfo(data) {
    	
    	var addressInfo = "<div class='row'>Delivery Address :</div><div class='row'><label>"+
							data.addressLine+"</label></div><div class='row'>"+
							data.landmarkReturn+" "+
							data.zipcodeReturn+"</div>";    	
    	var slotInfo = "<div class='row'>Delivery Slot Selected :<br><label>"+data.deliverySlot+"</label></div>";
    	var deliveryDetailsInfo = addressInfo+slotInfo;
    	return deliveryDetailsInfo;
    }
   function validateDeliveryForm(){
	  var phoneNumberVal =  $("#phoneNumber").val(),
	  firstNameVal1 = $("#firstNameAddr").val(),
	  lastNameVal1 = $("#lastNameAddr").val(),
	  addressVal1 = $("#addr1").val(),
	  addressVal2 = $("#addr2").val(),
	  landMarkVal = $("#landmarkAddr").val(),
	  zipcodeVal = $("#zipcodeAddr").val(),
	  deliverySlotVal = $("#selectDeliverySlot").val(),
	  noOfFieldsInError = 0,
	  errorMessage = "";
	  
	  if(phoneNumberVal === null || phoneNumberVal == "" || phoneNumberVal.length <10) {
		  $("#phoneNumber").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "Phone Number is a required field.";
	  }
	  if(firstNameVal1 === null || firstNameVal1 == "") {
		  $("#firstNameAddr").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "First Name is a required field.";
	  }
	  if(lastNameVal1 === null || lastNameVal1 == "") {
		  $("#lastNameAddr").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "Last Name is a required field.";
	  }
	  if(addressVal1 === null || addressVal1 == "") {
		  $("#addr1").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "Address Line 1 is a required field.";
	  }
	  if(zipcodeVal === null || zipcodeVal == "") {
		  $("#zipcodeAddr").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "Zipcode is a required field.";
	  } else if(zipcodeVal.length <6) {
		  $("#zipcodeAddr").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "Please enter a valid zipcode of 6 digits.";
	  }
	  if(deliverySlotVal === null || deliverySlotVal == "") {
		  $("#selectDeliverySlot").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "Please select a delivery slot to continue.";
	  }
	  if(noOfFieldsInError >1) {
		  errorMessage = "There are two or more firelds in error";
	  }
	  if(errorMessage !== "") {
		  showErrorMessage(errorMessage);
	  } else {
		  hideErrorMessage();
	  }
	  return false;
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
    $("#continueToVerify").on("click", function(e) {	
    	$('.error-field').removeClass('error-field');
    	hideErrorMessage();
    	if(validateDeliveryForm()) {
			ajax.postForm("verifyDetails?F=J", $("#deliveryForm")).done(function(data) {			
				ajax.loadFragment("html/orderverification.html").done(function(out) {
					hideErrorMessage();
					$("#verifyDetailsDiv").empty();
					$("#verifyDetailsDiv").append(out);
					$.each(data.itemList, function(i, item) {
			    		var individualItemRow =  createIndividualItemRow(item);
			    		$("#verifyOrderDetailsTable > tbody").append(individualItemRow);
			    	});
			    	var deliveryDetailsInfo = createDeliveryDetailsInfo(data);    	
			    	$("#verifyDeliveryDetails").append(deliveryDetailsInfo);    	
			    	$("#orderIdPayment").val(data.orderId);
			    	$("#contactNumber").html(data.contactNo);
					bindVerifyEvents(data);
					goToNextStep();
					disableAllOtherWizard();
				}).fail(function(data) {	        	
		        	alert("failed");
		        });						
	        }).fail(function(data) {        	
	        	alert("failed");
	        });
    	}
    });
    function bindDeliveryEvents() {
	    $('#phoneNumber').on("keyup",function() {
	    	var length = $(this).val().length;
	    	if(length && length == 10) {
	    		getAddressDetails();
	    	}
	    });
	    $("#zipcodeAddr").on('input', function(){			
			var length = $(this).val().length;			
	    	if(length && length == 6) {	    		
	    		getDeliverySlots();
	    	}
		});
	    $("#zipcodeAddr").on('blur', function(){			
			var length = $(this).val().length;			
	    	if(length && length < 6) {
	    		 $("#zipcodeAddr").parent().addClass('error-field');
	    		 errorMessage = "Please enter a valid zipcode of 6 digits.";
	    		 showErrorMessage(errorMessage);
	    	}
		});
	    
    }
    function getAddressDetails() {		
		ajax.postForm("fetchDeliveryDetails?F=J", $("#deliveryForm")).done(function(data) {		
			var ulFinal = "<ul style='list-style:none'>";
			var liFinal = "";
			var addressList= data.addressList;
			if(addressList) {
				$.each(addressList, function(i, d) {			
					var divIndividual = "<div class='well well-sm' data-th='individualAddress'>";
					var ulIndividual = "<ul style='list-style:none'>";
					var liIndividual="";
					var hiddenAddressId = "<input type='hidden' id='addressId' value='"+d.addressId+"' />"
					if(d.firstName || d.lastName) {
						var firstName= "";
						var lastName= "";
						if(d.firstName && d.firstName !== null){
							firstName =d.firstName;
						}
						if(d.lastName && d.lastName !== null){
							lastName =d.lastName;
						}
						if(firstName != "" || lastName !== ""){
							liIndividual = "<li id='addressName'>"
							if(firstName != "") {
								liIndividual = liIndividual+"<span id='firstName'>"+firstName+"</span>";
								if(lastName !== "") {
									liIndividual = liIndividual+" ";
								}
							}
							if(lastName !== "") {
								liIndividual = liIndividual+"<span id='lastName'>"+lastName+"</span>";
							}
							liIndividual = liIndividual+"</li>";
							ulIndividual = ulIndividual+liIndividual;
						}
						
					}
					if(d.addressLine1) {
						liIndividual = "<li id='addressLine1'>"+d.addressLine1+"</li>";
						ulIndividual = ulIndividual+liIndividual;
					}
					if(d.addressline2) {
						liIndividual = "<li id='addressLine2'>" +d.addressline2+"</li>";
						ulIndividual = ulIndividual+liIndividual;
					}	
					if(d.landmark) {
						liIndividual = "<li id='landmark'>" +d.landmark+"</li>";
						ulIndividual = ulIndividual+liIndividual;
					}	
					liIndividual = "<li>";					
					if(d.zipcode) {						
						liIndividual = liIndividual+"<span id='zipcode'>"+d.zipcode+"</span>";
					}						
					liIndividual = liIndividual+ "</li>";		
					ulIndividual = ulIndividual+liIndividual;					
					ulIndividual = ulIndividual+"</ul></a>";
					if(d.addressLine1 || d.addressline1) {
					 	divIndividual = divIndividual+ulIndividual+hiddenAddressId+"</div>";
						liFinal = "<li>"+divIndividual+"</li>";
						ulFinal = ulFinal+liFinal;
					}
				});
			}
			ulFinal = ulFinal+"</ul>";
			if(addressList && addressList !== null && addressList.length>0) {
				$('#myModal').modal();
				var addressDiv = $("#addressDiv");	
				addressDiv.empty();
				addressDiv.append(ulFinal);
				bindAddressModelEvents();
			} else {
				clearDeliveryForm();
				
			}			
        }).fail(function(data) {
        	
        	alert("failed");
        });		
	}
	function clearDeliveryForm() {
		$("#addressFields input[type=text]").val("");
		$('.error-field').removeClass('error-field');
		$('#selectDeliverySlot')
	    .find('option')
	    .remove()
	    .end()
	    .append('<option value="">Select Delivery Slot</option>')
	    .val('');
		$('#addressFields').removeClass("hide").addClass("show");
		hideErrorMessage();
	}
    function bindAddressModelEvents() {
    	$( ".well-sm" ).on('click', function(e) {
    		clearDeliveryForm();
    		$("#firstNameAddr").val($(this).find("#firstName").html());
			$("#lastNameAddr").val($(this).find("#lastName").html());
			$("#addr1").val($(this).find("#addressLine1").html());
			$("#addr2").val($(this).find("#addressLine2").html());
			$("#landmarkAddr").val($(this).find("#landmark").html());
			$("#zipcodeAddr").val($(this).find("#zipcode").html());
			$("#deliveryAddressId").val($(this).find("#addressId").val());			
			$('#myModal').modal('hide');
			$('#addressFields').removeClass("hide").addClass("show");
			getDeliverySlots();
		});
    	$("#enterNewAddress").on('click', function(e) {    		
    		clearDeliveryForm();
    	});
		
    }
    
    
    function getDeliverySlots() {	    	
		ajax.postForm("fetchDeliverySlots?F=J", $("#deliveryForm")).done(function(data) {		
			$("#selectDeliverySlot").prop( "disabled", false );
			if(data.deliverySlots) {
				$.each(data.deliverySlots, function (i, deliverySlot) {
					if(deliverySlot.slotQuantity >0) {
						var slotText ="Slot Time :"+deliverySlot.startTime+"-"+deliverySlot.endTime+"    Available Slots :"+deliverySlot.slotQuantity;
					    var slotValue = deliverySlot.deliverySlotId;
					    var option = new Option(slotText, slotValue);
						/// jquerify the DOM object 'o' so we can use the html method
						$(option).html(slotText);
						$("#selectDeliverySlot").append(option);
					}
					
				});
			}
		}).fail(function(data) {
        	
        	alert("failed");
        });
    }
    
   
    function bindVerifyEvents(data) {    	
    
    	$("#paymentOption, #otheroption").change(function () {
	    	if($("#paymentOption").is(":checked")) {
	    		$("#cashOnDeliveryDiv").show();
	    		$("#otherPaymentOptionDiv").hide();
	    	}
	    	if($("#otheroption").is(":checked")) {
	    		$("#cashOnDeliveryDiv").hide();
	    		$("#otherPaymentOptionDiv").show();
	    	}
    	});
    	$("#getCodeButton").on("click", function(e) {
    		ajax.postForm("sendOTP?F=J", $("#oneTimePasswordForm")).done(function(data) {
    			if(data.success === true) {
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
    	$("#confirmOrderButton").on("click", function(e) {
    		ajax.postForm("validateOTP?F=J", $("#oneTimePasswordForm")).done(function(data) {
    			ajax.loadFragment("html/orderconfirmation.html").done(function(out) {	
	    			$("#confirmDetailsDiv").empty();
					$("#confirmDetailsDiv").append(out);
					$.each(data.itemList, function(i, item) {
			    		var individualItemRow =  createIndividualItemRow(item);
			    		$("#confirmOrderDetailsTable > tbody").append(individualItemRow);
			    	});
			    	var deliveryDetailsInfo = createDeliveryDetailsInfo(data);    	
			    	$("#confirmDeliveryDetails").append(deliveryDetailsInfo);    	
			    	//$("#orderIdConfirmPayment").val(data.orderId);	
			    	goToNextStep();
			    	disableAllOtherWizard();
    			}).fail(function(data) {        	
                	alert("failed");
                });
            }).fail(function(data) {        	
            	alert("failed");
            });
    	});
    };
    
    $(".prev-step").click(function (e) {

        var $active = $('.wizard .nav-tabs li.active');
        $active.prev().removeClass('disabled');
        $active.addClass('disabled');
        prevTab($active);

    });
    function disableAllOtherWizard() {
    	$('.wizard .nav-tabs li').each(function() {
    		if($(this).hasClass("active")) {
    			//skip
    			
    		} else {
    			$(this).addClass("disabled");
    		}
    	});
    }
});

function nextTab(elem) {
    $(elem).next().find('a[data-toggle="tab"]').click();
}
function prevTab(elem) {
    $(elem).prev().find('a[data-toggle="tab"]').click();
}