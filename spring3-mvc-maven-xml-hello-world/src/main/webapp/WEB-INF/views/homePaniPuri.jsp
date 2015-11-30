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
<script src="js/inputnumber.js"></script>

</head>
<body>
	<div class="navbar navbar-inverse cutomColorBg navbar-fixed-top" role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand">PaniPuri Bites</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="index.html">Home</a></li>
                        <li><a href="#">About me</a></li>
                        <li><a href="#">Feedback</a></li>
                        <li><a href="#">Resume</a></li>
                        <li><a href="#">Contact Us</a></li>
                    </ul>
                </div>
            </div>
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
					<div id="errorDiv" class="alert alert-danger hide"></div>
					<div class="tab-pane active" role="tabpanel" id="step1">
						<form role="form" id="homeForm" action="deliveryDetails" method="post">
							<div class="form-group">
								<table id="cart" class="table table-hover table-condensed">
									<thead>
										<tr>
											<th style="width:45%">Pani Puri Packs</th>
											<th style="width:13%">Price</th>
											<th style="width:20%">Quantity</th>											
											<th style="width:22%" class="text-center">Subtotal</th>
											
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
												<div class="input-group">
										          <span class="input-group-btn quantity-counter">
										              <button type="button" class="btn btn-danger btn-number"  data-type="minus" data-field="item~${item.itemId}">
										                <span class="glyphicon glyphicon-minus"></span>
										              </button>
										          </span>
										          <input type="text" id="quantity${item.itemId}" name="item~${item.itemId}" class="form-control input-number quantity-counter-input" value="0" min="0" max="10">
										          <span class="input-group-btn quantity-counter">
										              <button type="button" class="btn btn-success btn-number" data-type="plus" data-field="item~${item.itemId}">
										                  <span class="glyphicon glyphicon-plus"></span>
										              </button>
										          </span>
									     		</div>								
											
											</td>
											
											<td data-th="Subtotal" id="subTotalItem${item.itemId}" class="text-center">0.00</td>
											
										</tr>
										
										</c:forEach>
										<c:forEach items="${stuffingList}" var="stuffing">
										<tr>
											<td data-th="Product">
												<div class="row">
													<div class="col-sm-2 hidden-xs"><img src="http://placehold.it/100x100" alt="..." class="img-responsive"/></div>
													<div class="col-sm-10">
													<h4 class="nomargin"><c:out value="${stuffing.toppingName}"></c:out></h4>													
													</div>
												</div>													
											</td>
											<td data-th="Price" ><span id="stuffingPrice${stuffing.toppingId}"><c:out value="${stuffing.price}"></c:out></span></td>
											<td data-th="QuantityStuffing" data-stuffingId="${stuffing.toppingId}" colspan="1">
												<div class="input-group">
										          <span class="input-group-btn quantity-counter">
										              <button type="button" class="btn btn-danger btn-number"  data-type="minus" data-field="stuffing~${stuffing.toppingId}">
										                <span class="glyphicon glyphicon-minus"></span>
										              </button>
										          </span>
										          <input type="text" id="stuffingquantity${stuffing.toppingId}" name="stuffing~${stuffing.toppingId}" class="form-control input-number quantity-counter-input" value="0" min="0" max="10">
										          <span class="input-group-btn quantity-counter">
										              <button type="button" class="btn btn-success btn-number" data-type="plus" data-field="stuffing~${stuffing.toppingId}">
										                  <span class="glyphicon glyphicon-plus"></span>
										              </button>
										          </span>
									     		</div>								
											
											</td>
											
											<td data-th="Subtotal" id="subTotalStuffing${stuffing.toppingId}" class="text-center">0.00</td>
											
										</tr>
										
										</c:forEach>
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
								<input type="hidden" id="orderId"
												class="form-control" name="orderId" value="${orderId}" />
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
						<div id="verifyDetailsDiv"></div>
						<ul class="list-inline pull-right">
							<li><button type="button" class="btn btn-default prev-step">Previous</button></li>							
							<li><button type="button"
									class="btn btn-primary btn-info-full next-step">Save
									and continue</button></li>
						</ul>
					</div>
					<div class="tab-pane" role="tabpanel" id="complete">
						<h3>Complete</h3>
						<p>You have successfully ordered Your Pani Puri.</p>
						<div id="confirmDetailsDiv"></div>
						<ul class="list-inline pull-right">
							<li><button type="button" id="placeNewOrder"
									class="btn btn-primary btn-info-full">Place a new Order</button></li>
						</ul>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			</section>
		</div>
	</div>
		
</body>
</html>