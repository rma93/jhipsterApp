(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('RestauranteMySuffixDetailController', RestauranteMySuffixDetailController);

    RestauranteMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Restaurante'];

    function RestauranteMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Restaurante) {
        var vm = this;

        vm.restaurante = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prueba3App:restauranteUpdate', function(event, result) {
            vm.restaurante = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
