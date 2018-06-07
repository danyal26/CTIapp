 <?php

require "connection.php";

$module_id = $_POST['module_id'];
$session_id = $_POST['session_id'];

// $module_id = 'C_ITSP300';
// $session_id = '22';

$qry = "select Modules.module_name, Lecturers.first_name, Lecturers.last_name,
Class_Timetable.room_no from Modules
left join Lecturers on Modules.lecturer_id = Lecturers.lecturer_id
left join Class_Timetable on Modules.module_id = Class_Timetable.module_id
left join Class_Sessions on Class_Timetable.session_id = Class_Sessions.session_id
where Modules.module_id = '$module_id' and Class_Timetable.session_id = '$session_id'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("module_name"=>$row[0], "first_name"=>$row[1], "last_name"=>$row[2], "room_no"=>$row[3]));
}

echo json_encode(array("session_info"=>$response));

mysqli_close($connect);

?>
