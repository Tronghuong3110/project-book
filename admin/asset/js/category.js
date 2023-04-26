var urlCategory = "http://localhost:8081/admin/category"
var urlGetOneById = "http://localhost:8081/admin/category/"

$.ajax({
    url: urlCategory,
    type: "GET",
    dataType: "JSON",
    success: function(response) {
        htmlCategories(response)
        // console.log(response)
    },
    error: function(xhr, status, error) {
        alert("error get category")
    }
})

// render table category
function htmlCategories(data) {
    $.each($(data), function(i, item) {
        $(".js-render-list").append(
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
        )
    })
}

// edit category
function openModalEdit(id) {
    $(".title-modal").html(`
        <h2 class="modal-title tdItem" style="font-size: 3rem;">Chỉnh sửa thể loại sách</h2>
    `)
    setValue(id)
    // $(".form-category").submit(saveOrUpdate(id))
}

// set value of category to input
function setValue(id) {
    $.ajax({
        url: urlGetOneById + id,
        type: "GET",
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

function saveOrUpdate(id) {
    var method = (id === '') ? "POST" : "PUT";
    var obj = getValueInputField(id);
    var check = (id === '') ? confirm("Bạn có chắc muốn thêm thể loại sách này không?") : confirm("Bạn có chắc muốn chỉnh sửa quyển sách này không?")
    if(check) {
        $.ajax({
            url: urlCategory,
            type: method,
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