(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('TapasMySuffixDetailController', TapasMySuffixDetailController);

    TapasMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tapas'];

    function TapasMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Tapas) {
        var vm = this;

        vm.tapas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prueba3App:tapasUpdate', function(event, result) {
            vm.tapas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
