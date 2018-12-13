 //控制层 
app.controller('userController' ,function($scope,$controller,userService){

    //注册
    $scope.reg = function () {
        if ($scope.entity.password != $scope.password) {
            alert("两次输入的密码不一样，请重新输入");
            return ;
        }
        userService.add($scope.entity,$scope.smsCode).success(
            function (response) {
                alert(response.message);
            }
        )
    };

    //发送验证码
    $scope.sendCode = function () {
        if ($scope.entity.phone == null) {
            alert("请输入手机号！");
            return ;
        }
        userService.sendCode($scope.entity.phone).success(
            function (response) {
                alert(response.message);
            }
        )
    };
});	
