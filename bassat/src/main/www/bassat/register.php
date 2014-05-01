<?PHP

    session_start();
    require('config.php');
    require($include.'lang.inc');

    $connect = mysql_connect($db_host, $db_user, $db_password) or die (mysql_error());
    $db = mysql_select_db($db_name) or die (mysql_error());

    if (isSet($_POST['register'])) {
        $email = trim(strip_tags($_POST[email]));
        $password = trim(strip_tags($_POST[password]));
        $passwordConfirm = trim(strip_tags($_POST[passwordConfirm]));
        $firstname = trim(strip_tags($_POST[firstname]));
        $lastname = trim(strip_tags($_POST[lastname]));
        $idNumber = trim(strip_tags($_POST[idNumber]));
        $title = trim(strip_tags($_POST[title]));

        if ($email == '' || $password == '' || $passwordConfirm == '' || $idNumber == '') {
            $error_message = 'There were required fields';
            require($include.'register.inc');
        } else {
            $query = "insert into user(id_number, registration_date, last_login_date, title, first_name, surname, email, password, store_original_statements, show_welcome_page) " +
                     "values ($idNumber, CURRENT_TIMESTAMP(), null, $title, $firstname, $lastname, $email, $password, 1, 1);";
            $insert = mysql_query($query) or die(mysql_error());
            header("Location: dashboard.php");
        }
    }

?>