<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>CovidRoads</title>
        <!-- logotipo-->
        <link rel="icon" type="image/x-icon" href="assets/img/logo.png" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Merriweather+Sans:400,700" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic" rel="stylesheet" type="text/css" />
        <!-- Third party plugin CSS-->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/magnific-popup.min.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet"/>
		
		<!-- Life-->
		<script src="js/jquery-3.6.0.js"></script>
		
		<?php 
			include('includes/config.php');
		?>
		
    </head>
    <body id="page-top" >
        <!-- Navigation-->
			<div style="background-color:brown;">
				<br>
				<br>
				<br>
				<br>
				<?php include("navBar.php");?>
			</div>
        <!-- Masthead-->

        <!-- About-->
		
		

			<section class="page-section" id="entrar"> 
				
				<?php
					
					include'classes/vol.php';
					$handVol=new handlerVoluntario($db);
				?>

			   
				<div class="container" >
					<h2>ZONA VOLUNTARIO</h2>
					<div class="row">
						
					</div>
					
					<hr class="" />
					<br>
					
						<div class="metade">
							<?php 
								echo "<h4>".$_SESSION['nome']."</h4>";
							?>
							<h6>Voluntario</h6>
						</div>
					
					
					
					
					
				</div>
			</section>
					 
		 
		 

        <!-- Contact-->

        <!-- Footer-->
        <footer class="bg-light py-5">
            <div class="container"><div class="small text-center text-muted">© 2021 CovidRoads. All rights reserved 
            <img src="assets/img/Ellipse">Termos<img src="assets/img/Ellipse">Privacidade</div></div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Third party plugin JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/jquery.magnific-popup.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>
