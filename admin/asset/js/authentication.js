
$(function() {
    var token = localStorage.getItem("token");
    if(!token) {
        window.location.href = "/login/login.html?message"
    }
    else {
        const fullName = localStorage.getItem("fullName");
        const role = localStorage.getItem("role");
        // console.log(role)
        if(role != "ADMIN") {
            $(".remove").css("display", "none");
            $(".no-skin").html(`
                <h1 style="text-align: center;">Bạn không được có quyền truy cập vào đường link này.
                    Vui lòng <a href="/login/login.html">quay lại tại đây</a>
                </h1>
            `)
            // window.location.href = "/login/login.html";
        }
        else {
            $(".user-info").html(`
                <small>xin chào,</small>
                ${fullName}
            `)
        }
    }
})

// get user name form token
function parseJwt(token) {
    // const base64Url = token.split('.')[1];
    // const base64 = base64Url.replace('-', '+').replace('_', '/');
    // var res = JSON.parse(window.atob(base64));
    // console.log(res)
}

$(document).ready(function() {
    $(".logout").click(function() {
        localStorage.clear();
        window.location.href = "/login/login.html";
    })
})


