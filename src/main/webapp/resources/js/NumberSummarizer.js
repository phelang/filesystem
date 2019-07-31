/// <reference path="/resources/frameworks/angular/angular.min.js"/>

var app = angular.module("NumberSummarizer", [])

app.controller("NumberSummarizerController", function ($scope, $http, $location, $anchorScroll){

    $scope.numbersForm = {
        numbers: "",
    };

    $scope.summarizedNumbers = "";

    $scope.summarizeNumbers = function (response) {
      console.log("Summarizing numbers");

        var method = 'POST';
        var url = '/summarizer/numbers';

        $http({
            method : method,
            url : url,
            data : angular.toJson($scope.numbersForm),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function successCallBack(response) {  /* HTTP status success */
            console.log(response.data);
            $scope.summarizedNumbers = response.data.numbers;
            }, function errorCallBack(response) {  /* HTTP status error */

            alert("Make sure input is in the expected format.");
        });

    }

});
