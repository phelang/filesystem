/// <reference path="/resources/frameworks/angular/angular.min.js"/>

    'use strict'

    //angular module
    var app= angular.module('FileSystem', ['angularTreeview']);


    //test controller
    app.controller('FileSystemController', function ($scope, $http, $location, $anchorScroll){

        //temporary node
        $scope.temporaryNode = {
            children: []
        };

        $scope.roleList = [];

        $scope.directoTree = {}

       var init = function () {

           var method = 'POST';
           var url = '/directory/tree';

            $http({
                method: method,
                url: url,
                data: angular.toJson($scope.directoTree),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function successCallBack(response) {  /!* HTTP status success *!/

                $scope.roleList = response.data;
                console.log(response.data);

            }, function errorCallBack(response) {  /!* HTTP status error *!/
                alert("Could not find Directory tree.");
            });

        };

        init();

        console.log($scope.roleList3);

       /*//test tree model
        $scope.roleList = [
            {
                label : "UangularTreeviewser",
                id : "role1",

                children : [
                    {
                        label : "subUser1",
                        id : "role11",
                        children : []
                    },

                    { label : "subUser2",
                        id : "role12",
                        children : [
                            { label : "subUser2-1",
                                id : "role121",
                                children : [
                                    {
                                        label : "subUser2-1-1",
                                        id : "role1211",
                                        children : []
                                    },
                                    {
                                        label : "subUser2-1-2",
                                        id : "role1212",
                                        children : []
                                    }
                                ]}
                        ]}
                ]},

            { label : "Admin", id : "role2", children : [] },

            { label : "Guest", id : "role3", children : [] }
        ];
*/


        $scope.directoryForm = {
            name: "",
        };

        $scope.createDirectory = function () {
            console.log("File System");

            console.log($scope.directoryForm.name);


            var method = 'POST';
            var url = '/directory/add';

            $http({
                method : method,
                url : url,
                data : angular.toJson($scope.directoryForm),
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).then(function successCallBack(response) {  /* HTTP status success */

                console.log(response.data);
                init();

            }, function errorCallBack(response) {  /* HTTP status error */
                alert("Could Not Add");
            });
        };

        /**
         * CREATE DIRECTORY from NAME to NAME
         * @type {{toDirectoryName: string, name: string}}
         */
        $scope.newDirectoryForm = {
            toDirectoryName: "",
            name: ""
        };

        $scope.createToDirectory = function(response){

            $scope.temporaryNode = angular.copy($scope.mytree.currentNode);
            console.log($scope.temporaryNode.label);

            $scope.newDirectoryForm.toDirectoryName = $scope.temporaryNode.label;

            console.log($scope.newDirectoryForm);

            var method = 'POST';
            var url = '/directory/create';

            $http({
                method : method,
                url : url,
                data : angular.toJson($scope.newDirectoryForm ),
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).then(function successCallBack(response) {  /* HTTP status success */

                console.log(response.data);
                init();
                $scope.done();

            }, function errorCallBack(response) {  /* HTTP status error */
                alert("Could Not Add");
            });
        } ;

        $scope.deleteDirectoryForm = {
            name: "SUB DIRECTORY TESTING"
        };
        $scope.deleteForm = function(response){

            var method = 'POST';
            var url = '/directory/delete';

            $http({
                method : method,
                url : url,
                data : angular.toJson($scope.deleteDirectoryForm),
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).then(function successCallBack(response) {  /* HTTP status success */

                console.log(response.data);
                init();

            }, function errorCallBack(response) {  /* HTTP status error */
                alert("Could Not Delete");
            });

        };

        $scope.updateDirectoryForm = {
            toUpdateName: "",
            name: ""
        };
        $scope.updateDirectory = function(response){

            $scope.temporaryNode = angular.copy($scope.mytree.currentNode);

            $scope.updateDirectoryForm.toUpdateName = $scope.temporaryNode.label;

            var method = 'POST';
            var url = '/directory/update';

            $http({
                method : method,
                url : url,
                data : angular.toJson($scope.updateDirectoryForm),
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).then(function successCallBack(response) {  /* HTTP status success */

                console.log(response.data);
                init();
                $scope.done();

            }, function errorCallBack(response) {  /* HTTP status error */
                alert("Could Not Add");
            });
        } ;

        $scope.moveDirectoryForm = {
            moveDirectoryName: "phelang",
            toDirectoryName: "hanlie"
        };
        $scope.moveDirectory = function(response){

            var method = 'POST';
            var url = '/directory/move';

            $http({
                method : method,
                url : url,
                data : angular.toJson($scope.moveDirectoryForm),
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).then(function successCallBack(response) {  /* HTTP status success */

                console.log(response.data);
                init();

            }, function errorCallBack(response) {  /* HTTP status error */
                alert("Could Not Move");
            });
        } ;

        $scope.done = function () {

            /* reset */
            $scope.mytree.currentNode.selected = undefined;
            $scope.mytree.currentNode = undefined;
            $scope.mode = undefined;
        };

        $scope.addChildDone = function () {
            /* add child */
            if( $scope.temporaryNode.id && $scope.temporaryNode.label ) {
                $scope.mytree.currentNode.children.push( angular.copy($scope.temporaryNode) );
            }

            /* reset */
            $scope.temporaryNode.id = "";
            $scope.temporaryNode.label = "";

            $scope.done();
        };

    });


