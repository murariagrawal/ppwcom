<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script
	src="js/mwf-core-ajax.js"></script>
	<script type="text/javascript">
	function getAddressDetails() {
		
		ajax.postForm("fetchDeliveryDetails?F=J", $("#deliveryForm")).done(function(data) {
			
			
			var ulFinal = "<ul style='list-style:none'>";
			var liFinal = "";
			var addressList= data.addressList;
			$.each(addressList, function(i, d) {			
				var divIndividual = "<div class='well well-sm'>";
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
					if(d.city) {
						liIndividual = liIndividual+"<span id='city'>"+d.city+"</span>";
					}
					if(d.state) {
						if(d.city) {
							liIndividual = liIndividual+", "
						}
						liIndividual = liIndividual+"<span id='state'>"+d.state+"</span>";
					}
					if(d.zipcode) {
						if(d.state) {
							liIndividual = liIndividual+", "
						}
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
			var addressDiv = $("#addressDiv")
			
			addressDiv.append(ulFinal);
			$( ".well-sm" ).on('click', function(e) {
				  
				  console.log($(this).find("#city"));
			});
			
        }).fail(function(data) {
        	
        	alert("failed");
        });
			
	
		
	}
	
	</script>
<title>Verify Order</title>
</head>
<body>
	<div class="container-fluid">
		<div class="panel-group">
			<div class="panel panel-primary">
				<div class="panel-heading">Delivery Details</div>
				<div class="panel-body">
					<form role="form" id="deliveryForm" action="verifyOrder" method="post">
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">Phone number:</span> 
								<input type="text" class="form-control" id="phoneNumber" onblur="getAddressDetails()"/>
							</div>
							<div class="input-group">
								<span class="input-group-addon">Address Line 1:</span>
								<input type="text" class="form-control" id="address">
							</div>
							<div class="input-group">
								<span class="input-group-addon">Address Line 1:</span>
								<input type="text" class="form-control" id="address">
							</div>
							<div class="input-group">
								<span class="input-group-addon">Address Line 1:</span>
								<input type="text" class="form-control" id="address">
							</div>
							<div class="input-group">
								<span class="input-group-addon">Landmark:</span> 
								<input type="text" class="form-control" id="landmark" />
							</div>
							<div class="input-group">
								<span class="input-group-addon">Pincode:</span>
								<input type="text" class="form-control" id="pincode" />
							</div>
							
							<div class="input-group">
								<label for="DeliverySlot">Select Delivery Slot:</label> 
								<select	class="form-control selectpicker" id="deliveryslot">
									<option>4-5</option>
									<option>5-6</option>
									<option>6-7</option>
									<option>7-8</option>
								</select>
							</div>
						</div>
						<input type="submit" class="btn btn-success" value="Submit">

					</form>

				</div>
			</div>
		</div>
		
		<div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      	<div class="modal-content">
			        <div class="modal-header" style="padding:10px 10px;">
			          <button type="button" class="close" data-dismiss="modal">&times;</button>
			          <h4><span class="glyphicon glyphicon-lock"></span> Select Delivery Address</h4>
			        </div>
			        <div class="modal-body" style="padding:10px 10px;">
			          <form role="form">
			            <div class="form-group" id="addressDiv">			              
			            </div>			            
			              <button type="submit" class="btn btn-success btn-block">Enter a new address</button>
			          </form>
			        </div>
			        <div class="modal-footer">			          
			        </div>		        
     	 		</div>      
    		</div>
  		</div>
	</div>
</body>
</html>