<?php

require "connection.php";

$qry = "select Events.event_id, Events.event_name, Events.time, Events.date, Events.details, Departments.department_name, Events.venue from Events left join Departments on Events.department_id = Departments.department_id";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("event_id"=>$row[0], "event_name"=>$row[1], "time"=>$row[2], "date"=>$row[3], "details"=>$row[4], "department_name"=>$row[5], "venue"=>$row[6]));
}

echo json_encode(array("events"=>$response));

mysqli_close($connect);

?>
