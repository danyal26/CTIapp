<?php

require "connection.php";

$student_id = $_POST['student_no'];
// $student_id = 'MB2015-0615';

$qry = "select Assignments.*, Modules.module_name from Assignments
left join Modules on Assignments.module_id = Modules.module_id
left join Students on Modules.program_id = Students.program_id
where Students.student_id = '$student_id' and Students.current_program_level = Modules.program_level";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("assignment_id"=>$row[0], "module_id"=>$row[1], "assignment_number"=>$row[2], "start_date"=>$row[3], "end_date"=>$row[4], "module_name"=>$row[5]));
}

echo json_encode(array("assignments"=>$response));

mysqli_close($connect);

?>
