<?php

require "connection.php";

$department_id = $_POST['department_id'];
$student_no = $_POST['student_no'];
$message = $_POST['message'];

// $department_id = 'DEPT01';
// $student_no = 'MB2015-0615';
// $message = 'Hello. How are you?';

$qry = "insert into Query(department_id,student_id,message) values('$department_id', '$student_no', '$message')";

if(mysqli_query($connect, $qry)){
      echo 'true';
} else {
      echo 'false';
}

mysqli_close($connect);

?>
