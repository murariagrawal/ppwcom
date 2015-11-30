$(document).ready(function () {
	
	$('.panel div.clickable').on('click',  function (e) {
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
	

	$('.panel-heading').each(function() {
		 var $this = $(this);
		 if ($this.hasClass('panel-collapsed')) {
		        $this.parents('.panel').find('.panel-body').slideUp();		        
		        $this.find('i').removeClass('glyphicon-minus').addClass('glyphicon-plus');
		    }
	});
   
    
});
