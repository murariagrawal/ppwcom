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
	src="js/jquery.min.js"></script>
<link rel="stylesheet"
	href="css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-formhelpers.js"></script>
<script src="js/formoid-solid-blue.js"></script>
<link rel="stylesheet" href="css/bootstrap-formhelpers.min.css">
<link rel="stylesheet" href="css/state.css">
<link rel="stylesheet" href="css/cart.css">
<link rel="stylesheet" href="css/formoid-solid-blue.css">
<script
	src="js/mwf-core-ajax.js"></script>
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
						<form role="form" id="homeForm" action="deliveryDetails" method="post">
							<div class="form-group">
								<table id="cart" class="table table-hover table-condensed">
									<thead>
										<tr>
											<th style="width:50%">Pani Puri Packs</th>
											<th style="width:10%">Price</th>
											<th style="width:8%">Quantity</th>											
											<th style="width:22%" class="text-center">Subtotal</th>
											<th style="width:10%"></th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${itemList}" var="item">
										<tr>
											<td data-th="Product">
												<div class="row">
													<div class="col-sm-2 hidden-xs"><img src="http://placehold.it/100x100" alt="..." class="img-responsive"/></div>
													<div class="col-sm-10">
													<h4 class="nomargin"><c:out value="${item.itemName}"></c:out></h4>
													<p><a class="accordion-toggle" data-toggle="collapse"
													href="#collapseOne${item.itemId}"><span
														class="label label-info">View Details</span></a></p>
													</div>
												</div>
												<div id="collapseOne${item.itemId}"
																class="accordion-body collapse">
													<div class="accordion-inner">
														<table class="table table-hover table-condensed">
															<tr>
																<td>
																	<c:forEach items="${item.itemDetails}" var="itemDetail">
																		<label><c:out value="${itemDetail}"></c:out></label>
																		<br>
																	</c:forEach>
																</td>
															</tr>
														</table>
													</div>
												</div>	
											</td>
											<td data-th="Price" ><span id="itemPrice${item.itemId}"><c:out value="${item.itemPrice}"></c:out></span></td>
											<td data-th="Quantity" data-itemId="${item.itemId}" colspan="1">
												<input type="number" id="quantity${item.itemId}" class="form-control text-center" value="0">
												
											</td>
											
											<td data-th="Subtotal" id="subTotal${item.itemId}" class="text-center">0.00</td>
											<td data-th="">
												<button type="button" id="customize${item.itemId}" data-toggle="collapse" 
													data-target="#collapseTwo${item.itemId}" class="btn btn-success disabled">Customize</button>
												<div id="collapseTwo${item.itemId}"
																class="accordion-body collapse">
													<div class="accordion-inner">
														<table class="table table-hover table-condensed">
															<tr>
																<td>
																	<c:forEach items="${item.itemDetails}" var="itemDetail">
																		<label><c:out value="${itemDetail}"></c:out></label>
																		<br>
																	</c:forEach>
																</td>
															</tr>
														</table>
													</div>
												</div>	
											</td>
										</tr>
										
										</c:forEach>
										<tr>
											<td><input type="hidden" id="orderId"
												class="form-control" name="orderId" value="${orderId}" /></td>
										</tr>
									</tbody>
									<tfoot>
										<tr class="visible-xs">
											<td class="text-center"><strong>Total <label id="totalAmount1">0.00</label></strong></td>
										</tr>
										<tr>
											<td colspan="3" class="hidden-xs"></td>
											<td class="hidden-xs text-center"><strong>Total <label id="totalAmount2">0.00</label></strong></td>
											<td></td>
										</tr>
									</tfoot>
								</table>
							</div>
							
						</form>
						<ul class="list-inline pull-right">

							<li><button type="button" id="continueToDelivery" class="btn btn-success next-step">Continue to DeliveryDetails</button></li>
						</ul>

					</div>
					<div class="tab-pane" role="tabpanel" id="step2">
						<div id="deliveryDetailsDiv"></div>
						<ul class="list-inline pull-right">
							<li><button type="button" class="btn btn-default prev-step">Previous</button></li>
							<li><button type="button" id="continueToVerify" class="btn btn-primary next-step">Continue to Verify</button></li>
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