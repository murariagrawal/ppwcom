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
			ajax.loadFragment("html/deliverydetails.html").done(function(data) {
				$("#deliveryDetailsDiv").append(data);
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
			$.each(addressList, function(i, d) {			
				var divIndividual = "<div class='well well-sm' data-th='individualAddress'>";
				var ulIndividual = "<ul style='list-style:none'>";
				var liIndividual="";
				var hiddenAddressId = "<input type='hidden' id='addressId' value='"+d.addressId+"' />"
				if(d.addressLine1) {
					liIndividual = "<li id='addressLine1'>"+d.addressLine1+"</li>";
					ulIndividual = ulIndividual+liIndividual;
				}
				if(d.addressline2) {
					liIndividual = "<li id='addressLine2'>" +d.addressline2+"</li>";
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
			ulFinal = ulFinal+"</ul>";
			
			$('#myModal').modal(); 
			var addressDiv = $("#addressDiv");	
			addressDiv.empty();
			addressDiv.append(ulFinal);
			bindAddressEvents();
			
			
        }).fail(function(data) {
        	
        	alert("failed");
        });		
	}
	
    function bindAddressEvents() {
    	$( ".well-sm" ).on('click', function(e) {
			$("#addr1").val($(this).find("#addressLine1").html())
			$("#addr2").val($(this).find("#addressLine2").html())
			$("#zipcodeAddr").val($(this).find("#zipcode").html())
			
			$('#myModal').modal('hide');
			getDeliverySlots();
		});
		
    }
    
    function getDeliverySlots() {	
    	alert();
		ajax.postForm("fetchDeliverySlots?F=J", $("#deliveryForm")).done(function(data) {	
			
		}).fail(function(data) {
        	
        	alert("failed");
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