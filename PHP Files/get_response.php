<?php

require "connection.php";

$student_id = $_POST['student_no'];
$event_id = $_POST['event_id'];

// $student_id = 'MB2015-0615';
// $event_id = 'EVT1';

$qry = "select * from Event_Responses where event_id = '$event_id' and student_id = '$student_id'";

$result = mysqli_query($connect, $qry);

if($result){
      if(mysqli_num_rows($result) > 0){
            echo 'true';
      } else {
            echo 'false';
      }
}

mysqli_close($connect);

?>
