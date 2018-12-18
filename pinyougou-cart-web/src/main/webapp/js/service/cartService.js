//购物车服务层
app.service('cartService',function($http){
    //购物车列表
    this.findCartList=function(){
        return $http.get('cart/findCartList.do');
    };

    //添加商品到购物车
    this.addGoodsToCartList=function(itemId,num){
        return $http.get('cart/addGoodsToCartList.do?itemId='+itemId+'&num='+num);
    };
    
    this.sum = function (cartList) {
        var totalValue = {totalNum:0,totalFee:0};
        for (var i=0;i<cartList.length;i++) {
            for (var j=0; j<cartList[i].orderItemList.length;j++) {
                totalValue.totalNum += cartList[i].orderItemList[j].num;
                totalValue.totalFee += cartList[i].orderItemList[j].totalFee;
            }
        }
        return totalValue;
    }
});