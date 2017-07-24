(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('UsuarioMySuffixDeleteController',UsuarioMySuffixDeleteController);

    UsuarioMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Usuario'];

    function UsuarioMySuffixDeleteController($uibModalInstance, entity, Usuario) {
        var vm = this;

        vm.usuario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Usuario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
