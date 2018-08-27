$(function () {
    function show(cid) {

    }
    $(".m-menu li").mouseenter(function () {
        var cid = $(this).attr("cid");
        $(".d-menu[cid=" + cid + "]").show();
        $(this).find("a,span").css("color", "#FF0036");

    });
    $(".m-menu li").mouseleave(function () {
        var cid = $(this).attr("cid");
        $(".d-menu[cid=" + cid + "]").hide();
        $(this).find("a,span").css("color", "");
    });
    $(".d-menu").mouseenter(function () {
        $(this).show();
        var cid = $(this).attr("cid");
        $(".m-menu li[cid="+cid+"]").css("background", "white");
        $(".m-menu li[cid="+cid+"]").find("a,span").css("color", "#FF0036");
    });
    $(".d-menu").mouseleave(function () {
        $(this).hide();
        var cid = $(this).attr("cid");
        $(".m-menu li[cid="+cid+"]").css("background", "");
        $(".m-menu li[cid="+cid+"]").find("a,span").css("color", "");
    });
    $(".product-items .item").mouseenter(function(){
        $(this).css("border-color","#FF0036");
    });
    $(".product-items .item").mouseleave(function(){
        $(this).css("border-color","");
    })
});

/*
$(function(){
    $("#keyword").keyup(function () {
        var value=($("#keyword").val().trim());
        $(".autoSearch").css("display", "none");
        $(".autoSearch").empty();
        var content=$("<ul></ul>").addClass("proposal-list");
        $.ajax({
            type:"post",
            url:  "http://localhost:8080/TMALL/searchProductName",
            data: {"keyword":value},
            dataType : "json",
            success: function(data){
              $.each(data,function (i,v) {
                  if(data.length!=0 && i<6) {
                      var element = $("<li></li>").html(v).addClass("proposal").mouseenter(function () {
                          $(this).addClass('selected');
                      }).mouseleave(function () {
                          $(this).removeClass('selected');
                      }).click(function () {
                          $("#keyword").val($(this).html());
                          $(".autoSearch").empty();
                          $(".autoSearch").css("display","none");
                      });
                      content = content.append(element);
                  }
              });
            $(".autoSearch").append(content);
            }
        });
        if (0 != value.length ) {
            $(".autoSearch").css("display", "block");
        }
    });

    $(document).click(function () {
        $(".autoSearch").empty();
        $(".autoSearch").css("display","none");
    });
});
    */