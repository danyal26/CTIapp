<?php

require "connection.php";

$student_id = $_POST['username'];
$pass = $_POST['password'];

// $student_id = 'LEC2000_001';
// $pass = 'pass';

$qry = "SELECT * FROM Lecturers WHERE lecturer_id like '$student_id' and app_password like '$pass'";

$qryresult = mysqli_query($connect, $qry);

if(mysqli_num_rows($qryresult) > 0) {
      $response = array();

      while ($row = mysqli_fetch_array($qryresult)) {

            array_push($response,array("lecturer_id"=>$row[0], "first_name"=>$row[1]));
      }

      echo json_encode(array("lecturer_info"=>$response));
}
else {
      echo 'false';
}


mysqli_close($connect);

?>
