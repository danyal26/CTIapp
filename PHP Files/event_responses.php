<?php

require "connection.php";

$student_id = $_POST['student_no'];
$event_id = $_POST['event_id'];
$response = $_POST['response'];

// $student_id = 'MB2015-0615';
// $event_id = 'EVT3';
// $response = 'false';

if ($response == 'true') {
      $qry = "insert into Event_Responses (event_id, student_id) values ('$event_id', '$student_id')";
}

if ($response == 'false') {
      $qry = "delete from Event_Responses where event_id = '$event_id' and student_id = '$student_id'";
}

if(mysqli_query($connect, $qry)){
      echo 'true';
} else {
      echo 'false';
}

mysqli_close($connect);

?>
