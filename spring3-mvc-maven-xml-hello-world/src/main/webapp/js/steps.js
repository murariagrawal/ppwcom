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

    $(".next-step").click(function (e) {

        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);

    });
    $('[data-th="Quantity"]').on('change',function (e) {
    	
    	var itemId = $(this).attr("data-itemId");
    	var subtotalId = "subTotal"+ itemId;
    	var itemPriceId = "itemPrice" +itemId;
    	var quantityId = "quantity" +itemId;    	
    	var itemPrice = $("#"+itemPriceId).html();    	
    	var itemQuantity = $("#"+quantityId).val();
    	var subtotal = itemQuantity *itemPrice;
    	var customizeId = "customize"+itemId
    	$("#"+customizeId).removeClass("disabled");
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
		ajax.postForm("deliveryDetails?F=J", $("#homeForm")).done(function(data) {			
			ajax.loadFragment("html/deliverydetails.html").done(function(out) {				
				$("#deliveryDetailsDiv").empty();
				$("#deliveryDetailsDiv").append(out);
				$("input[id=deliveryOrderId]").val(data.orderId);
				bindDeliveryEvents();
			}).fail(function(data) {	        	
	        	alert("failed");
	        });						
        }).fail(function(data) {        	
        	alert("failed");
        });
    });
    function bindDeliveryEvents() {
	    $('#phoneNumber').on("keyup",function() {
	    	var length = $(this).val().length;
	    	if(length && length == 10) {
	    		getAddressDetails();
	    	}
	    });
	    $("#zipcodeAddr").on('input', function(){
			alert($(this).val());
			var length = $(this).val().length;
			alert(length);
	    	if(length && length == 6) {
	    		getDeliverySlots();
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
				$('#addressFields').removeClass("hide").addClass("show");
			}			
        }).fail(function(data) {
        	
        	alert("failed");
        });		
	}
	
    function bindAddressModelEvents() {
    	$( ".well-sm" ).on('click', function(e) {
    		$("#firstNameAddr").val($(this).find("#firstName").html());
			$("#lastNameAddr").val($(this).find("#lastName").html());
			$("#addr1").val($(this).find("#addressLine1").html());
			$("#addr2").val($(this).find("#addressLine2").html());
			$("#landmarkAddr").val($(this).find("#landmark").html());
			$("#zipcodeAddr").val($(this).find("#zipcode").html());
			$("#deliveryAddressId").val($(this).find("#addressId").html());			
			$('#myModal').modal('hide');
			getDeliverySlots();
		});
    	$("#enterNewAddress").on('click', function(e) {
    		$('#myModal').modal('hide');
    		$('#addressFields').removeClass("hide").addClass("show");
    	});
		
    }
    
    
    function getDeliverySlots() {	    	
		ajax.postForm("fetchDeliverySlots?F=J", $("#deliveryForm")).done(function(data) {		
			$("#selectDeliverySlot").prop( "disabled", false );
			$.each(data, function (i, item) {
			    $('#selectDeliverySlot').append($('<option>', { 
			        value: item.value,
			        text : item.text 
			    }));
			});
		}).fail(function(data) {
        	
        	alert("failed");
        });
    }
    
    $("#continueToVerify").on("click", function(e) {		
		ajax.postForm("verifyDetails?F=J", $("#deliveryForm")).done(function(data) {			
			ajax.loadFragment("html/orderverification.html").done(function(out) {
				$("#verifyDetailsDiv").append(out);
				bindVerifyEvents(data);
			}).fail(function(data) {	        	
	        	alert("failed");
	        });						
        }).fail(function(data) {        	
        	alert("failed");
        });
    });
    function bindVerifyEvents(data) {
    	$.each(data.itemList, function(i, item) {
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
    		var itemDetailsDiv='<div id="itemDetails'+itemId+'"	class="accordion-body collapse"><div class="accordion-inner"><table class="table table-hover table-condensed">'+	
    		'<tr><td>'+itemDetailsLabel+
    		'</td></tr></table></div></div>';
    		var itemTotalPrice = itemPrice*itemQuantity;
    		var individualItemRow='<tr> <td>'+itemNameDiv+itemDetailsDiv+'</td><td>'+itemPrice+'</td><td>'+itemQuantity+'</td><td class="text-center">'+itemTotalPrice+'</td></tr>';
    		$("#verifyOrderDetailsTable > tbody").append(individualItemRow);
    	});
	   
    }
    $(".prev-step").click(function (e) {

        var $active = $('.wizard .nav-tabs li.active');
        prevTab($active);

    });
});

function nextTab(elem) {
    $(elem).next().find('a[data-toggle="tab"]').click();
}
function prevTab(elem) {
    $(elem).prev().find('a[data-toggle="tab"]').click();
}