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

		
			include_once('openconn.php');
			
			function test_input($data) {
				
				$data = trim($data);
				$data = stripslashes($data);
				$data = htmlspecialchars($data);
				return $data;
			}
			
			if ($_SERVER["REQUEST_METHOD"]== "POST") {
				
				$admin = test_input($_POST["Admin"]);
				$password = test_input($_POST["PasswordA"]);
				$stmt = $conn->prepare("SELECT * FROM Adm");
				$stmt->execute();
				$users = $stmt->fetchAll();
				
				foreach($users as $user) {
					
					if(($user['Admin'] == $admin) && 
						($user['PasswordA'] == $password)) {
							header("Location: AdminIndex.php");
					}
					else {
						echo "<script language='javascript'>";
						echo "alert('WRONG INFORMATION')";
						echo "</script>";
						die();
					}
				}
			}
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


		<section class="page-section" id="entrar">
			<div class="container">
				<hr class="" />
				<br>
				<div >
					
					<center>
					<h2>Autenticação</h2><br>
						<form method="POST" action="admin.php">
							<input type="hidden" name="loginA" value="true">
							<input type="text" value="" class="formRegist" name="Admin" required placeholder="Admin"></input><br><br>
							<input type="text" value="" class="formRegist" name="PasswordA" rqeuired placeholder="Password"></input><br><br>
							<input type="Submit" value="Entrar" class="btn2"></input>
						</form>
					</center>
					
				</div>
			</div>
		</section>
		
		
		
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
