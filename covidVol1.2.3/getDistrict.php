<?php 
include 'includes/config.php';   

include'classes/district.php';
$ctrldistrict=new handlerDistrict($db);


if (isset($_POST['distrito'])) {
	

	
	
	$resultado=$ctrldistrict->getConselho($db, $_POST['distrito']);
	
	echo"<br><select class='formRegist' name='conselho' required>";
	
	
	if ($resultado && $resultado->RecordCount()>0){
						
		while ($linha=$resultado->FetchRow()){
			echo "<option value='".$linha['id']."'  >".$linha['name']."</option>";
			
		}
	}else{
		echo "<option value=''  >Erro - Selecione um distrito primeiro!</option>";
	}
	
	
	
	
	echo "</select>";	
	
	

	
	
}

?>