<?php

require "connection.php";

$student_id = $_POST['student_no'];

$qry = "select Modules.module_name, Test_Timetable.module_id, Test_Timetable.date, Test_Timetable.start_time, Test_Timetable.end_time, Test_Timetable.venue
from Test_Timetable
left join Modules on Test_Timetable.module_id = Modules.module_id
left join Students on Students.program_id = Modules.program_id
where student_id like '$student_id'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("module_name"=>$row[0], "module_id"=>$row[1], "date"=>$row[2], "start_time"=>$row[3], "end_time"=>$row[4], "venue"=>$row[5]));
}

echo json_encode(array("tests"=>$response));

mysqli_close($connect);

?>
