$(document).ready(function () {
	
	var itemSet= [];
	ajax.loadFragment("html/admin/adminmenu.html").done(function(data) {
		$("#menuWrapper").empty();
		$("#menuWrapper").append(data);
		ajax.loadFragment("html/admin/quickmenu.html").done(function(data) {
			$("#page-wrapper").empty();
			$("#page-wrapper").append(data);
			bindMenuEvents();
		}).fail(function(data) {	        	
	    	alert("failed");
	    });		
	}).fail(function(data) {	        	
    	alert("failed");
    });	
	
	
	
	function bindMenuEvents() {
	    $('#orders, #ordersMenu').on("click",function() {
	    	loadFragment("html/admin/ItemManagement.html");
	    });
	    $('#homeMenu').on("click",function() {
	    	loadFragment("html/admin/quickmenu.html");
	    });
	    $('#delivery, #deliveryMenu').on("click",function() {
	    	loadFragment("html/admin/ItemManagement.html");
	    });
	    $('#reports, #reportsMenu').on("click",function() {
	    	loadFragment("html/admin/ItemManagement.html");
	    });
	    $('#crew, #crewMenu').on("click",function() {
	    	loadFragment("html/admin/ItemManagement.html");
	    });
	    $('#items, #itemsMenu').on("click",function() {
	    	loadFragment("html/admin/ItemManagement.html");
	    	
	    	ajax.getJSON("getAllItems").done(function(data) {
	    		itemSet= data;
	    		
	    		$.each(itemSet.itemList, function (i, item) {					
						var itemText =item.itemName;
					    var itemValue = item.itemId;
					    var option = new Option(itemText, itemValue);
					    
						/// jquerify the DOM object 'o' so we can use the html method
						$(option).html(itemText);
						$(option).attr("data-price",item.itemPrice);
						$(option).attr("data-details",item.itemDetails);
						
						var comboNameDiv = '<div class="form-group"><label>Item Name:</label><input id="itemNameCombo~'+itemValue+'" value="'+itemText+'" name="itemNameCombo~'+itemValue+'" type="text" class="form-control">'
						var comboQuantityDiv = '<label>Item Quantity:</label><input id="itemQuantityCombo~'+itemValue+'"  name="itemQuantityCombo~'+itemValue+'" type="text" class="form-control"></div>'
						
						$("#selectUpdateItem").append(option);
						$("#selectDeleteItem").append(option);
						$("#addComboItem").append(comboNameDiv);
						$("#addComboItem").append(comboQuantityDiv);
						
				});
	    		var comboPriceDiv = '<div class="form-group"><label>Combo Price:</label><input id="itemPriceCombo" name="itemPriceCombo" type="text" class="form-control"></div></div>'
	    		$("#addComboItem").append(comboPriceDiv);
	    		bindItemEvents();
	    		
	    	});
	    });
	    $('#stuffing, #stuffingMenu').on("click",function() {
	    	loadFragment("html/admin/StuffingManagement.html");
	    	ajax.getJSON("getAllStuffing").done(function(data) {
	    		$.each(data.stuffingList, function (i, stuffing) {					
						var stuffingText =stuffing.toppingName;
					    var stuffingValue = stuffing.toppingId;
					    var option = new Option(stuffingText, stuffingValue);
					    var option1 = new Option(stuffingText, stuffingValue);
					    
						/// jquerify the DOM object 'o' so we can use the html method
						$(option).html(stuffingText);
						$(option).attr("data-price",stuffing.toppingPrice);
						$(option1).html(stuffingText);
						$(option1).attr("data-price",stuffing.toppingPrice);
						$("#selectUpdateStuffing").append(option1);
						$("#selectDeleteStuffing").append(option);					
				});
	    		bindStuffingEvents();
	    		
	    	});
	    	
	    });
	   
	    $('#discount, #usersMenu').on("click",function() {
	    	loadFragment("html/admin/DiscountManagement.html");
	    	ajax.getJSON("fetchAllDiscountInfo").done(function(data) {
	    		$.each(data.masterDeliveryArea, function (i, area) {					
						var areaName =area.areaName;
					    var areaId = area.deliveryAreaId;
					    var option = new Option(areaName, areaId);
					    
						/// jquerify the DOM object 'o' so we can use the html method
						$(option).html(areaName);
						$("#masterArea").append(option);
											
				});
	    		
	    	});
	    });
	    $('#area , #areaMenu').on("click",function() {
	    	loadFragment("html/admin/areaManagement.html");
	    	ajax.getJSON("getAllItemAndStuffing").done(function(data) {
	    		var itemSet= data;
	    		
	    		$.each(itemSet.itemList, function (i, item) {
	    			var itemText =item.itemName;
				    var itemid = item.itemId;
	    			if(!item.comboItem && !item.partyItem) {
	    				var itemDiv = '<div class="form-group"><div class="col-md-6"><input id="itemName'+itemid+'"  name="itemName" type="text" disabled class="form-control" value="'+itemText+'">'
	    								+'<input id="itemId'+itemid+'"  name="itemId" type="hidden" class="form-control" value="'+itemid+'">'
	    								+'</div><div class="col-md-6"><input id="itemQuantity'+itemid+'" type="text" name="itemQuantity" class="form-control" value="0" ></div></div>';
	    								
	    				$("#slotItemStock").append(itemDiv);
	    			}
	    			
	    		});
	    		$.each(itemSet.stuffingList, function (i, stuffing) {
	    			var itemText =stuffing.toppingName;
				    var itemid = stuffing.toppingId;
	    			var stuffingDiv = '<div class="form-group"><div class="col-md-6"><input id="stuffingName'+itemid+'"  name="stuffingName" type="text" disabled class="form-control" value="'+itemText+'">'
					+'<input id="stuffingId'+itemid+'"  name="stuffingId" type="hidden" class="form-control" value="'+itemid+'">'
					+'</div><div class="col-md-6"><input id="stuffingQuantity'+itemid+'" type="text" name="stuffingQuantity" class="form-control" value="0" ></div></div>';
					
					$("#slotStuffingStock").append(stuffingDiv);
	    		});
	    		
	    	});
	    	ajax.getJSON("fetchAllMasterArea").done(function(data) {
	    		$.each(data.masterDeliveryArea, function (i, area) {					
						var areaName =area.areaName;
					    var areaId = area.deliveryAreaId;
					    var option = new Option(areaName, areaId);
					    
						/// jquerify the DOM object 'o' so we can use the html method
						$(option).html(areaName);
						$("#masterArea").append(option);
											
				});
	    		
	    	});
	    });
    }
	function loadFragment(fagmentName) {
		
		ajax.loadFragment(fagmentName).done(function(data) {
    		$("#page-wrapper").empty();
    		$("#page-wrapper").append(data);    		
    	}).fail(function(data) {	        	
        	alert("failed");
        });
	}
	function bindStuffingEvents() {
		$("#selectUpdateStuffing").on("change", function() {		
			 var selected = $(this).find('option:selected');			 
			 var price = selected.data('price');
			 
			 if(price) {
				 $("#stuffingUpdatePrice").val(price);
			 }			  
		});
		$("#selectDeleteStuffing").on("change", function() {		
			 var selected = $(this).find('option:selected');			 
			 var price = selected.data('price');
			 if(price) {
				 $("#stuffingDeletePrice").val(price);
			 }			  
		});
		$("#submitUpdateStuffing").on("click", function() {
			ajax.postForm("updateStuffing?F=J", $("#updateStuffingForm")).done(function(data) {			
							
			}).fail(function(data) {        	
				alert("failed");
			});
		});
		$("#submitAddStuffing").on("click", function() {
			ajax.postForm("addStuffing?F=J", $("#addStuffingForm")).done(function(data) {			
							
			}).fail(function(data) {        	
				alert("failed");
			});
		});
		$("#submitDeleteStuffing").on("click", function() {
			ajax.postForm("deleteStuffing?F=J", $("#deleteStuffingForm")).done(function(data) {			
							
			}).fail(function(data) {        	
				alert("failed");
			});
		});
	}
	function bindItemEvents() {
		$("#selectUpdateItem").on("change", function() {		
			 var selected = $(this).find('option:selected');			 
			 var price = selected.data('price');
			 var details = selected.data('details');
			 if(price) {
				 $("#itemUpdatePrice").val(price);
			 }
			 if(details) {
				 $("#itemUpdateDetails").val(details);
			 }
		});
		$("#selectDeleteItem").on("change", function() {		
			 var selected = $(this).find('option:selected');			 
			 var price = selected.data('price');
			 if(price) {
				 $("#itemDeletePrice").val(price);
			 }			  
		});
		$("#submitUpdateItem").on("click", function() {
			ajax.postForm("updateItem?F=J", $("#updateItemForm")).done(function(data) {			
							
			}).fail(function(data) {        	
				alert("failed");
			});
		});
		$("#submitAddItem").on("click", function() {
			ajax.postForm("addItem?F=J", $("#addItemForm")).done(function(data) {			
							
			}).fail(function(data) {        	
				alert("failed");
			});
		});
		$("#submitDeleteItem").on("click", function() {
			ajax.postForm("deleteItem?F=J", $("#deleteItemForm")).done(function(data) {			
							
			}).fail(function(data) {        	
				alert("failed");
			});
		});
		
		$("#partySelectedButton").on("click", function() {
			$("#addPartyItemQuantity").removeClass("hide");
			$("#addPartyItemQuantity").addClass('show');
			$("#addIndividualItemPrice").removeClass("show");
			$("#addIndividualItemPrice").addClass('hide');
			$("#addComboItemPrice").removeClass("show");
			$("#addComboItemPrice").addClass('hide');
			$("#itemType").val("P");
		});
		$("#individualSelectedButton").on("click", function() {
			$("#addPartyItemQuantity").removeClass("show");
			$("#addPartyItemQuantity").addClass('hide');
			$("#addIndividualItemPrice").removeClass("hide");
			$("#addIndividualItemPrice").addClass('show');
			$("#addComboItemPrice").removeClass("show");
			$("#addComboItemPrice").addClass('hide');
			$("#itemType").val("I");
			
		});
		$("#comboSelectedButton").on("click", function() {
			$("#addPartyItemQuantity").removeClass("show");
			$("#addPartyItemQuantity").addClass('hide');
			$("#addIndividualItemPrice").removeClass("show");
			$("#addIndividualItemPrice").addClass('hide');
			$("#addComboItem").removeClass("hide");
			$("#addComboItem").addClass('show');
			$("#itemType").val("C");
			
		});
	}
	
});