<?php

require "connection.php";

$student_id = $_POST['student_no'];
$totalCost = 0.0;
$totalPaid;
// $student_id = 'MB2015-0615';

$qry = "select SUM(Modules.cost) as cost from Modules
left join Students on Modules.program_id = Students.program_id
where Students.student_id = '$student_id' and Modules.program_level = Students.current_program_level";

$qryresult = mysqli_query($connect, $qry);

while ($row = mysqli_fetch_row($qryresult)) {
      $totalCost = $row[0];
}

$qry2 = "select SUM(payment_amount) as totalPaid from Payments where student_id = '$student_id'";

$qryresult2 = mysqli_query($connect, $qry2);

while ($row2 = mysqli_fetch_row($qryresult2)) {
      $totalPaid = $row2[0];
}

if ($totalCost > $totalPaid) {
      echo 'false';
}
else {
      echo 'true';
}

mysqli_close($connect);

?>
