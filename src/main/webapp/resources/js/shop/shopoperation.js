$(function(){
    var shopId = getQueryString("shopId");
    var isEdit = shopId ? true : false;
    var initUrl = "/o2o/shopadmin/getshopinitinfo";
    var registerShopUrl = "/o2o/shopadmin/registershop";
    var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId=" + shopId;
    var editShopUrl = "/o2o/shopadmin/modifyshop";
    if (isEdit) {
        getShopInfo(shopId);
    } else {
        getShopInitInfo();
    }
    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl,function (data) {
            if (data.success) {
                var shop = data.shop;
                $("#shopName").val(shop.shopName);
                $("#shopAddr").val(shop.shopAddr);
                $("#phone").val(shop.phone);
                $("#shopDesc").val(shop.shopDesc);
                var shopCategory = '<option data-id="' + shop.shopCategory.shopCategoryId + '" selected>' +
                    shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml;
                data.areaList.map(function (item,index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' +
                        item.areaName +'</option>'
                });
                $("#shopCategory").html(shopCategory);
                $("#shopCategory").attr("disabled","disabled");
                $("#shopArea").html(tempAreaHtml);
                $("#shopArea option[data-id='" + shop.area.areaId + "']").attr("selected","selected");
                // $('#shopArea').attr('data-id',shop.area.areaId);

            }
        })
    }
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' +
                        item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' +
                        item.areaName + '</option>';
                });
                $('#shopCategory').html(tempHtml);
                $('#shopArea').html(tempAreaHtml);
            }
        });
    }
    $('#submit').click(function () {
        var shop = {};
        if (isEdit) {
            shop.shopId = shopId;
        }
        shop.shopName = $('#shopName').val();
        shop.shopAddr = $('#shopAddr').val();
        shop.phone = $('#phone').val();
        shop.shopDesc = $('#shopDesc').val();
        shop.shopCategory = {
            shopCategoryId : $('#shopCategory').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId : $('#shopArea').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var shopImg = $('#shopImg')[0].files[0];
        var captcha = $("#captcha").val();
        if(!captcha){
            $.toast("请输入验证码");
            return;
        }
        var formData = new FormData();
        formData.append('shop',JSON.stringify(shop));
        formData.append('shopImg',shopImg);
        formData.append('captcha',captcha);
        $.ajax({
            url:(isEdit? editShopUrl:registerShopUrl),
            type:'post',
            data:formData,
            // 告诉jQuery不要去处理发送的数据
            processData : false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType : false,
            success:function (data) {
                if (data.success){
                    $.toast('提交成功');
                } else {
                    $.toast('提交失败' + data.errMsg);
                }
                $("#captchaImg").click();
            }
        })
    })
})