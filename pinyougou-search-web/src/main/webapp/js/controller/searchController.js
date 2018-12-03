app.controller("searchController",function ($scope,searchService) {

    //搜索对象
    $scope.searchMap = {'keywords':'','category':'','brand':'','spec':{},'price':''};

    $scope.search = function () {
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap = response;
            }
        )
    };

    //添加搜索项
    $scope.addSearchItem = function (key,value) {
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchMap[key] = value;
        }else {
            $scope.searchMap.spec[key] = value;
        }
        $scope.search();//执行搜索
    };

    //移除搜索项
    $scope.removeSearchItem = function (key) {
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchMap[key] = '';
        }else {
            delete $scope.searchMap.spec[key];//移除此属性
        }
        $scope.search();//执行搜索
    }
});