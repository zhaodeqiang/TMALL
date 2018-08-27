function changeSelect(tr) {
    //传入一个jquery-tr元素，修改它的选择属性
    if ($(tr).attr("select") === "false") {
        $(tr).find("td img.select-img").attr("src", "img/select.png");
        $(tr).attr("select", "true");
        $(tr).css("background-color", "#FFF8E1");
    } else if ($(tr).attr("select") === "true") {
        $(tr).find("td img.select-img").attr("src", "img/noselect.png");
        $(tr).attr("select", "false");
        $(tr).css("background-color", "#fff");
    }
}

function calculate(table) {
    //传入一个jquery-table元素，计算已选件数和总价
    var selectNum = 0;
    var sumPrice = 0;
    var allSelected = true;
    $(table).find("tr").each(function () {
        if (!isNaN(Number($(this).attr("price-per")))) {
            if ($(this).attr("select") === "true") {
                selectNum += Number($(this).attr("buy"));
                sumPrice += Number($(this).attr("buy")) * Number($(this).attr("price-per"));
            } else {
                allSelected = false;
            }
        }
    });
    return [selectNum, sumPrice.toFixed(2), allSelected];
}

function sumUpdate() {
    //结算更新器，更新按钮颜色 已选商品数量 更新全选按钮，单品总价也更新
    result = calculate($("table.cart-list"));
    $(".cart-num").text(result[0]);
    $(".cart-sum-price").text(result[1]);
    //如果没选满
    if (result[0] === 0) {
        $(".crate-order").css({"background-color": "#aaa", "cursor": "not-allowed"});
        $(".crate-order").attr("disabled", "disabled");
    } else {
        $(".crate-order").css({"background-color": "#FF0036", "cursor": "pointer"});
        $(".crate-order").attr("disabled", false);
    }

    //如果全部选满
    if (result[2]) {
        $(".select-all").attr("src", "img/select.png");
    } else {
        $(".select-all").attr("src", "img/noselect.png");
    }
}

function changeCartNum(num, ciid, sum) {
    //后台提交ajax修改数据库的商品数量
    var page = "changeCartNum";
    $.get(page, {"id": ciid, "num": num, "sum": sum},
        function (result) {
            if (result != "success") {
                //  已经控制了传到controller层的数量 num<=stock
                return false;
        }
            //location.reload(true); 效果不佳
            /*BUG：当在购物车页面改变数量提交购物项后又返回页面，页面的价格还是修改数量之前的价格，需要刷新页面才会改变*/
            return true;
        });
}

var deleteCiid = 0;
$(function () {
    sumUpdate();
    $("table.cart-list tbody .select-img").click(function () {
        changeSelect($(this).parents("tr"));
        sumUpdate();
    });

    $(".select-all").click(function () {
        if ($(this).attr("select") === "false") {
            $(this).attr("select", "true");
            //如果点击的时候全选是空的，那么现在要全选
            $("table.cart-list tbody tr").each(function () {
                //先全不选
                $(this).attr("select", "false");
                //再反选上色
                changeSelect($(this));
            });
            $(".select-all").attr("src", "img/select.png");
        } else if ($(this).attr("select") === "true") {
            $(this).attr("select", "false");
            $("table.cart-list tbody tr").each(function () {
                //先全选
                $(this).attr("select", "true");
                //再反选上色
                changeSelect($(this));
            });
            $(".select-all").attr("src", "img/noselect.png");
        }
        sumUpdate();
    });

    /*需要更新数据库*/
    $(".cart-change-button").click(function () {
        var tr = $(this).parents("tr");
        var stock = Number($(tr).attr("stock"));
        var pricePer = Number($(tr).attr("price-per"));
        var ciid = Number($(tr).attr("ciid"));
        var num = Number($(tr).find(".item-num-input").val());
        if (num < stock && $(this).attr("class").indexOf("increase") > 0) {
            $(tr).find(".item-num-input").val(++num);
        } else if (num > 1 && $(this).attr("class").indexOf("decrease") > 0) {
            $(tr).find(".item-num-input").val(--num);
        }
        var sum = (pricePer * num).toFixed(2);
        changeCartNum(num, ciid, sum);
        $(tr).attr("buy", num);
        $(tr).find(".cart-item-sum").html("￥" + (pricePer * num).toFixed(2));
        sumUpdate();
    });
    /*手动修改数量*/
    $(".item-num-input").keyup(function () {
        var tr = $(this).parents("tr");
        var ciid = Number($(tr).attr("ciid"));
        var stock = Number($(tr).attr("stock"));
        var pricePer = Number($(tr).attr("price-per"));
        var num = parseInt(($(tr).find(".item-num-input").val()));
        if (isNaN(num) || num < 0) {
            num = 1;
        } else if (num > stock) {
            num = stock
        }
        $(tr).find(".item-num-input").val(num);
        $(tr).attr("buy", num);
        $(tr).find(".cart-item-sum").html("￥" + (pricePer * num).toFixed(2));
        changeCartNum(num, ciid, (pricePer * num).toFixed(2));
        sumUpdate();
    });

    $(".delete-button").click(function () {
        deleteCiid = $(this).attr("ciid");
        $("#confirmModal").modal('show');
    });

    $("#confirmDelete").click(function () {
        $("#confirmModal").modal("hide");
        var page = "deleteCartItem";
        $.get(page, {"id": deleteCiid}, function (result) {
            if (result === "success") {
                $("tr[ciid=" + deleteCiid + "]").remove();
                sumUpdate();
            } else {
                $('body').toast({
                    content:'删除失败！',
                    duration:1000,
                    isCenter:true,
                    background:'rgba(230,0,0,0.5)',
                    animateIn:'bounceIn-hastrans',
                    animateOut:'bounceOut-hastrans',
                });
            }
        });
    });

    $("#cancelDelete").click(function () {
        $("#confirmModal").modal("hide");
    });

    /* $("#countMoney").click(function () {
         var id = [];
         $("table").find("tbody").find("tr").each(function (i) {
             if ($(this).attr("select") == "true") {
                 var ciid = Number($(this).attr("ciid"));
                 id[i] = ciid;
             }
         });*/

    /*只提交被选中的商品*/
    $("#cartForm").submit(function () {
        $("table").find("tbody").find("tr").each(function () {
            if ($(this).attr("select") == "false") {
                /*必须找当前<tr>下的input标签*/
                $(this).find("input#ciid").remove();
            }
        });
        return true;
    });
});