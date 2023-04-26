var urlGetAllBook = "http://localhost:8081/admin/book";
var urlDeleteBook = "http://localhost:8081/admin/book/"
$.ajax({
    url: urlGetAllBook,
    type: "GET",
    dataType: "JSON",
    success: function(response) {
        console.log(response)
        renderListBook(response)
    },
    error: function(xhr, status, error) {
        alert("error")
    }
})

// render list book
function renderListBook(data) {
    $.each($(data), function(i, item) {
        $(".js-render-list").append(`
            <tr>
                <td class = "tdItem"> ${item.name}</td>
                <td class = "tdItem"> ${item.author} </td>
                <td class = "tdItem"> ${item.category.name} </td>
                <td class = "tdItem"> <input type="date" name="date" value="${item.publication_date}" disabled style = "font-size: 2rem; text-align: center; background-color: #fff !important; border: none;"></td>
                <td class = "tdItem"> ${item.total_page} </td>
                <td class = "tdItem"> ${item.total_sold} </td>
                <td class = "tdItem"> 
                    <a href="detail.html?id=${item.id}" data-history="replaceState"><button type="button" class="btn btn-success" class = "remove">View</button></a>
                    <button type="button" class="btn btn-secondary" onclick = "deleteBook(${item.id}, ${item.name})" class = "remove test">Delete</button>
                </td>
            </tr>

        `)
    })
}


// function deleteBook(id, name) {
//     var check = confirm("Bạn có chắc muốn xóa quyển sách " + name +" không");
//     if(check) {
//         $.ajax({
//             url: urlGetAllBook + id,
//             type: "DELETE",
//             success: function() {
//                 alert("Xóa sách " + name + " thành công")
//                 window.location.href = "index.html"
//             },
//             error: function(xhr, status, error) {
//                 alert("Xóa sách " + name + " không thành công");
//             }
//         })
//     }
// }

$('.test').click(function() {
    alert("hello")
}) 