var urlGetOneBook = "http://localhost:8081/api/admin/book/";
var urlGetAllCategory = "http://localhost:8081/api/admin/categories";

// get id book from param
function getProductIdFromUrl() {
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}
var idBook = getProductIdFromUrl();
// console.log(idBook)
const token = localStorage.getItem("token");
console.log(token)
if(idBook != -1) {
    $.ajax({
        url: urlGetOneBook + idBook,
        type: "GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
        },
        dataType: "JSON",
        success: function(response) {
            console.log(response)
            // setValue(response)
            $(".js-infor-book").append(setValue(response))
            $("#decription").text(response.decription)
            // renderCategories(response)
        },
        error: function(xhr, status, error) {
            alert("error get book !!")
        }
    })
}

else {
    var obj = {};
    obj["category"] = {}
    $(".js-infor-book").append(setValue(obj))
    $("#category").html('<option value="null">---Thể loại sách---</option>')
    $(".js-infor-book input").each(function() {
        // reset value of input
        $(this).val('');
        $(this).removeAttr("disabled");
    })
    $(".js-categories").removeAttr("disabled");
    getAllCategory()
    $('#decription').removeAttr("disabled");
    // ẩn edit và save
    $(".js-btn-edit").css("display", "none")
    $(".js-btn-add").css("display", "inline-block")
}

// render input field
function setValue(data) {
    return `
        <!-- left -->
        <div class="col-lg-8 col-md-8">
            <!-- tiêu đề & tác giả -->
            <div class="row space-bottom">
                <!-- Tiêu đề -->
                <div class="col-md-4">
                    <label class="control-label font-label" for="name">Tiêu đề</label><br>
                    <input type="text" name="name" id="name" class="form-contr-mol input-field" value = "${data.name}" required disabled>
                </div>
                <!-- Tác giả -->
                <div class="col-md-4">
                    <label class="control-label font-label" for="author">Tác Giả</label> <br>
                    <input type="text" name="author" id="author" class="form-contr-mol input-field" value = "${data.author}" required disabled>
                </div>
            </div>

            <!-- Mô tả -->
            <div class="row space-bottom" style="padding: 0 12px;">
                <label class="control-label font-label" for="decription">Mô tả về cuốn sách</label> <br>
                <textarea class="form-control" id="decription" name="decription" rows="8" cols="30" disabled></textarea>
            </div>

            <!-- Ngày phát hành & số trang -->
            <div class="row space-bottom">
                <!-- Ngày phát hành -->
                <div class="col-md-4">
                    <label class="control-label font-label" for="publication_date">Ngày phát hành</label> <br>
                    <input type="date" name="publication_date" id="publication_date" class="form-contr-mol input-field" value = "${data.publication_date}" required disabled>
                </div>
                <!-- Số trang -->
                <div class="col-md-4">
                    <label class="control-label font-label" for="total_page">Số trang</label> <br>
                    <input type="number" name="total_page" id="total_page" class="form-contr-mol input-field" min="0" value = "${data.total_page}" required disabled>
                </div>
            </div>

            <!-- Thể loại -->
            <div class="row space-bottom" style="margin: 0;">
                <label class="control-label font-label" for="category">Thể loại</label> <br>
                <select id="category" name="category" class="form-control input-field js-categories" disabled>
                    <option value="${data.category.id}">${data.category.name}</option>
                </select> 
            </div>
        </div>
        <!-- right -->
        <div class="col-lg-4 js-file" style="display: flex; flex-flow: column; align-items: center; margin-top: 22px;">
            <form id="form-upload">
                <label for="img">Hình ảnh</label>
                <input type="file" class="form-control-file" name="image" id="img" value="${data.image}" disabled>
            </form>
            <div class="image" style="margin-top: 12px;">
                <img class="image" src="http://localhost:8081/api/file/${data.image}" alt="" style="object-fit: cover; height: 300px;">
            </div>
            <input type="hidden" id="imageHidden" name="oldImage" value="${data.image}">
        </div>    
    `
}

// get all category
function getAllCategory() {
    $.ajax({
        url: urlGetAllCategory + "?key",
        type: "GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
        },
        dataType: "JSON",
        success: function(response) {
            console.log(response)
            renderCategories(response);
        },
        error: function(xhr, status, error) {
            alert("error get category")
        }
    })
}

// render categories in select
function renderCategories(data) {
    var categoryId = $("#category").val();
    $.each($(data), function(i, item) {
        if(item.id != categoryId) {
            $(".js-categories").append(`
                <option value="${item.id}">${item.name}</option>
            `)
        }
    })
}

// solve add and edit book

// ============================================== Dùng chung =================================================================

// get value of input set to obj
function getValueInputField() {
    var obj = {};
    $(".js-infor-book input").each(function() {
        obj[$(this).attr("name")] = $(this).val();
    })
    obj[$("#decription").attr("name")] = $("#decription").val();
    obj["id"] = $("#id").val();
    return obj;
}

// upload file save image book to server 
function uploadFile(obj) {
    var formData = new FormData(); // C://....
    formData.append('image', $('#img')[0].files[0]);
    console.log("Upload file: " + obj)
    $.ajax({
        url: 'http://localhost:8081/api/file',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(response) {
            // alert("success")
            // Xử lý kết quả trả về từ server
            obj["image"] = response;
            saveBook(obj)
        },
        error: function(xhr, status, error) {
            // Xử lý lỗi nếu có
            alert("Vui lòng chọn ảnh cho quyển sách muốn thêm !!")
            console.log(xhr.responseText);
        }
    });
}

// save infor book to database
function saveBook(obj) {
    var method = obj["id"] == '' ? "POST" : "PUT";
    console.log(method);
    // console.log(obj)
    console.log("Id thể loại: " + $("#category").val())
    $.ajax({
        url: "http://localhost:8081/api/admin/book/" + $("#category").val(),
        method: method,
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
        },
        data: JSON.stringify(obj),
        dataType: "JSON",
        contentType: "application/json; charset=UTF-8",
        success: function(response) {
            // console.log(response)
            alert("success")
            window.location.href = "index.html"
        },
        error: function(xhr, status, error) {
            alert("Không thể thêm sách !!")
        }
    })
}

// ============================================== Kết thúc phần dùng chung ============================================

// ============================================== Begin edit book =====================================================
$('.js-btn-edit').click(function() {
    $(".js-infor-book input").each(function() {
        $(this).removeAttr("disabled");
    })
    $(".js-categories").removeAttr("disabled");
    $('#decription').removeAttr("disabled");
    $(".js-btn-save").css("display", "inline-block")
    $(this).css("display", "none")
    getAllCategory();
})

// click save book
$(".js-btn-save").click(function() {
    // get name image of book on database
    var check = confirm("Bạn có chắc muốn thay đổi quyển sách này không ?");
    if(check) {
        var imageValue = $("#imageHidden").val();
        save(imageValue);   
    }
})

// save
function save(imageValue) {
    var obj = getValueInputField();
    obj["id"] = idBook;
    if(valiDateForm(obj)) {
        if(imageValue != obj["image"] && obj["image"] != '') {
            uploadFile(obj);
            // console.log("Anh khac" + obj)
            console.log(obj)
        }
        else {
            saveBook(obj);
            console.log(obj)
        }
    }
    else {
        alert("Vui lòng điền đầy đủ thông tin sách!")
    }
}

// ====================================== End edit book ===============================================================

// ====================================== Begin add book ===============================================================

// click add book in footer
$(".js-btn-add").click(function() {
    var check = confirm("Bạn có chắc muốn thêm quyển sách này không?")
    if(check) {
        var obj = getValueInputField();
        obj["id"] = '';
        if(valiDateForm(obj)) {
            var categoryId = $("#category").val();
            console.log("categoryId = " + categoryId)
            if(categoryId == 'null') {
                alert("Vui lòng chọn thể loại sách!")
            }
            else {
                uploadFile(obj)
                // alert("ok")
            }
        }
        else {
            alert("Vui lòng điền đầy đủ thông tin của sách!")
        }
    }
})

// ====================================== End add book ===============================================================


// hiển thị ảnh tạm thời khi chọn ảnh
$(document).ready(function() {
    // get input
    var input = $("#img");
    var img = $(".image");
    input.on("input", function() {
        // alert("Đã thay ảnh")
        var file = input[0].files[0];
        const image = URL.createObjectURL(file);
        img.attr("src", image);
    })
})

// check các trường input có trống không
function valiDateForm(obj) {
    if(obj['author'] === '' || obj['name'] === '' ||
        obj['publication_date'] === '') {
            return false;
    }
    return true;
}