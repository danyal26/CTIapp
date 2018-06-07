<?php

require "connection.php";

$student_id = $_POST['username'];
$pass = $_POST['password'];

$qry = "SELECT * FROM students WHERE student_id like '$student_id' and app_password like '$pass'";

$qryresult = mysqli_query($connect, $qry);

if(mysqli_num_rows($qryresult) > 0) {
      $response = array();

      while ($row = mysqli_fetch_array($qryresult)) {

            array_push($response,array("student_id"=>$row[0], "first_name"=>$row[1], "last_name"=>$row[3], "program_id"=>$row[13], "current_program_level"=>$row[14]));
      }

      echo json_encode(array("student_info"=>$response));
}
else {
      echo 'false';
}


mysqli_close($connect);

?>
