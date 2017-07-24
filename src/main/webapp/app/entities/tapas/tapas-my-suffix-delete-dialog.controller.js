(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('TapasMySuffixDeleteController',TapasMySuffixDeleteController);

    TapasMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tapas'];

    function TapasMySuffixDeleteController($uibModalInstance, entity, Tapas) {
        var vm = this;

        vm.tapas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tapas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
