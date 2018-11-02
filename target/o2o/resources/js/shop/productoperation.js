$(function () {
    var productId = getQueryString("productId");
    var isEdit = productId ? true : false;
    var initUrl = "/o2o/productadmin/getproductinitinfo";
    var productInfoUrl = "/o2o/productadmin/getproductbyid?productId=" + productId;
    var addProductUrl = "/o2o/productadmin/addproduct";
    var editProductUrl = "/o2o/productadmin/modifyproduct";
    if (isEdit) {
        getInitInfo();
    } else {
        getProductCategoryInitInfo();
    }

    function getInitInfo() {
        $.getJSON(productInfoUrl, function (data) {
            if (data.success) {
                var product = data.product;
                $('#productName').val(product.productName);
                var productCategoryHtml ='';
                data.productCategoryList.map(function (item, index) {
                    productCategoryHtml += '<option data-id="'+item.productCategoryId+'">'+item.productCategoryName+
                        '</option>'
                })
                $('#priority').val(product.priority);
                $('#normalPrice').val(product.normalPrice);
                $('#promotionPrice').val(product.promotionPrice);
                $('#productDesc').val(product.productDesc);
                $('#productCategory').html(productCategoryHtml);
                $('#productCategory option[data-id="'+product.productCategory.productCategoryId+'"]').attr("selected","selected");
            }
        })
    }

    function getProductCategoryInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                data.productCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.productCategoryId + '">' +
                        item.productCategoryName + '</option>';
                });
                $('#productCategory').html(tempHtml);
            }
        });
    }

    $('#submit').click(function () {
        var product = {};
        if (isEdit) {
            product.productId = productId;
        }
        product.productName = $('#productName').val();
        product.productCategory = {
            productCategoryId: $('#productCategory').find('option').not(function () {
                return !this.selected;
            }).data("id")
        }
        product.priority = $('#priority').val();
        product.normalPrice = $('#normalPrice').val();
        product.promotionPrice = $('#promotionPrice').val();
        product.productDesc = $('#productDesc').val();
        var formData = new FormData();
        var productImg = $("#productImg")[0].files[0];
        formData.append("productImg", productImg);
        $('.productImg').map(function (index, item) {
            if ($(".productImg")[index].files.length > 0) {
                formData.append("productImg" + index, $(".productImg")[index].files[0]);
            }
        })
        formData.append("productStr", JSON.stringify(product))
        var captcha = $('#captcha').val();
        if (!captcha) {
            $.toast("请输入验证码");
            return;
        }
        formData.append("captcha", captcha);
        $.ajax({
            url: isEdit ? editProductUrl : addProductUrl,
            type: 'post',
            data: formData,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType: false,
            // 告诉jQuery不要去处理发送的数据
            processData: false,
            cache: true,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功');
                } else {
                    $.toast('提交失败' + data.errMsg);
                }
                $("#captchaImg").click();
            }
        })
    });

    $("#productImgList").on("change", ".productImg:last-child", function () {
        if ($('.productImg').length < 6) {
            $('#productImgList').append('<input type="file" class="productImg"/>')
        }
    });
})