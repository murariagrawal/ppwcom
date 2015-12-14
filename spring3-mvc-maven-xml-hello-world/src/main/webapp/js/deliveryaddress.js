$(document).ready(function () {
	
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
	function getDeliverySlots() {	    	
		ajax.postForm("fetchDeliverySlots?F=J", $("#deliveryForm")).done(function(data) {	
			
			$("#selectDeliverySlot").prop( "disabled", false );
			if(data.errormessage && data.errormessage !== "") {
				showErrorMessage(data.errormessage);
			} else if(data.deliverySlots) {
				hideErrorMessage();
				$('#selectDeliverySlot')
			    .find('option')
			    .remove()
			    .end()
			    .append('<option value="">Select Delivery Slot</option>')
			    .val('');
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
								}
							});
						}
						$.each(data.subarea,function(j, subarea) {
							var dataStrSubArea= subarea.areaName;
							
							var li ="";								
							if(dataStrSubArea.search(regex) !==-1) {
								li = "<div class='list-group-item individualSubArea' data-name='"+dataStrSubArea+"' id='"+subarea.deliveryAreaId+"'>"+dataStrSubArea.replace(regex, '<strong>'+inputValue+'</strong>')+", "+
								dataStr+"</div>";
								$("#areaMenu").append(li);
							}
						});
						
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
			$("#searchId").val($(this).find("#areaId").val());
			$('#myModal').modal('hide');
			//$('#addressFields').removeClass("hide").addClass("show");
			getDeliverySlots();
		});
		$( ".editbutton" ).on('click', function(e) {
			clearDeliveryForm();
			
			$("#firstNameAddr").val($(this).find("#firstName").html());
			$("#lastNameAddr").val($(this).find("#lastName").html());
			$("#addr1").val($(this).find("#addressLine1").html());
			$("#addr2").val($(this).find("#addressLine2").html());
			$("#landmarkAddr").val($(this).find("#landmark").html());
			$("#areaAddr").val($(this).find("#area").html());
			$("#subAreaAddr").val($(this).find("#subAreaAddr").html());
			$("#deliveryAddressId").val($(this).find("#addressId").val());
			$("#searchId").val($(this).find("#areaId").val());	
			$('#myModal').modal('hide');
			$('#addressFields').removeClass("hide").addClass("show");
			getDeliverySlots();
		});
		$("#enterNewAddress").on('click', function(e) {  
			$('#myModal').modal('hide');
			clearDeliveryForm();
			$("#addressFieldsExisting").removeClass("show").addClass("hide");
			$('#addressFields').removeClass("hide").addClass("show");
			fetchDeliveryArea();
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
						var hiddenAreaId = "<input type='hidden' id='areaId' value='"+d.area.deliveryAreaId+"' />"
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
						 	divIndividual = divIndividual+ulIndividual+hiddenAreaId+hiddenAddressId+"</div>";
							liFinal = "<li>"+divIndividual+"</li>";
							var liedit = "<li><div class='row editbutton'><button>Edit Address</button></li>";
							ulFinal = ulFinal+liFinal+liedit;
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
		function validateDeliveryForm(){
			  var phoneNumberVal =  $("#phoneNumber").val(),
			  firstNameVal1 = $("#firstNameAddr").val(),
			  lastNameVal1 = $("#lastNameAddr").val(),
			  addressVal1 = $("#addr1").val(),
			  addressVal2 = $("#addr2").val(),
			  landMarkVal = $("#landmarkAddr").val(),	  
			  deliverySlotVal = $("#selectDeliverySlot").val(),
			  existingAddress = $("#addressFieldsExisting").html(),
			  noOfFieldsInError = 0,
			  errorMessage = "";
			  if(existingAddress ==="") {
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
			  } else {
				  if($("#deliveryAddressId").val() === "") {
					  noOfFieldsInError = noOfFieldsInError+1;
					  errorMessage = "Something went wrong. Please try again later.";
				  }
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
	}

});