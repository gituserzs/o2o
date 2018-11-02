$(function () {
    var url = "/o2o/shopadmin/getproductcategorylist";
    var addUrl = "/o2o/shopadmin/addproductcategorys";
    var deleteUrl = "/o2o/shopadmin/removeproductcategory";
    getList();
    $("#add").click(function () {
        addLine();
    });
    $('#submit').click(function () {
        var tempArr = $('.temp');
        var productCategoryList = [];
        tempArr.map(function (index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url: addUrl,
            type: "POST",
            data: JSON.stringify(productCategoryList),
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功');
                    $('.productcategory-wrap').html("");
                    getList();
                } else {
                    $.toast('提交失败');
                }
            }
        })
    });

    $('.productcategory-wrap').on('click', '.temp-delete', function () {
        $(this).parent().parent().remove();
    });

    $('.productcategory-wrap').on('click', '.delete', function (e) {
        var target = e.currentTarget;
        $.confirm("确定删除?", function () {
            $.ajax({
                url: deleteUrl,
                type: "POST",
                data: {productCategoryId: target.dataset.id},
                dataType:"json",
                success:function (data) {
                    if(data.success){
                        $.toast("删除成功");
                        $('.productcategory-wrap').html("");
                        getList();
                    }else {
                        $.toast("删除失败");
                    }
                }
            })
        })
    });

    function getList() {
        $.ajax({
            url: url,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    // handleUser(data.user);
                    handleList(data.productCategoryList);
                }
            }
        });
    }

    function handleUser(user) {
        $("#userName").text(user.name);
    }

    function handleList(productCategoryList) {
        productCategoryList.map(function (item, index) {
            var html = '<div class="row" style="border-top: 1px solid darkgray">' +
                '<div class="col-33">' + item.productCategoryName + '</div>' +
                '<div class="col-33">' + item.priority + '</div>' +
                '<div class="col-33"><a href="javascript:void(0);" data-id="' + item.productCategoryId +
                '" class="button delete">删除</a></div>' +
                '</div>'
            $(".productcategory-wrap").append(html);
        });
    }

    function addLine() {
        var html = '<div class="row row-product-category temp" style="border-top: 1px solid darkgray">' +
            '<div class="col-33"><input class="category-input category" type="text" placeholder="类别"></div>' +
            '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>' +
            '<div class="col-33"><a href="#' +
            '" class="button temp-delete">删除</a></div>' +
            '</div>'
        $(".productcategory-wrap").append(html);
    }
});