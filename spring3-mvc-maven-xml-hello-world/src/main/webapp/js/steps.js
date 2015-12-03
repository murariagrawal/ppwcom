$(document).ready(function () {
    //Initialize tooltips
	var dataSet= [];
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
    function hideOverlay() {
    	//$('body').removeClass("overlay");
		//$('container').removeClass("overlay");
    }
    function showOverlay() {
    	//$('body').addClass("overlay");
    	//$('container').addClass("overlay");
    }
    $("#continueToDelivery").on("click", function(e) {
    	showOverlay();
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
    		 var deliveryAddressId = $("#deliveryAddressId").val();
    		 if(deliveryAddressId && deliveryAddressId!== ""){    			 
    			 goToNextStep();
    			 hideOverlay();
    			 
    		 } else {
    		 	ajax.postForm("deliveryDetails?F=J", $("#homeForm")).done(function(data) {			
    				ajax.loadFragment("html/deliverydetails.html").done(function(out) {				
    					$("#deliveryDetailsDiv").empty();
    					$("#deliveryDetailsDiv").append(out);
    					$("input[id=deliveryOrderId]").val(data.orderId);
    					bindDeliveryEvents();
    					hideErrorMessage();
    					goToNextStep();
    					disableAllOtherWizard();
    					 hideOverlay();
    				}).fail(function(data) {	        	
    		        	alert("failed");
    		        });						
    	        }).fail(function(data) {        	
    	        	alert("failed");
    	        }); 
    		 }
    		 
    	 } else {
    		 showErrorMessage('Please add atleast one Box of Pani puri to continue');    		
    	 }
		
    
    });
    
    function bindDeliveryEvents() {
	    $('#phoneNumber').on("keyup",function() {
	    	var length = $(this).val().length;
	    	if(length && length == 10) {
	    		getAddressDetails();
	    	}
	    });
	    
	    
    }
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
								itemQuantity+'</td><td class="text-center subtotal">'+
								itemTotalPrice+'</td></tr>';
		return individualItemRow;
		
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
							data.landmarkReturn+" "+
							data.area+"</div></div>";    	
    	var slotInfo = "<div class='row'><div class='col-lg-12'>Delivery Slot Selected :<br><label>"+data.deliverySlot+"</label></div></div>";
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
	  if(deliverySlotVal === null || deliverySlotVal == "") {
		  $("#selectDeliverySlot").parent().addClass('error-field');
		  noOfFieldsInError = noOfFieldsInError+1;
		  errorMessage = "Please select a delivery slot to continue.";
	  }
	  if(noOfFieldsInError >1) {
		  errorMessage = "There are two or more fields in error";
	  }
	  if(errorMessage !== "") {
		  showErrorMessage(errorMessage);
		  return false;
	  } else {
		  hideErrorMessage();
		  return true;
	  }
	  
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
    	showOverlay();
    	$('.error-field').removeClass('error-field');
    	hideErrorMessage();
    	if(validateDeliveryForm()) {
			ajax.postForm("verifyDetails?F=J", $("#deliveryForm")).done(function(data) {			
				ajax.loadFragment("html/orderverification.html").done(function(out) {
					if(data.errormessage && errormessage !== "") {
						showErrorMessage(errormessage);
						hideOverlay();
					} else {
						hideErrorMessage();
						$("#verifyDetailsDiv").empty();
						$("#verifyDetailsDiv").append(out);
						addItemToppingAndPrice("verifyOrderDetailsTable", data);						
				    	var deliveryDetailsInfo = createDeliveryDetailsInfo(data);    	
				    	$("#verifyDeliveryDetails").append(deliveryDetailsInfo);    	
				    	$("#orderIdPayment").val(data.orderId);
				    	$("#contactNumber").html(data.contactNo);
						bindVerifyEvents(data);
						goToNextStep();
						disableAllOtherWizard();
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
					if(d.area) {
						liIndividual = liIndividual+"<span id='area'>";
						if(d.area.subAreaName) {
							liIndividual = liIndividual+d.area.subAreaName+", ";
						}
						if(d.area.areaName) {
							liIndividual = liIndividual+d.area.areaName+ ", ";
						} 
						if(d.area.zipcode) {
							liIndividual = liIndividual+d.area.zipcode;
						}
						
						liIndividual = liIndividual+"</span>"
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
				$('#myModal').modal('handleUpdate')
				bindAddressModelEvents();
			} else {
				clearDeliveryForm();
				$("#addressFieldsExisting").removeClass("show").addClass("hide");
				$('#addressFields').removeClass("hide").addClass("show");
				fetchDeliveryArea();
			}			
        }).fail(function(data) {
        	
        	alert("failed");
        });		
	}
	function clearDeliveryForm() {
		$("#addressFields input[type=text]").val("");
		$("#addressFieldsExisting").empty();
		$('.error-field').removeClass('error-field');
		$('#selectDeliverySlot')
	    .find('option')
	    .remove()
	    .end()
	    .append('<option value="">Select Delivery Slot</option>')
	    .val('');
		
		$('#addressDivElements').removeClass("hide").addClass("show");
		hideErrorMessage();
	}
	function fetchDeliveryArea() {
		if(dataSet.length === 0) {
			ajax.getJSON("fetchAllArea").done(function(data) {
				
	    		$.each(data.deliveryArea, function (i, area) {
	    			areaObj = {};
	    			subAreaArray=area.subArea;
	    			zipcodeArray=[];
					var areaName =area.areaName;
					$.each(area.subArea, function (i, subArea) {
						if($.inArray(subArea.zipcode, zipcodeArray)=== -1 ) {
							zipcodeArray.push(subArea.zipcode);
						}
					});
				    areaObj ["area"] = areaName;
				    areaObj ["subarea"] = subAreaArray;
	    			areaObj ["zipcodes"] = zipcodeArray;
	    			dataSet.push(areaObj);
											
				});
	    		bindAreaEvents();
	    	});
		}
		
	}
	function showDefaultSubArea() {
		var inputValueArea = $("input:text[name=areaAddr]").val();
		$("#subAreaMenu").empty();
		var regexArea = new RegExp( '(' + inputValueArea + ')', 'gi' );
		var haveSubMenu = false;
		$.each(dataSet,function(i, data) {
			var dataStrArea= data.area;
			if(dataStrArea.search(regexArea) !==-1) {
				
				$.each(data.subarea,function(j, subarea) {
					haveSubMenu = true;
					var dataStrSubArea= subarea.areaName;
					var li = "<div class='list-group-item individualSubArea' data-name='"+dataStrSubArea+"' id='"+subarea.deliveryAreaId+"'>"+dataStrSubArea+"</div>";
					$("#subAreaMenu").append(li);
				});					
				return false;					
			}
		});
		if(!haveSubMenu) {
			$("#subAreaMenu").removeClass("show");
			$("#subAreaMenu").addClass("hide");
		} else {
			$("#subAreaMenu").removeClass("hide");
			$("#subAreaMenu").addClass("show");
		}
	}
	function bindAreaEvents() {
		$("#areaAddr").on("focusin" , function() {			
			if($("input:text[name=areaAddr]").val() && $("input:text[name=areaAddr]").val().length >0) {
				if($("input:text[name=areaAddr]").val() !== $("input:text[name=areaAddr]").attr("data-name")) {
					$("#areaMenu").removeClass("hide");
					$("#areaMenu").addClass("show");
				}
			}	
		});		
		/*$("#areaAddr").on("blur" , function() {				
					$("#areaMenu").removeClass("show");
					$("#areaMenu").addClass("hide");
		});*/
		$("#areaAddr").on("input", function() {
			if($(this).val() && $(this).val().length >0) {
				var inputValue = $(this).val();
				$("#areaMenu").empty();
				var regex = new RegExp( '(' + inputValue + ')', 'gi' );
					$.each(dataSet,function(i, data) {
						var dataStr= data.area;
						var li ="";
						if(dataStr.search(regex) !==-1) {
							li = "<div class='list-group-item individualArea' data-name='"+dataStr+"'>"+dataStr.replace(regex, '<strong>'+inputValue+'</strong>')+"</div>";
							$("#areaMenu").append(li);
						} else {
							$.each(data.zipcodes, function(j, zipcode) {
								if(zipcode.search(regex) !==-1) {
									li = "<div class='list-group-item individualArea' data-name='"+dataStr+"'>"+dataStr+"</div>";
									$("#areaMenu").append(li);
									return false;
								}
							});
						}
						
					});
				
				$("#areaMenu").removeClass("hide");
				$("#areaMenu").addClass("show");
			} else {
				$("#areaMenu").removeClass("show");
				$("#areaMenu").addClass("hide");
			}
		});
		$("#subAreaAddr").on("focusin" , function() {			
			if($("input:text[name=subAreaAddr]").val() && $("input:text[name=subAreaAddr]").val().length >0) {
					if($("input:text[name=subAreaAddr]").val() !== $("input:text[name=subAreaAddr]").attr("data-name")) {
						$("#subAreaMenu").removeClass("hide");
						$("#subAreaMenu").addClass("show");	
					}
								
			} else {
				showDefaultSubArea();
					
			}	
		});		
		/*$("#subAreaAddr").on("blur" , function() {				
					$("#subAreaMenu").removeClass("show");
					$("#subAreaMenu").addClass("hide");
		});*/
		$("#subAreaAddr").on("input", function() {
			if($(this).val() && $(this).val().length >0) {
				var inputValueArea = $("input:text[name=areaAddr]").val();
				var inputValue = $(this).val();
				$("#subAreaMenu").empty();
				var regexArea = new RegExp( '(' + inputValueArea + ')', 'gi' );
				var regex = new RegExp( '(' + inputValue + ')', 'gi' );
					$.each(dataSet,function(i, data) {
						var dataStrArea= data.area;
						if(dataStrArea.search(regexArea) !==-1) {
							$.each(data.subarea,function(j, subarea) {
								var dataStrSubArea= subarea.areaName;
								var li ="";								
								if(dataStrSubArea.search(regex) !==-1) {
									li = "<div class='list-group-item individualSubArea' data-name='"+dataStrSubArea+"' id='"+subarea.deliveryAreaId+"'>"+dataStrSubArea.replace(regex, '<strong>'+inputValue+'</strong>')+"</div>";
									$("#subAreaMenu").append(li);
								}
							});
							return false;
						}
					});
				
				$("#subAreaMenu").removeClass("hide");
				$("#subAreaMenu").addClass("show");
			} else {
				showDefaultSubArea();
			}
		});
		$("#addressDivElements").on("click", ".individualSubArea", function() {
			$("#subAreaAddr").val($(this).attr('data-name'));
			$("#subAreaAddr").attr("data-name",$(this).attr('data-name'));
			$("#searchId").val($(this).attr('id'));
			$("#subAreaMenu").removeClass("show");
			$("#subAreaMenu").addClass("hide");
			getDeliverySlots();
		});
		$("#addressDivElements").on("click", ".individualArea", function() {
			if($("#areaAddr").val() !== $(this).attr('data-name')) {
				$("#areaAddr").attr("data-name",$(this).attr('data-name'));
				$("#areaAddr").val($(this).attr('data-name'));
				$("#subAreaAddr").val('');
			}
			
			$("#areaMenu").removeClass("show");
			$("#areaMenu").addClass("hide");
		});
		
	}
    function bindAddressModelEvents() {
    	$( ".well-sm" ).on('click', function(e) {
    		clearDeliveryForm();
    		$("#addressFieldsExisting").removeClass("hide").addClass("show");
			$('#addressFields').removeClass("show").addClass("hide");
			$("#addressFieldsExisting").html($(this).html());
    		/*$("#firstNameAddr").val($(this).find("#firstName").html());
			$("#lastNameAddr").val($(this).find("#lastName").html());
			$("#addr1").val($(this).find("#addressLine1").html());
			$("#addr2").val($(this).find("#addressLine2").html());
			$("#landmarkAddr").val($(this).find("#landmark").html());
			$("#areaAddr").val($(this).find("#area").html());*/
			$("#deliveryAddressId").val($(this).find("#addressId").val());			
			$('#myModal').modal('hide');
			//$('#addressFields').removeClass("hide").addClass("show");
			getDeliverySlots();
		});
    	$("#enterNewAddress").on('click', function(e) {  
    		$('#myModal').modal('hide');
    		clearDeliveryForm();
    		$("#addressFieldsExisting").removeClass("show").addClass("hide");
			$('#addressFields').removeClass("hide").addClass("show");
			fetchDeliveryArea();
    	});
		
    }
    $('input').focus(function() {

        var padding = 100; // Desired page "padding"

        var lbound = $(this).offset().top - $(window).height() + padding;
        var ubound = $(this).offset().top - padding;

        if ($(window).scrollTop() < lbound)
            $(window).scrollTop(lbound);
        else if ($(window).scrollTop() > ubound)
            $(window).scrollTop(ubound);

    });
    
    function getDeliverySlots() {	    	
		ajax.postForm("fetchDeliverySlots?F=J", $("#deliveryForm")).done(function(data) {		
			$("#selectDeliverySlot").prop( "disabled", false );
			if(data.errormessage && data.errormessage !== "") {
				showErrorMessage(data.errormessage);
			} else if(data.deliverySlots) {
				hideErrorMessage();
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
			showOverlay();
			ajax.postForm("validateOTP?F=J", $("#oneTimePasswordForm")).done(function(data) {
				if(data.errormessage && data.errormessage!== "") {
					showErrorMessage(data.errormessage);
					hideOverlay();
				} else {
					hideErrorMessage();
	    			ajax.loadFragment("html/orderconfirmation.html").done(function(out) {	
		    			$("#confirmDetailsDiv").empty();
						$("#confirmDetailsDiv").append(out);
						addItemToppingAndPrice("confirmOrderDetailsTable", data);						
				    	var deliveryDetailsInfo = createDeliveryDetailsInfo(data);    	
				    	$("#confirmDeliveryDetails").append(deliveryDetailsInfo);    	
				    	//$("#orderIdConfirmPayment").val(data.orderId);	
				    	goToNextStep();
				    	disableAllOtherWizard();
				    	$("#placeNewOrder").on("click", function() {
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
    }
	function addItemToppingAndPrice(tableId, data) {
		$.each(data.itemList, function(i, item) {
    		var individualItemRow =  createIndividualItemRow(item);
    		$("#"+tableId+" > tbody").append(individualItemRow);
    	});
		$.each(data.toppingList, function(i, topping) {
			var individualToppingRow =  createIndividualToppingRow(topping);
    		
    		$("#"+tableId+" > tbody").append(individualToppingRow);
    	});
		var totalPrice = 0.00;
		$(".subtotal").each(function() {
			if ($(this).html() && !isNaN($(this).html())) {
				totalPrice += parseFloat($(this).html());
			}
		})	
		var totalPriceHtml = '<tfoot><tr class="visible-xs"><td class="text-center">'+
							 '<strong>Total <label id="totalAmount1">'+totalPrice.toFixed(2)+'</label></strong></td>'+
							 '</tr><tr><td colspan="3" class="hidden-xs"></td><td class="hidden-xs text-center">'+
							 '<strong>Total <label id="totalAmount2">'+totalPrice.toFixed(2)+'</label></strong></td><td></td></tr></tfoot>';
		$("#"+tableId).append(totalPriceHtml);
	}
    $(".prev-step").click(function (e) {
    	hideErrorMessage();
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