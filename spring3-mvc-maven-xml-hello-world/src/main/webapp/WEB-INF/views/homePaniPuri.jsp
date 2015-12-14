<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<script src="js/vendor/jquery.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="js/vendor/bootstrap.min.js"></script>
<script src="js/vendor/bootstrap-formhelpers.js"></script>
<link rel="stylesheet" href="css/bootstrap-formhelpers.min.css">
<link rel="stylesheet" href="css/state.css">
<link rel="stylesheet" href="css/cart.css">
<script src="js/mwf-core-ajax.js"></script>
<script src="js/common.js"></script>
<script src="js/deliveryaddress.js"></script>
<script src="js/steps.js"></script>
<script src="js/inputnumber.js"></script>

</head>
<body>
	<div class="navbar navbar-inverse cutomColorBg navbar-fixed-top"
		role="banner">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand">PaniPuri Bites</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="home">Home</a></li>
					<li><a href="#">About Us</a></li>
					<li class="active"><a href="#">Order Online</a></li>
					<li><a href="#">Offers</a></li>
					<li><a href="#">Feedback</a></li>
					<li><a href="#">Contact Us</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div id="myCarousel" class="carousel slide">
		<!-- Carousel indicators -->

		<!-- Wrapper for carousel items -->
		<div class="carousel-inner">
			<div class="active item"></div>
			<div class="item"></div>
			<div class="item"></div>
		</div>

	</div>
	<div class="container">
		<div class="row">
			<section>

				<div class="tab-content">
					<div id="errorDiv" class="alert alert-danger hide"></div>
					<div class="tab-pane item active" role="tabpanel" id="step1">
						<div class="row">
							<div class="jumbotron">
								<h1>Stall At Home</h1>
								<p>Book a stall at your home for a minimum consumption of
									100 Puris</p>

								<p>You can consume Sev puri, Dahi puri, Masala Puri or Pani
									puri whatever u want</p>
							</div>
						</div>
						<div class="customrow">
							<form role="form" id="homeForm" action="deliveryDetails"
								method="post">

								<div id="pageContentCarousel"
									class="col-sm-12 col-md-8 col-lg-8 carousel slide"
									data-interval="false">
									<div class="carousel-inner">
										<div class="active item">
											<div class="panel panel-primary">

												<div class="panel-body">
													<ul class="nav nav-tabs">
														<li class="active"><a data-toggle="tab"
															href="#individualOrder" id="individualOrdernav">Place
																Order</a></li>
														<li><a data-toggle="tab" id="comboOrdernav"
															href="#comboOrder">Combo Order</a></li>
														<li><a data-toggle="tab" id="partyOrdernav"
															href="#partyOrder">Stall At Home</a></li>
													</ul>
													<div class="tab-content">
														<div id="individualOrder" class="tab-pane fade in active">
															<div class="form-group">
																<table id="cart"
																	class="table table-hover table-condensed">
																	<thead>
																		<tr>
																			<th style="width: 45%">Panipuri Box</th>
																			<th style="width: 20%">Price</th>
																			<th style="width: 35%; padding-left: 40px;">Quantity</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${itemList}" var="item">
																			<tr>
																				<td data-th="Product">
																					<div class="row">
																						<div class="col-sm-2 hidden-xs">
																							<img src="http://placehold.it/100x100" alt="..."
																								class="img-responsive" />
																						</div>
																						<div class="col-sm-10">
																							<h4 class="nomargin">
																								<c:out value="${item.itemName}"></c:out>
																							</h4>
																							<p>
																								<a class="accordion-toggle"
																									data-toggle="collapse"
																									href="#collapseOne${item.itemId}"><span
																									class="label label-info">View Details</span></a>
																							</p>
																						</div>
																					</div>
																					<div id="collapseOne${item.itemId}"
																						class="accordion-body collapse">
																						<div class="accordion-inner">
																							<table class="table table-hover table-condensed">
																								<tr>
																									<td><c:forEach items="${item.itemDetails}"
																											var="itemDetail">
																											<label><c:out value="${itemDetail}"></c:out></label>
																											<br>
																										</c:forEach></td>
																								</tr>
																							</table>
																						</div>
																					</div>
																				</td>
																				<td data-th="Price"><span
																					id="itemPrice${item.itemId}"><c:out
																							value="${item.itemPrice}"></c:out></span></td>
																				<td data-th="Quantity" data-itemId="${item.itemId}"
																					data-itemName="${item.itemName}" colspan="1">
																					<div class="input-group">
																						<span class="input-group-btn quantity-counter">
																							<button type="button"
																								class="btn btn-danger btn-number"
																								data-type="minus"
																								data-field="item~${item.itemId}">
																								<span class="glyphicon glyphicon-minus"></span>
																							</button>
																						</span> <input type="text" id="quantity${item.itemId}"
																							name="item~${item.itemId}"
																							class="form-control input-number quantity-counter-input"
																							value="0" min="0" max="10"> <span
																							class="input-group-btn quantity-counter">
																							<button type="button"
																								class="btn btn-success btn-number"
																								data-type="plus"
																								data-field="item~${item.itemId}">
																								<span class="glyphicon glyphicon-plus"></span>
																							</button>
																						</span>
																					</div>

																				</td>



																			</tr>
																		</c:forEach>
																		<tr>
																			<td colspan="4"><span>Extra Stuffing</span></td>
																		</tr>
																		<c:forEach items="${stuffingList}" var="stuffing">
																			<tr>
																				<td data-th="Product">
																					<div class="row">
																						<div class="col-sm-2 hidden-xs">
																							<img src="http://placehold.it/100x100" alt="..."
																								class="img-responsive" />
																						</div>
																						<div class="col-sm-10">
																							<h4 class="nomargin">
																								<c:out value="${stuffing.toppingName}"></c:out>
																							</h4>
																						</div>
																					</div>
																				</td>
																				<td data-th="Price"><span
																					id="stuffingPrice${stuffing.toppingId}"><c:out
																							value="${stuffing.price}"></c:out></span></td>
																				<td data-th="QuantityStuffing"
																					data-itemName="${stuffing.toppingName}"
																					data-stuffingId="${stuffing.toppingId}" colspan="1">
																					<div class="input-group">
																						<span class="input-group-btn quantity-counter">
																							<button type="button"
																								class="btn btn-danger btn-number"
																								data-type="minus"
																								data-field="stuffing~${stuffing.toppingId}">
																								<span class="glyphicon glyphicon-minus"></span>
																							</button>
																						</span> <input type="text"
																							id="stuffingquantity${stuffing.toppingId}"
																							name="stuffing~${stuffing.toppingId}"
																							class="form-control input-number quantity-counter-input"
																							value="0" min="0" max="10"> <span
																							class="input-group-btn quantity-counter">
																							<button type="button"
																								class="btn btn-success btn-number"
																								data-type="plus"
																								data-field="stuffing~${stuffing.toppingId}">
																								<span class="glyphicon glyphicon-plus"></span>
																							</button>
																						</span>
																					</div>
																				</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
														<div id="comboOrder" class="tab-pane fade">
															<div class="form-group">
																<table id="cart"
																	class="table table-hover table-condensed">
																	<thead>
																		<tr>
																			<th style="width: 45%">Combo Box</th>
																			<th style="width: 20%">Price</th>
																			<th style="width: 35%">Quantity</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${comboList}" var="item">
																			<tr>
																				<td data-th="Product">
																					<div class="row">
																						<div class="col-sm-2 hidden-xs">
																							<img src="http://placehold.it/100x100" alt="..."
																								class="img-responsive" />
																						</div>
																						<div class="col-sm-10">
																							<h4 class="nomargin">
																								<c:out value="${item.itemName}"></c:out>
																							</h4>
																							<p>
																								<a class="accordion-toggle"
																									data-toggle="collapse"
																									href="#collapseOne${item.itemId}"><span
																									class="label label-info">View Details</span></a>
																							</p>
																						</div>
																					</div>
																					<div id="collapseOne${item.itemId}"
																						class="accordion-body collapse">
																						<div class="accordion-inner">
																							<table class="table table-hover table-condensed">
																								<tr>
																									<td><c:forEach items="${item.itemDetails}"
																											var="itemDetail">
																											<label><c:out value="${itemDetail}"></c:out></label>
																											<br>
																										</c:forEach></td>
																								</tr>
																							</table>
																						</div>
																					</div>
																				</td>
																				<td data-th="Price"><span
																					id="itemPrice${item.itemId}"><c:out
																							value="${item.itemPrice}"></c:out></span></td>
																				<td data-th="Quantity" data-itemId="${item.itemId}"
																					data-itemName="${item.itemName}" colspan="1">
																					<div class="input-group">
																						<span class="input-group-btn quantity-counter">
																							<button type="button"
																								class="btn btn-danger btn-number"
																								data-type="minus"
																								data-field="item~${item.itemId}">
																								<span class="glyphicon glyphicon-minus"></span>
																							</button>
																						</span> <input type="text" id="quantity${item.itemId}"
																							name="item~${item.itemId}"
																							class="form-control input-number quantity-counter-input"
																							value="0" min="0" max="10"> <span
																							class="input-group-btn quantity-counter">
																							<button type="button"
																								class="btn btn-success btn-number"
																								data-type="plus"
																								data-field="item~${item.itemId}">
																								<span class="glyphicon glyphicon-plus"></span>
																							</button>
																						</span>
																					</div>

																				</td>


																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
														<div id="partyOrder" class="tab-pane fade">
															<div class="customrow">
																<div class="form-group">
																	<table id="cart"
																		class="table table-hover table-condensed">
																		<thead>
																			<tr>
																				<th style="width: 45%">Stall</th>
																				<th style="width: 20%">No. Of Puri's</th>
																				<th style="width: 35%; padding-left: 40px">Price</th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach items="${partyItemList}" var="item">
																				<tr>
																					<td data-th="Product">
																						<div class="row">
																							<div class="col-sm-2 hidden-xs">
																								<img src="http://placehold.it/100x100" alt="..."
																									class="img-responsive" />
																							</div>
																							<div class="col-sm-10">
																								<h4 class="nomargin">
																									<c:out value="${item.itemName}"></c:out>
																								</h4>
																							</div>
																						</div>
																					</td>

																					<td data-itemId="${item.itemId}" colspan="1">
																						<div class="input-group">
																							<select name="partyQauntity"
																								id="partyQauntity${item.itemId}"
																								data-itemId="${item.itemId}"
																								data-itemName="${item.itemName}"
																								class="form-control">
																								<option data-price="0.00" value="">Select
																									Quantity</option>
																								<c:forEach items="${quantityList}"
																									var="quantity">
																									<option data-price="${quantity.price}"
																										value="${quantity.quantity}">${quantity.quantity}</option>
																								</c:forEach>
																							</select> <input type="hidden" id="quantity${item.itemId}"
																								name="item~${item.itemId}" />
																						</div>

																					</td>
																					<td data-th="Price"><span
																						id="itemPrice${item.itemId}">0.00</span></td>

																				</tr>
																			</c:forEach>
																		</tbody>

																	</table>
																</div>
															</div>
														</div>
														<input type="hidden" id="orderId" class="form-control"
															name="orderId" value="${orderId}" />
													</div>
												</div>
											</div>
										</div>
										<div class="item">
											<div id="deliveryDetailsDiv"></div>

										</div>
										<div class="item">
											<div id="verifyDetailsDiv"></div>

										</div>
										<div class="item">
											<h3>Complete</h3>
											<p>You have successfully ordered Your Pani Puri.</p>
											<div id="confirmDetailsDiv"></div>

										</div>
									</div>
								</div>
								<div class="col-md-4 col-lg-4 hidden-xs">
									<div class="panel panel-primary">
										<div class="panel-heading">Order Summary</div>
										<div class="panel-body">
											<div id="orderSummary" style="line-height: 50px;">

												<div id="itemQuantitySummaryNotAdded">
													<div class="row">
														<div class="col-md-12">Please add Items</div>

													</div>
												</div>
												<div class="row" class="hide" id="itemQuantitySummary">
												</div>
												<div class="row" class="hide" id="comboQuantitySummary">
												</div>
												<div class="row" class="hide" id="partyQuantitySummary">
												</div>
												<div class="row" class="hide" id="stuffiingQuantitySummary">
												</div>
												<div class="row" id="itemSummary">
													<div class="customrow">
														<div class="col-md-8">SubTotal</div>
														<div class="col-md-4">
															<label id="totalAmount1">0.00</label>
														</div>
													</div>

												</div>
												<div class="row" id="couponCode">
													<div class="col-md-12">
														<div class="input-group">
															<input type="text" class="form-control"
																placeholder="Coupon Code"
																aria-describedby="basic-addon2"> <span
																class="input-group-addon" id="basic-addon2">Apply<span
																class="glyphicon glyphicon-menu-right"></span></span>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="panel-footer">
											<div class="row show" id="continueToDeliverySummary">
												<div id="continueToDelivery"
													class="col-md-12 btn btn-success">
													Continue to DeliveryDetails<span
														class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>
											<div class="row hide" id="continueToVerifySummary">
												<div id="editOrder" class="col-md-6 btn btn-success">
													<span class="glyphicon glyphicon-menu-left"></span>Edit
													Order
												</div>
												<div id="continueToVerify" class="col-md-6 btn btn-success">
													Continue to Payment<span
														class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>
											<div class="row hide" id="continueToConfirmSummary">
												<div id="backToAddress" class="col-md-6 btn btn-success">
													<span class="glyphicon glyphicon-menu-left"></span>Back to
													Address
												</div>
												<div id="confirmOrderButton"
													class="col-md-6 btn btn-success">
													Confirm Order<span class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>
											<div class="row hide" id="placeNewOrderSummary">
												<div id="placeNewOrder" class="col-md-12 btn btn-success">
													Place A New Order<span
														class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>

										</div>
									</div>
								</div>
							</form>
						</div>
					</div>

					<div class="clearfix"></div>
				</div>

			</section>
		</div>
	</div>
	<div class="modal fade" tabindex="-1" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="padding: 10px 10px;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>
						<span class="glyphicon glyphicon-lock"></span> Select Delivery
						Address
					</h4>
				</div>
				<div class="modal-body" style="padding: 10px 10px;">
					<form role="form">
						<div class="form-group" id="addressDiv"></div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="enterNewAddress" class="btn btn-success">Enter
						a new address</button>
				</div>
			</div>
		</div>
		<div class="modal fade bs-example-modal-sm"
			style="top: 30%; left: 40%" data-backdrop="static"
			data-keyboard="false" id="spinnerModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div>
					<div>
						<span><i class="fa fa-spinner fa-spin"></i></span>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>