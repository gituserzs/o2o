$(function () {
    var getProductListUrl = "/o2o/productadmin/getproductlist?pageNum=1&pageSize=999";
    var statusUrl = "/o2o/productadmin/changeenablestatus";

    initProductList();
    function initProductList() {
        $.getJSON(getProductListUrl, function (data) {
            if (data.success) {
                var html = '';
                data.productList.map(function (item, index) {
                    var op = "下架";
                    if (item.enableStatus == 0) {
                        op = "上架"
                    }
                    html += '<div class="row" style="border-top: 1px solid darkgray">' +
                        '<div class="col-40">' + item.productName + '</div>' +
                        '<div class="col-20">' + item.priority + '</div>' +
                        '<div class="col-40">' +
                        '<a class="external edit" href="/o2o/shopadmin/productoperation?productId=' + item.productId + '">编辑</a>' +
                        '<a class="status" data-id="' + item.productId + '"data-status="' + item.enableStatus + '" href="javascript:void(0);">' + op + '</a>' +
                        '<a href="#">预览</a> </div>' +
                        '</div>'
                });
                $(".product-wrap").html(html);
            }
        });
    }


    $(".product-wrap").on('click','a',function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('status')){
            var productId = target.data("id");
            var enableStatus = target.data("status");
            enableStatus = enableStatus == 0 ? 1 : 0;
            $.confirm("确定吗", function () {
                $.ajax({
                    url: statusUrl,
                    type: "post",
                    data: "productId=" + productId + "&enableStatus=" + enableStatus,
                    success: function (data) {
                        if (data.success) {
                            $.toast("操作成功");
                            initProductList()
                        } else {
                            $.toast("操作失败");
                        }
                    }
                })
            })
        }
    })
})