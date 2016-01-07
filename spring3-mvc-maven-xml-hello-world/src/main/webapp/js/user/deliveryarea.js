 var dataSet = [];

function showDefaultSubArea() {
    var inputValueArea = $("input:text[name=areaAddr]").val();
    $(".subAreaMenu").empty();
    var regexArea = new RegExp('(' + inputValueArea + ')', 'gi');
    var haveSubMenu = false;
    $.each(dataSet, function(i, data) {
	var dataStrArea = data.area;
	if (dataStrArea === inputValueArea) {
	    $.each(data.subarea, function(j, subarea) {
		haveSubMenu = true;
		var dataStrSubArea = subarea.areaName;
		var li = "<div class='list-group-item individualSubArea' data-name='" + dataStrSubArea + "' id='"
			+ subarea.deliveryAreaId + "'>" + dataStrSubArea + "</div>";
		$(".subAreaMenu").append(li);
	    });
	    return false;
	}
    });
    if (!haveSubMenu) {
	$(".subAreaMenu").removeClass("show");
	$(".subAreaMenu").addClass("hide");
    } else {
	$(".subAreaMenu").removeClass("hide");
	$(".subAreaMenu").addClass("show");
    }
}
function fetchDeliveryArea() {
    if (dataSet.length === 0) {
	ajax.getJSON("fetchAllArea").done(function(data) {
	    $.each(data.deliveryArea, function(i, area) {
		areaObj = {};
		subAreaArray = area.subArea;
		zipcodeArray = [];
		var areaName = area.areaName;
		$.each(area.subArea, function(i, subArea) {
		    if ($.inArray(subArea.zipcode, zipcodeArray) === -1) {
			zipcodeArray.push(subArea.zipcode);
		    }
		});
		areaObj["area"] = areaName;
		areaObj["subarea"] = subAreaArray;
		areaObj["zipcodes"] = zipcodeArray;
		dataSet.push(areaObj);
	    });
	});
    }
    bindAreaEvents();
}
function bindAreaEvents() {
    $(".areaAddr").on("focusin", function() {
	if ($(this).find("input:text[name=areaAddr]").val() && $(this).find("input:text[name=areaAddr]").val().length > 0) {
	    if ($(this).find("input:text[name=areaAddr]").val() !== $(this).find("input:text[name=areaAddr]").attr("data-name")) {
		$(".areaMenu").removeClass("hide");
		$(".areaMenu").addClass("show");
	    }
	}
    });
    $(document).on("click", function(e) {
	if ($(e.target).attr("id") !== $('.areaAddr').attr('id')) {
	    $(".areaMenu").removeClass("show");
	    $(".areaMenu").addClass("hide");
	}
	if ($(e.target).attr("id") !== $('.subAreaAddr').attr('id')) {
	    $(".subAreaMenu").removeClass("show");
	    $(".subAreaMenu").addClass("hide");
	}
    });
    $(".areaAddr").on( "input", function() {
		if ($(this).val() && $(this).val().length > 0) {
		    var inputValue = $(this).val();
		    $(".areaMenu").empty();
		    var regex = new RegExp('(' + inputValue + ')', 'gi');
		    $.each(dataSet, function(i, data) {
			var dataStr = data.area;
			var li = "";
			if (dataStr.search(regex) !== -1) {
			    li = "<div class='list-group-item individualArea' data-index='" + dataStr.indexOf(regex)
				    + "' data-name='" + dataStr + "'>"
				    + dataStr.replace(regex, '<strong>' + inputValue + '</strong>') + "</div>";
			    $(".areaMenu").append(li);
			} else {
			    $.each(data.zipcodes, function(j, zipcode) {
				if (zipcode.search(regex) !== -1) {
				    li = "<div class='list-group-item individualArea' data-name='" + dataStr + "'>"
					    + dataStr + "</div>";
				    $(".areaMenu").append(li);
				}
			    });
			}
			$.each(data.subarea, function(j, subarea) {
			    var dataStrSubArea = subarea.areaName;
			    var li = "";
			    if (dataStrSubArea.search(regex) !== -1) {
				li = "<div class='list-group-item individualAreaSubArea' data-name='" + dataStrSubArea
					+ "' data-areaName='" + dataStr + "' id='" + subarea.deliveryAreaId + "'>"
					+ dataStrSubArea.replace(regex, '<strong>' + inputValue + '</strong>') + ", "
					+ dataStr + "</div>";
				$(".areaMenu").append(li);
			    }
			});
		    });
		    $(".areaMenu").removeClass("hide");
		    $(".areaMenu").addClass("show");
		} else {
		    $(".areaMenu").removeClass("show");
		    $(".areaMenu").addClass("hide");
		}
	    });
    $(".subAreaAddr").on("focusin", function() {
	if ($(this).find("input:text[name=subAreaAddr]").val() && $(this).find("input:text[name=subAreaAddr]").val().length > 0) {
	    if ($(this).find("input:text[name=subAreaAddr]").val() !== $(this).find("input:text[name=subAreaAddr]").attr("data-name")) {
		$(".subAreaMenu").removeClass("hide");
		$(".subAreaMenu").addClass("show");
	    }
	} else {
	    showDefaultSubArea();
	}
    });
    $(".subAreaAddr").on("input", function() {
		if ($(this).val() && $(this).val().length > 0) {
		    var inputValueArea = $(this).parent().parent().parent().find("input:text[name=areaAddr]").val();
		    var inputValue = $(this).val();
		    $(".subAreaMenu").empty();
		    var regexArea = new RegExp('(' + inputValueArea + ')', 'gi');
		    var regex = new RegExp('(' + inputValue + ')', 'gi');
		    $.each(dataSet, function(i, data) {
			var dataStrArea = data.area;
			if (dataStrArea.search(regexArea) !== -1) {
			    $.each(data.subarea, function(j, subarea) {
				var dataStrSubArea = subarea.areaName;
				var li = "";
				if (dataStrSubArea.search(regex) !== -1) {
				    li = "<div class='list-group-item individualSubArea' data-name='" + dataStrSubArea
					    + "' id='" + subarea.deliveryAreaId + "'>"
					    + dataStrSubArea.replace(regex, '<strong>' + inputValue + '</strong>')
					    + "</div>";
				    $(".subAreaMenu").append(li);
				}
			    });
			    return false;
			}
		    });
		    $(".subAreaMenu").removeClass("hide");
		    $(".subAreaMenu").addClass("show");
		} else {
		    showDefaultSubArea();
		}
    });
    $("#areaDivElements").on("click", ".individualSubArea", function() {
	$(".subAreaAddr").val($(this).attr('data-name'));
	$(".subAreaAddr").attr("data-name", $(this).attr('data-name'));
	$(".searchId").val($(this).attr('id'));
	$("#selectedAreaId").val($(this).attr('id'));
	$("#selectedSubArea").val($(this).attr('data-name'));
	
	$(".subAreaMenu").removeClass("show");
	$(".subAreaMenu").addClass("hide");
	//getDeliverySlots();
    });
    $("#areaDivElements").on("click", ".individualAreaSubArea", function() {
	$(".subAreaAddr").val($(this).attr('data-name'));
	$(".subAreaAddr").attr("data-name", $(this).attr('data-name'));
	$(".areaAddr").val($(this).attr('data-areaName'));
	$(".areaAddr").attr("data-name", $(this).attr('data-areaName'));
	$("#searchId").val($(this).attr('id'));
	$("#selectedAreaId").val($(this).attr('id'));
	$("#selectedArea").val($(this).attr('data-areaName'));
	$("#selectedSubArea").val($(this).attr('data-name'));
	$(".areaMenu").removeClass("show").addClass("hide");
	$(".subAreaMenu").removeClass("show").addClass("hide");
	//getDeliverySlots();
    });
    $("#areaDivElements").on("click", ".individualArea", function() {
	if ($(".areaAddr").val() !== $(this).attr('data-name')) {
	    $(".areaAddr").attr("data-name", $(this).attr('data-name'));
	    $(".areaAddr").val($(this).attr('data-name'));
	    
		$("#selectedArea").val($(this).attr('data-name'));
	    $(".subAreaAddr").val('');
	}
	$(".areaMenu").removeClass("show");
	$(".areaMenu").addClass("hide");
    });
}