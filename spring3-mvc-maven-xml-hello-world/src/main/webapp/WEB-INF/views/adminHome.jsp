<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
	<link rel="stylesheet" href="css/admin.css">
<title>Admin Home</title>
</head>
<body>


	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-bookmark"></span> Quick
							Shortcuts
						</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-6 col-md-6">
								<a href="#" class="btn btn-danger btn-lg" role="button"><span
									class="glyphicon glyphicon-list-alt"></span> <br />Orders</a> <a
									href="#" class="btn btn-warning btn-lg" role="button"><span
									class="glyphicon glyphicon-time"></span> <br />Delivery</a> <a
									href="#" class="btn btn-primary btn-lg" role="button"><span
									class="glyphicon glyphicon-signal"></span> <br />Reports</a> <a
									href="#" class="btn btn-primary btn-lg" role="button"><span
									class="glyphicon glyphicon-comment"></span> <br />Crew Management</a>
							</div>
							<div class="col-xs-6 col-md-6">
								<a href="#" class="btn btn-success btn-lg" role="button"><span
									class="glyphicon glyphicon-folder-close"></span> <br />Item</a> <a
									href="#" class="btn btn-info btn-lg" role="button"><span
									class="glyphicon glyphicon-tint"></span> <br />Stuffing</a> <a
									href="#" class="btn btn-primary btn-lg" role="button"><span
									class="glyphicon glyphicon-user"></span> <br />User</a> <a
									href="#" class="btn btn-primary btn-lg" role="button"><span
									class="glyphicon glyphicon-map-marker"></span> <br />Available Delivery Area</a>
							</div>
						</div>
						<a href="http://www.jquery2dotnet.com/"
							class="btn btn-success btn-lg btn-block" role="button"><span
							class="glyphicon glyphicon-globe"></span> Website</a>
					</div>
				</div>
			</div>
		</div>
		<div>
			<form method="post" action="addItem">
			<table>
				<tbody>
					<tr>
					<td><label>Item Name:</label></td>
						<td>
							
							<input id="itemName" name="itemName" type="text" >
						</td>
					</tr>
					<tr>
					<td><label>Item Price:</label></td>
						<td>
							
							<input id="itemPrice" name="itemPrice"  type="text" >
						</td>
					</tr>
					<tr>
					<td><label>Item Details</label></td>
						<td>
							
							<input id="itemDetails" name="itemDetails"  type="text" >
						</td>
					</tr>
					<td><label>Item Details</label></td>
						<td>
							
							<input  type="submit" value="submit">
						</td>
					</tr>
					
				</tbody>
			</table>
			</form>
		</div>
	</div>

</body>
</html>