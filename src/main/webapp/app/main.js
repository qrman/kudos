angular.module('KudosApp', [])
        .controller('KudosCtrl', function($scope) {
            $scope.kudos = {};
            $scope.variable = 42;
    
            $scope.giveKudos = function() {
                console.log($scope.kudos);
            }
        });
        