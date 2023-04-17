var urlGetOneBook = "http://localhost:8081/admin/book/";

// get id book from param
function getProductIdFromUrl() {
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}
var idBook = getProductIdFromUrl();
// console.log(idBook)
$.ajax({
    url: urlGetOneBook + idBook,
    type: "GET",
    dataType: "JSON",
    success: function(response) {
        console.log(response)
        // setValue(response)
    },
    error: function(xhr, status, error) {
        alert("error")
    }
})

// set valu in input
function setValue(data) {
    return `
        <!-- left -->
        <div class="col-lg-8 col-md-8">
            <!-- tiêu đề & tác giả -->
            <div class="row space-bottom">
                <!-- Tiêu đề -->
                <div class="col-md-4">
                    <label class="control-label font-label " for="title">Tiêu đề</label> <br>
                    <input type="text" name="title" id="title" class="form-contr-mol input-field" value = "${data.title}">
                </div>
                <!-- Tác giả -->
                <div class="col-md-4">
                    <label class="control-label font-label" for="author">Tác Giả</label> <br>
                    <input type="text" name="author" id="author" class="form-contr-mol input-field" value = "${data.author}">
                </div>
            </div>

            <!-- Mô tả -->
            <div class="row space-bottom" style="padding: 0 12px;">
                <label class="control-label font-label" for="dicription">Mô tả về cuốn sách</label> <br>
                <textarea class="form-control" id="dicription" name="dicription" rows="8" cols="30" value = "${data.decription}"></textarea>
            </div>

            <!-- Ngày phát hành & số trang -->
            <div class="row space-bottom">
                <!-- Ngày phát hành -->
                <div class="col-md-4">
                    <label class="control-label font-label" for="date_publication">Ngày phát hành</label> <br>
                    <input type="date" name="date_publication" id="date_publication" class="form-contr-mol input-field" value = "${data.date_publication}">
                </div>
                <!-- Số trang -->
                <div class="col-md-4">
                    <label class="control-label font-label" for="total_page">Số trang</label> <br>
                    <input type="number" name="total_page" id="total_page" class="form-contr-mol input-field" min="0" value = "${data.total_page}">
                </div>
            </div>

            <!-- Thể loại -->
            <div class="row space-bottom" style="margin: 0;">
                <label class="control-label font-label" for="category">Thể loại</label> <br>
                <select id="category" name="category" class="form-control input-field" disabled>
                    <option value="1">kinh dị</option>
                    <option value="2">Trinh thám</option>
                    <option value="3">Tiểu thuyết</option>
                    <option value="4">Cổ tích</option>
                </select>
            </div>
        </div>
        <!-- right -->
        <div class="col-lg-4" style="display: flex; flex-flow: column; align-items: center; margin-top: 22px;">
            <div class="form-upload">
                <label for="image">Hình ảnh</label>
                <input type="file" class="form-control-file" name="image" id="image" value="upload">
            </div>
            <div class="image" style="margin-top: 12px;">
                <img src="./asset/image/tải xuống.jpg" alt="" style="object-fit: cover; height: 333px;">
            </div>
        </div>    
    `
}