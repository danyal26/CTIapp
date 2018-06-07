<?php

require "connection.php";

$module_id = $_POST['module_id'];
$date = $_POST['date'];
$student_id = $_POST['student_id'];

// $module_id = 'C_ITDS221';
// $date = '2016-10-10';
// $student_id = 'MB2014-0203';

$qrycheck = "SELECT * FROM Attendance_List
WHERE module_id = '$module_id' AND student_id = '$student_id' AND date = '$date'";

$qryresult = mysqli_query($connect, $qrycheck);

if(mysqli_num_rows($qryresult) > 0) {
      echo 'scanned';
} else {
      $qry = "insert into Attendance_List(`module_id`, `student_id`, `date`) values ('$module_id', '$student_id', '$date')";

      if(mysqli_query($connect, $qry)){
            echo 'true';
      } else {
            echo 'false';
      }
}

mysqli_close($connect);
?>
