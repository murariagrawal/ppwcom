<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script data-main="js/mainconfig" src="js/vendor/require.js"></script>
<!-- Latest compiled and minified CSS -->
<script src="js/vendor/jquery.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="css/bootstrap-theme.min.css">
<link rel="stylesheet" href="css/bootstrap-formhelpers.min.css">
<link rel="stylesheet" href="css/state.css">
<link rel="stylesheet" href="css/cart.css">

</head>
<body>
	<div class="navbar navbar-inverse cutomColorBg navbar-fixed-top"
		style="margin-right: 20px" role="banner">
		<div class="container">
			<div class="navbar-header" style="margin-right: 5px;">
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

				<div class="tab-pane item active" role="tabpanel" id="step1">
					<div class="row">
						<div class="jumbotron hidden-xs">
							<h1>Stall At Home</h1>
							<p>Book a stall at your home for a minimum consumption of 100
								Puris</p>

							<p>You can consume Sev puri, Dahi puri, Masala Puri or Pani
								puri whatever u want</p>
						</div>
					</div>
					<div class="row hidden-md hidden-lg" style="height: 60px"></div>
					<div class="row" style="margin-left: 2px; margin-right: 2px;">
						<form role="form" id="homeForm" action="deliveryDetails"
							method="post">

							<div id="pageContentCarousel"
								class="col-sm-12 col-md-8 col-lg-8 carousel slide"
								data-interval="false">
								<div class="carousel-inner">
									<div id="errorDiv" class="alert alert-danger hide"></div>
									<div class="active item">
										<div class="panel panel-primary">
											<div class="panel-body" style="padding: 8px;">
												<ul class="nav nav-tabs navtabcustom">
													<li class="active"><a data-toggle="tab"
														class=".nav li a .customnav" href="#individualOrder"
														id="individualOrdernav">Place Order</a></li>
													<li><a data-toggle="tab" id="comboOrdernav"
														class="navtabcustom" href="#comboOrder">Combo Order</a></li>
													<li><a data-toggle="tab" id="partyOrdernav"
														class="navtabcustom" href="#partyOrder">Stall At Home</a></li>
												</ul>
												<div class="tab-content">
													<div id="individualOrder" class="tab-pane fade in active">
														<div class="form-group">
															<table id="cart"
																class="table table-hover table-condensed">
																<thead>
																	<tr>
																		<th style="width: 40%">Panipuri Box</th>
																		<th style="width: 20%">Price</th>
																		<th style="width: 40%; padding-left: 40px;">Quantity</th>
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
																							<a data-toggle="modal"
																								data-target="#collapseOne${item.itemId}"> <span>View
																									Details</span>
																							</a>
																						</p>
																					</div>
																				</div>
																				<div id="collapseOne${item.itemId}" tabindex="-1"
																					data-backdrop="false" class="modal fade"
																					role="dialog">
																					<div class="modal-dialog">

																						<!-- Modal content-->
																						<div class="modal-content">
																							<div class="modal-header">
																								<button type="button" class="close"
																									data-dismiss="modal">&times;</button>
																								<h4 class="modal-title">${item.itemName}Details</h4>
																							</div>
																							<div class="modal-body">
																								<table class="table table-hover table-condensed">
																									<tr>
																										<td><c:forEach
																												items="${item.itemDetails}" var="itemDetail">
																												<label><c:out value="${itemDetail}"></c:out></label>
																												<br>
																											</c:forEach></td>
																									</tr>
																								</table>
																							</div>
																							<div class="modal-footer">
																								<button type="button" class="btn btn-default"
																									data-dismiss="modal">Close</button>
																							</div>
																						</div>

																					</div>

																				</div>
																			</td>
																			<td data-th="Price"><span
																				id="itemPrice${item.itemId}"><c:out
																						value="${item.itemPrice}"></c:out></span></td>
																			<td data-th="Quantity" data-itemId="${item.itemId}"
																				data-itemName="${item.itemName}" colspan="1">
																				<div class="input-group" style="margin-left: 30px;">
																					<input type="hidden" name="item~${item.itemId}">
																					<span data-field="item~${item.itemId}"
																						class="quantity-counter"> <span
																						class="glyphicon glyphicon-minus"> </span>
																					</span> <span data-field="item~${item.itemId}"
																						id="item~${item.itemId}"
																						class="quantity-counter-input"
																						style="margin: 15px;">0</span> <span
																						data-field="item~${item.itemId}"
																						class="quantity-counter"> <span
																						class="glyphicon glyphicon-plus"></span>

																					</span>
																				</div>
																			</td>
																		</tr>
																	</c:forEach>
																	<tr>
																		<td colspan="4"><span><strong>Extra
																					Stuffing</strong></span></td>
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
																				<div class="input-group" style="margin-left: 30px;">
																					<input type="hidden"
																						name="stuffing~${stuffing.toppingId}"> <span
																						data-field="stuffing~${stuffing.toppingId}"
																						class="quantity-counter"> <span
																						class="glyphicon glyphicon-minus"></span>
																					</span> <span data-field="stuffing~${stuffing.toppingId}"
																						id="stuffing~${stuffing.toppingId}"
																						class="quantity-counter-input"
																						style="margin: 15px;">0</span> <span
																						data-field="stuffing~${stuffing.toppingId}"
																						class="quantity-counter"> <span
																						class="glyphicon glyphicon-plus"></span>

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
																		<th style="width: 40%">Combo Box</th>
																		<th style="width: 20%">Price</th>
																		<th style="width: 40%; padding-left: 40px;">Quantity</th>
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
																							<a class="accordion-toggle" data-toggle="modal"
																								href="#collapseOne${item.itemId}"> <span>View
																									Details</span>
																							</a>
																						</p>
																					</div>
																				</div>
																				<div id="collapseOne${item.itemId}" tabindex="-1"
																					data-backdrop="false" class="modal fade"
																					role="dialog">
																					<div class="modal-dialog">

																						<!-- Modal content-->
																						<div class="modal-content">
																							<div class="modal-header">
																								<button type="button" class="close"
																									data-dismiss="modal">&times;</button>
																								<h4 class="modal-title">${item.itemName}Details</h4>
																							</div>
																							<div class="modal-body">
																								<table class="table table-hover table-condensed">
																									<tr>
																										<td><c:forEach
																												items="${item.itemDetails}" var="itemDetail">
																												<label><c:out value="${itemDetail}"></c:out></label>
																												<br>
																											</c:forEach></td>
																									</tr>
																								</table>
																							</div>
																							<div class="modal-footer">
																								<button type="button" class="btn btn-default"
																									data-dismiss="modal">Close</button>
																							</div>
																						</div>

																					</div>

																				</div>
																			</td>
																			<td data-th="Price"><span
																				id="itemPrice${item.itemId}"><c:out
																						value="${item.itemPrice}"></c:out></span></td>
																			<td data-th="Quantity" style="padding: 2px;"
																				data-itemId="${item.itemId}"
																				data-itemName="${item.itemName}" colspan="1">
																				<div class="input-group" style="margin-left: 30px;">
																					<input type="hidden" name="item~${item.itemId}">
																					<span data-field="item~${item.itemId}"
																						class="quantity-counter"> <span
																						class="glyphicon glyphicon-minus"></span>
																					</span> <span id="item~${item.itemId}"
																						data-field="item~${item.itemId}"
																						class="quantity-counter-input"
																						style="margin: 15px;">0</span> <span
																						class="quantity-counter"> <span
																						data-field="item~${item.itemId}"
																						class="glyphicon glyphicon-plus"></span>

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
																							<c:forEach items="${quantityList}" var="quantity">
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
										<div id="confirmDetailsDiv"></div>

									</div>
								</div>
							</div>
							<div class="hidden-md hidden-lg col-sm-12 col-xs-12">
								<div class="col-sm-8 col-xs-8">Total</div>
								<div class="col-sm-4 col-xs-4">
									<label class="totalAmount1" id="totalAmount1">0.00</label>
								</div>
							</div>
							<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12">
								<div class="panel panel-primary">
									<div class="panel-heading hidden-xs hidden-sm">Order
										Summary</div>
									<div class="panel-heading hidden-md hidden-lg">
										Order Summary <a data-toggle="collapse"
											data-target="#viewDetails"> </a>

									</div>
									<div id="viewDetails"
										class="panel-collapse collapse in orderSummary panel-body">
										<div id="orderSummary" style="line-height: 30px;">

											<div id="itemQuantitySummaryNotAdded">
												<div class="row">
													<div class="col-md-12">Please add Items</div>

												</div>
											</div>
											<div class="row" class="hide" id="itemQuantitySummary"></div>
											<div class="row" class="hide" id="comboQuantitySummary"></div>
											<div class="row" class="hide" id="partyQuantitySummary"></div>
											<div class="row" class="hide" id="stuffiingQuantitySummary"></div>
											<div class="row" id="itemSummary">
												<div class="customrow"
													style="border-bottom: 1px solid; margin-right: 0px"></div>
												<div class="customrow">

													<div class="customrow">
														<div class="col-md-6 col-lg-6 col-xs-6">SubTotal</div>
														<div class="col-md-2 col-lg-2 col-xs-2"></div>
														<div class="col-md-2 col-lg-2 col-xs-2"
															style="margin-left: 10px">
															<label class="totalAmount" id="subTotal">0.00</label>
														</div>
													</div>
													<div class="customrow">
														<div class="col-md-8 col-lg-8 col-xs-8">Delivery
															Charges</div>
														<div class="col-md-2 col-lg-2 col-xs-2"></div>
														<div class="col-md-2 col-lg-2 col-xs-2"
															style="margin-left: 10px">
															<label id="deliveryAmount">0.00</label>
														</div>
													</div>
													<div class="customrow">
														<div class="col-md-6 col-lg-6 col-xs-6">Total</div>
														<div class="col-md-2 col-lg-2 col-xs-2"></div>
														<div class="col-md-2 col-lg-2 col-xs-2"
															style="margin-left: 10px">
															<label class="totalAmount" id="totalAmount">0.00</label>
														</div>
													</div>

												</div>
												<div class="row hide" id="couponCode">
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
										<div class="panel-footer hidden-xs hidden-sm"
											style="padding: 0px;">
											<div class="row show continueToDeliverySummary"
												id="continueToDeliverySummary">
												<div id="continueToDelivery"
													class="continueToDelivery col-md-12 btn btn-success">
													Continue to DeliveryDetails<span
														class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>
											<div class="row hide continueToVerifySummary"
												id="continueToVerifySummary">
												<div id="editOrder"
													class="editOrder col-md-6 btn btn-success">
													<span class="glyphicon glyphicon-menu-left"></span>Edit
													Order
												</div>
												<div id="continueToVerify"
													class="continueToVerify col-md-6 btn btn-success">
													Continue to Payment<span
														class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>
											<div class="row hide continueToConfirmSummary"
												id="continueToConfirmSummary">
												<div id="backToAddress"
													class="backToAddress col-md-6 btn btn-success">
													<span class="glyphicon glyphicon-menu-left"></span>Back to
													Address
												</div>
												<div id="confirmOrderButton"
													class="confirmOrderButton col-md-6 btn btn-success">
													Confirm Order<span class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>
											<div class="row hide placeNewOrderSummary"
												id="placeNewOrderSummary">
												<div id="placeNewOrder"
													class="placeNewOrder col-md-12 btn btn-success">
													Place A New Order<span
														class="glyphicon glyphicon-menu-right"></span>
												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="row hidden-lg hidden-md">
						<div class="row show continueToDeliverySummary"
							style="width: 100%; margin-left: 10%"
							id="continueToDeliverySummary">
							<div id="continueToDelivery" style="width: 80%;"
								class="continueToDelivery col-sm-12 btn btn-success">
								Continue to DeliveryDetails<span
									class="glyphicon glyphicon-menu-right"></span>
							</div>

						</div>
						<div class="row hide continueToVerifySummary"
							style="width: 100%; margin-left: 10%"
							id="continueToVerifySummary">
							<div id="editOrder" style="width: 40%;"
								class="editOrder col-md-6 btn btn-success">
								<span class="glyphicon glyphicon-menu-left"></span>Edit Order
							</div>
							<div id="continueToVerify" style="width: 40%;"
								class="continueToVerify col-md-6 btn btn-success">
								Continue to Payment<span class="glyphicon glyphicon-menu-right"></span>
							</div>
						</div>
						<div class="row hide continueToConfirmSummary"
							style="width: 100%; margin-left: 10%"
							id="continueToConfirmSummary">
							<div id="backToAddress" style="width: 40%;"
								class="backToAddress col-md-6 btn btn-success">
								<span class="glyphicon glyphicon-menu-left"></span>Back to
								Address
							</div>
							<div id="confirmOrderButton" style="width: 40%"
								class="confirmOrderButton col-md-6 btn btn-success">
								Confirm Order<span class="glyphicon glyphicon-menu-right"></span>
							</div>
						</div>
						<div class="row hide placeNewOrderSummary"
							style="width: 100%; margin-left: 10%" id="placeNewOrderSummary">
							<div id="placeNewOrder" style="width: 80%;"
								class="placeNewOrder col-md-12 btn btn-success">
								Place A New Order<span class="glyphicon glyphicon-menu-right"></span>
							</div>
						</div>


					</div>
				</div>

				<div class="clearfix"></div>


			</section>
		</div>
	</div>
	<div class="modal fade bs-example-modal-sm" data-backdrop="static"
		id="areaModal" tabindex="-1" role="dialog"
		aria-labelledby="Select Delivery Area" aria-hidden="true">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="padding: 10px 10px;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>
						<span class="glyphicon glyphicon-lock"></span>Select Delivery Area
					</h4>
				</div>
				<div class="modal-body" style="padding: 10px 10px;">
					<form role="form" id="areaForm">
						<input type="hidden" id="selectedAreaId" class="form-control"
							name="selectedAreaId" />
						<div class="form-group" id="areaDivElements">
							<div class="row customrow">
								<div class="col-xs-12 col-md-6 customformgroup">
									<input class="form-control customformelements" type="text"
										maxlength="10" name="phoneNumber" id="phoneNumberInit"
										placeholder="Phone Number" value="" /><span
										class="icon-place"></span>
								</div>
							</div>
							<div class="row customrow">
								<div class="col-xs-12 col-md-6 control-group customformgroup">
									<div class="controls" id="areaAddrDiv" class="areaAddrDiv">
										<input type="text"
											class="areaAddr form-control customformelements"
											id="areaAddr" name="areaAddr"
											placeholder="Search Area/Zipcode" />
									</div>
									<div id="areaMenu" class="areaMenu list-group hide"></div>
								</div>
								<div class="col-xs-12 col-md-6 control-group customformgroup">
									<div class="controls" id="subAreaAddrDiv"
										class="subAreaAddrDiv">
										<input type="text"
											class="subAreaAddr form-control customformelements"
											id="subAreaAddr" name="subAreaAddr"
											placeholder="Search Sub Area" />
									</div>
									<div id="subAreaMenu" class="subAreaMenu list-group hide"></div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="enterArea" class="btn btn-success">Order
						Now</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" tabindex="-1" id="myModal" role="dialog">
		<div class="modal-dialog" style="width: 80%">

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
</body>
</html>