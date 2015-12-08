<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<script
	src="js/jquery.min.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
	<header></header>
	<section></section>
    <div id="myCarousel" class="carousel slide" data-interval="3000" data-ride="carousel">
    	<!-- Carousel indicators -->
        <ol >
            <li data-target="#myCarousel" class="active"></li>
            <li data-target="#myCarousel" ></li>
            <li data-target="#myCarousel" ></li>
        </ol>   
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
                <img src="fonts/panipuri1.jpg" alt="Second Slide">
                <div class="carousel-caption">
                  <h3>Second slide label</h3>
                  <p></p>
                </div>
            </div>
            <div class="item">
                <img src="fonts/panipuri2.jpg" alt="Third Slide">
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
    	<a class="button home-order-btn" href="http://wichplease.com/order-online">Order Now</a>
    	<div class="contact-no-home font_2">or Call at 9673142211</div>
    	</div>
    </div>
</div>

</body>
</html>