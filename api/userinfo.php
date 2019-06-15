<?php  include("../includes/connection.php");
 	
	
	$file_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/';
	
	$flag=$_POST["flag"];
	
	switch ($flag) {
		case '1':
			selectUser($mysqli);
			break;
		case '2':
			# update...
			updateUser($mysqli);
			break;
		case '3':
			# delete...
			resetUserPass($mysqli);
			break;
		case '4':
			checkUser($mysqli);
			break;
		case '5':
			forgotUserPass($mysqli);
		case '6':
			saveUser($mysqli);
		default:
			# code...
			break;
	}
	
	
	function saveUser($conn){
		$firstName=$_POST["fname"];
		$lastName=$_POST["lname"];
		$mobile=$_POST["mobile"];
		$email=$_POST["email"];
		$pass=$_POST["pass"];
		
		$sql="insert into tbl_user(fname, lname, mobile,email,pass) values ('$firstName','$lastName',$mobile,'$email','$pass')";

		if ($conn->query($sql) === TRUE) {
		    echo json_encode("New record created successfully");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $conn->error);
		}

		$conn->close();
	}
	
	function updateUser($conn){
		
		$id=$_POST["id"];
		$fname=$_POST["fname"];
		$lname=$_POST["lname"];
		$address=$_POST["address"];
		$country=$_POST["country"];
		$city=$_POST["city"];
		$state=$_POST["state"];
		$pincode=$_POST["pincode"];
		$user_image=rand(0,99999)."_".$_POST["user_image"];
		$encodedImage=$_POST["encodedImage"];
		
		$output_file_path="../images/userimages/".$user_image;
		$ifp = fopen( $output_file_path, 'wb'); 

		fwrite( $ifp, base64_decode( $encodedImage) );
		
	
    // clean up the file resource
			fclose( $ifp );    
		 
		$sql="update tbl_user set fname='$fname', lname='$lname', address='$address',shipping_address='$address',
					country='$country',city='$city',state='$state',pincode='$pincode',landmark='$city',
					user_image='$user_image'  where id=$id";
		if ($conn->query($sql) === TRUE) {
		    echo json_encode( "Record updated successfully");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $conn->error);
		}

		$conn->close();
	}


	function resetUserPass($conn){
		
		
		$email=$_POST["email"];
		$mobile=$_POST["mobile"];
		$pass=$_POST["pass"];
		$sql="update tbl_user set pass='$pass' where email='$email' and mobile='$mobile'" ;
		if ($conn->query($sql) === TRUE) {
		    echo json_encode( "Password successfully Change");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $conn->error);
		}

		$conn->close();
		
	}
	function selectUser($conn){
		$email=$_POST["email"];
		$pass=$_POST["pass"];
		$user_type=$_POST["user_type"];
		$sql="select * from tbl_user where email='$email' and pass='$pass' and user_type='$user_type'";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["users"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$conn->close();
	}
	
	function checkUser($conn){
		$email=$_POST["email"];
		$pass=$_POST["pass"];
		$sql="select * from tbl_user where email='$email' and pass='$pass' and status=1";
		$result=$conn->query($sql);
		if($result->num_rows>0){
			while($row=$result->fetch_assoc()){	
					$usertype=$row["user_type"];
				 echo json_encode($usertype." - login Successfull");
			}
			
		}else{
				
				 echo json_encode("login unSuccessfull");
				
			}
	}
	
	
		function forgotUserPass($conn){
		$email=$_POST["email"];
		$mobile=$_POST["mobile"];
		$sql="select * from tbl_user where email='$email' and mobile='$mobile'";
		$result=$conn->query($sql);
		if($result->num_rows>0){
			while($row=$result->fetch_assoc()){	
				 echo json_encode("Valid User");
			}
			
		}else{
				 echo json_encode("User Not Found");
			}
	}
	
	?>