<?php  include("../includes/connection.php");
 	
	
	$file_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/';
	
	$flag=$_POST["flag"];
	
	switch ($flag) {
		case '1':
			selectCategory($mysqli);
			break;
		default:
			# code...
			break;
	}
	
	function selectCategory($mysqli){
		$sql="select * from tbl_category";
		$result = $mysqli->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["category"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$mysqli->close();
	}
	
	
	
	
	?>