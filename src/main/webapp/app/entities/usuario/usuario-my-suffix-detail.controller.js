(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('UsuarioMySuffixDetailController', UsuarioMySuffixDetailController);

    UsuarioMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Usuario'];

    function UsuarioMySuffixDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Usuario) {
        var vm = this;

        vm.usuario = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('prueba3App:usuarioUpdate', function(event, result) {
            vm.usuario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
