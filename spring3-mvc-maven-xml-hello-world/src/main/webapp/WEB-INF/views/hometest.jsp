<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script src="js/bootstrap-formhelpers.js"></script>
<link rel="stylesheet" href="css/bootstrap-formhelpers.min.css">
<link rel="stylesheet" href="css/state.css">
<script src="js/steps.js"></script>
</head>
<body>
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
			aria-expanded="false">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="#">PaniPuri Bites</a>
	</div>
	<!-- Collect the nav links, forms, and other content for toggling -->
	<div class="navbar-collapse collapse" id="bs-example-navbar-collapse-1"
		aria-expanded="false" style="height: 1px;">
		<ul class="nav navbar-nav">
			<li><a href="#">About</a></li>
			<li><a href="#">Feedback</a></li>
			<li><a href="#">Contact</a></li>
		</ul>
	</div>
	<!-- /.navbar-collapse -->
	</div>

	<div class="container">
		<div class="row">
			<section>
			<div class="wizard">
				<div class="wizard-inner">
					<div class="connecting-line"></div>
					<ul class="nav nav-tabs" role="tablist">

						<li role="presentation" class="active"><a href="#step1"
							data-toggle="tab" aria-controls="step1" role="tab"
							title="Choose Quantity"> <span class="round-tab"> <i
									class="glyphicon glyphicon-shopping-cart"></i>
							</span>
						</a></li>

						<li role="presentation" class="disabled"><a href="#step2"
							data-toggle="tab" aria-controls="step2" role="tab"
							title="Delivery Details"> <span class="round-tab"> <i
									class="glyphicon glyphicon-map-marker"></i>
							</span>
						</a></li>
						<li role="presentation" class="disabled"><a href="#step3"
							data-toggle="tab" aria-controls="step3" role="tab"
							title="Verification And Payment"> <span class="round-tab">
									<i class="glyphicon glyphicon-edit"></i>
							</span>
						</a></li>

						<li role="presentation" class="disabled"><a href="#complete"
							data-toggle="tab" aria-controls="complete" role="tab"
							title="Complete"> <span class="round-tab"> <i
									class="glyphicon glyphicon-ok"></i>
							</span>
						</a></li>
					</ul>
				</div>


				<div class="tab-content">
					<div class="tab-pane active" role="tabpanel" id="step1">
						<h3>Select Pack</h3>

						<form role="form" action="deliveryDetails" method="post">
							<div class="form-group">
								<table class="table table-striped">

									<tbody>

										<c:forEach items="${itemList}" var="item">
											<tr>
												<td>
													<div>
														<label><c:out value="${item.itemName}"></c:out></label>
													</div>
												</td>
												<td>
													<div class="input-group">
														<span class="input-group-addon">Quantity</span> <input
															type="text" class="form-control bfh-number"
															name="item~${item.itemId}" />
													</div>
												</td>
												<td><a class="accordion-toggle" data-toggle="collapse"
													href="#collapseOne${item.itemId}"><span
														class="label label-info">View Details</span></a></td>
											</tr>
											<tr>
												<td colspan="3">
													<div id="collapseOne${item.itemId}"
														class="accordion-body collapse">
														<div class="accordion-inner">

															<c:forEach items="${item.itemDetails}" var="itemDetail">
																<label><c:out value="${itemDetail}"></c:out></label>
																<br>
															</c:forEach>
														</div>
													</div>
												</td>

											</tr>
										</c:forEach>
										<tr>
											<td><input type="hidden" id="orderId"
												class="form-control" name="orderId" value="${orderId}" /></td>
										</tr>
									<tbody>
								</table>
							</div>
							<input type="submit" class="btn btn-primary next-step"
								value="Continue to DeliveryDetails">
						</form>
						<ul class="list-inline pull-right">

							<li><button type="button" class="btn btn-primary next-step">Save
									and continue</button></li>
						</ul>

					</div>
					<div class="tab-pane" role="tabpanel" id="step2">
						<h3>Step 2</h3>
						<p>This is step 2</p>
						<ul class="list-inline pull-right">
							<li><button type="button" class="btn btn-default prev-step">Previous</button></li>
							<li><button type="button" class="btn btn-primary next-step">Save
									and continue</button></li>
						</ul>
					</div>
					<div class="tab-pane" role="tabpanel" id="step3">
						<h3>Step 3</h3>
						<p>This is step 3</p>
						<ul class="list-inline pull-right">
							<li><button type="button" class="btn btn-default prev-step">Previous</button></li>
							<li><button type="button" class="btn btn-default next-step">Skip</button></li>
							<li><button type="button"
									class="btn btn-primary btn-info-full next-step">Save
									and continue</button></li>
						</ul>
					</div>
					<div class="tab-pane" role="tabpanel" id="complete">
						<h3>Complete</h3>
						<p>You have successfully completed all steps.</p>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			</section>
		</div>
</body>
</html>