const urlApiCart = "http://localhost:8081/api/user/cart/cartItems";

const token = sessionStorage.getItem("token");
const fullName = sessionStorage.getItem("fullName");

if(!token) {
    window.location.href = "/login/login.html";
}

$(".user-name").text(fullName);

start();
function start() {
    $.ajax({
        type: "GET",
        url: urlApiCart + "/0",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        dataType: "JSON",
        success: function (response) {
            renderListCartItem(response);
            $(".qty").text(response.length);
        },
        error: function(xhr, status, message) {
            alert("Lỗi rồi!");
            console.log(message.message);
        }
    });
}

function renderListCartItem(data) {
    var html = '';
    $.each($(data), function (i, item) { 
        html += `
            <tr>
                <td>${item.book.name}</td>
                <td>${item.book.author}</td>
                <td>${item.book.total_page}</td>
                <td><input type="date" value="${item.book.publication_date}" disabled style = "background-color: #fff; border: none;"></td>
                <td>${item.book.price}</td>
                <td>
                    <div class="cart-quantity">
                        <div class="input-cart-quantity">
                            <button class="mJX7hG btn-minus">
                                <i class="fa-solid fa-minus"></i>
                            </button>	
                            <input class="mJX7hG _8BP9GU qty" data-id = "${item.id}" min = "0" type="text" role="spinbutton" aria-valuenow="9" value="${item.quantity}">
                            <button class="mJX7hG btn-plus">
                                <i class="fa-solid fa-plus"></i>
                            </button>
                        </div>
                    </div>
                </td>
                <td>${item.totalPrice}</td>
                <td data-productid ="${item.id}" class = "productId"> <button class = "btn btn-delete" onclick = "deleteProduct(${item.id})">Xóa</button></td>
            </tr>
        `;
    });
    $(".js-render").html(html);
    if(data.length <= 0) {
        $(".js-btn-payment").css("display", "none");
    }
}

function deleteProduct(id) {
    var check = confirm("Bạn có chắc muốn xóa sản phẩm này không?");
    if(check) {
        $.ajax({
            method: "DELETE",
            url: `http://localhost:8081/api/user/cart/${id}`,
            dataType: "TEXT",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            success: function(message) {
                alert(message);
                start();
            },
            error: function(xhr, status, message) {
                alert("Xóa lỗi rồi!");
                console.log(message.message);
            }
        })
    }
}

// window.addEventListener("load", changeQuantity);
function changeQuantity() {
    $(".input-cart-quantity").each(function() {
        var input_quantity = $(this).find('.qty');
        var btn_minus = $(this).find('.btn-minus');
        var btn_plus = $(this).find('.btn-plus');
        var quantity = input_quantity.val();
        // giam so luong
        btn_minus.click(function() {
            if(quantity >= 0) quantity--;
            $(".quantity").val(quantity);
        })
        // tang so luong
        btn_plus.click(function() {
            quantity++;
            $(".quantity").val(quantity);
        })
    })
    
}


// logout
$(".js-btn-logout").click(function() {
    sessionStorage.clear();
    window.location.href = "/login/login.html";
})

//search
$('.search').submit(function(e) {
	e.preventDefault();
})