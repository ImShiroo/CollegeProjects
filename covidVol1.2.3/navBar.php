<nav class="navbar navbar-expand-lg navbar-light fixed-top py-3" id="mainNav">
	<div class="container">
		<a class="navbar-brand js-scroll-trigger" href="#page-top"><img alt="logo" src="assets/img/logo.png">&nbsp;&nbsp;&nbsp;&nbsp;CovidRoads</a>
		<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav ml-auto my-2 my-lg-0">
				<!--<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#about">Voluntarios</a></li>-->
				<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#">Sobre</a></li>
				<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#contact">Contactos</a></li>
				<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#services">FAQ</a></li>
				
				<?php
					/// Session acesso contem o tipo de utilizador, 1 para voluntario, 2 para instituiçao
					if (!isset($_SESSION['acesso'])){
				?>
						<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#login">Iniciar Sessão</a></li>
						<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#login">Criar Conta</a></li>
				
				<?php 
					}else if ($_SESSION['acesso'] == 1){ // VOL
				?>	
						<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#">O meu voluntariado</a></li>	
				<?php 
					}else if ($_SESSION['acesso'] == 2){ // INST
				?>	
						<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#registar">A minha instituição</a></li>
						<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#contact">Contactos</a></li>
				<?php 
					}
					if(isset($_SESSION['acesso'])){ // ambas partilham dos mesmos links
				?>	
					<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#notification">Notificações</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger" href="#definition">Definiçoes</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger" href="logout.php">Sair</a></li>
				<?php 
					}
				?>		
			</ul>
		</div>
	</div>
</nav>



