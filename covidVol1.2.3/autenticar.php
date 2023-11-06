
<script>
	
	function hideReg(){
		document.getElementById("V").style.display = "none";
		document.getElementById("I").style.display = "none";
	}
	
	function showDiv(divname){
		hideReg();
		document.getElementById(divname).style.display = "block";
	}

	
	function obtemDistrito(){
		codigo = $("#district").val();
		$.ajax({
		  type: "POST",
		  url: 'getDistrict.php',
		  data: {distrito: codigo},
		  dataType: "html",
		  success: function (data){
				if (data) {
					document.getElementById('conselho').innerHTML = data;
				}
			}
		});
		
		atribuiClientePorTelefone();

	}	
	
	function pw(){
		registo.password2.setAttribute('pattern', registo.password1.value);
	}

	
	
	

	
	
	
	
</script>

<section class="page-section" id="entrar"> 
	
	<?php
		
		include'classes/vol.php';
		$handVol=new handlerVoluntario($db);
		
		
		$msgError = array(0=>"",1=>"Nome de utilizador em uso", 2=>"Email já em uso", 3=>"Ocorreu um erro inesperado", 4=>"Registado com sucesso", 5=>"Capcha invalido", 6=>"Cartão do Cidadão já foi utilizado!");
		$codError = 0;
		
		
		
		if (isset($_POST['login'])){
			if ($_POST['login']){
				
				$resultado = $handVol->login($db, $_POST['nomeU'], $_POST['senha']);
				if (!$resultado) {
					$erroLogin=true;
				}
					
				
			}
		}
		
		
		if (isset($_POST['volReg']) and ($_POST['volReg'] == 1)){
		
			// yaayyy vamos registar um voluntario
			$email = $_POST['email'];
			$username = $_POST['username'];
			$name = $_POST['name'];
			$surName = $_POST['surName'];
			$nasc = $_POST['nasc'];
			$pw = $_POST['password1'];
			$genero = $_POST['genero'];
			$district = (int)$_POST['district'];
			$conselho =  (int)$_POST['conselho'];
			$freguesia = $_POST['freguesia'];
			$telemovel = $_POST['Telemovel'];
			$cc = $_POST['Cartao'];
			$carta = (int)$_POST['carta'];
		
			if (!$handVol->is_username_available($db, $username)){
				$codError = 1;
			}
			if (!$handVol->is_email_available($db, $email)){
				$codError = 2;
			}
			if (md5($_POST['capcha']) != $_POST['capchaSol']){
				$codError = 5;
			}
			if (!$handVol->is_cc_available($db, $email)){
				$codError = 6;
			}
			
			if ($codError == 0){
				
				if ($handVol->createUser($username,$name,$surName,$email, $pw, $nasc, $genero, $conselho,$district, $freguesia, $telemovel, $cc, $carta, 1, 0)){
					$codError = 4;
				}else{
					$codError = 3;
				}
			}
			
			if ($codError != 4){
				?>
					<script>
						showDiv("V");
					</script>
				<?php
			}
			
		}
		
		if (isset($_POST['intiReg'])){
			// yaayyy vamos registar uma Instituicao
		}
		
		
		
	?>

   
	<div class="container" >
		<h2 class="text-center mt-0">Autenticar</h2>
		
		<hr class="" />
		<br>
		<div class="row">
			<div class="metade" style="border-style: none solid none none; border-color:#F4623A;">
			<center>
			
			<?php
				if ( (isset($erroLogin)) and $erroLogin ){
					//echo "<h2 class='text-center mt-0'>Erro ao autenticar, Verifique os dados novamente</h2>";
					
					?>
					
						<script>
							alert("erro");
						</script>
					
					<?php
				}
			?>
			
			
			<h3>Autenticar</h3> <br>
				<form method="POST" action="index.php#entrar">
					<input type="hidden" name="login" value="true">
					<label class="label ">Nome Utilizador: &nbsp;</label> <input type="text" value="" name="nomeU" required placeholder="Nome utilizador" ></input> <br>
					<label class="label">Senha: &nbsp;</label> <input type="password" value="" name="senha" required placeholder="Sua senha" ></input><br>
					<!--<label class="label">Instituição: </label> <input type="radio" value="0" name="tipo" required ></input><br>
					<label class="label">Voluntario: </label> <input type="radio" value="1" name="tipo" required ></input><br>-->
					<br>
					<input type="Submit" value="Entrar" class="btn2"></input>
					<input type="reset" value="Limpar" class="btn2"></input>
					

				</form>
			</center>
			
			</div>
			<div class="metade">
				<center><h3>Registar</h3><br>
				<button class="btn1" onclick="showDiv('V');" href="#V">Voluntario</button><br>
				<button class="btn1" onclick="showDiv('I');" href="#I">Instituição</button></center>
			</div>
		</div>
		
		<br>
		<br>
		<hr class="" />
		<br>
		
		<div class="vol" id="V" style="display:none">
			<center>
				<h2>Registo de voluntario</h2><br>
				<?php echo "<h2> ".$msgError[$codError]."</h2>"?>
				<form name="registo" method="POST" action="index.php" >
					<input type="email" class="formRegist" name="email"  value ="<?php echo (isset($email)? $email: "" ); ?>" required placeholder="Email"></input><br>
					<input type="text" class="formRegist" name="username" required placeholder="Nome de usuario"></input><br>
					<input type="text" class="formRegist" name="name" required placeholder="Nome real"></input><br>
					<input type="text" class="formRegist" name="surName" required placeholder="Sobrenome"></input><br>
					<br><label>Data de nascimento</label><br>
					<input type="date" id="datepicker" class="formRegist" name="nasc" required placeholder="Data de Nascimento"></input>
					
					
					
					<br>
					<input type="password" class="formRegist" name="password1" required placeholder="Password"></input><br>
					<input type="password" class="formRegist" name="password2" onchange="pw()" required placeholder="Confirmar Password"></input><br>
					<select name="genero" required  class="formRegist">
						<option value="" placeHolder="Genero">Genero</option>
						<option value="F">F</option>
						<option value="M">M</option>
						<option value="null">Prefiro não dizer</option>
					
					</select><br>
					
					<?php
						include'classes/district.php';
						$ctrldistrict=new handlerDistrict($db);
						
						
						$resultado=$ctrldistrict->getDistrict($db);
						echo "<select name='district' id='district' class='formRegist2' required >";
						echo "<option value=''> Distrito </option>";
						if ($resultado && $resultado->RecordCount()>0){
							
							while ($linha=$resultado->FetchRow()){
								echo "<option value='".$linha['id']."'  >".$linha['name']."</option>";
								
							}
						}
						echo "</select>";	
						echo "<button onclick ='obtemDistrito(); return false;' class='formButon'><img src='assets/img/lupa.png' width='22px' height='22px'/></button>";						
					
					?>
					<span id="conselho">
						<input type="hidden" value="" required></input>
					
					
					
					</span>
					<br><input type="text" class="formRegist" name="freguesia" required placeholder="Freguesia"></input><br>
					<input type="text" class="formRegist" name="Telemovel" required placeholder="Telemovel"></input><br>
					<input type="text" class="formRegist" name="Cartao"required placeholder="Cartão Cidadão"></input><br>
					<select name="carta" required class="formRegist">
						<option >Carta de Condução?</option>
						<option value="0"> Não</option>
						<option value="1"> Sim</option>
					</select>
					<br>
					
					<?php
					$capcha1 = rand ( 0 , 10 );
					$capcha2 = rand ( 0 , 10 );
					if (rand (1 , 2 ) == 1){
						$sol=md5($capcha1+$capcha2);
						$text= $capcha1 ." + ".$capcha2;
					}else{
						$sol=md5($capcha1-$capcha2);
						$text= $capcha1 ." - ".$capcha2;
					}
					//header("Content-Type: image/png");
					$im = @imagecreate(70, 35)or die("Cannot Initialize new GD image stream");
					$background_color = imagecolorallocate($im, 255, 255, 255);
					$text_color = imagecolorallocate($im, rand ( 100 , 255 ), rand ( 100 , 255 ), rand ( 100 , 255 ));
					imagestring($im, 6, 8, 8,  $text, $text_color);
					imagepng($im,"./images/png/pic.png");
					echo ("<img src='images/png/pic.png' style='border: 1px solid grey;'/> = <input type='text' class='formRegistCapcha' name='capcha' required placeholder='Prove que não é um robot'>");
					echo ("<input type='hidden' name='capchaSol' value='".$sol."' ");
					imagedestroy($im);
					unset($capcha2, $capcha1, $sol, $text);
					?>
					<br><br>
					<br><br>
					<input type="hidden" value="1" name="volReg" />
					<input type="Submit" class="btn2" value="Registar">
				</form>
			</center>
		
		</div>
		<div class="insti" id="I" style="display:none" >
		 asdoasdnoasdoaosdo
		
		
		
		</div>
		
		
	</div>
</section>