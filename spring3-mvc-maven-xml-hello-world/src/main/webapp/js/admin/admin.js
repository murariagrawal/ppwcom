$(document).ready(function () {
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
	    });
	    $('#stuffing, #stuffingMenu').on("click",function() {
	    	loadFragment("html/admin/StuffingManagement.html");
	    });
	    $('#stuffing').on("click",function() {
	    	loadFragment("html/admin/ItemManagement.html");
	    });
	    $('#user, #usersMenu').on("click",function() {
	    	loadFragment("html/admin/ItemManagement.html");
	    });
	    $('#area , #areaMenu').on("click",function() {
	    	loadFragment("html/admin/areaManagement.html");
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
	
	
});