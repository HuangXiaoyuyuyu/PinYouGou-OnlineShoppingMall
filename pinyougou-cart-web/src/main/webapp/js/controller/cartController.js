//购物车控制层
app.controller('cartController',function($scope,cartService){
    //查询购物车列表
    $scope.findCartList=function(){
        cartService.findCartList().success(
            function(response){
                $scope.cartList=response;
                $scope.totalValue= cartService.sum($scope.cartList);
            }
        );
    };
    
    $scope.addGoodsToCartList = function (itemId,num) {
        cartService.addGoodsToCartList(itemId,num).success(
            function (response) {
                if (response.success) {
                    $scope.findCartList();
                } else {
                    alert(response.message);
                }
            }
        );
    };

    //获取地址列表
    $scope.findAddressList=function(){
        cartService.findAddressList().success(
            function(response){
                $scope.addressList=response;
                for (var i=0; i<$scope.addressList.length; i++) {
                    if ($scope.addressList[i].isDefault == '1') {
                        $scope.address = $scope.addressList[i];
                        break;
                    }
                }
            }
        );
    };

    $scope.selectAddress = function (address) {
        $scope.address = address;
    };

    $scope.isSelectedAddress = function (address) {
        if (address == $scope.address) {
            return true;
        } else {
            return false;
        }
    };

});