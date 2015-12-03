<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="js/jquery.min.js"></script>
	<link rel="stylesheet" href="js/vegas/vegas.min.css">
	
<script src="js/vegas/vegas.min.js"></script>
<script src="js/vegas/start.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
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
                <img src="../images/slide1.png" alt="First Slide">
         		<div class="carousel-caption">
                  <h3>First slide label</h3>
                  <p></p>
                </div>
            </div>
            <div class="item">
                <img src="../images/slide2.png" alt="Second Slide">
                <div class="carousel-caption">
                  <h3>Second slide label</h3>
                  <p></p>
                </div>
            </div>
            <div class="item">
                <img src="../images/slide3.png" alt="Third Slide">
                <div class="carousel-caption">
                  <h3>Third slide label</h3>
                  <p></p>
                </div>
            </div>
        </div>
       
    </div>
</div>

</body>
</html>