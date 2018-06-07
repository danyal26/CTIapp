<?php

require "connection.php";

// $student_no = 'MB2015-0615';
$student_no = $_POST['student_no'];

$qry = "select Query_Feedback.*, Departments.department_name from Query_Feedback
left join Departments on Query_Feedback.department_id = Departments.department_id
where Query_Feedback.student_id = '$student_no'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("feedback_id"=>$row[0], "department_id"=>$row[1], "student_id"=>$row[2], "message"=>$row[3], "department_name"=>$row[4]));
}

echo json_encode(array("feedback"=>$response));

mysqli_close($connect);

?>
