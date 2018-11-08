app.controller('brandController',function ($scope,$controller,brandService) {

    $controller('baseController',{$scope:$scope});

    $scope.findAll = function () {
        brandService.findAll().success(function (response) {
            $scope.list = response;
        })
    };


    //分页查询
    $scope.findPage = function (pageNum, pageSize) {
        brandService.findPage(pageNum, pageSize)
            .success(function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;
            })
    };

    //新增
    $scope.save = function () {
        var object = null;
        if ($scope.entity.id != null) {
            object = brandService.update($scope.entity);
        } else {
            object = brandService.add($scope.entity);
        }
        object.success(function (response) {
            if (response.success) {
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        })
    };

    //修改-先查找
    $scope.findOne = function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    };


    //删除
    $scope.del = function () {
        if (confirm('确定要删除吗？')) {
            brandService.dele($scope.selectIds)
                .success(function (response) {
                    if (response.success) {
                        $scope.reloadList();
                    } else {
                        alert(response.message)
                    }
                })
        }
    };

    $scope.searchEntity = {};
    $scope.search = function (pageNum, pageSize) {
        brandService.search(pageNum, pageSize, $scope.searchEntity)
            .success(function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;
            })
    }
});