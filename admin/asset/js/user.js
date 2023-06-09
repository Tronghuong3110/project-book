const urlGetAllUser = "http://localhost:8081/api/admin/users"
const token = sessionStorage.getItem("token");
$.ajax({
    type: "GET",
    beforeSend: function(xhr) {
        xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        // console.log('Authorization Header: '+ 'Bearer ' + token);
    },
    url: urlGetAllUser + "?key",
    dataType: "JSON",
    success: function(response) {
        // console.log(response.roles)
        localStorage.setItem("listUser", JSON.stringify(response));
        renderInforUser(response);
        // renderListBook(response)
    },
    error: function(xhr, status, error) {
        alert("error")
    }
})

// render infor user
function renderInforUser(data) {
    var html = '';
    $.each($(data), function(i, item) {
        html += `
            <tr>
                <td class = "tdItem"> ${item.id}</td>
                <td class = "tdItem"> ${item.userName} </td>
                <td class = "tdItem"> ${item.fullname} </td>
                <td class = "tdItem"> ${item.email} </td>
                <td class = "tdItem"> ${item.roles[0].name} </td>
                <td class = "tdItem"> 
                    <button type="button" class="btn btn-secondary" onclick = "" class = "remove test">Delete</button>
                </td>
            </tr>
        `
    })
    $(".js-render-list").html(html)
}

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
        url: urlGetAllUser + "?key=" + key,
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            // console.log('Authorization Header: '+ 'Bearer ' + token);
        },
        type: "GET",
        dataType: "JSON",
        success: function(response) {
            localStorage.setItem("listUser", JSON.stringify(response))
            console.log(response)
            renderInforUser(response);
        },
        error: function(xhr, status, error) {
            alert("Tìm kiếm lỗi rồi!!");
        }
    })
}

function filterUser(key) {
    console.log(key)
    const oldListUser = JSON.parse(localStorage.getItem("listUser"));
    const listUserFilter = oldListUser.filter(user => {
        return (
            user.userName.toLowerCase().includes(key.toLowerCase()) ||
            user.fullname.toLowerCase().includes(key.toLowerCase())
        ); 
    })
    renderInforUser(listUserFilter);
}
