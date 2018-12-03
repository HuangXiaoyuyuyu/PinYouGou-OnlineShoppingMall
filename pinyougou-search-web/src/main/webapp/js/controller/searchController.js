app.controller("searchController",function ($scope,searchService) {

    //搜索对象
    $scope.searchMap = {'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':10,'sort':'','sortField':''};

    $scope.search = function () {
        $scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo);
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap = response;
                buildPageLabel();   //构建分页栏
            }
        )
    };

    buildPageLabel = function() {
        $scope.pageLabel = [];//新增分页栏属性
        var firstPage = 1;//开始页码
        var lastPage = $scope.resultMap.totalPages;//最后页码
        $scope.firstDot = true;//前面有点
        $scope.lastDot = true;//后面有点

        if ($scope.resultMap.totalPages > 5) {//如果总页数大于5页 显示部分页码
            if ($scope.searchMap.pageNo <= 3) {//如果当前页小于3
                lastPage = 5;   //显示前五页
                $scope.firstDot = false; //前面没点
            }else if ($scope.searchMap.pageNo >= lastPage -2) { //如果当前页码大于最大页码-2
                firstPage = lastPage - 4; //显示后五页
                $scope.lastDot = false; //后面没点
            }else { //显示当前页为中间页
                firstPage = $scope.searchMap.pageNo - 2;
                lastPage = $scope.searchMap.pageNo + 2;
            }
        }
        //循环产生页码标签
        for (i=firstPage; i<=lastPage; i++) {
            $scope.pageLabel.push(i);
        }
    };

    //根据页码查询
    $scope.queryByPage = function(pageNo) {
        if (pageNo < 1 || pageNo > $scope.resultMap.totalPages) {
            return ;
        }
        $scope.searchMap.pageNo = pageNo;
        $scope.search();
    };

    //判断当前页是否为第一页
    $scope.isTopPage = function() {
        if ($scope.searchMap.pageNo == 1) {
            return true;
        } else {
            return false;
        }
    };
    //判断当前页是否为最后一页
    $scope.isEndPage = function() {
        if ($scope.searchMap.pageNo == $scope.resultMap.totalPages) {
            return true;
        } else {
            return false;
        }
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