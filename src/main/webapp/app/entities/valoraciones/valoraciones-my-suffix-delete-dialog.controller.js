(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('ValoracionesMySuffixDeleteController',ValoracionesMySuffixDeleteController);

    ValoracionesMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Valoraciones'];

    function ValoracionesMySuffixDeleteController($uibModalInstance, entity, Valoraciones) {
        var vm = this;

        vm.valoraciones = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Valoraciones.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
