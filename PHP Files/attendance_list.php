<?php

require "connection.php";

$module_id = $_POST['module_id'];
$date = $_POST['date'];

// $module_id = 'C_ITDS221';
// $date = '2016-10-10';

$qry = "select Attendance_List.att_list_id, Attendance_List.student_id, Students.first_name, Students.last_name from Attendance_List
left join Students on Attendance_List.student_id = Students.student_id
where Attendance_List.date = '$date' and Attendance_List.module_id = '$module_id'
order by Students.last_name";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("id"=>$row[0], "student_id"=>$row[1], "first_name"=>$row[2], "last_name"=>$row[3]));
}

echo json_encode(array("attendance_list"=>$response));

mysqli_close($connect);

?>
