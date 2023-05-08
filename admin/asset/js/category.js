var urlCategory = "http://localhost:8081/api/admin/categories"
var urlOne = "http://localhost:8081/api/admin/category/"

const token = localStorage.getItem("token");
// find All
$.ajax({
    url: urlCategory + "?key",
    type: "GET",
    beforeSend: function(xhr) {
        xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        // console.log('Authorization Header: '+ 'Bearer ' + token);
    },
    dataType: "JSON",
    success: function(response) {
        localStorage.setItem("categories", JSON.stringify(response));
        htmlCategories(response)
        // console.log(response)
    },
    error: function(xhr, status, error) {
        alert("error get category")
    }
})

// render table category
function htmlCategories(data) {
    var html = '';
    $.each($(data), function(i, item) {
        html += 
            `
                <tr>
                    <td class = "tdItem"> ${item.id}</td>
                    <td class = "tdItem"> ${item.name} </td>
                    <td class = "tdItem"> ${item.code} </td>
                    <td class = "tdItem"> 
                        <button type="button" class="btn btn-success btn-edit" data-toggle="modal" data-target="#myModal" onclick="openModalEdit(${item.id})">Edit</button>
                    </td>
                </tr>
            `
    })
    $(".js-render-list").html(html);
}

// edit category
function openModalEdit(id) {
    $(".title-modal").html(`
        <h2 class="modal-title tdItem" style="font-size: 3rem;">Chỉnh sửa thể loại sách</h2>
    `)
    setValue(id)
    // $(".form-category").submit(saveOrUpdate(id))
}

// set value of category to input ---> find One by id
function setValue(id) {
    $.ajax({
        url: urlOne + id,
        type: "GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
        },
        dataType: "JSON",
        success: function(response) {
            $("#name").val(response.name);
            $("#code").val(response.code);
            $("#id").val(response.id);
        },
        error: function(xhr, status, error) {
            alert("error get category")
        }
    })
}

// add category
function openModalAdd() {
    $(".title-modal").html(`
        <h2 class="modal-title tdItem" style="font-size: 3rem;">Thêm thể loại sách</h2>
    `)
    $(".form-category input").each(function() {
        $(this).val('');
    })
}

$(".form-category").submit(function(e) {
    e.preventDefault();
    var idCategory = $("#id").val();
    saveOrUpdate(idCategory);
})

// add or update book
function saveOrUpdate(id) {
    var method = (id === '') ? "POST" : "PUT";
    var obj = getValueInputField(id);
    var check = (id === '') ? confirm("Bạn có chắc muốn thêm thể loại sách này không?") : confirm("Bạn có chắc muốn chỉnh sửa quyển sách này không?")
    if(check) {
        $.ajax({
            url: urlOne,
            type: method,
            beforeSend: function(xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token);
                // console.log('Authorization Header: '+ 'Bearer ' + token);
            },
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify((obj)),
            success: function(response) {
                alert("success")
                window.location.href = "category.html"
            },
            error: function(xhr, status, error) {
                alert("Cập nhật không thành công!")
                window.location.href = "category.html"

            }
        })
    }
    // console.log(method + " " + obj )
}

function getValueInputField(id) {
    var obj = {}
    $(".form-category input").each(function() {
        obj[$(this).attr("name")] = $(this).val();
    })
    obj["id"] = id;
    return obj;
}


// ========================================= search ===============================

// search filter
$(".js-search").on("input", function(e) {
    const key = $(this).val().trim();
    filterUser(key);
})
// search back end
$(".js-btn-search").click(function(e) {
    const key = $(".js-search").val().toLowerCase().trim();
    console.log(key)
    searchByKey(key);
})


function searchByKey(key) {
    $.ajax({
        url: urlCategory + "?key=" + key,
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
        },
        type: "GET",
        dataType: "JSON",
        success: function(response) {
            localStorage.setItem("categories", JSON.stringify(response))
            console.log(response)
            htmlCategories(response);
        },
        error: function(xhr, status, error) {
            alert("Tìm kiếm lỗi rồi!!");
        }
    })
}

function filterUser(key) {
    console.log(key)
    const oldListCategory = JSON.parse(localStorage.getItem("categories"));
    const listCategoryFilter = oldListCategory.filter(category => {
        return (
            category.name.toLowerCase().includes(key.toLowerCase()) ||
            category.code.toLowerCase().includes(key.toLowerCase())
        ); 
    })
    htmlCategories(listCategoryFilter);
}
