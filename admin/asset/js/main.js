var urlGetAllBook = "http://localhost:8081/admin/book";
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
                <td class = "tdItem"> ${item.title}</td>
                <td class = "tdItem"> ${item.author} </td>
                <td class = "tdItem"> ${item.category.name} </td>
                <td class = "tdItem"> <input type="date" name="date" value="${item.publication_date}" disabled style = "font-size: 2rem; text-align: center; background-color: #fff !important; border: none;"></td>
                <td class = "tdItem"> ${item.total_page} </td>
                <td class = "tdItem"> ${item.total_sold} </td>
                <td class = "tdItem"> 
                    <a href="detail.html?id=${item.id}" data-history="replaceState"><button type="button" class="btn btn-success" class = "remove">View</button></a>
                    <button type="button" class="btn btn-secondary" onclick = "deleteBook(${item.id})" class = "remove test">Delete</button>
                </td>
            </tr>

        `)
    })
}


function deleteBook(id) {

}