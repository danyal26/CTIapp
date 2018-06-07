<?php

require "connection.php";

$student_id = $_POST['student_no'];
// $student_id = 'MB2015-0615';

$qry = "select payment_id, payment_amount, payment_date from Payments where student_id = '$student_id'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("payment_id"=>$row[0], "payment_amount"=>$row[1], "payment_date"=>$row[2]));
}

echo json_encode(array("payments"=>$response));

mysqli_close($connect);

?>
