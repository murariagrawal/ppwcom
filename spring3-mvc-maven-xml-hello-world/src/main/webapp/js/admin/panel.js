$(document).ready(function () {
	
	$(document).on('click', '.panel div.clickable', function (e) {
	    var $this = $(this);
	    if (!$this.hasClass('panel-collapsed')) {
	        $this.parents('.panel').find('.panel-body').slideUp();
	        $this.addClass('panel-collapsed');
	        $this.find('i').removeClass('glyphicon-minus').addClass('glyphicon-plus');
	    } else {
	        $this.parents('.panel').find('.panel-body').slideDown();
	        $this.removeClass('panel-collapsed');
	        $this.find('i').removeClass('glyphicon-plus').addClass('glyphicon-minus');
	    }
	});
	var addFormGroup = function (event) {
	    event.preventDefault();
	
	    var $formGroup = $(this).closest('.form-group');
	    var $multipleFormGroup = $formGroup.closest('.multiple-form-group');
	    var $formGroupClone = $formGroup.clone();
	
	    $(this)
	        .toggleClass('btn-success btn-add btn-danger btn-remove')
	        .html('–');
	
	    $formGroupClone.find('input').val('');
	    $formGroupClone.find('.concept').text('Select Slot');
	    $formGroupClone.insertAfter($formGroup);
	
	    var $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
	    if ($multipleFormGroup.data('max') <= countFormGroup($multipleFormGroup)) {
	        $lastFormGroupLast.find('.btn-add').attr('disabled', true);
	    }
	};
	
	var removeFormGroup = function (event) {
	    event.preventDefault();
	
	    var $formGroup = $(this).closest('.form-group');
	    var $multipleFormGroup = $formGroup.closest('.multiple-form-group');
	
	    var $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
	    if ($multipleFormGroup.data('max') >= countFormGroup($multipleFormGroup)) {
	        $lastFormGroupLast.find('.btn-add').attr('disabled', false);
	    }
	
	    $formGroup.remove();
	};
	
	var selectFormGroup = function (event) {
	    event.preventDefault();
	
	    var $selectGroup = $(this).closest('.input-group-select');
	    var param = $(this).attr("href").replace("#","");
	    var concept = $(this).text();
	
	    $selectGroup.find('.concept').text(concept);
	    $selectGroup.find('.input-group-select-val').val(param);
	
	}
	
	var countFormGroup = function ($form) {
	    return $form.find('.form-group').length;
	};
	
	var submitAddArea = function() {
		
		ajax.postForm("addArea?F=J", $("#addAreaForm")).done(function(data) {			
							
	    }).fail(function(data) {        	
	    	alert("failed");
	    });
		
	}

	$('.panel-heading').each(function() {
		 var $this = $(this);
		 if ($this.hasClass('panel-collapsed')) {
		        $this.parents('.panel').find('.panel-body').slideUp();		        
		        $this.find('i').removeClass('glyphicon-minus').addClass('glyphicon-plus');
		    }
	});
   
    $(document).on('click', '.btn-add', addFormGroup);
    $(document).on('click', '.btn-remove', removeFormGroup);
    $(document).on('click', '.dropdown-menu a', selectFormGroup);
    $("#submitAddArea").on('click', submitAddArea);
});
