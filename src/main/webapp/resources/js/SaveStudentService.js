/// <reference path="/resources/frameworks/angular/angular.min.js"/>

var app = angular.module("SaveStudent", []);

app.controller("SaveStudentController", function ($scope, $http, $location, $anchorScroll) {

    $scope.studentForm = {
        name: "",
        surname: ""
    };

    $scope.studentSavedResponse = [];

    $scope.createStudent = function (response) {
        var method = 'POST';
        var url = '/student/add';

        $http({
            method : method,
            url : url,
            data : angular.toJson($scope.studentForm),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallBack(response) {  /* HTTP status success */
            $scope.studentSavedResponse = response.data;
            alert($scope.studentForm.name + " " + $scope.studentForm.surname + " " + "saved.");
            clearForm(); /* Clear form data */
        }, function errorCallBack(response) {  /* HTTP status error */
            alert("Could Not save " + $scope.studentForm.name + " " + $scope.studentForm.surname);
        });
    }

    /* Clear form data */
    function clearForm(){
        $scope.studentForm.name = "";
        $scope.studentForm.surname = "";
    }

    $scope.scrollTo = function(id) {
        $location.hash(id);
        $anchorScroll();
    }

});