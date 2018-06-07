<?php

require "connection.php";

$student_id = $_POST['student_no'];

$qry = "select distinct Modules.program_level from Marks left join Modules on Modules.module_id = Marks.module_id where Marks.student_id like '$student_id' order by program_level";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("program_level"=>$row[0]));
}

echo json_encode(array("levels"=>$response));

mysqli_close($connect);

?>
