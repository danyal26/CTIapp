<?php

require "connection.php";

$student_id = $_POST['student_no'];
// $student_id = 'MB2015-0615';

$qry = "select SUM(Modules.cost) as cost from Modules
left join Students on Modules.program_id = Students.program_id
where Students.student_id = '$student_id' and Modules.program_level = Students.current_program_level";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("cost"=>$row[0]));
}

echo json_encode(array("cost"=>$response));

mysqli_close($connect);

?>
