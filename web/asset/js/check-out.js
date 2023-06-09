

const urlApiCart = "http://localhost:8081/api/user/cart/cartItems";
const urLApiPayment = "http://localhost:8081/api/user/bill";
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
            $(".qty").text(response.length)
        },
        error: function(xhr, status, message) {
            alert("Get List cartItem lỗi rồi!");
            console.log(message.message);
        }
    });
}

// render list product of cart
function renderListCartItem(data) {
    var totalPrice = 0;
    var shipmentPrice;
    $.each($(data), function (i, item) { 
        $(".js-product").append(`
            <div class="order-col">
                <div>${item.quantity}x ${item.book.name}</div>
                <div>${item.totalPrice}đ</div>
            </div> 
        `)
        totalPrice += item.totalPrice;
    });
    if(totalPrice >= 250) {
        shipmentPrice = "Miễn Phí";
    }
    else {
        shipmentPrice = "30đ";
        $(".js-shipping").data("price", 30);
    }
    // render shippemt price
    $(".js-shipping").text(shipmentPrice);
    // render total price
    $(".js-total-price").text(totalPrice + "đ");
}

// đăng xuất
$(".js-btn-logout").click(function() {
    sessionStorage.clear();
    window.location.href = "/login/login.html";
})


// liet ke danh sach tỉnh, huyện, xã
$(function () {
    apiProvince=(prodvince)=>{
        let district;
        var htmlConscious = '<option value="null">--- Chọn tỉnh/Thành ---</option>';
        var htmlDistricts = '<option value="null">--- Chọn quận/huyện ---</option>';
        var commune = '<option value="null">--- Chọn xã/phường ---</option>';

        // danh sach tỉnh
        $.each($(prodvince), function(i, element){ 
            htmlConscious += '<option value="' + element.name + '">' + element.name + '</option>';
        });
        $('#conscious').html(htmlConscious);

        // danh sách quận, huyện
        $('#conscious').change(function () {
            let value = $(this).val();
            $.each(prodvince,function(index,element){
                if (element.name == value) {
                    district = element.districts;
                    $.each(element.districts,function(index,element1){
                        htmlDistricts += '<option value="' + element1.name + '">' + element1.name + '</option>';
                    })
                }
            })
            $('#district').html(htmlDistricts);         
        }); 
        
        // danh sách xã, phường
        $('#district').change(function () {
            let value = $(this).val();
            $.each(district,function(index,element){
                if (element.name == value) { 
                    $.each($(element.wards), function(i, element1){
                        commune += '<option value="' + element1.name + '">' + element1.name + '</option>';
                    });
                }
            })  
            $('#commune').html(commune);     
        });
    }
    prodvince = JSON.parse(data);
    apiProvince(prodvince);
})

// click thanh toán
$(".js-payment").click(function(e) {
    e.preventDefault();
    var obj = getValue();
    var totalPay = $(".js-total-price").text().replace("đ", '');
    console.log(urLApiPayment + `?totalPay=${totalPay}`);
    console.log(obj)
    $.ajax({
        url: urLApiPayment + `?totalPay=${totalPay}`,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        },
        data: JSON.stringify(obj),
        dataType: "TEXT",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        success: function(message) {
            alert(message);
            window.location.href = "/web/bill.html";
        },
        error: function(xhr, status, message) {
            alert("Thanh toán lỗi rồi !");
            console.log(message.message);
        }
    })
})

// get infor customer
function getValue() {
    var obj = {};
    $(".billing-details input").each(function() {
        obj[$(this).attr("name")] = $(this).val();
    })
    $(".billing-details select").each(function() {
        obj[$(this).attr("name")] = $(this).val();
    })
    obj["note"] = $(".js-note").val();
    return obj;
}

//search
$('.search').submit(function(e) {
	e.preventDefault();
})