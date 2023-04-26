var urlLogin = "http://localhost:8081/api/auth/login";

$(document).ready(function () {

    $(".login").submit(function (e) {
        e.preventDefault();
        var inforLogin = {};
        $(".login input").each(function () {
            inforLogin[$(this).attr("name")] = $(this).val();
        })
        login(inforLogin);
    })

    $(function() {
        var param = getParam();
        if(param === "unauthorized") {
            // không có quyền truy cập
        }
        else if (param === '') {
            // chưa đăng nhập
        }
        else {
            
        }
    })
})

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
            localStorage.setItem("token", token);
            // set role
            localStorage.setItem("role", response.roles[0].authority)
            // set full name
            localStorage.setItem("fullName", response.fullName);
            if(response.roles[0].authority === "USER") {
                console.log(response);
                window.location.href = "/admin/index.html";
                alert("user đăng nhập thành công")
            }
            else {
                // console.log(response.roles[0].authority);
                window.location.href = "/admin/index.html";
            }
        },
        error: function(xhr, status, error) {
            // Xử lý lỗi nếu có
            // alert("Tài Khoản hoặc mật khẩu không đúng, vui lòng thử lại")
            $(".alert").css("display", "block");
            console.log(xhr.responseText);
        }
    })
}

// get param from url
function getParam() {
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('message');
}
