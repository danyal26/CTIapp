<?php

require "connection.php";

$monthYear = $_POST['monthYear'];
// $monthYear = '2016-01%';

$qry = "select * from year_planner where date like '$monthYear'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("year_event_id"=>$row[0], "year_event_type"=>$row[1], "date"=>$row[2], "details"=>$row[3]));
}

echo json_encode(array("year_planner"=>$response));

mysqli_close($connect);

?>
