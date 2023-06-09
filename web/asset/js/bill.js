const token = sessionStorage.getItem("token");
const fullName = sessionStorage.getItem("fullName");
const apiGetAllBill = "http://localhost:8081/api/user/bills";
const urlBook = "http://localhost:8081/api/user/book";
if(!token) {
	window.location.href = "/login/login.html";
}
else {
	$(".user-name").text(fullName);
}
start()
function start() {
    $.ajax({
        type: "GET",
        beforeSend: function(xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + token);
		},
        url: apiGetAllBill,
        dataType: "json",
        success: function (response) {
            console.log(response);
            renderListBill(response);
        },
        error: function(xhr, status, message) {
            alert("Lỗi rồi!");
            console.log(message.message);
        }
    });
    setQuantity()
}

function setQuantity() {
	$.ajax({
		url: "http://localhost:8081/api/user/cart/count",
		method: "GET",
		beforeSend: function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer "+ token);
		},
		dataType: "TEXT",
		success: function(quantity) {
			// console.log(quantity);
			$(".qty").text(quantity);
		},
		error: function(xhr, status, message) {
			alert("Đếm lỗi rồi!");
			console.log(message.message);
		}
	})
}

// render list bill
function renderListBill(response) {
    var html = '';
    if(response.length <= 0) {
        html = `<h3>Ban chưa có đơn hàng nào, click <a href="./index.html"> vào đây </a> để có thể mua hàng của chúng tôi!</h3>`;
    }
    else {
        $.each($(response), function(i, item) {
            html += `
                <div class="col-lg-12" style="display: inline-grid;">
                    <div class="header-bill">
                        <h3>Đơn hàng số: ${item.billId}</h3>
                        <h3>Ngày đặt hàng: <input type="date" value = "${item.createDate}" disabled style="border: none; background-color: #fff;"/></h3>
                    </div>
                    <table class="table table-bordered table-condensed " style="margin-left: auto; margin-right: auto;">
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Tác giả</th>
                                <th>Tổng số trang</th>
                                <th>Ngày xuất bản</th>
                                <th>Đơn giá</th>
                                <th>Số lượng</th>
                                <th>Thành Tiền</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${listCartItemOfBill(item.listCartitem)}
                        </tbody>
                    </table>
                    <button class="btn btn-danger btn-cancel" onclick="deleteBill(${item.billId})">
                        Hủy đặt hàng 
                        <span class="icon-arrow-right"></span>
                    </button>
                </div>
            `
        })
    }
    $(".js-render-list-bill").html(html);
}

// render list cartItem
function listCartItemOfBill(listCartItem) {
    var str = "";
    $.each($(listCartItem), function(i, item) {
        str += `
            <tr>
                <td>${item.book.name}</td>
                <td>${item.book.author}</td>
                <td>${item.book.total_page}</td>
                <td><input type="date" value="${item.book.publication_date}" disabled style="border: none; background-color: #fff;" /></td>
                <td>${item.book.price}</td>
                <td>${item.quantity}</td>
                <td>${item.totalPrice}</td>
                <td>
                    <button class="btn btn-success">
                        <a onclick="viewBook(${item.book.id})" class="btn-view">
                            Xem sách
                            <span class="icon-arrow-right"></span>
                        </a>
                    </button>
                </td>
            </tr>
        `
    })
    return str;
}

// cancel order 
function deleteBill(id) {
    var check = confirm("Bạn có chắc muốn hủy đơn hàng này không ?");
    if(check) {
        $.ajax({
            url: `http://localhost:8081/api/user/bill?billId=${id}`,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            type: "DELETE",
            dataType: "TEXT",
            success: function(message) {
                alert(message);
                start();
            },
            error: function(xhr, status, message) {
                alert("Hủy lỗi rồi!");
                console.log(message.message);
            }
        })
    }
}


// đăng xuất
$(".js-btn-logout").click(function() {
    sessionStorage.clear();
    window.location.href = "/login/login.html";
})

//search
$('.search').submit(function(e) {
	e.preventDefault();
})

function viewBook(id) {
    $.ajax({
		type: "Get",
		url: urlBook + "/" + id,
		beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
		dataType: "JSON",
		success: function (response) {
			console.log(response);
			if(response.id == null) {
				alert("Quyển sách này không còn trên hệ thống!");
			}
			else
				window.location.href = `./detail-product.html?id=${id}`
		},
		error: function(xhr, status, error) {
            alert("error get book !!")
        }
	});
}