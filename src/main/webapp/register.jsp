<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Đăng ký</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link href="resources/css/register.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<script>
    function check(form) {
        if (form.user.value === "") {
            alert("Vui lòng nhập tên tài khoản！");
            return false;
        }
        if (form.password.value === "") {
            alert("Vui lòng nhập mật khẩu！");
            return false;
        }
        return true;
    }
</script>
<h1 style="margin: 50px 80px; color: darkgray; font-family: cursive;">Hệ thống quản lý sinh viên</h1>
<div class="main">
    <h5 class="title">
        <a href="login.jsp" id="login">Đăng nhập</a>
        <b>&nbsp;·&nbsp;</b>
        <a href="register.jsp" id="register">Đăng ký</a>
    </h5>
    <form action="check_register" method="post" onsubmit="return check(this)">
        <div class="form-group">
            <input type="email" name="email" class="form-control email" placeholder="Email">
            <input type="text" name="user" class="form-control user" placeholder="Tên tài khoản">
            <input type="password" name="password" class="form-control password" placeholder="Mật khẩu">
            <input type="submit" value="Đăng ký" class="btn btn-primary btn-lg btn-block we"/>
        </div>
    </form>
</div>
<script src="resources/js/jquery-3.2.1.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>
