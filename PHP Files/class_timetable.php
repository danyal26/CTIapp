<?php

require "connection.php";

$student_id = $_POST['student_no'];
// $student_id = 'MB2015-0615';


$qry = "select Class_Timetable.*, Modules.module_name, Lecturers.first_name, Lecturers.last_name from Class_Timetable
left join Modules on Class_Timetable.module_id = Modules.module_id
left join Students on Modules.program_id = Students.program_id
left join Lecturers on Modules.lecturer_id = Lecturers.lecturer_id
where Students.student_id = '$student_id' and Modules.program_level = Students.current_program_level";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("c_timetable_id"=>$row[0], "module_id"=>$row[1], "room_no"=>$row[2], "session_id"=>$row[3], "module_name"=>$row[4], "first_name"=>$row[5], "last_name"=>$row[6]));
}

echo json_encode(array("timetable"=>$response));

mysqli_close($connect);

?>
