<?php  include("../includes/connection.php");
 	
	
	$file_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/';
	
	$flag=$_POST["flag"];
	
	switch ($flag) {
		case '1':
			# insert...
			selectCategoryProduct($mysqli);
			break;
		case '2':
			# update...
			selectAllProduct($mysqli);
			break;
		case '3':
			# update...
			selectAllProductList($mysqli);
			break;
		default:
			# code...
			break;
	}
	
	
	function selectAllProductList($mysqli){
		
		$query1="Select p.product_id as product_id,p.product_title as product_title,p.product_image as product_image,
		c.cid as cid,c.category_name as category_name,c.category_image as category_image,r.product_type_name as product_type_name,
		p.product_description as product_description,p.product_status as product_status from tbl_product p CROSS JOIN tbl_category c 
		INNER JOIN tbl_product_rate r on p.product_id=r.product_rate_id AND c.cid=r.cat_id";
		
		 
		$result = $mysqli->query($query1);
		if ($result->num_rows > 0) {
    
		    while($row = $result->fetch_assoc()) {
				$current_product_id=$row['product_id'];
				$query2="select q.product_qty_type as product_qty_type, d.product_rate as product_rate from tbl_product_qty q CROSS JOIN 
				tbl_product_rate r inner join tbl_product_rate_details d
				ON d.product_qty_id=q.product_qty_id AND r.product_rate_id=d.product_rate_id and r.product_id='$current_product_id'";
				$result2 = $mysqli->query($query2);
				if ($result2->num_rows > 0) {
					$DetailPrice=array();
					while($row2 = $result2->fetch_assoc()) {
						
						
				$DetailPrice[] = array(
							'pweight' => $row2['product_qty_type'],
							'prate' => $row2['product_rate']
							);
					}
				}
				
				
				$finalresult["ProductList"][]=array(
				'product_id'=>$row['product_id'],
				'product_title'=>$row['product_title'],
				'product_image'=>$row['product_image'],
				'cid'=>$row['cid'],
				'category_name'=>$row['category_name'],
				'category_image'=>$row['category_image'],
				'product_type_name'=>$row['product_type_name'],
				'DetailPrice'=>$DetailPrice,
				'product_description'=>$row['product_description'],
				'product_status'=>$row['product_status'],
				);
		  }
		   echo json_encode($finalresult);
		} else {
		    echo json_encode("0 results");
		}
		
		$mysqli->close();
	}
	
	function selectAllProduct($conn){
		$sql="select * from tbl_product";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["product"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$conn->close();
	}
	
	
	

	function selectCategoryProduct($conn){
		$cat_id=$_POST["cid"];
		$sql="select * from tbl_product where cat_id=$cat_id";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["product"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$conn->close();
	}
	
	
	
	?>