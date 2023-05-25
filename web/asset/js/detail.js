
const urlBook = "http://localhost:8081/api/user/book";
const urlBookBycategory = "http://localhost:8081/api/user/books";
const urlApiAddBookToCart = "http://localhost:8081/api/user/cart/";

const bookId = getParam();
const token = localStorage.getItem("token");
const fullName = localStorage.getItem("fullName");
if(!token) {
	window.location.href = "/login/login.html";
}
else {
	console.log(token)
	$(".user-name").text(fullName);
}
$(".js-btn-logout").click(function (e) { 
	e.preventDefault();
	localStorage.clear();
	window.location.href = '/login/login.html';
});
statr();

// render detail book
function statr() {
	$.ajax({
		type: "Get",
		url: urlBook + "/" + bookId,
		beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
		dataType: "JSON",
		success: function (response) {
			console.log(response);
			render(response);
		},
		error: function(xhr, status, error) {
            alert("error get book !!")
        }
	});
}
// detail product
function render(data) {
	$(".js-detail-product").append(
		`
		<div class="product-details" style = "font-size: 1.5rem;">
			<h2 class="product-name">${data.name}</h2>
			<div>
				<div class="product-rating">
					<i class="fa fa-star"></i>
					<i class="fa fa-star"></i>
					<i class="fa fa-star"></i>
					<i class="fa fa-star"></i>
					<i class="fa fa-star-o"></i>
				</div>
				<a class="review-link font-size-12" href="#">Tổng sổ review</a>
			</div>
			<div>
				<h3 class="product-price"> ${data.price} đ</h3>
			</div>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
		
			<div class="add-to-cart">
				<div class="qty-label">
					Số lượng
					<div class="input-number">
						<input type="number" min="0" value="0" class = "quantity">
						<span class="qty-up" onclick="quantityUp()">+</span>
						<span class="qty-down" onclick="quantityDown()">-</span>
					</div>
				</div>
				<button class="add-to-cart-btn" onclick="addProductToCart(${data.id})"><i class="fa fa-shopping-cart"></i> add to cart</button>
			</div>
		
			<ul class="product-links">
				<li class = "font-size-12">Share:</li>
				<li class = "font-size-12"><a href="#"><i class="fa fa-facebook"></i></a></li>
				<li class = "font-size-12"><i class="fa fa-twitter"></i></a></li>
				<li class = "font-size-12"><i class="fa fa-google-plus"></i></a></li>
				<li class = "font-size-12"><i class="fa fa-envelope"></i></a></li>
			</ul>
		</div>
		`
	);

	$("#image-book").attr("src", `http://localhost:8081/api/file/${data.image}`)
	// render san phẩm liên quan
	productRelative(data.category.id)
	$(".js-name-category").text(data.category.name)
	$(".js-name-product").text(data.name)

	// render decription
	renderDecription(data.decription);
	// render inforBook
	renderInforBook(data);
	// render review Book
}

// product relative
function productRelative(categoryId) {
	$.ajax({
		url: urlBookBycategory + `/${categoryId}`,
		beforeSend: function(xhr) {
			xhr.setRequestHeader("Authorization", 'Bearer ' + token);
		},
		type: 'GET',
		dataType: "JSON",
		success: function(response) {
			console.log(response);
			renderRelativeProduct(response);
		},	
		error: function(xhr, status, message) {
			alert(message.message);
		}
	})
}
// render san pham lien quan
function renderRelativeProduct(data) {
	var html = `
		<div class="carousel-item active">
			<div class="row">
	`
	$.each($(data), function(i, item) {
		html += `
			<div class="col-lg-3 mb-5">
				<div class="product">
					<div class="product-img">
						<img src="http://localhost:8081/api/file/${item.image}" alt="" style = "object-fit: cover; height: 377px;">
					</div>
					<div class="product-body">
						<p class="product-category">${item.category.name}</p>
						<h3 class="product-name"><a href="#">${item.name}</a></h3>
						<h4 class="product-price">${item.price} <del class="product-old-price">$990.00</del></h4>
						<div class="product-rating">
							<i class="fa fa-star"></i>
							<i class="fa fa-star"></i>
							<i class="fa fa-star"></i>
							<i class="fa fa-star"></i>
							<i class="fa fa-star-o"></i>
						</div>
					</div>
					<div class="add-to-cart">
						<button class="add-to-cart-btn" onclick="addProductToCart(${item.id}, 1)"><i class="fa fa-shopping-cart"></i>Thêm vào giỏ hàng</button>
					</div>
				</div>
			</div> `
		if((i + 1) === data.length || (i + 1) % 4 === 0) {
			html +=
				`
					</div>
				</div>
				`
			if((i + 1) < data.length) {
				html += `
					<div class="carousel-item">
						<div class="row">
					`
			}
		}
	})
	$(".js-relative-product").html(html)
}

// render Mô tả sản phẩm
function renderDecription(decription) {
	$(".js-decription").html( `
		<div class="row">
			<div class="col-md-12 text-center">
				<p>${decription}</p>
			</div>
		</div>
	`);
}

// render inforProduct
function renderInforBook(inforProduct) {
	$(".js-inforBook").html( `
		<tr>
			<td class = "tdItem"> Tiêu đề: </td>
			<td class = "tdItem"> ${inforProduct.name}</td>
		</tr>
		<tr>
			<td class = "tdItem"> Tên tác giả: </td>
			<td class = "tdItem"> ${inforProduct.author} </td>
		</tr>
		<tr>
			<td class = "tdItem"> Thể loại: </td>
			<td class = "tdItem"> ${inforProduct.category.name} </td>
		</tr>
		<tr>
			<td class = "tdItem"> Ngày xuất bản: </td>
			<td class = "tdItem"> <input type="date" name="date" value="${inforProduct.publication_date}" disabled style = " background-color: #fff !important; border: none;"></td>
		</tr>
		<tr>
			<td class = "tdItem"> Tổng số trang: </td>
			<td class = "tdItem"> ${inforProduct.total_page} </td>
		</tr>
	`);
}


function quantityUp() {
	var quantity = $(".quantity").val();
	quantity ++;
	$(".quantity").val(quantity);
}
function quantityDown() {
	var quantity = $(".quantity").val();
	if(quantity > 0) 
		quantity--;
	$(".quantity").val(quantity);
}

function getParam() {
	var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}


// add book to cart
function addProductToCart(idBook, quantity) {
    var obj = {};
    if(quantity === undefined) {
        obj["quantity"] = $(".quantity").val();
    }
    else {
        obj['quantity'] = quantity;
    }
    callAPiAdd(obj, idBook)
}

function callAPiAdd(obj, idBook) {
    $.ajax({
        type: "POST",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        url: urlApiAddBookToCart + idBook,
        data: JSON.stringify(obj),
        dataType: "JSON",
		contentType: "application/json; charset=UTF-8",
        success: function (response) {
            console.log(response);
			alert("Thêm thành công!")
        },
        error: function(xhr, status, message) {
			alert("Thêm sản phẩm lỗi!")
            console.log(message.message);
        }
    });
    // console.log(obj)
}
