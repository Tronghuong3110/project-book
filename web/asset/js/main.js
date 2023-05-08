// (function($) {
// 	"use strict"

// 	// Mobile Nav toggle
// 	$('.menu-toggle > a').on('click', function (e) {
// 		e.preventDefault();
// 		$('#responsive-nav').toggleClass('active');
// 	})

// 	// Fix cart dropdown from closing
// 	$('.cart-dropdown').on('click', function (e) {
// 		e.stopPropagation();
// 	});

// 	/////////////////////////////////////////

// 	// Products Slick
// 	$('.products-slick').each(function() {
// 		var $this = $(this),
// 				$nav = $this.attr('data-nav');

// 		$this.slick({
// 			slidesToShow: 4,
// 			slidesToScroll: 1,
// 			autoplay: true,
// 			infinite: true,
// 			speed: 300,
// 			dots: false,
// 			arrows: true,
// 			appendArrows: $nav ? $nav : false,
// 			responsive: [{
// 	        breakpoint: 991,
// 	        settings: {
// 	          slidesToShow: 2,
// 	          slidesToScroll: 1,
// 	        }
// 	      },
// 	      {
// 	        breakpoint: 480,
// 	        settings: {
// 	          slidesToShow: 1,
// 	          slidesToScroll: 1,
// 	        }
// 	      },
// 	    ]
// 		});
// 	});

// 	// Products Widget Slick
// 	$('.products-widget-slick').each(function() {
// 		var $this = $(this),
// 				$nav = $this.attr('data-nav');

// 		$this.slick({
// 			infinite: true,
// 			autoplay: true,
// 			speed: 300,
// 			dots: false,
// 			arrows: true,
// 			appendArrows: $nav ? $nav : false,
// 		});
// 	});

// 	/////////////////////////////////////////

// 	// Product Main img Slick
// 	$('#product-main-img').slick({
//     infinite: true,
//     speed: 300,
//     dots: false,
//     arrows: true,
//     fade: true,
//     asNavFor: '#product-imgs',
//   });

// 	// Product imgs Slick
//   $('#product-imgs').slick({
//     slidesToShow: 3,
//     slidesToScroll: 1,
//     arrows: true,
//     centerMode: true,
//     focusOnSelect: true,
// 		centerPadding: 0,
// 		vertical: true,
//     asNavFor: '#product-main-img',
// 		responsive: [{
//         breakpoint: 991,
//         settings: {
// 					vertical: false,
// 					arrows: false,
// 					dots: true,
//         }
//       },
//     ]
//   });

// 	// Product img zoom
// 	var zoomMainProduct = document.getElementById('product-main-img');
// 	if (zoomMainProduct) {
// 		$('#product-main-img .product-preview').zoom();
// 	}

// 	/////////////////////////////////////////

// 	// Input number
// 	$('.input-number').each(function() {
// 		var $this = $(this),
// 		$input = $this.find('input[type="number"]'),
// 		up = $this.find('.qty-up'),
// 		down = $this.find('.qty-down');

// 		down.on('click', function () {
// 			var value = parseInt($input.val()) - 1;
// 			value = value < 1 ? 1 : value;
// 			$input.val(value);
// 			$input.change();
// 			updatePriceSlider($this , value)
// 		})

// 		up.on('click', function () {
// 			var value = parseInt($input.val()) + 1;
// 			$input.val(value);
// 			$input.change();
// 			updatePriceSlider($this , value)
// 		})
// 	});

// 	var priceInputMax = document.getElementById('price-max'),
// 			priceInputMin = document.getElementById('price-min');

// 	priceInputMax.addEventListener('change', function(){
// 		updatePriceSlider($(this).parent() , this.value)
// 	});

// 	priceInputMin.addEventListener('change', function(){
// 		updatePriceSlider($(this).parent() , this.value)
// 	});

// 	function updatePriceSlider(elem , value) {
// 		if ( elem.hasClass('price-min') ) {
// 			console.log('min')
// 			priceSlider.noUiSlider.set([value, null]);
// 		} else if ( elem.hasClass('price-max')) {
// 			console.log('max')
// 			priceSlider.noUiSlider.set([null, value]);
// 		}
// 	}

// 	// Price Slider
// 	var priceSlider = document.getElementById('price-slider');
// 	if (priceSlider) {
// 		noUiSlider.create(priceSlider, {
// 			start: [1, 999],
// 			connect: true,
// 			step: 1,
// 			range: {
// 				'min': 1,
// 				'max': 999
// 			}
// 		});

// 		priceSlider.noUiSlider.on('update', function( values, handle ) {
// 			var value = values[handle];
// 			handle ? priceInputMax.value = value : priceInputMin.value = value
// 		});
// 	}

// })(jQuery);



const url = "http://localhost:8081/api/user/books";
const urlCategory = "http://localhost:8081/api/user/categories";
const token = localStorage.getItem("token");
if(!token) {
	window.location.href = "/login/login.html";
}
function start() {
	$.ajax({
		type: "GET",
		beforeSend: function(xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			// console.log('Authorization Header: '+ 'Bearer ' + token);
		},
		url: url + "?key",
		dataType: "JSON",
		success: function (response) {
			console.log(response);
			render(response)
		},
		error: function(xhr, status, error) {
			alert("Get all book error!")
		}
	});
}
start();

// function render book
function render(data) {
	var html = '<div class="row">';
	$.each($(data), function(i, item) {
		html += 
			`
				<div class="col-lg-3 mb-5">
					<div class="product product-display">
						<div class="product-img">
							<img src="http://localhost:8081/api/file/${item.image}" alt="Ảnh sản phẩm thử ${i + 1}" style="object-fit: cover; height: 300px;">
							<div class="product-label">
								<span class="sale">-30%</span>
								<span class="new">NEW</span>
							</div>
						</div>
						<div class="product-body">
							<p class="product-category" style="font-weight: 500; font-size: 1rem; color: #000;">${item.category.name}</p>
							<h3 class="product-name"><a href="#">${item.name}</a></h3>
							<h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
							<div class="product-rating">
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
							</div>
							<div class="product-btns">
								<button class="add-to-wishlist"><i class="fa fa-heart-o"></i><span class="tooltipp">add to wishlist</span></button>
								<button class="add-to-compare"><i class="fa fa-exchange"></i><span class="tooltipp">add to compare</span></button>
								<button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
							</div>
						</div>
						<div class="add-to-cart add-to-cart-new">
							<button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
						</div>
					</div>
				</div>
			`
		// console.log((i + 1))
		if((i + 1) % 4 == 0 || (i + 1) == data.length) {
			html += `</div>`
			if((i + 1) < data.length) {
				html += `<div class="row">` 
			}
		}
	})
	$(".js-render").html(html);
}

// render category 
$.ajax({
	type: "GET",
	beforeSend: function(xhr) {
        xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        // console.log('Authorization Header: '+ 'Bearer ' + token);
    },
	url: urlCategory + "?key",
	dataType: "JSON",
	success: function (response) {
		console.log(response);
		renderCategory(response);
	},
	error: function(xhr, status, error) {
        alert("Get all book error!")
    }
});

function renderCategory(data) {
	$.each($(data), function(i, item) {
		$(".js-render-category").append(
			`
				<li class="nav-item" onclick="renderBookByCategory(${item.id})"><a class="nav-link font-nav" href="#">${item.name}</a></li>
			`
		)
	})
}

$(".js-btn-logout").click(function (e) { 
	e.preventDefault();
	localStorage.clear();
	window.location.href = '/login/login.html';
});

function renderBookByCategory(categoryId) {
	$.ajax({
		url: url + "/" + categoryId,
		beforeSend: function(xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			// console.log('Authorization Header: '+ 'Bearer ' + token);
		},
		type: "GET",
		dataType: "JSON",
		success: function(response) {
			render(response);
		},
		error: function(xhr, status, error) {
			alert("Get book by category error!")
		}
	})
	console.log(parseJwt(token).sub)
}

function parseJwt(token) {
    if (!token) { 
        return;
    }
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
}