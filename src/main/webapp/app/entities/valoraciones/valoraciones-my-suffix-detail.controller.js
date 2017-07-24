(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('ValoracionesMySuffixDetailController', ValoracionesMySuffixDetailController);

    ValoracionesMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Valoraciones'];

    function ValoracionesMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Valoraciones) {
        var vm = this;

        vm.valoraciones = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('prueba3App:valoracionesUpdate', function(event, result) {
            vm.valoraciones = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
