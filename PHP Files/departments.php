<?php

require "connection.php";

$qry = "select * from departments";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("department_id"=>$row[0], "department_name"=>$row[1], "coordinator"=>$row[2], "department_type"=>$row[3]));
}

echo json_encode(array("departments"=>$response));

mysqli_close($connect);

?>
