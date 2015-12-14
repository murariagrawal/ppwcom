<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<script
	src="js/vendor/jquery.min.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
					<li class="active"><a>Home</a></li>
					<li><a href="#">About Us</a></li>
					<li ><a href="orderOnline">Order Online</a></li>
					<li><a href="#">Offers</a></li>
					<li><a href="#">Feedback</a></li>
					<li><a href="#">Contact Us</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container">
	
	<section class="introduction">
	<div id="myCarousel" class="carousel slide" data-interval="3000" style="width:100%" data-ride="carousel">
    	<!-- Carousel indicators -->
           
        <!-- Wrapper for carousel items -->
        <div class="carousel-inner">
            <div class="active item">
                <img src="fonts/panipuri1.jpg" alt="First Slide">
         		<div class="carousel-caption">
                  <h3>First slide label</h3>
                  <p></p>
                </div>
            </div>
            <div class="item">
                <img src="fonts/panipuri2.JPG" alt="Second Slide">
                <div class="carousel-caption">
                  <h3>Second slide label</h3>
                  <p></p>
                </div>
            </div>
            <div class="item">
                <img src="fonts/panipuri3.jpg" alt="Third Slide">
                <div class="carousel-caption">
                  <h3>Third slide label</h3>
                  <p></p>
                </div>
            </div>
        </div>
       
    </div>
    <div class="bg-overlay-screen"></div>
    <div class="text-on-image">
    	<div class="intro-caption">
    	<div class="home-text-heading"></div>
    	<div class="logo-home"><img src="fonts/logo.jpg"></div>
    	<a class="button home-order-btn" href="orderOnline">Order Now</a>
    	<div class="contact-no-home font_2">or Call at 9673142211</div>
    	</div>
    </div>
    </section>
</div>

</body>
</html>