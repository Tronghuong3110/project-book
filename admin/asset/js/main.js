var urlBook = "http://localhost:8081/api/admin/book";
const token = sessionStorage.getItem("token");
console.log(token)
$.ajax({
    type: "GET",
    beforeSend: function(xhr) {
        xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        // console.log('Authorization Header: '+ 'Bearer ' + token);
    },
    url: urlBook + "?key",
    dataType: "JSON",
    success: function(response) {
        console.log(response)
        localStorage.setItem("listBook", JSON.stringify(response));
        renderListBook(response)
    },
    error: function(xhr, status, error) {
        alert("error")
    }
})

// render list book
function renderListBook(data) {
    var html = '';
    $.each($(data), function(i, item) {
        html += `
            <tr>
                <td class = "tdItem"> ${item.name}</td>
                <td class = "tdItem"> ${item.author} </td>
                <td class = "tdItem"> ${item.category.name} </td>
                <td class = "tdItem"> <input type="date" name="date" value="${item.publication_date}" disabled style = "font-size: 2rem; text-align: center; background-color: #fff !important; border: none;"></td>
                <td class = "tdItem"> ${item.total_page} </td>
                <td class = "tdItem"> ${item.sold_quantity} </td>
                <td class = "tdItem"> 
                    <a href="detail.html?id=${item.id}" data-history="replaceState"><button type="button" class="btn btn-success" class = "remove">View</button></a>
                    <button type="button" class="btn btn-secondary" onclick = "deleteBook(${item.id})" class = "remove test">Delete</button>
                </td>
            </tr>

        `
    })
    $(".js-render-list").html(html)
}


function deleteBook(id) {
    var check = confirm("Bạn có chắc muốn xóa quyển sách không");
    // console.log(id)
    if(check) {
        $.ajax({
            url: urlBook + "/" + id,
            beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
            },
            type: "DELETE",
            success: function() {
                alert("Xóa sách thành công")
                var oldListBook = JSON.parse(localStorage.getItem("listBook"));
                var newListBook = oldListBook.filter(item => item.id !== id);
                localStorage.setItem("listBook", JSON.stringify(newListBook))
                console.log(newListBook)
                renderListBook(newListBook);
                // console.log(oldListBook)
            },
            error: function(xhr, status, error) {
                alert("Xóa sách không thành công");
            }
        })
    }
}

// search book by title or author

// filter
$(".js-search").on("input", function(e) {
    const key = $(this).val().trim();
    filterBook(key);
})
// search back end
$(".js-btn-search").click(function(e) {
    const key = $(".js-search").val().toLowerCase().trim();
    console.log(key)
    searchByKey(key);
})

function searchByKey(key) {
    $.ajax({
        url: urlBook + "?key=" + key,
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
        },
        type: "GET",
        dataType: "JSON",
        success: function(response) {
            localStorage.setItem("listBook", JSON.stringify(response))
            console.log(response)
            renderListBook(response);
        },
        error: function(xhr, status, error) {
            alert("Tìm kiếm lỗi rồi!!");
        }
    })
}

function filterBook(key) {
    const oldListBook = JSON.parse(localStorage.getItem("listBook"));
    const listBookFilter = oldListBook.filter(book => {
        return (
            book.name.toLowerCase().includes(key.toLowerCase()) ||
            book.author.toLowerCase().includes(key.toLowerCase())
        ); 
    })
    renderListBook(listBookFilter);
}
