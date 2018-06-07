<?php

require "connection.php";

$student_id = $_POST['student_no'];
$program_level = $_POST['program_level'];

$qry = "select Modules.* from Marks left join Modules on Modules.module_id = Marks.module_id where Marks.student_id like '$student_id' and Modules.program_level like '$program_level'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("module_id"=>$row[0], "module_name"=>$row[1], "module_length"=>$row[2], "program_id"=>$row[3], "lecturer_id"=>$row[4], "nqf_level"=>$row[5], "cost"=>$row[6], "program_level"=>$row[7]));
}

echo json_encode(array("modules"=>$response));

mysqli_close($connect);

?>
