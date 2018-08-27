$(function(){
    var showItem2 = function(){
        var low = $("input#low_price").val();
        if(isNaN(low) || low<=0 || low === ""){
            low = 0
        }
        var high = $("input#high_price").val();
        if(isNaN(high) || high===""){
            high =  Number.MAX_VALUE;//javaScript中最大的数
        }
        $("div.border").show();
        $("div.border").each(function(){
            var price = $(this).attr("price");//获取属性为price的值
            price = Number(price);//Number() 函数把对象的值转换为数字。
            if(price<low || price>high){
                $(this).hide();
            }
        });
    };
    $("input").keyup(showItem2);
});