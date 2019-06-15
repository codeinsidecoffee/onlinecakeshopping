<?php  include("../includes/connection.php");
 	
	
	$file_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/';
	
	$flag=$_POST["flag"];
	
	switch ($flag) {
		case '1':
			addNewOrder($mysqli);
			break;
		case '2':
			fetchUserOrder($mysqli);
			break;
		case '3':
			placeNewOrder($mysqli);
			break;
		default:
			# code...
			break;
	}
	function placeNewOrder($mysqli){
		
		
		 $finalresult=$_POST['send_order_list'];
		$jsondata = json_decode($finalresult);
			$order_id='';
			$order_amount = $jsondata->order_amount;
			$order_date=date("Y-m-d");
			$total_product = $jsondata->total_product;
			$user_id = $jsondata->user_id;
			
			
			
		$query_insert_tbl_order="insert into tbl_order(order_amount,total_product,user_id,order_date) values('$order_amount','$total_product','$user_id','$order_date')";
		
				if($mysqli->query($query_insert_tbl_order) === TRUE){
	
				$query_select_tbl_order="select max(order_id) as order_id from tbl_order";
				$result_select_order_id = $mysqli->query($query_select_tbl_order);
				if ($result_select_order_id->num_rows > 0) {
			
						while($row_select_order_id = $result_select_order_id->fetch_assoc()) {
							 $order_id=$row_select_order_id['order_id'];
							
						}
				}
				
				
			foreach ($jsondata->product_details as $pds) {
		
				$product_id = $pds->product_id;
				$total_unit = $pds->total_unit;
				$unit_rate = $pds->unit_rate;
				$purchase_amount = $pds->purchase_amount;
				$cid = $pds->cid;
				$product_qty_type = $pds->product_qty_type;
				$receive_date = $pds->receive_date;
				$receive_time = $pds->receive_time;
				
				
						
				
					$query_select_tbl_product_qty="select product_qty_id as product_qty_id from tbl_product_qty where product_qty_type='$product_qty_type'";
					$result_select_product_qty_id = $mysqli->query($query_select_tbl_product_qty);
					if ($result_select_product_qty_id->num_rows > 0) {
			
						while($row_select_product_qty_id = $result_select_product_qty_id->fetch_assoc()) {
							 $product_qty_id=$row_select_product_qty_id['product_qty_id'];
							 
				 $query_insert_tbl_order_details="insert into 
				tbl_order_details(order_id,product_id,total_unit,unit_rate,purchase_amount,cid,product_qty_id,receive_date,receive_time)
			values('$order_id','$product_id','$total_unit','$unit_rate','$purchase_amount','$cid','$product_qty_id','$receive_date','$receive_time')"; 
							 if($mysqli->query($query_insert_tbl_order_details) === TRUE){
								$status=1;
							 }
							
						}
						
					}
			}
			echo json_encode("Order Placed successfully");
				
		}else{
				echo json_encode("Error: " . $query_insert_tbl_order_details . "<br>" . $mysqli->error);
			}
		
			
		$mysqli->close();
	}
	function fetchUserOrder($mysqli){
		$user_id=$_POST["user_id"];
		$query_select_order_details="select order_id,order_amount,order_date,order_status,total_product,user_id from tbl_order where 
		order_id in (SELECT order_id FROM tbl_order_details group BY order_id) and user_id='$user_id'";
		$result_order_details = $mysqli->query($query_select_order_details);
					if ($result_order_details->num_rows > 0) {
			
						while($row_order_details = $result_order_details->fetch_assoc()) {
							 $order_id=$row_order_details['order_id'];
							
							 
				 $query_select_order_details2="select od.product_id,p.product_title,p.product_image,od.cid,c.category_name,r.product_type_name,
				od.order_details_id,od.product_qty_id,q.product_qty_type, od.unit_rate,od.total_unit,od.purchase_amount,od.receive_date,od.	receive_time,od.product_status
				from tbl_order o INNER JOIN 
				tbl_order_details od on o.order_id=od.order_id INNER JOIN 
				tbl_category c on c.cid=od.cid INNER JOIN 
				tbl_product_qty q on q.product_qty_id=od.product_qty_id INNER JOIN 
				tbl_product p on p.product_id=od.product_id INNER JOIN
				tbl_product_rate r on r.product_id=p.product_id and r.cat_id=c.cid
							where o.user_id='$user_id' and o.order_id='$order_id'";
							
							$result_order_details2 = $mysqli->query($query_select_order_details2);
							if ($result_order_details2->num_rows > 0) {
										$product_details=array();
									while($row_order_details2 = $result_order_details2->fetch_assoc()) {
										
										
										$product_details[]=array(
												'order_details_id' => $row_order_details2['order_details_id'],
												'product_id' => $row_order_details2['product_id'],
												'product_title' => $row_order_details2['product_title'],
												'product_image' => $row_order_details2['product_image'],
												'cid' => $row_order_details2['cid'],
												'category_name' => $row_order_details2['category_name'],
												'product_type_name' => $row_order_details2['product_type_name'],
												'product_qty_id' => $row_order_details2['product_qty_id'],
												'product_qty_type' => $row_order_details2['product_qty_type'],
												'unit_rate' => $row_order_details2['unit_rate'],
												'total_unit' => $row_order_details2['total_unit'],
												'purchase_amount' => $row_order_details2['purchase_amount'],
												'receive_date' => $row_order_details2['receive_date'],
												'receive_time' => $row_order_details2['receive_time'],
												'product_status' => $row_order_details2['product_status']
												);
				
									}
							 
							}
							
										$finalresult["OrderDetails"][]=array(
														'order_id'=>$row_order_details['order_id'],
														 'order_date'=>$row_order_details['order_date'],
														 'order_amount'=>$row_order_details['order_amount'],
														 'order_status'=>$row_order_details['order_status'],
														  'total_product'=>$row_order_details['total_product'],
														  'user_id'=>$row_order_details['user_id'],
														'product_details'=>$product_details,
														);
						}
						
						echo json_encode($finalresult);
					} else {
						echo json_encode("0 results");
					}
		
		$mysqli->close();
	}

	function addNewOrder($mysqli){
		
		$order_amount=$_POST["order_amount"];
		$order_date=date("Y-m-d");
		$total_product=$_POST["total_product"];
		$user_id=$_POST["user_id"];
		
		
		$order_id='';
	
		$product_id=$_POST["product_id"];
		$total_unit=$_POST["total_unit"];
		$unit_rate=$_POST["unit_rate"];
		$purchase_amount=$_POST["purchase_amount"];
		$cid=$_POST["cid"];
		$product_qty_type=$_POST["product_qty_type"];
		$receive_date=$_POST["receive_date"];
		$receive_time=$_POST["receive_time"];
		$product_qty_id="";
		
		$dateTimestamp1 = strtotime($order_date); 
		$dateTimestamp2 = strtotime($receive_date); 
  
			if ($dateTimestamp1 > $dateTimestamp2) {
			echo json_encode("Please atleast next day delivery date");
		 }else{
			 
			 $query_select_product_qty_id="select product_qty_id as product_qty_id from tbl_product_qty where product_qty_type='$product_qty_type'";
			 	$result_product_qty_id = $mysqli->query($query_select_product_qty_id);
					if ($result_product_qty_id->num_rows > 0) {
			
						while($row_tbl_product_qty = $result_product_qty_id->fetch_assoc()) {
							 $product_qty_id=$row_tbl_product_qty['product_qty_id'];
						}
					}
		
		
		 $query_insert_order="insert into tbl_order(order_amount,order_date,total_product,user_id) 
				values('$order_amount','$order_date','$total_product','$user_id')";
		 if($mysqli->query($query_insert_order) === TRUE){
			 
			 $query_select_order_id="select max(order_id) as order_id from tbl_order";
			 	$result_select_order_id = $mysqli->query($query_select_order_id);
					if ($result_select_order_id->num_rows > 0) {
			
						while($row_tbl_order = $result_select_order_id->fetch_assoc()) {
								$order_id=$row_tbl_order['order_id'];
								
				$query_insert_order_details="insert into 
		tbl_order_details(order_id,product_id,total_unit,unit_rate,purchase_amount,cid,product_qty_id,receive_date,receive_time) 
					values('$order_id','$product_id','$total_unit','$unit_rate','$purchase_amount','$cid','$product_qty_id','$receive_date','$receive_time')";
							
							if($mysqli->query($query_insert_order_details) === TRUE){
								 echo json_encode("Order Placed successfully");
							 }else{
									$qry_deleteOrderTable="delete From tbl_order where product_id='$product_id'";
									$mysqli->query($qry_deleteOrderTable) === TRUE;
									echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
								}
					
						}
						
					}
				
		 }else{
			 
			 echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		 }
			 
		 
		 }
		
		$mysqli->close();
	}
	
	
	
	?>