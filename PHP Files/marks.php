<?php

require "connection.php";

$student_id = $_POST['student_no'];
$module_id = $_POST['mod'];


$qry = "select Modules.module_name, Marks.module_id, Marks.test1_mark, Marks.test2_mark, Marks.assignment_mark, Marks.project_mark, Marks.exam_mark from Marks left join Modules on Modules.module_id = Marks.module_id where Marks.student_id like '$student_id' and Marks.module_id like '$module_id'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("module_name"=>$row[0], "module_id"=>$row[1], "test1_mark"=>$row[2], "test2_mark"=>$row[3], "assignment_mark"=>$row[4], "project_mark"=>$row[5], "exam_mark"=>$row[6]));
}

echo json_encode(array("marks"=>$response));

mysqli_close($connect);

?>
