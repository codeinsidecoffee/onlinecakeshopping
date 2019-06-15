<?php include("../includes/connection.php");
 	
		$flag=$_POST["flag"];
	
	switch ($flag) {
		case '1':
			selectDashboard($mysqli);
			break;
		case '2':
			fetchAllUserInfo($mysqli);
			break;
		case '3':
			changeUserStatus($mysqli);
			break;
		case '4':
			addNewProduct($mysqli);
			break;
		case '5':
			selectCategoryForPrize($mysqli);
			break;
		case '6':
			selectProductForPrize($mysqli);
			break;
		case '7':
			selectQtyTypeForPrize($mysqli);
			break;
		case '8':
			insertProductPrize($mysqli);
			break;
		case '9':
			addNewCategory($mysqli);
			break;
		case '10':
			deleteCategory($mysqli);
			break;
		case '11':
			updateCategory($mysqli);
			break;
		case '12':
			updateProductStatus($mysqli);
			break;
		case '13':
			fetchProductDetails($mysqli);
			break;
		case '14':
			updateProductAllDetails($mysqli);
			break;	
		case '15':
			deleteCurrentProduct($mysqli);
			break;	
		case '16':
			selectOrderDashboard($mysqli);
			break;
		case '17':
			fetchAllOrderDetails($mysqli);
			break;
		case '18':
			changeProductStatus($mysqli);
			break;
		case '19':
			changeCurrentOrderStatus($mysqli);
			break;
		case '20':
			cancelCurrentOrderStatus($mysqli);
			break;
		case '21':
			cancelCurrentProductStatus($mysqli);
			break;
		case '99':
				dummyData($mysqli);
				break;
		default:
			# code...
			break;
	}
	
	function cancelCurrentProductStatus($mysqli){
		$order_details_id=$_POST["order_details_id"];
		$cancelorder="Canceled";
				
		$qry_cancelorderStatus="update tbl_order_details set product_status='$cancelorder' where order_details_id=$order_details_id";
		
		if ($mysqli->query($qry_cancelorderStatus) === TRUE) {
		    echo json_encode( "ProductStatus Updated Sucessfully");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}

		$mysqli->close();
	}
	
	function cancelCurrentOrderStatus($mysqli){
			$order_status=$_POST["order_status"];
		$order_id=$_POST["order_id"];
		
				
		$qry_updateorderStatus="update tbl_order set order_status='$order_status' 
				where order_id=$order_id";
		
				
			
		if ($mysqli->query($qry_updateorderStatus) === TRUE) {
				if($order_status=="Canceled"){
					$qry_updateproductStatus="update tbl_order_details set product_status='$order_status',purchase_amount='000.00'
					where order_id='$order_id' and product_status IN ('In Process','Pending')" ;
					if ($mysqli->query($qry_updateproductStatus) === TRUE) {
						 
					}
				}
				
				
		   echo json_encode( "Cart Order successfully Updated");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		
		}
		$mysqli->close();
	}
	
	function changeCurrentOrderStatus($mysqli){
		$order_status=$_POST["order_status"];
		$order_id=$_POST["order_id"];
		
				
		$qry_updateorderStatus="update tbl_order set order_status='$order_status' 
				where order_id=$order_id";
			
		if ($mysqli->query($qry_updateorderStatus) === TRUE) {
				if($order_status=="Completed"){
					$qry_updateproductStatus="update tbl_order_details set product_status='$order_status' 
					where order_id='$order_id' and product_status IN ('In Process','Pending')" ;
					if ($mysqli->query($qry_updateproductStatus) === TRUE) {
						 
					}
				}
				if($order_status=="In Process"){
				$qry_updateproductStatus1="update tbl_order_details set product_status='$order_status' 
					where order_id='$order_id' and product_status='Pending'" ;
					if ($mysqli->query($qry_updateproductStatus1) === TRUE) {
						 
					}
				}
				
		   echo json_encode( "Cart Order successfully Updated");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		
		}
		$mysqli->close();
	}
	function changeProductStatus($mysqli){
		$product_status=$_POST["product_status"];
		$order_details_id=$_POST["order_details_id"];
		
				
		$qry_updateproductStatus="update tbl_order_details set product_status='$product_status' 
				where order_details_id=$order_details_id";
			
		if ($mysqli->query($qry_updateproductStatus) === TRUE) {
		    echo json_encode( "Product Order successfully Updated");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}
		$mysqli->close();
	}
	function fetchAllOrderDetails($mysqli){
		$order_status=$_POST["order_status"];
		
		if($order_status==""){
			$query_select_order_details="select o.order_id,o.order_amount,o.order_date,o.order_status,o.total_product,o.user_id,u.fname,u.lname,u.mobile,u.email,u.user_image from tbl_order o INNER JOIN  tbl_user u  on u.id=o.user_id where 
			order_id in (SELECT order_id FROM tbl_order_details group BY order_id)";
		}else{
			$query_select_order_details="select o.order_id,o.order_amount,o.order_date,o.order_status,o.total_product,o.user_id,u.fname,u.lname,u.mobile,u.email,u.user_image from tbl_order o INNER JOIN  tbl_user u  on u.id=o.user_id where 
			order_id in (SELECT order_id FROM tbl_order_details group BY order_id) and order_status='$order_status'";
		}
		
		$result_order_details = $mysqli->query($query_select_order_details);
					if ($result_order_details->num_rows > 0) {
			
						while($row_order_details = $result_order_details->fetch_assoc()) {
							 $order_id=$row_order_details['order_id'];
							  $user_id=$row_order_details['user_id'];
							
							 
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
														  'fname'=>$row_order_details['fname'],
														  'lname'=>$row_order_details['lname'],
														  'mobile'=>$row_order_details['mobile'],
														  'email'=>$row_order_details['email'],
														  'user_image'=>$row_order_details['user_image'],
														'product_details'=>$product_details,
														);
						}
						
						echo json_encode($finalresult);
					} else {
						echo json_encode("0 results");
					}
		
		$mysqli->close();
	}
	
		function selectOrderDashboard($mysqli){
		
		$qry_order="SELECT COUNT(*) as num FROM tbl_order";
		$total_order = mysqli_fetch_array(mysqli_query($mysqli,$qry_order));
		$total_order = $total_order['num'];


		$qry_order_pending="SELECT COUNT(*) as num FROM tbl_order where order_status='Pending'";
		$total_order_pending = mysqli_fetch_array(mysqli_query($mysqli,$qry_order_pending));
		$total_order_pending = $total_order_pending['num'];

		$qry_order_inprocess="SELECT COUNT(*) as num FROM tbl_order where order_status='In Process'";
		$total_order_inprocess = mysqli_fetch_array(mysqli_query($mysqli,$qry_order_inprocess));
		$total_order_inprocess = $total_order_inprocess['num'];
		
		$qry_order_cancel="SELECT COUNT(*) as num FROM tbl_order where order_status='Cancel'";
		$total_order_cancel = mysqli_fetch_array(mysqli_query($mysqli,$qry_order_cancel));
		$total_order_cancel = $total_order_cancel['num'];
		
		$qry_order_completed="SELECT COUNT(*) as num FROM tbl_order where order_status='Completed'";
		$total_order_completed = mysqli_fetch_array(mysqli_query($mysqli,$qry_order_completed));
		$total_order_completed = $total_order_completed['num'];


		$data= array($total_order, $total_order_pending,$total_order_inprocess,$total_order_cancel,$total_order_completed);
	
		    echo json_encode($data);
		$mysqli->close();
	}
	
	
	function deleteCurrentProduct($mysqli){
		
		$product_id=$_POST["product_id"];
		
		$qry_rate_id="Select product_rate_id AS current_product_rate_id from tbl_product_rate where product_id='$product_id'";
		$result_rate_id = $mysqli->query($qry_rate_id);
		if ($result_rate_id->num_rows > 0) {
    
		    while($row_rate_id = $result_rate_id->fetch_assoc()) {
				$product_rate_id=$row_rate_id['current_product_rate_id'];
			}
//		echo $product_rate_id;
		}
			
		$qry_deleteProductTable="delete From tbl_product where product_id='$product_id'";
		$qry_deleteProductType="delete From tbl_product_rate where product_id='$product_id'";
		$qry_deleteProductRate="delete From tbl_product_rate_details where product_rate_id='$product_rate_id'";
			
		if ($mysqli->query($qry_deleteProductTable) === TRUE &&
					$mysqli->query($qry_deleteProductType) === TRUE &&
							$mysqli->query($qry_deleteProductRate) === TRUE) {
			
			echo json_encode("Product Delete successfully");
			 
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}
	

		$mysqli->close();
	}
	
	function updateProductAllDetails($mysqli){
		$product_id=$_POST["product_id"];
		$product_title=$_POST["product_title"];
		$product_description=$_POST["product_description"];
		$cid=$_POST["cat_id"];
		$product_rate=$_POST["product_rate"];
		$product_qty_type=$_POST["product_qty_type"];
		$product_type_name=$_POST["product_type_name"];
		$product_qty_id="";
		$product_rate_id="";
		
		$qry_weightid="Select product_qty_id AS current_qty_type_id from tbl_product_qty where product_qty_type='$product_qty_type'";
		
		$result_weightid = $mysqli->query($qry_weightid);
		if ($result_weightid->num_rows > 0) {
    
		    while($row_weightid = $result_weightid->fetch_assoc()) {
				$product_qty_id=$row_weightid['current_qty_type_id'];
			}
//		echo $product_qty_id;
		}
		
		$qry_rate_id="Select product_rate_id AS current_product_rate_id from tbl_product_rate where product_id='$product_id'";
		$result_rate_id = $mysqli->query($qry_rate_id);
		if ($result_rate_id->num_rows > 0) {
    
		    while($row_rate_id = $result_rate_id->fetch_assoc()) {
				$product_rate_id=$row_rate_id['current_product_rate_id'];
			}
//		echo $product_rate_id;
		}
		
		
		$qry_updateProductTable="update tbl_product set product_title='$product_title',product_description='$product_description' 
									where product_id=$product_id";
									
		$qry_updateProductType="update tbl_product_rate set product_type_name='$product_type_name',cat_id='$cid' where product_rate_id=$product_rate_id";
		
		$qry_updateProductRate="update tbl_product_rate_details set product_rate='$product_rate' 
			where product_rate_id=$product_rate_id AND product_qty_id='$product_qty_id'";
		
			
		if ($mysqli->query($qry_updateProductType) === TRUE &&
					$mysqli->query($qry_updateProductTable) === TRUE &&
							$mysqli->query($qry_updateProductRate) === TRUE) {

			echo json_encode( "ProductStatus Updated Sucessfully");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}
		
		$mysqli->close();
	
		
		
	}
	
	function fetchProductDetails($mysqli){
		$product_id=$_POST["product_id"];
		
		$query1="Select p.product_id as product_id,p.product_title as product_title,p.product_image as product_image,
		c.cid as cid,c.category_name as category_name,c.category_image as category_image,r.product_type_name as product_type_name,
		p.product_description as product_description from tbl_product p CROSS JOIN tbl_category c 
		INNER JOIN tbl_product_rate r on p.product_id=r.product_rate_id AND c.cid=r.cat_id where p.product_id='$product_id'";
		
		 
		$result = $mysqli->query($query1);
		if ($result->num_rows > 0) {
    
		    while($row = $result->fetch_assoc()) {
				
				$query2="select q.product_qty_type as product_qty_type, d.product_rate as product_rate from tbl_product_qty q CROSS JOIN 
				tbl_product_rate r inner join tbl_product_rate_details d
				ON d.product_qty_id=q.product_qty_id AND r.product_rate_id=d.product_rate_id and r.product_id='$product_id'";
				$result2 = $mysqli->query($query2);
				if ($result2->num_rows > 0) {
					while($row2 = $result2->fetch_assoc()) {
						
					$cakerate[] = array(
							'pweight' => $row2['product_qty_type'],
							'prate' => $row2['product_rate']
							);
					}
				}
				
				
				$finalresult=array(
				'product_id'=>$row['product_id'],
				'product_title'=>$row['product_title'],
				'product_image'=>$row['product_image'],
				'cid'=>$row['cid'],
				'category_name'=>$row['category_name'],
				'category_image'=>$row['category_image'],
				'product_type_name'=>$row['product_type_name'],
				'cakerate'=>$cakerate,
				'product_description'=>$row['product_description'],
				);
		  }
		    echo json_encode($finalresult);
		} else {
		    echo json_encode("0 results");
		}
		
		$mysqli->close();
	}
	
	function updateProductStatus($mysqli){
		$product_id=$_POST["product_id"];
		
		$product_status=$_POST["product_status"];
		
		$qry_updateProductStatus="update tbl_product set product_status='$product_status' where product_id=$product_id";
			
		if ($mysqli->query($qry_updateProductStatus) === TRUE) {
		    echo json_encode( "ProductStatus Updated Sucessfully");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}

		$mysqli->close();
	
		
		
	}
	function updateCategory($mysqli){
		$cid=$_POST["cid"];
		
		$category_name=$_POST["category_name"];
		$category_image=rand(0,99999)."_".$_POST["category_name"].".jpg";
	
		$encodedImage=$_POST["encodedImage"];
		
		$output_file_path="../images/category/thumbs/".$category_image;
		$ifp = fopen( $output_file_path, 'wb'); 

		fwrite( $ifp, base64_decode( $encodedImage) );
		
		fclose( $ifp );    
		
		$qry_updatecategory="update tbl_category set category_name='$category_name',category_image='$category_image' where cid=$cid";
			
		if ($mysqli->query($qry_updatecategory) === TRUE) {
		    echo json_encode( "Category successfully Updated");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}

		$mysqli->close();
	
	}
	function deleteCategory($mysqli){
		
		$cid=$_POST["cid"];
		
	
			
		$qry_deletecategory="delete From tbl_category where cid='$cid'";
			
		if ($mysqli->query($qry_deletecategory) === TRUE) {
			
			echo json_encode("Category Delete successfully");
			 
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}
	

		$mysqli->close();
	}
	
	function addNewCategory($mysqli){
		
		$category_name=$_POST["category_name"];
		
		$category_image=rand(0,99999)."_".$_POST["category_name"].".jpg";
		$encodedImage=$_POST["encodedImage"];
		
		$output_file_path="../images/category/thumbs/".$category_image;
		$ifp = fopen( $output_file_path, 'wb'); 

		fwrite( $ifp, base64_decode( $encodedImage) );
		
		fclose( $ifp );    
	
			
		$qry_insertcategory="insert into tbl_category(category_name,category_image)
			values('$category_name','$category_image')";
			
		if ($mysqli->query($qry_insertcategory) === TRUE) {
			
			echo json_encode("New record Inserted successfully");
			 
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}
	

		$mysqli->close();
	}

	
	function insertProductPrize($mysqli){
		$cat_id=$_POST["cat_id"];
		$product_type_name=$_POST["product_type_name"];
		$product_id=$_POST["product_id"];
		$product_qty_id=$_POST["product_qty_id"];
		$product_rate=$_POST["product_rate"];
		
		$qry_select_rate_id="select product_rate_id AS crate_id from tbl_product_rate 
					where product_id='$product_id' AND product_type_name='$product_type_name' AND cat_id='$cat_id'";
	
			
		
		
					
					$result = $mysqli->query($qry_select_rate_id);
					if ($result->num_rows > 0) {
						 while($row = $result->fetch_assoc()) {
					
								$my_product_rate_id=$row['crate_id'];
								$qry_updateProduct="Insert into tbl_product_rate_details(product_rate_id,product_qty_id,product_rate)
								values('$my_product_rate_id','$product_qty_id','$product_rate')";
							
								if ($mysqli->query($qry_updateProduct) === TRUE) {
									echo json_encode( "Product Prize is added Sucessfully");
								} else {
									echo json_encode("Error: " . $qry_updateProduct . "<br>" . $mysqli->error);
								}
						
						}
					}else{
						echo json_encode("Error: " . $qry_select_rate_id . "<br>" . $mysqli->error);
					}
					
		

		$mysqli->close();
	}
	
	
	function selectQtyTypeForPrize($mysqli){
	
		$sql="SELECT * FROM tbl_product_qty";
		$result = $mysqli->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["qtytype"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$mysqli->close();
	}
	
	function selectProductForPrize($mysqli){
		$cat_id=$_POST["cat_id"];
		$product_type_name=$_POST["product_type_name"];
		$sql="SELECT * FROM tbl_product WHERE product_id IN (SELECT product_id from tbl_product_rate where cat_id='$cat_id' AND product_type_name='$product_type_name')";
		$result = $mysqli->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["product"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$mysqli->close();
	}
	function selectCategoryForPrize($mysqli){
		
		$product_type_name=$_POST["product_type_name"];
		$sql="SELECT * FROM tbl_category WHERE cid IN(SELECT cat_id FROM tbl_product_rate WHERE product_type_name='$product_type_name')";
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
	
	function addNewProduct($mysqli){
		$cat_id=$_POST["cat_id"];
		$product_title=$_POST["product_title"];
		
		$product_description=$_POST["product_description"];
		$product_image=rand(0,99999)."_".$_POST["product_title"].".jpg";;
		$encodedImage=$_POST["encodedImage"];
		$product_type_name=$_POST["product_type_name"];
		
		$output_file_path="../images/product/".$product_image;
		$ifp = fopen( $output_file_path, 'wb'); 

		fwrite( $ifp, base64_decode( $encodedImage) );
		
			fclose( $ifp );    
	
			
		$qry_insertproduct="insert into tbl_product(product_title,product_image,product_description)
			values('$product_title','$product_image','$product_description')";
			
		if ($mysqli->query($qry_insertproduct) === TRUE) {
			
			
			 $qry_select_proid="SELECT max(product_id) AS highest from tbl_product";
				$result = $mysqli->query($qry_select_proid);
					if ($result->num_rows > 0) {
						 while($row = $result->fetch_assoc()) {
					
							 $last_product_id=$row['highest'];
							 $qry_insert_catid="insert into tbl_product_rate(cat_id,product_id,product_type_name)
							 values('$cat_id','$last_product_id','$product_type_name')";	

								if ($mysqli->query($qry_insert_catid) === TRUE) {
										echo json_encode("New record Inserted successfully");
								} else {
									echo json_encode("Error: " . $qry_updateProduct . "<br>" . $mysqli->error);
								}
						 }	
						 
					}else{
						 echo json_encode("Error: " . $qry_select_proid . "<br>" . $mysqli->error);
						}
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}
	

		$mysqli->close();
	}
	
	function selectDashboard($mysqli){
		
		$qry_user="SELECT COUNT(*) as num FROM tbl_user where user_type=1";
		$total_user = mysqli_fetch_array(mysqli_query($mysqli,$qry_user));
		$total_user = $total_user['num'];
		
		$qry_cat="SELECT COUNT(*) as num FROM tbl_category";
		$total_category= mysqli_fetch_array(mysqli_query($mysqli,$qry_cat));
		$total_category = $total_category['num'];
		
		$qry_product="SELECT COUNT(*) as num FROM tbl_product";
		$total_product = mysqli_fetch_array(mysqli_query($mysqli,$qry_product));
		$total_product = $total_product['num'];

		
		
		$qry_order="SELECT COUNT(*) as num FROM tbl_order";
		$total_order = mysqli_fetch_array(mysqli_query($mysqli,$qry_order));
		$total_order = $total_order['num'];

		$data= array($total_user, $total_category,$total_product,$total_order);
	
		    echo json_encode($data);
		$mysqli->close();
	}
	
	function fetchAllUserInfo($mysqli){
		$sql="select * from tbl_user where user_type=1";
		$result = $mysqli->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["users"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$mysqli->close();
	}
	
	function changeUserStatus($mysqli){
		
		
		$email=$_POST["email"];
		$mobile=$_POST["mobile"];
		$status=$_POST["status"];
		$sql="update tbl_user set status='$status' where email='$email' and mobile='$mobile'" ;
		if ($mysqli->query($sql) === TRUE) {
		    echo json_encode( "Status successfully Change");
		} else {
		    echo json_encode("Error: " . $sql . "<br>" . $mysqli->error);
		}

		$mysqli->close();
		
	}
	
	function dummyData($mysqli){
		
			$product_id=$_POST["product_id"];
	
		$sql="select p.product_id,p.product_title,c.cid,c.category_name,c.category_image,p.product_image,
		p.product_description,p.product_status,r.product_type_name,d.product_rate,q.product_qty_type,
		r.product_rate_id,q.product_qty_id,d.product_rate_detail_id from 
		tbl_product p CROSS JOIN tbl_category c inner join 
		tbl_product_rate r on p.product_id=r.product_id AND c.cid=r.cat_id CROSS JOIN 
		tbl_product_qty q INNER JOIN tbl_product_rate_details d on r.product_rate_id=d.product_rate_id AND
		 q.product_qty_id=d.product_qty_id where p.product_id='$product_id'";
		 
		 
		$result = $mysqli->query($sql);
		if ($result->num_rows > 0) {
    // output data of each row
		    while($row = $result->fetch_assoc()) {
				
		      	$data["ProductDetails"][]=$row;
		    }
		    echo json_encode($data);
		} else {
		    echo json_encode("0 results");
		}
		$mysqli->close();
	}
	
	
	
	?>