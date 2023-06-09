const urlLogin = "http://localhost:8081/api/auth/login";
const urlSignup = "http://localhost:8081/api/auth/signup"

$(document).ready(function () {
    // đăng nhập
    $(".login").submit(function (e) {
        e.preventDefault();
        var inforLogin = {};
        $(".login input").each(function () {
            inforLogin[$(this).attr("name")] = $(this).val();
        })
        login(inforLogin);
    })

    // đăng ký tài khoản
    $(".signup").submit(function (e) {
        e.preventDefault();
        var inforSignup = {};
        $(".signup input").each(function () {
            inforSignup[$(this).attr("name")] = $(this).val();
        })
        var passwordConfirm = $("#passwordAgain").val();
        var password = $("#password").val();
        var checkPassword = check(password, passwordConfirm);
        var checkUserName = validateUserName(inforSignup["userName"]);
        // console.log(checkUserName);  
        if(checkPassword && checkUserName) {
            registerUser(inforSignup);
        }
        else if(!checkPassword) {
            var alertMess = $(".js-alert");
            alertMess.css("display", "block");
            alertMess.text("Xác nhận mật khẩu không đúng!")
        }
        else {
            var alertMess = $(".js-alert");
            alertMess.css("display", "block");
            alertMess.text("UserName không hợp lệ!")
        }
        // login(inforSignup);
    })
})

// đăng nhập
function login(infor) {
    $.ajax({
        url: urlLogin,
        type: "POST",
        data: JSON.stringify(infor),
        datatype: "JSON",
        contentType: "application/json",
        success: function(response) {
            var token = response.token;
            // set token
            sessionStorage.setItem("token", token);
            // set role
            sessionStorage.setItem("role", response.roles[0].authority)
            // set full name
            sessionStorage.setItem("fullName", response.fullName);
            if(response.roles[0].authority === "USER") {
                console.log(response);
                window.location.href = "/web/index.html";
                // alert("user đăng nhập thành công")
                console.log(token)
            }
            else {
                console.log(token)
                window.location.href = "/admin/index.html";
            }
        },
        error: function(xhr, status, error) {
            // Xử lý lỗi nếu có
            $(".alert").css("display", "block");
            console.log(xhr.responseText);
        }
    })
}

// đăng ký tài khoản
function registerUser(infor) {
    $.ajax({
        url: urlSignup,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(infor),
        datatype: "JSON",
        success: function(response) {
            const mess = response.message
            var alertMess = $(".js-alert");
            if(mess === "User register successfully!") {
                alert("Đăng ký tài khoản thành công!")
                window.location.href = "/login/login.html"
            }
            else {
                alertMess.css("display", "block");
                alertMess.text(mess);
            }
        },
        error: function(xhr, status, error) {
            // Xử lý lỗi nếu có
            $(".js-alert").css("display", "none");
            console.log(xhr.responseText);
        }
    })
}

//confirm password
function check(password, confirmPassword) {
    return password===confirmPassword;
}

function validateUserName(userName) {
    return userName && !/^[a-zA-Z0-9]*$/.test(userName) ? false : true;
}
  