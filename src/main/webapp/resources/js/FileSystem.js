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

                console.log('Tree');
                console.log(response.data);
                console.log('Tree');
                $scope.roleList = response.data;

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
        /**
         * CREATE DIRECTORY from NAME to NAME
         * @type {{toDirectoryName: string, name: string}}
         */
        $scope.newDirectoryForm = {
            toDirectoryName: '',
            name: ''
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
                $scope.done();

            }, function errorCallBack(response) {  /* HTTP status error */

                init();
                console.log('Tree Above');

                $scope.done();
                $scope.done();

                alert("Process could not be completed. \n\n" +
                    "1. Re-try." +
                    "1. Or try adding to a different directory.\n\n" +
                    "2. Or delete the directory and re-create. \n\n"+

                    "Caused by angular.treeview component, failure to function in expected way.");
            });
        } ;

        $scope.deleteDirectoryForm = {
            name: ""
        };
        $scope.deleteDirectory = function(response){

            $scope.temporaryNode = angular.copy($scope.mytree.currentNode);

            $scope.deleteDirectoryForm.name = $scope.temporaryNode.label;

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
                $scope.done();

            }, function errorCallBack(response) {  /* HTTP status error */

                alert("Process could not be completed. \n\n" +
                    "1. Try deleting a different directory.\n\n" +

                    "Caused by treeview component, failure to function in expected way.");

            });

        };

        $scope.updateDirectoryForm = {
            toUpdateName: '',
            name: ''
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

                $scope.updateDirectoryForm = {
                    toUpdateName: '',
                    name: ''
                };
                $scope.done();
                $scope.done();

            }, function errorCallBack(response) {  /* HTTP status error */

                alert("Process could not be completed. \n\n" +
                    "1. Re-try.\n\n" +

                    "2. OR try updating a different directory.\n\n" +

                    "Caused by treeview component, failure to function in expected way.");

            });
        } ;

        $scope.moveDirectoryForm = {
            moveDirectoryName: '',
            toDirectoryName: ''
        };
        $scope.moveDirectory = function(response){

            $scope.temporaryNode = angular.copy($scope.mytree.currentNode);
            $scope.moveDirectoryForm.moveDirectoryName = $scope.temporaryNode.label;

            console.log($scope.moveDirectoryForm);

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

                $scope.moveDirectoryForm = {
                    moveDirectoryName: '',
                    toDirectoryName: ''
                };

                $scope.done();

            }, function errorCallBack(response) {  /* HTTP status error */
                alert("Could Not Move");
            });
        } ;

        $scope.done = function () {

            /* reset */
            $scope.mytree.currentNode.selected = {};
            $scope.mytree.currentNode = {};
            $scope.mode = {};
            $scope.temporaryNode = {};
            //$scope.directoTree

            $scope.temporaryNode = null;

            $scope.newDirectoryForm = {
                toDirectoryName: '',
                name: ''
            };
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


