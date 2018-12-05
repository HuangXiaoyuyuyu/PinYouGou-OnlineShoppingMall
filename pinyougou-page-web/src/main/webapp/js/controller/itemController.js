app.controller('itemController',function ($scope) {

    $scope.specificationItem = {};//记录用户选择的规格

    //用户选择规格
    $scope.selectSpecification = function (key,value) {
        $scope.specificationItem[key] = value;
        searchSku();//查询SKU
    };

    //判断某规格选项是否被用户选中
    $scope.isSelected = function (key,value) {
        if ($scope.specificationItem[key] == value) {
            return true;
        } else {
            return false;
        }
    };

    //数量操作
    $scope.addNum = function (x) {
        $scope.num = parseInt($scope.num);
        $scope.num += x;
        if ($scope.num < 1) {
            $scope.num = 1;
        }
    };

    $scope.sku = {};  //当前选择的SKU
    //加载默认的SKU
    $scope.loadSku = function () {
        $scope.sku = skuList[0];
        $scope.specificationItem = JSON.parse(JSON.stringify($scope.sku.spec));
    }

    //匹配两个对象是否相等
    matchObject=function(map1,map2){
        for(var k in map1){
            if(map1[k]!=map2[k]){
                return false;
            }
        }
        for(var k in map2){
            if(map2[k]!=map1[k]){
                return false;
            }
        }
        return true;
    }

    //查询SKU
    searchSku = function () {
        for (var i=0; i<skuList.length; i++) {
            if (matchObject(skuList[i].spec,$scope.specificationItem)) {
                $scope.sku = skuList[i];
                return;
            }
        }
        $scope.sku={id:0,title:'--------',price:0}
    }

    $scope.addToCart = function () {
        alert("SKUID： " + $scope.sku.id);
    }
});